package com.example.miformacionctma

import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.miformacionctma.data.local.database.AppDatabase
import com.example.miformacionctma.data.local.database.MIGRATION_1_2
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

        val context =
            ApplicationProvider.getApplicationContext<android.content.Context>()

        database =
            Room.inMemoryDatabaseBuilder(
                context,
                AppDatabase::class.java
            )
                .setDriver(AndroidSQLiteDriver())
                .addMigrations(MIGRATION_1_2)
                .build()
    }

    @After
    fun cerrarBaseDeDatos() {
        database.close()
    }

    @Test
    fun migracionConservaDatosYResueltoEsFalse() {

        val actividad =
            com.example.miformacionctma.data.local.entity.ActividadEntity(
                titulo = "Actividad antigua",
                descripcion = "Actividad de prueba",
                aprendiz = "APR-01",
                estado = "PENDIENTE",
                createdAt = "2026-09-21T00:00:00",
                resuelto = false
            )

        val dao =
            database.actividadDao()

        kotlinx.coroutines.runBlocking {

            val id =
                dao.guardar(actividad)

            val resultado =
                dao.obtenerPorId(id)

            assertEquals(
                "Actividad antigua",
                resultado?.titulo
            )

            assertFalse(
                resultado?.resuelto ?: true
            )
        }
    }
}