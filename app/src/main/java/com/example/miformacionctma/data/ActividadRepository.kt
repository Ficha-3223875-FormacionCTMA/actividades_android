package com.example.miformacionctma.data

import com.example.miformacionctma.data.local.dao.ActividadDao
import com.example.miformacionctma.data.local.mapper.toActividad
import com.example.miformacionctma.data.local.mapper.toEntity
import com.example.miformacionctma.data.remote.CrearActividadDto
import com.example.miformacionctma.data.remote.RemoteActividadDataSource
import com.example.miformacionctma.data.remote.toDomain
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ActividadRepository(
    private val api: ApiService,
    private val dao: ActividadDao
) {

    private val remote = RemoteActividadDataSource(api)

    fun observarActividadesLocales(): Flow<List<Actividad>> {
        return dao.observarActividades()
            .map { lista ->
                lista.map { it.toActividad() }
            }
    }

    fun buscarActividadesLocales(texto: String): Flow<List<Actividad>> {
        return dao.buscar(texto)
            .map { lista ->
                lista.map { it.toActividad() }
            }
    }

    suspend fun obtenerActividadLocal(id: Long): Actividad? {
        return dao.obtenerPorId(id)?.toActividad()
    }

    suspend fun guardarLocal(actividad: Actividad) {
        dao.guardar(actividad.toEntity())
    }

    suspend fun guardarTodasLocales(actividades: List<Actividad>) {
        dao.guardarTodas(
            actividades.map { it.toEntity() }
        )
    }

    suspend fun actualizarLocal(actividad: Actividad) {
        dao.guardar(actividad.toEntity())
    }

    suspend fun eliminarLocal(actividad: Actividad) {
        dao.eliminarPorId(
            actividad.id
                .removePrefix("ACT-")
                .toLongOrNull() ?: 0L
        )
    }

    suspend fun eliminarTodasLocales() {
        dao.eliminarTodas()
    }

    suspend fun sincronizarDesdeApi(): List<Actividad> {
        return try {
            val actividadesDto = remote.obtenerActividades()

            val actividades = actividadesDto.map {
                it.toDomain()
            }

            guardarTodasLocales(actividades)

            actividades
        } catch (e: CancellationException) {
            throw e
        }
    }

    suspend fun crearActividad(
        actividad: Actividad
    ): Actividad {
        return try {

            val solicitud = CrearActividadDto(
                titulo = actividad.titulo,
                descripcion = actividad.descripcion,
                aprendiz = actividad.aprendiz
            )

            val creadaDto = remote.crearActividad(
                solicitud
            )

            val creada = creadaDto.toDomain()

            guardarLocal(creada)

            creada

        } catch (e: CancellationException) {
            throw e
        }
    }
}