package com.example.miformacionctma.data.remote

import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class NetworkFailure(
    val error: DataError
) : Exception(
    when (error) {
        DataError.NoConnection ->
            "Sin conexión con el servidor"

        DataError.Timeout ->
            "Tiempo de espera agotado"

        DataError.Unauthorized ->
            "No autorizado (401)"

        DataError.NotFound ->
            "Recurso no encontrado (404)"

        is DataError.Server ->
            "Error del servidor (${error.code})"

        DataError.InvalidPayload ->
            "Respuesta JSON inválida"

        is DataError.Unknown ->
            "Error desconocido: ${error.cause::class.simpleName}: ${error.cause.message}"
    }
)

suspend fun <T> classifyNetworkError(
    block: suspend () -> T
): Result<T> {

    return try {

        Result.success(
            block()
        )

    } catch (e: CancellationException) {

        throw e

    } catch (e: SocketTimeoutException) {

        Result.failure(
            NetworkFailure(
                DataError.Timeout
            )
        )

    } catch (e: SerializationException) {

        Result.failure(
            NetworkFailure(
                DataError.InvalidPayload
            )
        )

    } catch (e: IOException) {

        Result.failure(
            NetworkFailure(
                DataError.Unknown(e)
            )
        )

    } catch (e: HttpException) {

        val cuerpoError =
            e.response()?.errorBody()?.string()

        val detalle =
            if (!cuerpoError.isNullOrBlank()) {
                cuerpoError
            } else {
                "Sin detalle del servidor"
            }

        Result.failure(
            NetworkFailure(
                DataError.Unknown(
                    Exception(
                        "HTTP ${e.code()} - $detalle"
                    )
                )
            )
        )

    } catch (e: Exception) {

        Result.failure(
            NetworkFailure(
                DataError.Unknown(e)
            )
        )
    }
}