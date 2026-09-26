package com.luciana.miformacionctma.data.api

import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import okhttp3.MultipartBody
import java.io.IOException
import java.net.SocketTimeoutException

class ActividadRemoteDataSource(
    private val api: ActividadApiService
) {
    suspend fun obtenerActividades(token: String = "token-APR-01"): List<ActividadDto> =
        DirectApiClient.obtenerActividades(token).actividades

    suspend fun obtenerActividad(id: String, token: String = "token-APR-01"): ActividadDto =
        DirectApiClient.obtenerActividad(id, token)

    suspend fun crearActividad(actividad: ActividadCreateDto, token: String = "token-APR-01"): ActividadDto =
        DirectApiClient.crearActividad(actividad, token)

    suspend fun actualizarActividad(id: String, actividad: ActividadUpdateDto, token: String = "token-APR-01"): ActividadDto =
        DirectApiClient.actualizarActividad(id, actividad, token)

    suspend fun eliminarActividad(id: String, token: String = "token-APR-01"): EliminarActividadDto =
        DirectApiClient.eliminarActividad(id, token)

    suspend fun subirEvidencia(id: String, archivo: MultipartBody.Part, token: String = "token-APR-01"): EvidenciaResponseDto =
        DirectApiClient.subirEvidencia(id, archivo, token)

    suspend fun cambiarEstado(id: String, estado: String, token: String = "token-APR-01"): ActividadDto =
        DirectApiClient.cambiarEstado(id, EstadoUpdateDto(estado), token)

    suspend fun <T> ejecutarSeguro(block: suspend () -> T): T {
        return try {
            block()
        } catch (e: CancellationException) {
            throw e
        } catch (e: SocketTimeoutException) {
            throw NetworkException(NetworkError.Timeout)
        } catch (e: DirectApiClient.HttpStatusException) {
            throw NetworkException(when (e.statusCode) {
                401 -> NetworkError.Unauthorized
                404 -> NetworkError.NotFound
                408 -> NetworkError.Timeout
                in 500..599 -> NetworkError.Server(e.statusCode)
                else -> NetworkError.Unknown(e)
            })
        } catch (e: SerializationException) {
            throw NetworkException(NetworkError.InvalidPayload)
        } catch (e: IOException) {
            throw NetworkException(NetworkError.NoConnection)
        }
    }
}
