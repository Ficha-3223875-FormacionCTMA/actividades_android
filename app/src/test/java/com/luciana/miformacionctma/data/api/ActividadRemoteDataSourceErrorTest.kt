package com.luciana.miformacionctma.data.api

import java.io.IOException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

class ActividadRemoteDataSourceErrorTest {
    @Test
    fun sinRed_seClasifica() = runTest {
        val api = object : ActividadApiService {
            override suspend fun obtenerActividades(limite: Int): Response<List<ActividadDto>> =
                throw IOException("offline")
            override suspend fun obtenerActividad(id: String): Response<ActividadDto> =
                throw IOException("offline")
        }

        try {
            ActividadRemoteDataSource(api).obtenerActividades()
            throw AssertionError("Se esperaba NetworkException")
        } catch (e: NetworkException) {
            assertTrue(e.error is NetworkError.NoConnection)
        }
    }

    @Test
    fun cancelacion_noSeConvierteEnErrorDeNegocio() = runTest {
        val api = object : ActividadApiService {
            override suspend fun obtenerActividades(limite: Int): Response<List<ActividadDto>> {
                delay(1_000)
                return Response.success(emptyList())
            }
            override suspend fun obtenerActividad(id: String): Response<ActividadDto> =
                Response.success(null)
        }
        var cancelada = false
        val job = launch {
            try {
                ActividadRemoteDataSource(api).obtenerActividades()
            } catch (e: CancellationException) {
                cancelada = true
                throw e
            }
        }
        advanceTimeBy(100)
        job.cancel()
        job.join()
        advanceUntilIdle()
        assertTrue(cancelada)
    }
}
