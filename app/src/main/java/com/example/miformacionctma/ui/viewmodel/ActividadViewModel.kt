package com.example.miformacionctma.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miformacionctma.data.Actividad
import com.example.miformacionctma.data.ActividadRepository
import com.example.miformacionctma.data.PreferenciasDataSource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
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

    private val textoBusqueda =
        MutableStateFlow("")

    private val ordenDescendente =
        preferencesRepository
            .observarOrdenDescendente()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = true
            )

    private val _errorListado =
        MutableStateFlow<String?>(null)

    private val actividadesFiltradas: Flow<List<Actividad>> =
        combine(
            repository.observarActividadesLocales(),
            textoBusqueda
                .debounce(300)
                .distinctUntilChanged()
        ) { actividades, texto ->
            Pair(actividades, texto)
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

            if (error != null) {

                /*
                 * Si ya existen actividades en Room,
                 * seguimos mostrando los datos locales
                 * aunque falle FastAPI.
                 */
                if (actividadesOriginales.isNotEmpty()) {

                    val ordenadas =
                        if (descendente) {
                            filtradas
                        } else {
                            filtradas.reversed()
                        }

                    ListadoUiState.Contenido(
                        ordenadas
                    )

                } else {

                    ListadoUiState.Error(error)
                }

            } else if (
                actividadesOriginales.isEmpty() &&
                texto.isBlank()
            ) {

                ListadoUiState.Vacio

            } else {

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
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ListadoUiState.Cargando
        )

    private val _operacionUiState =
        MutableStateFlow<OperacionUiState>(
            OperacionUiState.Inactiva
        )

    val operacionUiState:
            StateFlow<OperacionUiState> =
        _operacionUiState

    fun reiniciarEstadoOperacion() {

        _operacionUiState.value =
            OperacionUiState.Inactiva
    }

    fun cambiarBusqueda(
        texto: String
    ) {

        textoBusqueda.value = texto
    }

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

    fun obtenerTextoBusqueda():
            StateFlow<String> {

        return textoBusqueda
    }

    fun obtenerOrdenDescendente():
            StateFlow<Boolean> {

        return ordenDescendente
    }

    // =========================================
    // ACTIVIDAD LOCAL DESDE ROOM
    // =========================================

    fun observarActividadLocal(
        id: Long
    ): Flow<Actividad?> {

        return repository
            .observarActividadesLocales()
            .map { actividades ->

                actividades.firstOrNull { actividad ->

                    actividad.id
                        .removePrefix("ACT-")
                        .toLongOrNull() == id
                }
            }
    }

    // =========================================
    // SINCRONIZACIÓN CON FASTAPI
    // =========================================

    fun sincronizar() {

        viewModelScope.launch {

            _errorListado.value = null

            _operacionUiState.value =
                OperacionUiState.EnCurso

            try {

                repository.sincronizarDesdeApi(
                    token = "token-APR-01"
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
                    actividad = actividad,
                    token = "token-APR-01"
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
                        "No se pudo crear la actividad"
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