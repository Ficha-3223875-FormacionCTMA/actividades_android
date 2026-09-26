package com.luciana.miformacionctma

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FlujoActividadesTest {

    @Test
    fun busquedaRapida_conservaElUltimoResultado() = runTest {
        val texto = MutableStateFlow("")

        val resultados = texto
            .flatMapLatest { valor ->
                flowOf(valor)
            }

        val recibidos = mutableListOf<String>()

        val job = launch {
            resultados.collect { valor ->
                recibidos.add(valor)
            }
        }

        texto.value = "Kotlin"
        texto.value = "Compose"

        advanceUntilIdle()
        yield()

        assertEquals("Compose", recibidos.last())

        job.cancel()
    }
}