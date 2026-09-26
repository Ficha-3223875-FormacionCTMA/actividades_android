package com.luciana.miformacionctma.data.api

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.MediaType.Companion.toMediaType
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class ActividadRemoteDataSourceMockWebServerTest {
    private lateinit var server: MockWebServer

    @Before
    fun iniciarServidor() {
        server = MockWebServer()
        server.start()
    }

    @After
    fun detenerServidor() {
        server.shutdown()
    }

    private fun dataSource(client: OkHttpClient = OkHttpClient()) : ActividadRemoteDataSource {
        val json = Json { ignoreUnknownKeys = true; explicitNulls = false }
        val api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ActividadApiService::class.java)
        return ActividadRemoteDataSource(api)
    }

    private fun fixture(name: String): String =
        requireNotNull(javaClass.classLoader?.getResource("fixtures/$name"))
            .readText()

    @Test
    fun ca01_200_valida_seConvierte() = runBlocking {
        server.enqueue(MockResponse().setResponseCode(200).setBody(fixture("actividad_valida.json")))

        val result = dataSource().obtenerActividades()

        assertEquals(1, result.size)
        assertEquals("Actividad REST válida", result.first().titulo)
        assertEquals("ADSO-01", result.first().competenciaId)
    }

    @Test
    fun ca02_200Vacio_esRespuestaValida() = runBlocking {
        server.enqueue(MockResponse().setResponseCode(200).setBody(fixture("actividades_vacias.json")))

        assertTrue(dataSource().obtenerActividades().isEmpty())
    }

    @Test
    fun ca05_401_seClasificaComoUnauthorized() = runBlocking {
        server.enqueue(MockResponse().setResponseCode(401))

        try {
            dataSource().obtenerActividades()
            throw AssertionError("Se esperaba NetworkException")
        } catch (e: NetworkException) {
            assertTrue(e.error is NetworkError.Unauthorized)
        }
    }

    @Test
    fun ca06_500_seClasificaComoServer() = runBlocking {
        server.enqueue(MockResponse().setResponseCode(500))

        try {
            dataSource().obtenerActividades()
            throw AssertionError("Se esperaba NetworkException")
        } catch (e: NetworkException) {
            assertEquals(NetworkError.Server(500), e.error)
        }
    }

    @Test
    fun ca06_jsonInvalido_seClasificaComoInvalidPayload() = runBlocking {
        server.enqueue(MockResponse().setResponseCode(200).setBody(fixture("actividad_invalida.json")))

        try {
            dataSource().obtenerActividades()
            throw AssertionError("Se esperaba NetworkException")
        } catch (e: NetworkException) {
            assertTrue(e.error is NetworkError.InvalidPayload)
        }
    }

    @Test
    fun ca03_timeout_seClasificaComoTimeout() = runBlocking {
        server.enqueue(MockResponse().setResponseCode(200).setBody(fixture("actividad_valida.json")).setBodyDelay(500, TimeUnit.MILLISECONDS))
        val client = OkHttpClient.Builder().readTimeout(100, TimeUnit.MILLISECONDS).build()

        try {
            dataSource(client).obtenerActividades()
            throw AssertionError("Se esperaba NetworkException")
        } catch (e: NetworkException) {
            assertTrue(e.error is NetworkError.Timeout)
        }
    }

    @Test
    fun tokenInterceptor_enviaBearerSinExponerloEnRespuestaUi() = runBlocking {
        val token = "TEST_TOKEN_ONLY"
        server.enqueue(MockResponse().setResponseCode(200).setBody(fixture("actividades_vacias.json")))
        val provider = TokenProvider { token }
        val client = OkHttpClient.Builder()
            .addInterceptor(BearerTokenInterceptor(provider))
            .build()

        dataSource(client).obtenerActividades()
        val request: RecordedRequest = server.takeRequest()
        assertEquals("Bearer $token", request.getHeader("Authorization"))
    }
}
