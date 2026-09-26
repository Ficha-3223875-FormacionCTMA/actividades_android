package com.luciana.miformacionctma.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.luciana.miformacionctma.data.ActividadRepository
import com.luciana.miformacionctma.data.AppDatabase
import com.luciana.miformacionctma.data.PreferenciasRepository
import com.luciana.miformacionctma.data.EvidenciaRepository
import com.luciana.miformacionctma.data.EvidenciaLocal
import kotlinx.coroutines.flow.flatMapLatest

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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.luciana.miformacionctma.data.api.NetworkError
import com.luciana.miformacionctma.data.api.NetworkException

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


sealed interface RefreshUiState {
    data object Idle : RefreshUiState
    data object Running : RefreshUiState
    data class Success(val cantidad: Int, val atMillis: Long) : RefreshUiState
    data class Failed(val mensaje: String) : RefreshUiState
}

data class EvidenciasUiState(
    val actividadId: Long = 0L,
    val evidencias: List<EvidenciaLocal> = emptyList(),
    val mensaje: String? = null
)

sealed interface OperacionUiState {
    data object Inactiva : OperacionUiState

    data object EnCurso : OperacionUiState

    data object Sincronizando : OperacionUiState

    data object Exitosa : OperacionUiState

    data class Sincronizada(val cantidad: Int) : OperacionUiState

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
        ActividadRepository(database.actividadDao(), database = database)

    private val preferencias =
        PreferenciasRepository(application)

    private val evidenciaRepository =
        EvidenciaRepository(
            context = application,
            dao = database.evidenciaDao()
        )

    private val _evidencias =
        MutableStateFlow(EvidenciasUiState())

    val evidencias: StateFlow<EvidenciasUiState> =
        _evidencias.asStateFlow()

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
    private var sincronizacionJob: Job? = null

    private val _operacion =
        MutableStateFlow<OperacionUiState>(
            OperacionUiState.Inactiva
        )

    val operacion: StateFlow<OperacionUiState> =
        _operacion.asStateFlow()

    private val _refresh = MutableStateFlow<RefreshUiState>(RefreshUiState.Idle)
    val refreshState: StateFlow<RefreshUiState> = _refresh.asStateFlow()

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
            repository.crearRemota(
                titulo = formulario.titulo,
                descripcion = formulario.descripcion
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
            val actividad = repository.buscarPorId(id)
                ?: return@ejecutarOperacion

            val estado = if (actividad.resuelto) "COMPLETADA" else "EN_PROCESO"

            repository.actualizarRemota(
                id = id,
                titulo = formulario.titulo,
                descripcion = formulario.descripcion,
                estado = estado
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
            repository.eliminarRemota(id)
        }
    }

    /**
     * Sincroniza actividades remotas y las persiste en Room.
     * La corrutina pertenece al viewModelScope para respetar el ciclo de vida.
     */
    fun sincronizar() {
        sincronizacionJob?.cancel()
        sincronizacionJob = viewModelScope.launch {
            _refresh.value = RefreshUiState.Running
            _operacion.value = OperacionUiState.Sincronizando

            try {
                val cantidad = repository.sincronizarDesdeApi()
                _refresh.value = RefreshUiState.Success(
                    cantidad = cantidad,
                    atMillis = System.currentTimeMillis()
                )
                _operacion.value = OperacionUiState.Sincronizada(cantidad)
            } catch (e: CancellationException) {
                _refresh.value = RefreshUiState.Failed(
                    "Sincronización cancelada."
                )
                _operacion.value = OperacionUiState.Inactiva
                throw e
            } catch (e: NetworkException) {
                val mensaje = when (e.error) {
                    NetworkError.NoConnection ->
                        "No se pudo conectar con la API."
                    NetworkError.Timeout ->
                        "La API tardó demasiado en responder."
                    NetworkError.Unauthorized ->
                        "La API rechazó la autorización."
                    NetworkError.NotFound ->
                        "El endpoint de actividades no fue encontrado."
                    NetworkError.InvalidPayload ->
                        "La API respondió con un formato no válido."
                    is NetworkError.Server ->
                        "La API devolvió un error del servidor."
                    is NetworkError.Unknown ->
                        "Ocurrió un error de red."
                }
                _refresh.value = RefreshUiState.Failed(mensaje)
                _operacion.value = OperacionUiState.Fallida(mensaje)
            } catch (e: Exception) {
                val mensaje = e.message ?: "No se pudo sincronizar."
                _refresh.value = RefreshUiState.Failed(mensaje)
                _operacion.value = OperacionUiState.Fallida(mensaje)
            } finally {
                sincronizacionJob = null
            }
        }
    }

    fun cargarEvidencias(actividadId: Long) {
        viewModelScope.launch {
            evidenciaRepository.observar(actividadId).collect { lista ->
                _evidencias.value = EvidenciasUiState(
                    actividadId = actividadId,
                    evidencias = lista.map {
                        EvidenciaLocal(
                            id = it.id,
                            actividadId = it.actividadId,
                            localUri = it.localUri,
                            mimeType = it.mimeType,
                            sizeBytes = it.sizeBytes,
                            estado = runCatching {
                                com.luciana.miformacionctma.data.EvidenciaEstado.valueOf(it.estado)
                            }.getOrDefault(com.luciana.miformacionctma.data.EvidenciaEstado.LOCAL)
                        )
                    }
                )
            }
        }
    }

    fun prepararUriCaptura(): Uri = evidenciaRepository.crearUriCaptura()

    fun cancelarCaptura(uri: Uri) {
        evidenciaRepository.eliminarArchivoTemporal(uri)
    }

    fun guardarEvidencia(actividadId: Long, uri: Uri) {
        viewModelScope.launch {
            val resultado = evidenciaRepository.guardarDesdeUri(actividadId, uri)
            resultado.exceptionOrNull()?.let {
                _evidencias.value = _evidencias.value.copy(
                    mensaje = it.message ?: "No se pudo guardar la evidencia."
                )
            }
        }
    }

    fun subirEvidencia(id: String) {
        viewModelScope.launch {
            val resultado = evidenciaRepository.subir(id)
            resultado.exceptionOrNull()?.let {
                _evidencias.value = _evidencias.value.copy(
                    mensaje = it.message ?: "No se pudo sincronizar la evidencia."
                )
            }
        }
    }

    fun eliminarEvidencia(id: String) {
        viewModelScope.launch {
            evidenciaRepository.eliminar(id)
        }
    }

    fun limpiarMensajeEvidencia() {
        _evidencias.value = _evidencias.value.copy(mensaje = null)
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