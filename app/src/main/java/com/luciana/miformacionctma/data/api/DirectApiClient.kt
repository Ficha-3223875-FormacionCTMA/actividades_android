package com.luciana.miformacionctma.data.api

import com.luciana.miformacionctma.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MultipartBody
import okio.buffer
import okio.sink
import java.io.BufferedOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.Proxy
import java.net.URL
import java.util.UUID

/**
 * Cliente HTTP directo para la API local.
 */
object DirectApiClient {

    private const val CONNECT_TIMEOUT_MS = 10_000
    private const val READ_TIMEOUT_MS = 20_000
    private const val TOKEN = "token-APR-01"

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
    }

    private fun open(
        path: String,
        method: String,
        token: String = TOKEN
    ): HttpURLConnection {

        val base = BuildConfig.API_BASE_URL.trimEnd('/')
        val url = URL("$base/$path")

        return (url.openConnection(Proxy.NO_PROXY) as HttpURLConnection).apply {
            requestMethod = method
            connectTimeout = CONNECT_TIMEOUT_MS
            readTimeout = READ_TIMEOUT_MS
            useCaches = false
            doInput = true

            setRequestProperty("Authorization", token)
            setRequestProperty("Accept", "application/json")
            setRequestProperty("Connection", "close")
        }
    }

    private fun readResponse(connection: HttpURLConnection): String {

        val code = connection.responseCode

        val stream =
            if (code in 200..299) {
                connection.inputStream
            } else {
                connection.errorStream
            }

        val body = stream
            ?.bufferedReader(Charsets.UTF_8)
            ?.use { it.readText() }
            .orEmpty()

        if (code !in 200..299) {
            throw HttpStatusException(code, body)
        }

        return body
    }

    private fun HttpURLConnection.closeQuietly() {
        runCatching {
            inputStream?.close()
        }

        runCatching {
            errorStream?.close()
        }

        disconnect()
    }

    suspend fun obtenerActividades(
        token: String = TOKEN
    ): ActividadResponseDto =
        withContext(Dispatchers.IO) {

            val connection =
                open("api/actividades", "GET", token)

            try {
                json.decodeFromString(
                    readResponse(connection)
                )
            } finally {
                connection.closeQuietly()
            }
        }

    suspend fun obtenerActividad(
        id: String,
        token: String = TOKEN
    ): ActividadDto =
        withContext(Dispatchers.IO) {

            val connection =
                open("api/actividades/$id", "GET", token)

            try {
                json.decodeFromString(
                    readResponse(connection)
                )
            } finally {
                connection.closeQuietly()
            }
        }

    private suspend inline fun <reified T> sendJson(
        path: String,
        method: String,
        payload: String,
        token: String
    ): T =
        withContext(Dispatchers.IO) {

            val connection =
                open(path, method, token)

            try {

                connection.doOutput = true

                connection.setRequestProperty(
                    "Content-Type",
                    "application/json; charset=utf-8"
                )

                BufferedOutputStream(
                    connection.outputStream
                ).use { output ->

                    output.write(
                        payload.toByteArray(Charsets.UTF_8)
                    )

                    output.flush()
                }

                json.decodeFromString(
                    readResponse(connection)
                )

            } finally {
                connection.closeQuietly()
            }
        }

    suspend fun crearActividad(
        actividad: ActividadCreateDto,
        token: String = TOKEN
    ): ActividadDto =
        sendJson(
            "api/actividades",
            "POST",
            json.encodeToString(actividad),
            token
        )

    suspend fun actualizarActividad(
        id: String,
        actividad: ActividadUpdateDto,
        token: String = TOKEN
    ): ActividadDto =
        sendJson(
            "api/actividades/$id",
            "PUT",
            json.encodeToString(actividad),
            token
        )

    suspend fun cambiarEstado(
        id: String,
        estado: EstadoUpdateDto,
        token: String = TOKEN
    ): ActividadDto =
        sendJson(
            "api/actividades/$id/estado",
            "PATCH",
            json.encodeToString(estado),
            token
        )

    suspend fun eliminarActividad(
        id: String,
        token: String = TOKEN
    ): EliminarActividadDto =
        withContext(Dispatchers.IO) {

            val connection =
                open("api/actividades/$id", "DELETE", token)

            try {

                json.decodeFromString(
                    readResponse(connection)
                )

            } finally {
                connection.closeQuietly()
            }
        }

    suspend fun subirEvidencia(
        id: String,
        archivo: MultipartBody.Part,
        token: String = TOKEN
    ): EvidenciaResponseDto =
        withContext(Dispatchers.IO) {

            val boundary =
                "----MiFormacionCTMA-${UUID.randomUUID()}"

            val connection =
                open(
                    "api/actividades/$id/evidencias",
                    "POST",
                    token
                )

            try {

                connection.doOutput = true

                connection.setRequestProperty(
                    "Content-Type",
                    "multipart/form-data; boundary=$boundary"
                )

                /*
                 * IMPORTANTE:
                 * Usamos Okio para convertir el OutputStream
                 * en BufferedSink.
                 */
                val sink =
                    connection.outputStream.sink().buffer()

                try {

                    val disposition =
                        archivo.headers
                            ?.get("Content-Disposition")
                            ?: "form-data; name=\"archivo\"; filename=\"evidencia.jpg\""

                    val mediaType =
                        archivo.body.contentType()
                            ?.toString()
                            ?: "application/octet-stream"

                    sink.writeUtf8(
                        "--$boundary\r\n"
                    )

                    sink.writeUtf8(
                        "$disposition\r\n"
                    )

                    sink.writeUtf8(
                        "Content-Type: $mediaType\r\n\r\n"
                    )

                    archivo.body.writeTo(sink)

                    sink.writeUtf8(
                        "\r\n--$boundary--\r\n"
                    )

                    sink.flush()

                } finally {
                    sink.close()
                }

                json.decodeFromString(
                    readResponse(connection)
                )

            } finally {
                connection.closeQuietly()
            }
        }

    class HttpStatusException(
        val statusCode: Int,
        val responseBody: String
    ) : IOException(
        "HTTP $statusCode"
    )
}