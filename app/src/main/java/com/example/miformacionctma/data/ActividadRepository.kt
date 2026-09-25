package com.example.miformacionctma.data

import com.example.miformacionctma.data.local.dao.ActividadDao
import com.example.miformacionctma.data.local.dao.EvidenciaDao
import com.example.miformacionctma.data.local.mapper.toActividad
import com.example.miformacionctma.data.local.mapper.toEntity
import com.example.miformacionctma.data.local.mapper.toEvidenciaDomain
import com.example.miformacionctma.data.local.mapper.toEvidenciaEntity
import com.example.miformacionctma.data.ApiService
import com.example.miformacionctma.data.remote.CrearActividadDto
import com.example.miformacionctma.data.remote.RemoteActividadDataSource
import com.example.miformacionctma.data.remote.toDomain
import com.example.miformacionctma.domain.Evidencia
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ActividadRepository(
    private val api: ApiService,
    private val dao: ActividadDao,
    private val evidenciaDao: EvidenciaDao
) {

    private val remote = RemoteActividadDataSource(api)

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
        dao.guardar(actividad.toEntity())
    }

    suspend fun guardarTodasLocales(
        actividades: List<Actividad>
    ) {
        dao.guardarTodas(
            actividades.map { it.toEntity() }
        )
    }

    suspend fun actualizarLocal(
        actividad: Actividad
    ) {
        dao.guardar(actividad.toEntity())
    }

    suspend fun eliminarLocal(
        actividad: Actividad
    ) {
        dao.eliminarPorId(
            actividad.id
                .removePrefix("ACT-")
                .toLongOrNull() ?: 0L
        )
    }

    suspend fun eliminarTodasLocales() {
        dao.eliminarTodas()
    }

    suspend fun sincronizarDesdeApi(
        token: String
    ): List<Actividad> {

        val respuesta = remote.obtenerActividades(
            token = token
        )

        val actividades = respuesta.actividades.map { dto ->
            dto.toDomain()
        }

        guardarTodasLocales(actividades)

        return actividades
    }

    suspend fun crearActividad(
        actividad: Actividad,
        token: String
    ): Actividad {

        val solicitud = CrearActividadDto(
            titulo = actividad.titulo.trim(),
            descripcion = actividad.descripcion.trim(),
            aprendiz = "APR-01"
        )

        val creadaDto = remote.crearActividad(
            token = token,
            actividad = solicitud
        )

        val creada = creadaDto.toDomain()

        guardarLocal(creada)

        return creada
    }

    fun observarEvidencias(
        actividadId: Long
    ): Flow<List<Evidencia>> {

        return evidenciaDao
            .observarPorActividad(actividadId)
            .map { lista ->
                lista.map {
                    it.toEvidenciaDomain()
                }
            }
    }

    suspend fun guardarEvidencia(
        evidencia: Evidencia
    ) {
        evidenciaDao.guardar(
            evidencia.toEvidenciaEntity()
        )
    }

    suspend fun actualizarEstadoEvidencia(
        id: String,
        estado: String
    ) {
        val evidencia = evidenciaDao.obtenerPorId(id)
            ?: return

        evidenciaDao.actualizar(
            evidencia.copy(
                estado = estado
            )
        )
    }

    suspend fun obtenerEvidencia(
        id: String
    ): Evidencia? {
        return evidenciaDao
            .obtenerPorId(id)
            ?.toEvidenciaDomain()
    }

    suspend fun eliminarEvidencia(
        evidencia: Evidencia
    ) {
        evidenciaDao.eliminarPorId(evidencia.id)
    }

    suspend fun eliminarEvidenciasDeActividad(
        actividadId: Long
    ) {
        evidenciaDao.eliminarPorActividad(actividadId)
    }

    suspend fun subirEvidenciaAlServidor(
        actividadId: String,
        archivo: File,
        mimeType: String,
        token: String
    ): Boolean {

        return try {

            val requestBody = archivo.asRequestBody(
                mimeType.toMediaType()
            )

            val parte = MultipartBody.Part.createFormData(
                "archivo",
                archivo.name,
                requestBody
            )

            api.subirEvidencia(
                actividadId = actividadId,
                token = token,
                archivo = parte
            )

            true

        } catch (e: CancellationException) {

            throw e

        } catch (e: Exception) {

            e.printStackTrace()

            false
        }
    }
}