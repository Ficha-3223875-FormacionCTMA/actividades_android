package com.example.miformacionctma

import com.example.miformacionctma.data.Actividad
import com.example.miformacionctma.data.ActividadRepository
import com.example.miformacionctma.data.ActividadesResponse
import com.example.miformacionctma.data.ApiService
import com.example.miformacionctma.data.PreferenciasDataSource
import com.example.miformacionctma.data.local.dao.ActividadDao
import com.example.miformacionctma.data.local.entity.ActividadEntity
import com.example.miformacionctma.ui.viewmodel.ActividadViewModel
import com.example.miformacionctma.ui.viewmodel.ListadoUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(
    ExperimentalCoroutinesApi::class,
    FlowPreview::class
)
class ActividadViewModelTest {

    private val dispatcher =
        StandardTestDispatcher()

    private lateinit var dao: FakeActividadDao

    private lateinit var repository: ActividadRepository

    private lateinit var preferences: FakePreferenciasDataSource

    @Before
    fun configurar() {

        Dispatchers.setMain(dispatcher)

        dao = FakeActividadDao()

        repository = ActividadRepository(
            api = FakeApiService(),
            dao = dao
        )

        preferences = FakePreferenciasDataSource()
    }

    @After
    fun limpiar() {
        Dispatchers.resetMain()
    }

    @Test
    fun ca01_sinActividades_muestraVacio() =
        runTest {

            val viewModel =
                ActividadViewModel(
                    repository = repository,
                    preferencesRepository = preferences
                )

            val coleccion =
                launch {
                    viewModel.listadoUiState.collect { }
                }

            advanceUntilIdle()

            assertEquals(
                ListadoUiState.Vacio,
                viewModel.listadoUiState.value
            )

            coleccion.cancel()
        }

    @Test
    fun ca02_roomActualizaElContenidoAutomaticamente() =
        runTest {

            val viewModel =
                ActividadViewModel(
                    repository = repository,
                    preferencesRepository = preferences
                )

            val coleccion =
                launch {
                    viewModel.listadoUiState.collect { }
                }

            advanceUntilIdle()

            val actividad =
                ActividadEntity(
                    id = 1,
                    titulo = "Actividad Flow",
                    descripcion = "Prueba reactiva",
                    aprendiz = "APR-01",
                    estado = "PENDIENTE",
                    createdAt = ""
                )

            dao.agregar(actividad)

            advanceUntilIdle()

            val estado =
                viewModel.listadoUiState.value

            assertTrue(
                estado is ListadoUiState.Contenido
            )

            val contenido =
                estado as ListadoUiState.Contenido

            assertEquals(
                1,
                contenido.actividades.size
            )

            assertEquals(
                "Actividad Flow",
                contenido.actividades.first().titulo
            )

            coleccion.cancel()
        }

    @Test
    fun ca04_busquedaSinCoincidencias_mantieneContenido() =
        runTest {

            dao.agregar(
                ActividadEntity(
                    id = 1,
                    titulo = "Android",
                    descripcion = "Compose",
                    aprendiz = "APR-01",
                    estado = "PENDIENTE",
                    createdAt = ""
                )
            )

            val viewModel =
                ActividadViewModel(
                    repository = repository,
                    preferencesRepository = preferences
                )

            val coleccion =
                launch {
                    viewModel.listadoUiState.collect { }
                }

            advanceUntilIdle()

            viewModel.cambiarBusqueda("aaaa")

            advanceTimeBy(400)
            advanceUntilIdle()

            val estado =
                viewModel.listadoUiState.value

            assertTrue(
                estado is ListadoUiState.Contenido
            )

            val contenido =
                estado as ListadoUiState.Contenido

            assertTrue(
                contenido.actividades.isEmpty()
            )

            coleccion.cancel()
        }

    @Test
    fun busquedaEncuentraActividad() =
        runTest {

            dao.agregar(
                ActividadEntity(
                    id = 1,
                    titulo = "Jetpack Compose",
                    descripcion = "Interfaz Android",
                    aprendiz = "APR-01",
                    estado = "PENDIENTE",
                    createdAt = ""
                )
            )

            dao.agregar(
                ActividadEntity(
                    id = 2,
                    titulo = "Kotlin",
                    descripcion = "Programación",
                    aprendiz = "APR-01",
                    estado = "PENDIENTE",
                    createdAt = ""
                )
            )

            val viewModel =
                ActividadViewModel(
                    repository = repository,
                    preferencesRepository = preferences
                )

            val coleccion =
                launch {
                    viewModel.listadoUiState.collect { }
                }

            advanceUntilIdle()

            viewModel.cambiarBusqueda("Compose")

            advanceTimeBy(400)
            advanceUntilIdle()

            val estado =
                viewModel.listadoUiState.value

            assertTrue(
                estado is ListadoUiState.Contenido
            )

            val contenido =
                estado as ListadoUiState.Contenido

            assertEquals(
                1,
                contenido.actividades.size
            )

            assertEquals(
                "Jetpack Compose",
                contenido.actividades.first().titulo
            )

            coleccion.cancel()
        }
}

/**
 * Implementación falsa de preferencias.
 *
 * No utiliza Android ni DataStore.
 */
private class FakePreferenciasDataSource :
    PreferenciasDataSource {

    private val orden =
        MutableStateFlow(true)

    override fun observarOrdenDescendente():
            Flow<Boolean> {

        return orden
    }

    override suspend fun guardarOrdenDescendente(
        descendente: Boolean
    ) {

        orden.value = descendente
    }
}

/**
 * DAO falso utilizado únicamente
 * durante las pruebas unitarias.
 */
private class FakeActividadDao :
    ActividadDao {

    private val actividades =
        MutableStateFlow<List<ActividadEntity>>(
            emptyList()
        )

    override fun observarActividades():
            Flow<List<ActividadEntity>> {

        return actividades
    }

    override suspend fun obtenerPorId(
        id: Long
    ): ActividadEntity? {

        return actividades.value
            .firstOrNull {
                it.id == id
            }
    }

    override fun buscar(
        texto: String
    ): Flow<List<ActividadEntity>> {

        return actividades
    }

    override suspend fun guardar(
        actividad: ActividadEntity
    ): Long {

        val id =
            if (actividad.id == 0L) {

                (actividades.value
                    .maxOfOrNull { it.id }
                    ?: 0L) + 1L

            } else {

                actividad.id
            }

        val nueva =
            actividad.copy(
                id = id
            )

        actividades.value =
            actividades.value
                .filterNot {
                    it.id == id
                } +
                    nueva

        return id
    }

    override suspend fun guardarTodas(
        actividades: List<ActividadEntity>
    ) {

        this.actividades.value =
            actividades
    }

    override suspend fun eliminarTodas() {

        actividades.value =
            emptyList()
    }

    override suspend fun eliminarPorId(
        id: Long
    ) {

        actividades.value =
            actividades.value.filterNot {
                it.id == id
            }
    }

    fun agregar(
        actividad: ActividadEntity
    ) {

        actividades.value =
            actividades.value + actividad
    }
}

/**
 * API falsa.
 *
 * No realiza llamadas de red.
 */
private class FakeApiService :
    ApiService {

    override suspend fun obtenerActividades(
        token: String
    ): ActividadesResponse {

        return ActividadesResponse(
            total = 0,
            actividades = emptyList()
        )
    }

    override suspend fun crearActividad(
        token: String,
        actividad: Actividad
    ): Actividad {

        return actividad
    }
}