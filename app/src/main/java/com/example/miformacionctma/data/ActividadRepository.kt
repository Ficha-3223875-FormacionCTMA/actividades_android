package com.example.miformacionctma.data

import com.example.miformacionctma.data.local.dao.ActividadDao
import com.example.miformacionctma.data.local.mapper.toActividad
import com.example.miformacionctma.data.local.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ActividadRepository(
    private val api: ApiService,
    private val dao: ActividadDao
) {

    fun observarActividadesLocales(): Flow<List<Actividad>> {
        return dao.observarActividades()
            .map { lista ->
                lista.map { it.toActividad() }
            }
    }

    fun buscarActividadesLocales(
        texto: String
    ): Flow<List<Actividad>> {
        return dao.buscar(texto)
            .map { lista ->
                lista.map { it.toActividad() }
            }
    }

    suspend fun obtenerActividadLocal(
        id: Long
    ): Actividad? {
        return dao.obtenerPorId(id)?.toActividad()
    }

    suspend fun guardarLocal(
        actividad: Actividad
    ) {
        val resultado = dao.guardar(
            actividad.toEntity()
        )

        println("ROOM GUARDÓ ACTIVIDAD. ID: $resultado")
    }

    suspend fun guardarTodasLocales(
        actividades: List<Actividad>
    ) {
        dao.guardarTodas(
            actividades.map {
                it.toEntity()
            }
        )

        println(
            "ROOM GUARDÓ ${actividades.size} ACTIVIDADES"
        )
    }

    suspend fun actualizarLocal(
        actividad: Actividad
    ) {
        dao.guardar(
            actividad.toEntity()
        )

        println(
            "ROOM ACTUALIZÓ ACTIVIDAD: ${actividad.id}"
        )
    }

    suspend fun eliminarLocal(
        actividad: Actividad
    ) {
        dao.eliminarPorId(
            actividad.id
                .removePrefix("ACT-")
                .toLongOrNull()
                ?: 0L
        )
    }

    suspend fun eliminarTodasLocales() {
        dao.eliminarTodas()
    }

    suspend fun sincronizarDesdeApi(
        token: String
    ): List<Actividad> {

        val respuesta =
            api.obtenerActividades(token)

        guardarTodasLocales(
            respuesta.actividades
        )

        return respuesta.actividades
    }

    suspend fun crearActividad(
        token: String,
        actividad: Actividad
    ): Actividad {

        return try {

            // Primero se intenta crear la actividad en FastAPI
            val creada = api.crearActividad(
                token = token,
                actividad = actividad
            )

            // Se guarda una sola vez la actividad
            // que devuelve FastAPI
            guardarLocal(creada)

            println(
                "ACTIVIDAD CREADA EN FASTAPI Y ROOM: ${creada.id}"
            )

            creada

        } catch (e: Exception) {

            // Si FastAPI no está disponible,
            // se conserva la actividad en Room
            guardarLocal(actividad)

            println(
                "FASTAPI NO DISPONIBLE. " +
                        "ACTIVIDAD GUARDADA EN ROOM: " +
                        e.message
            )

            actividad
        }
    }
}