package com.example.miformacionctma

import androidx.room3.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.miformacionctma.data.local.database.AppDatabase
import com.example.miformacionctma.data.local.entity.ActividadEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
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
        val context =
            ApplicationProvider.getApplicationContext<android.content.Context>()

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
        id: Long = 0L,
        titulo: String = "Actividad de prueba"
    ) = ActividadEntity(
        id = id,
        titulo = titulo,
        descripcion = "Descripción de prueba",
        aprendiz = "APR-01",
        estado = "PENDIENTE",
        createdAt = "2026-09-21T00:00:00",
        resuelto = false
    )

    @Test
    fun guardarYConsultarActividad() = runTest {
        val dao = database.actividadDao()

        val id = dao.guardar(
            actividadPrueba()
        )

        val resultado = dao.obtenerPorId(id)

        assertNotNull(resultado)

        assertEquals(
            "Actividad de prueba",
            resultado?.titulo
        )
    }

    @Test
    fun buscarActividadPorTexto() = runTest {
        val dao = database.actividadDao()

        dao.guardar(
            actividadPrueba()
        )

        val resultados =
            dao.buscar("prueba").first()

        assertEquals(
            1,
            resultados.size
        )
    }

    @Test
    fun actualizarActividad() = runTest {
        val dao = database.actividadDao()

        val id = dao.guardar(
            actividadPrueba()
        )

        dao.guardar(
            actividadPrueba(
                id = id,
                titulo = "Actividad actualizada"
            )
        )

        val resultado =
            dao.obtenerPorId(id)

        assertEquals(
            "Actividad actualizada",
            resultado?.titulo
        )
    }

    @Test
    fun eliminarActividad() = runTest {
        val dao = database.actividadDao()

        val id = dao.guardar(
            actividadPrueba()
        )

        dao.eliminarPorId(id)

        val resultado =
            dao.obtenerPorId(id)

        assertEquals(
            null,
            resultado
        )
    }
}