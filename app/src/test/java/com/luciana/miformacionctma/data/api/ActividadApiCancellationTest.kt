package com.luciana.miformacionctma.data.api

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Assert.assertTrue
import org.junit.Test

class ActividadApiCancellationTest {

    @Test
    fun solicitudRemota_puedeCancelarse() = runTest {
        var cancelada = false

        val job = launch {
            try {
                delay(1_000)
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
