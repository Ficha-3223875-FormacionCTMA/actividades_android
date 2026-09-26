package com.luciana.miformacionctma.data

import com.luciana.miformacionctma.data.api.ActividadApiService
import com.luciana.miformacionctma.data.api.ActividadDto
import kotlinx.serialization.json.JsonPrimitive
import retrofit2.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ActividadRepositoryTest {

    @Test
    fun sincronizarDesdeApi_guardaActividadesRemotasSinColisionar() = runTest {
        val dao = FakeActividadDao()
        val api = FakeActividadApiService(
            listOf(
                ActividadDto(
                    id = JsonPrimitive("a-1"),
                    titulo = "Actividad remota 1",
                    descripcion = "Remota",
                    progreso = 0,
                    prioridad = "MEDIA",
                    competenciaId = "ADSO-01",
                    fechaLimite = "30/09/2026",
                    completada = false,
                    actualizadoEn = "2026-09-23T10:00:00Z"
                ),
                ActividadDto(
                    id = JsonPrimitive("a-2"),
                    titulo = "Actividad remota 2",
                    descripcion = "Remota",
                    progreso = 100,
                    prioridad = "ALTA",
                    competenciaId = "ADSO-02",
                    fechaLimite = "30/09/2026",
                    completada = true,
                    actualizadoEn = "2026-09-23T10:00:00Z"
                )
            )
        )
        val repository = ActividadRepository(dao, api)

        dao.insertar(
            ActividadEntity(
                id = 1L,
                titulo = "Actividad local",
                descripcion = null,
                progreso = 50,
                diasRestantes = 5,
                prioridad = com.luciana.miformacionctma.domain.Prioridad.MEDIA,
                fecha = "2026-09-20"
            )
        )

        val cantidad = repository.sincronizarDesdeApi()

        assertEquals(2, cantidad)
        assertEquals("Actividad local", dao.buscarPorId(1L)?.titulo)
        assertEquals("Actividad remota 1", dao.buscarPorId(actividadRemotaId("a-1"))?.titulo)
        assertEquals("Actividad remota 2", dao.buscarPorId(actividadRemotaId("a-2"))?.titulo)
        assertEquals(100, dao.buscarPorId(actividadRemotaId("a-2"))?.progreso)
    }

    private fun actividadRemotaId(raw: String): Long {
        val hash = raw.hashCode().toLong()
        return -(if (hash == Long.MIN_VALUE) 1L else kotlin.math.abs(hash))
    }

    private class FakeActividadApiService(
        private val respuesta: List<ActividadDto>
    ) : ActividadApiService {
        override suspend fun obtenerActividades(limite: Int): Response<List<ActividadDto>> =
            Response.success(respuesta.take(limite))
    }

    private class FakeActividadDao : ActividadDao {
        private val datos = LinkedHashMap<Long, ActividadEntity>()
        private val flujo = MutableStateFlow<List<ActividadEntity>>(emptyList())

        private fun publicar() {
            flujo.value = datos.values.sortedBy { it.id }
        }

        override fun observarTodas(): Flow<List<ActividadEntity>> = flujo

        override fun buscarPorTitulo(texto: String): Flow<List<ActividadEntity>> =
            MutableStateFlow(
                datos.values.filter { it.titulo.contains(texto, ignoreCase = true) }
            )

        override suspend fun buscarPorId(id: Long): ActividadEntity? = datos[id]

        override suspend fun insertar(actividad: ActividadEntity) {
            datos[actividad.id] = actividad
            publicar()
        }

        override suspend fun insertarTodas(actividades: List<ActividadEntity>) {
            actividades.forEach { datos[it.id] = it }
            publicar()
        }

        override suspend fun actualizar(actividad: ActividadEntity) {
            datos[actividad.id] = actividad
            publicar()
        }

        override suspend fun eliminar(actividad: ActividadEntity) {
            datos.remove(actividad.id)
            publicar()
        }

        override suspend fun eliminarPorId(id: Long) {
            datos.remove(id)
            publicar()
        }

        override suspend fun contar(): Int = datos.size
    }
}

class ActividadRepositoryCacheTest {
    @org.junit.Test
    fun timeout_noBorraCacheExistente() = kotlinx.coroutines.test.runTest {
        val dao = CacheFakeDao()
        dao.insertar(
            ActividadEntity(
                id = 1L,
                titulo = "Cache válida",
                descripcion = "Local",
                progreso = 40,
                diasRestantes = 3,
                prioridad = com.luciana.miformacionctma.domain.Prioridad.MEDIA,
                fecha = "30/09/2026"
            )
        )
        val api = object : ActividadApiService {
            override suspend fun obtenerActividades(limite: Int): retrofit2.Response<List<ActividadDto>> =
                throw NetworkException(NetworkError.Timeout)
            override suspend fun obtenerActividad(id: String): retrofit2.Response<ActividadDto> =
                throw NetworkException(NetworkError.Timeout)
        }
        val repository = ActividadRepository(dao, api)

        try {
            repository.sincronizarDesdeApi()
            throw AssertionError("Se esperaba NetworkException")
        } catch (e: NetworkException) {
            org.junit.Assert.assertTrue(e.error is NetworkError.Timeout)
        }

        org.junit.Assert.assertEquals("Cache válida", dao.buscarPorId(1L)?.titulo)
    }

    private class CacheFakeDao : ActividadDao {
        private val data = linkedMapOf<Long, ActividadEntity>()
        private val flow = kotlinx.coroutines.flow.MutableStateFlow<List<ActividadEntity>>(emptyList())
        override fun observarTodas() = flow
        override fun buscarPorTitulo(texto: String) = kotlinx.coroutines.flow.MutableStateFlow(data.values.filter { it.titulo.contains(texto, true) })
        override suspend fun buscarPorId(id: Long) = data[id]
        override suspend fun insertar(actividad: ActividadEntity) { data[actividad.id] = actividad; flow.value = data.values.toList() }
        override suspend fun insertarTodas(actividades: List<ActividadEntity>) { actividades.forEach { data[it.id] = it }; flow.value = data.values.toList() }
        override suspend fun actualizar(actividad: ActividadEntity) { data[actividad.id] = actividad }
        override suspend fun eliminar(actividad: ActividadEntity) { data.remove(actividad.id) }
        override suspend fun eliminarPorId(id: Long) { data.remove(id) }
        override suspend fun contar() = data.size
    }
}
