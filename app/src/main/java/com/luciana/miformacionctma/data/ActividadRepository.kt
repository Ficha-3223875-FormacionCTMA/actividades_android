package com.luciana.miformacionctma.data

import com.luciana.miformacionctma.domain.ActividadFormativa
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ActividadRepository(
    private val dao: ActividadDao
) {

    fun observarActividades(texto: String): Flow<List<ActividadFormativa>> {
        val flujo = if (texto.isBlank()) {
            dao.observarTodas()
        } else {
            dao.buscarPorTitulo(texto.trim())
        }

        return flujo.map { lista ->
            lista.map { it.toDomain() }
        }
    }

    suspend fun buscarPorId(id: Long): ActividadFormativa? =
        dao.buscarPorId(id)?.toDomain()

    suspend fun guardar(actividad: ActividadFormativa) {
        val anterior = dao.buscarPorId(actividad.id)
        dao.insertar(actividad.toEntity(anterior?.resuelto ?: actividad.resuelto))
    }

    suspend fun guardarTodas(actividades: List<ActividadFormativa>) {
        dao.insertarTodas(actividades.map { it.toEntity() })
    }

    suspend fun eliminar(id: Long) {
        dao.eliminarPorId(id)
    }

    suspend fun hayActividades(): Boolean = dao.contar() > 0
}
