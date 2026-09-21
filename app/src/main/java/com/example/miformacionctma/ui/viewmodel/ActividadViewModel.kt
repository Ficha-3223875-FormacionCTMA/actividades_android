package com.example.miformacionctma.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miformacionctma.data.Actividad
import com.example.miformacionctma.data.ActividadRepository
import com.example.miformacionctma.data.AuthManager
import com.example.miformacionctma.data.PreferenciasDataSource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface ListadoUiState {

    data object Cargando : ListadoUiState

    data object Vacio : ListadoUiState

    data class Contenido(
        val actividades: List<Actividad>
    ) : ListadoUiState

    data class Error(
        val mensaje: String
    ) : ListadoUiState
}

sealed interface OperacionUiState {

    data object Inactiva : OperacionUiState

    data object EnCurso : OperacionUiState

    data object Exitosa : OperacionUiState

    data class Fallida(
        val mensaje: String
    ) : OperacionUiState
}

class ActividadViewModel(
    private val repository: ActividadRepository,
    private val preferencesRepository: PreferenciasDataSource
) : ViewModel() {

    // =========================================
    // TEXTO DE BÚSQUEDA
    // =========================================

    private val textoBusqueda =
        MutableStateFlow("")

    // =========================================
    // ORDEN GUARDADO EN DATASTORE
    // =========================================

    private val ordenDescendente =
        preferencesRepository
            .observarOrdenDescendente()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(
                    5_000
                ),
                initialValue = true
            )

    // =========================================
    // ERROR DE LISTADO
    // =========================================

    private val _errorListado =
        MutableStateFlow<String?>(null)

    // =========================================
    // ACTIVIDADES FILTRADAS
    // =========================================
    //
    // mapLatest permite cancelar el procesamiento
    // anterior cuando llega una búsqueda nueva.
    //
    // =========================================

    private val actividadesFiltradas: Flow<List<Actividad>> =
        combine(
            repository.observarActividadesLocales(),
            textoBusqueda
                .debounce(300)
                .distinctUntilChanged()
        ) { actividades, texto ->

            Pair(
                actividades,
                texto
            )

        }.mapLatest { (actividades, texto) ->

            if (texto.isBlank()) {

                actividades

            } else {

                actividades.filter { actividad ->

                    actividad.titulo.contains(
                        texto,
                        ignoreCase = true
                    ) ||
                            actividad.descripcion.contains(
                                texto,
                                ignoreCase = true
                            )
                }
            }
        }

    // =========================================
    // ESTADO PRINCIPAL DEL LISTADO
    // =========================================

    val listadoUiState:
            StateFlow<ListadoUiState> =

        combine(
            repository.observarActividadesLocales(),
            actividadesFiltradas,
            textoBusqueda
                .debounce(300)
                .distinctUntilChanged(),
            ordenDescendente,
            _errorListado
        ) {
                actividadesOriginales,
                filtradas,
                texto,
                descendente,
                error ->

            // =================================
            // ERROR
            // =================================

            if (error != null) {

                ListadoUiState.Error(
                    error
                )

            }

            // =================================
            // BASE DE DATOS REALMENTE VACÍA
            // =================================

            else if (
                actividadesOriginales.isEmpty() &&
                texto.isBlank()
            ) {

                ListadoUiState.Vacio
            }

            // =================================
            // HAY ACTIVIDADES
            //
            // Aunque la búsqueda no encuentre
            // coincidencias, seguimos enviando
            // Contenido.
            //
            // Esto permite que la pantalla
            // principal siga mostrando el
            // buscador.
            // =================================

            else {

                val ordenadas =

                    if (descendente) {

                        filtradas

                    } else {

                        filtradas.reversed()
                    }

                ListadoUiState.Contenido(
                    ordenadas
                )
            }

        }.stateIn(
            scope = viewModelScope,

            started =
                SharingStarted.WhileSubscribed(
                    5_000
                ),

            initialValue =
                ListadoUiState.Cargando
        )

    // =========================================
    // ESTADO DE OPERACIÓN
    // =========================================

    private val _operacionUiState =
        MutableStateFlow<OperacionUiState>(
            OperacionUiState.Inactiva
        )

    val operacionUiState:
            StateFlow<OperacionUiState> =
        _operacionUiState

    // =========================================
    // REINICIAR ESTADO DE OPERACIÓN
    // =========================================

    fun reiniciarEstadoOperacion() {

        _operacionUiState.value =
            OperacionUiState.Inactiva
    }

    // =========================================
    // CAMBIAR BÚSQUEDA
    // =========================================

    fun cambiarBusqueda(
        texto: String
    ) {

        textoBusqueda.value = texto
    }

    // =========================================
    // CAMBIAR ORDEN
    // =========================================

    fun cambiarOrden() {

        viewModelScope.launch {

            val nuevoOrden =
                !ordenDescendente.value

            try {

                preferencesRepository
                    .guardarOrdenDescendente(
                        nuevoOrden
                    )

            } catch (
                e: CancellationException
            ) {

                throw e

            } catch (
                e: Exception
            ) {

                _operacionUiState.value =
                    OperacionUiState.Fallida(
                        e.message
                            ?: "No se pudo guardar el orden"
                    )
            }
        }
    }

    // =========================================
    // OBTENER TEXTO DE BÚSQUEDA
    // =========================================

    fun obtenerTextoBusqueda():
            StateFlow<String> {

        return textoBusqueda
    }

    // =========================================
    // OBTENER ORDEN
    // =========================================

    fun obtenerOrdenDescendente():
            StateFlow<Boolean> {

        return ordenDescendente
    }

    // =========================================
    // SINCRONIZAR CON FASTAPI
    // =========================================

    fun sincronizar() {

        viewModelScope.launch {

            _errorListado.value = null

            _operacionUiState.value =
                OperacionUiState.EnCurso

            try {

                repository.sincronizarDesdeApi(
                    token = AuthManager.token
                )

                _operacionUiState.value =
                    OperacionUiState.Exitosa

            } catch (
                e: CancellationException
            ) {

                throw e

            } catch (
                e: Exception
            ) {

                val mensaje =
                    e.message
                        ?: "No se pudieron cargar las actividades"

                _errorListado.value =
                    mensaje

                _operacionUiState.value =
                    OperacionUiState.Fallida(
                        mensaje
                    )
            }
        }
    }

    // =========================================
    // CREAR ACTIVIDAD
    // =========================================

    fun crearActividad(
        titulo: String,
        descripcion: String,
        onCompletado: () -> Unit
    ) {

        viewModelScope.launch {

            _operacionUiState.value =
                OperacionUiState.EnCurso

            try {

                val actividad =
                    Actividad(
                        titulo = titulo,
                        descripcion = descripcion,
                        aprendiz = "APR-01",
                        estado = "PENDIENTE",
                        createdAt = ""
                    )

                repository.crearActividad(
                    token = AuthManager.token,
                    actividad = actividad
                )

                _operacionUiState.value =
                    OperacionUiState.Exitosa

                onCompletado()

            } catch (
                e: CancellationException
            ) {

                throw e

            } catch (
                e: Exception
            ) {

                _operacionUiState.value =
                    OperacionUiState.Fallida(
                        e.message
                            ?: "No se pudo guardar la actividad"
                    )
            }
        }
    }

    // =========================================
    // ACTUALIZAR ACTIVIDAD
    // =========================================

    fun actualizarActividad(
        actividad: Actividad
    ) {

        viewModelScope.launch {

            _operacionUiState.value =
                OperacionUiState.EnCurso

            try {

                repository.actualizarLocal(
                    actividad
                )

                _operacionUiState.value =
                    OperacionUiState.Exitosa

            } catch (
                e: CancellationException
            ) {

                throw e

            } catch (
                e: Exception
            ) {

                _operacionUiState.value =
                    OperacionUiState.Fallida(
                        e.message
                            ?: "No se pudo actualizar"
                    )
            }
        }
    }

    // =========================================
    // ELIMINAR ACTIVIDAD
    // =========================================

    fun eliminarActividad(
        actividad: Actividad
    ) {

        viewModelScope.launch {

            _operacionUiState.value =
                OperacionUiState.EnCurso

            try {

                repository.eliminarLocal(
                    actividad
                )

                _operacionUiState.value =
                    OperacionUiState.Exitosa

            } catch (
                e: CancellationException
            ) {

                throw e

            } catch (
                e: Exception
            ) {

                _operacionUiState.value =
                    OperacionUiState.Fallida(
                        e.message
                            ?: "No se pudo eliminar"
                    )
            }
        }
    }
}