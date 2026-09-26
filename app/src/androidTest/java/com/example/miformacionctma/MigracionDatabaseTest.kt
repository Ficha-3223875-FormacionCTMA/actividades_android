package com.example.miformacionctma

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.miformacionctma.data.local.database.AppDatabase
import com.example.miformacionctma.data.local.entity.ActividadEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MigracionDatabaseTest {

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

    @Test
    fun actividadNuevaTieneResueltoEnFalse() = runBlocking {
        val dao = database.actividadDao()

        val actividad = ActividadEntity(
            id = 1L,
            titulo = "Actividad antigua",
            descripcion = "Actividad de prueba",
            aprendiz = "APR-01",
            estado = "PENDIENTE",
            createdAt = "2026-09-21T00:00:00",
            resuelto = false
        )

        dao.guardar(actividad)

        val resultado = dao.obtenerPorId(1L)

        assertEquals(
            "Actividad antigua",
            resultado?.titulo
        )

        assertFalse(
            resultado?.resuelto ?: true
        )
    }
}