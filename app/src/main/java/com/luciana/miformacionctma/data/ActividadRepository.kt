package com.luciana.miformacionctma.data

import androidx.room3.withWriteTransaction
import com.luciana.miformacionctma.data.api.ActividadCreateDto
import com.luciana.miformacionctma.data.api.ActividadRemoteDataSource
import com.luciana.miformacionctma.data.api.ActividadUpdateDto
import com.luciana.miformacionctma.data.api.RetrofitClient
import com.luciana.miformacionctma.data.api.toDomain
import com.luciana.miformacionctma.domain.ActividadFormativa
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ActividadRepository(
    private val dao: ActividadDao,
    private val database: AppDatabase
) {

    private val remote =
        ActividadRemoteDataSource(RetrofitClient.actividadApi)

    fun observarActividades(
        texto: String
    ): Flow<List<ActividadFormativa>> {

        val origen = if (texto.isBlank()) {
            dao.observarTodas()
        } else {
            dao.buscarPorTitulo(texto.trim())
        }

        return origen.map { lista ->
            lista.map { it.toDomain() }
        }
    }

    suspend fun buscarPorId(
        id: Long
    ): ActividadFormativa? {
        return dao.buscarPorId(id)?.toDomain()
    }

    suspend fun guardar(
        actividad: ActividadFormativa
    ) {
        dao.insertar(
            actividad.toEntity()
        )
    }

    suspend fun guardarTodas(
        actividades: List<ActividadFormativa>
    ) {

        database.withWriteTransaction {

            dao.insertarTodas(
                actividades.map {
                    it.toEntity()
                }
            )
        }
    }

    suspend fun eliminar(
        id: Long
    ) {
        dao.eliminarPorId(id)
    }

    suspend fun hayActividades(): Boolean {
        return dao.contar() > 0
    }

    suspend fun sincronizarDesdeApi(
        token: String = "token-APR-01"
    ): Int {

        val remotas = remote.ejecutarSeguro {
            remote.obtenerActividades(token)
        }

        // La respuesta de la API se convierte
        // al modelo de dominio.
        val actividades = remotas.map {
            it.toDomain()
        }

        // Solo actualizamos Room si la API
        // devolvió actividades.
        if (actividades.isNotEmpty()) {
            guardarTodas(actividades)
        }

        return actividades.size
    }

    suspend fun crearRemota(
        titulo: String,
        descripcion: String,
        token: String = "token-APR-01"
    ): ActividadFormativa {

        val creada = remote.ejecutarSeguro {

            remote.crearActividad(
                ActividadCreateDto(
                    titulo = titulo,
                    descripcion = descripcion,
                    aprendiz = "APR-01"
                ),
                token
            )

        }.toDomain()

        // Guardamos también la actividad
        // recibida desde la API en Room.
        guardar(creada)

        return creada
    }

    suspend fun actualizarRemota(
        id: Long,
        titulo: String,
        descripcion: String,
        estado: String,
        token: String = "token-APR-01"
    ): ActividadFormativa {

        val remoteId =
            "ACT-${id.toString().padStart(3, '0')}"

        val actualizada = remote.ejecutarSeguro {

            remote.actualizarActividad(
                remoteId,
                ActividadUpdateDto(
                    titulo = titulo,
                    descripcion = descripcion,
                    aprendiz = "APR-01",
                    estado = estado
                ),
                token
            )

        }.toDomain()

        // Actualizamos el caché local.
        guardar(actualizada)

        return actualizada
    }

    suspend fun eliminarRemota(
        id: Long,
        token: String = "token-APR-01"
    ) {

        val remoteId =
            "ACT-${id.toString().padStart(3, '0')}"

        remote.ejecutarSeguro {
            remote.eliminarActividad(
                remoteId,
                token
            )
        }

        // Si la API eliminó correctamente,
        // eliminamos también de Room.
        dao.eliminarPorId(id)
    }

    suspend fun cambiarEstadoRemoto(
        id: Long,
        estado: String,
        token: String = "token-APR-01"
    ): ActividadFormativa {

        val remoteId =
            "ACT-${id.toString().padStart(3, '0')}"

        val actualizada = remote.ejecutarSeguro {

            remote.cambiarEstado(
                remoteId,
                estado,
                token
            )

        }.toDomain()

        // Guardamos el nuevo estado en Room.
        guardar(actualizada)

        return actualizada
    }
}