package com.example.miformacionctma

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.miformacionctma.data.local.database.AppDatabase
import com.example.miformacionctma.data.local.entity.ActividadEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ActividadDaoInstrumentedTest {

    private lateinit var database: AppDatabase

    @Before
    fun crearBaseDeDatos() {
        val context: Context =
            ApplicationProvider.getApplicationContext()

        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun cerrarBaseDeDatos() {
        database.close()
    }

    private fun actividadPrueba(
        id: Long = 1L,
        titulo: String = "Actividad de prueba"
    ): ActividadEntity {
        return ActividadEntity(
            id = id,
            titulo = titulo,
            descripcion = "Descripción de prueba",
            aprendiz = "APR-01",
            estado = "PENDIENTE",
            createdAt = "2026-09-21T00:00:00",
            resuelto = false
        )
    }

    @Test
    fun guardarYConsultarActividad() = runBlocking {
        val dao = database.actividadDao()

        dao.guardar(actividadPrueba())

        val resultado = dao.obtenerPorId(1L)

        assertNotNull(resultado)
        assertEquals(
            "Actividad de prueba",
            resultado?.titulo
        )
    }

    @Test
    fun buscarActividadPorTexto() = runBlocking {
        val dao = database.actividadDao()

        dao.guardar(actividadPrueba())

        val resultados = dao.buscar("prueba").first()

        assertEquals(
            1,
            resultados.size
        )
    }

    @Test
    fun actualizarActividad() = runBlocking {
        val dao = database.actividadDao()

        dao.guardar(actividadPrueba())

        dao.guardar(
            actividadPrueba(
                id = 1L,
                titulo = "Actividad actualizada"
            )
        )

        val resultado = dao.obtenerPorId(1L)

        assertEquals(
            "Actividad actualizada",
            resultado?.titulo
        )
    }

    @Test
    fun eliminarActividad() = runBlocking {
        val dao = database.actividadDao()

        dao.guardar(actividadPrueba())

        dao.eliminarPorId(1L)

        val resultado = dao.obtenerPorId(1L)

        assertEquals(
            null,
            resultado
        )
    }
}