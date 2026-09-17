package com.luciana.miformacionctma.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.luciana.miformacionctma.data.ActividadRepository
import com.luciana.miformacionctma.data.AppDatabase
import com.luciana.miformacionctma.data.PreferenciasRepository
import com.luciana.miformacionctma.domain.ActividadFormativa
import com.luciana.miformacionctma.domain.Prioridad
import com.luciana.miformacionctma.domain.calcularDiasRestantes
import com.luciana.miformacionctma.ui.state.FormularioActividadUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface ListadoUiState {
    data object Cargando : ListadoUiState

    data object Vacio : ListadoUiState

    data class Contenido(
        val actividades: List<ActividadFormativa>
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

@OptIn(FlowPreview::class)
class ActividadViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database = AppDatabase.obtener(application)

    private val repository =
        ActividadRepository(database.actividadDao())

    private val preferencias =
        PreferenciasRepository(application)

    /*
     * ---------------------------------------------------------
     * BÚSQUEDA
     * ---------------------------------------------------------
     *
     * Se mantiene en memoria para que el TextField responda
     * inmediatamente mientras el usuario escribe.
     */
    private val _busqueda =
        MutableStateFlow("")

    val busqueda: StateFlow<String> =
        _busqueda.asStateFlow()

    /*
     * ---------------------------------------------------------
     * VISTA
     * ---------------------------------------------------------
     *
     * Lista o cuadrícula.
     * Esta preferencia sí se conserva en DataStore.
     */
    val vista: StateFlow<String> =
        preferencias.vista.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = "lista"
        )

    /*
     * ---------------------------------------------------------
     * LISTADO DE ACTIVIDADES
     * ---------------------------------------------------------
     *
     * La búsqueda espera 250 ms antes de consultar Room.
     * Esto evita hacer una consulta por cada tecla.
     */
    val estadoLista: StateFlow<ListadoUiState> =
        busqueda
            .debounce(250)
            .distinctUntilChanged()
            .flatMapLatest { texto ->

                repository
                    .observarActividades(texto)
                    .map<List<ActividadFormativa>, ListadoUiState> { actividades ->

                        if (actividades.isEmpty()) {
                            ListadoUiState.Vacio
                        } else {
                            ListadoUiState.Contenido(
                                actividades = actividades
                            )
                        }
                    }
                    .catch {
                        emit(
                            ListadoUiState.Error(
                                mensaje = "No se pudieron cargar las actividades."
                            )
                        )
                    }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ListadoUiState.Cargando
            )

    /*
     * Estado de operaciones: crear, editar y eliminar.
     */
    private val _operacion =
        MutableStateFlow<OperacionUiState>(
            OperacionUiState.Inactiva
        )

    val operacion: StateFlow<OperacionUiState> =
        _operacion.asStateFlow()

    /*
     * ---------------------------------------------------------
     * INICIALIZACIÓN
     * ---------------------------------------------------------
     */
    init {
        cargarBusquedaGuardada()
        cargarActividadesIniciales()
    }

    /*
     * Recupera la última búsqueda guardada.
     *
     * Se hace una sola vez al iniciar el ViewModel.
     */
    private fun cargarBusquedaGuardada() {

        viewModelScope.launch {

            try {

                val busquedaGuardada =
                    preferencias.busqueda.first()

                _busqueda.value =
                    busquedaGuardada

            } catch (e: CancellationException) {

                throw e

            } catch (_: Exception) {

                _busqueda.value = ""
            }
        }
    }

    /*
     * Si la base de datos está vacía,
     * carga las actividades iniciales.
     */
    private fun cargarActividadesIniciales() {

        viewModelScope.launch {

            if (!repository.hayActividades()) {

                repository.guardarTodas(
                    actividadesIniciales()
                )
            }
        }
    }

    /*
     * ---------------------------------------------------------
     * CAMBIAR BÚSQUEDA
     * ---------------------------------------------------------
     *
     * IMPORTANTE:
     *
     * Primero actualizamos _busqueda.
     * Esto hace que el TextField muestre inmediatamente
     * exactamente lo que el usuario está escribiendo.
     *
     * Después guardamos la preferencia.
     */
    fun cambiarBusqueda(texto: String) {

        _busqueda.value = texto

        viewModelScope.launch {

            try {

                preferencias.guardarBusqueda(texto)

            } catch (e: CancellationException) {

                throw e

            } catch (_: Exception) {

                // Si DataStore falla, la búsqueda
                // continúa funcionando normalmente.
            }
        }
    }

    /*
     * ---------------------------------------------------------
     * CAMBIAR VISTA
     * ---------------------------------------------------------
     */
    fun cambiarVista(nuevaVista: String) {

        viewModelScope.launch {

            preferencias.guardarVista(
                nuevaVista
            )
        }
    }

    /*
     * ---------------------------------------------------------
     * AGREGAR ACTIVIDAD
     * ---------------------------------------------------------
     */
    fun agregar(
        formulario: FormularioActividadUiState
    ) {

        ejecutarOperacion {

            val id =
                siguienteId()

            repository.guardar(

                ActividadFormativa(
                    id = id,
                    titulo = formulario.titulo,
                    descripcion = formulario.descripcion,
                    progreso =
                        formulario.progreso.toIntOrNull()
                            ?: 0,
                    diasRestantes =
                        calcularDiasRestantes(
                            formulario.fecha
                        ),
                    prioridad = formulario.prioridad,
                    fecha = formulario.fecha
                )
            )
        }
    }

    /*
     * ---------------------------------------------------------
     * EDITAR ACTIVIDAD
     * ---------------------------------------------------------
     */
    fun editar(
        id: Long,
        formulario: FormularioActividadUiState
    ) {

        ejecutarOperacion {

            val actividad =
                repository.buscarPorId(id)
                    ?: return@ejecutarOperacion

            repository.guardar(

                actividad.copy(
                    titulo = formulario.titulo,
                    descripcion = formulario.descripcion,
                    progreso =
                        formulario.progreso.toIntOrNull()
                            ?: 0,
                    prioridad = formulario.prioridad,
                    fecha = formulario.fecha,
                    diasRestantes =
                        calcularDiasRestantes(
                            formulario.fecha
                        )
                )
            )
        }
    }

    /*
     * ---------------------------------------------------------
     * ELIMINAR ACTIVIDAD
     * ---------------------------------------------------------
     */
    fun eliminar(id: Long) {

        ejecutarOperacion {

            repository.eliminar(id)
        }
    }

    /*
     * Fuerza nuevamente la búsqueda actual.
     */
    fun repetir() {

        val textoActual =
            _busqueda.value

        _busqueda.value = ""

        _busqueda.value =
            textoActual
    }

    /*
     * Limpia el mensaje de operación.
     */
    fun limpiarOperacion() {

        _operacion.value =
            OperacionUiState.Inactiva
    }

    /*
     * ---------------------------------------------------------
     * OPERACIONES DE BASE DE DATOS
     * ---------------------------------------------------------
     */
    private fun ejecutarOperacion(
        accion: suspend () -> Unit
    ) {

        viewModelScope.launch {

            _operacion.value =
                OperacionUiState.EnCurso

            try {

                accion()

                _operacion.value =
                    OperacionUiState.Exitosa

            } catch (e: CancellationException) {

                throw e

            } catch (_: Exception) {

                _operacion.value =
                    OperacionUiState.Fallida(
                        mensaje =
                            "No se pudo completar la operación."
                    )
            }
        }
    }

    /*
     * Busca el siguiente ID disponible.
     */
    private suspend fun siguienteId(): Long {

        var id = 1L

        while (
            repository.buscarPorId(id) != null
        ) {
            id++
        }

        return id
    }

    /*
     * Factory del ViewModel.
     */
    companion object {

        fun factory(
            application: Application
        ): ViewModelProvider.Factory =

            object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : androidx.lifecycle.ViewModel>
                        create(
                    modelClass: Class<T>
                ): T {

                    return ActividadViewModel(
                        application
                    ) as T
                }
            }
    }
}

/*
 * -------------------------------------------------------------
 * ACTIVIDADES INICIALES
 * -------------------------------------------------------------
 */
private fun actividadesIniciales():
        List<ActividadFormativa> =

    listOf(

        ActividadFormativa(
            1L,
            "Introducción a Android Studio",
            "Configurar el entorno de desarrollo.",
            100,
            0,
            Prioridad.ALTA
        ),

        ActividadFormativa(
            2L,
            "Fundamentos de Kotlin",
            "Repasar variables, funciones y clases.",
            80,
            2,
            Prioridad.ALTA
        ),

        ActividadFormativa(
            3L,
            "Jetpack Compose",
            "Construir interfaces utilizando Compose.",
            60,
            5,
            Prioridad.ALTA
        ),

        ActividadFormativa(
            4L,
            "Componentes Material 3",
            "Utilizar componentes visuales de Material 3.",
            40,
            7,
            Prioridad.MEDIA
        ),

        ActividadFormativa(
            5L,
            "Listas con LazyColumn",
            "Implementar listas eficientes en Compose.",
            20,
            3,
            Prioridad.MEDIA
        ),

        ActividadFormativa(
            6L,
            "Accesibilidad en aplicaciones móviles",
            "Aplicar buenas prácticas de accesibilidad.",
            0,
            10,
            Prioridad.MEDIA
        ),

        ActividadFormativa(
            7L,
            "Diseño adaptable",
            "Adaptar la interfaz a diferentes tamaños de pantalla.",
            30,
            8,
            Prioridad.BAJA
        ),

        ActividadFormativa(
            8L,
            "Pruebas de la interfaz",
            "Realizar pruebas de los componentes Compose.",
            0,
            4,
            Prioridad.ALTA
        ),

        ActividadFormativa(
            9L,
            "Documentación del proyecto",
            "Registrar las decisiones y funcionalidades implementadas.",
            50,
            6,
            Prioridad.BAJA
        ),

        ActividadFormativa(
            10L,
            "Entrega de la Semana 3",
            "Preparar el proyecto para la entrega.",
            10,
            1,
            Prioridad.ALTA
        )
    )