package com.luciana.miformacionctma

import androidx.sqlite.driver.AndroidSQLiteDriver
import com.luciana.miformacionctma.data.AppDatabase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import androidx.sqlite.execSQL

class MigracionDatabaseTest {

    @Test
    fun migracion1a2_agregaResueltoYConservaDatos() = runTest {
        val connection = AndroidSQLiteDriver().open(":memory:")

        try {
            connection.execSQL(
                """
                CREATE TABLE actividades (
                    id INTEGER NOT NULL PRIMARY KEY,
                    titulo TEXT NOT NULL,
                    descripcion TEXT,
                    progreso INTEGER NOT NULL,
                    diasRestantes INTEGER NOT NULL,
                    prioridad TEXT NOT NULL,
                    fecha TEXT NOT NULL
                )
                """.trimIndent()
            )

            connection.execSQL(
                """
                INSERT INTO actividades (
                    id,
                    titulo,
                    descripcion,
                    progreso,
                    diasRestantes,
                    prioridad,
                    fecha
                )
                VALUES (
                    1,
                    'Actividad anterior',
                    'Datos antes de la migración',
                    50,
                    5,
                    'MEDIA',
                    '2026-09-20'
                )
                """.trimIndent()
            )

            AppDatabase.MIGRATION_1_2.migrate(connection)

            val resultado = connection.prepare(
                """
                SELECT
                    id,
                    titulo,
                    resuelto
                FROM actividades
                WHERE id = 1
                """.trimIndent()
            ).use { statement ->

                assertTrue(statement.step())

                Triple(
                    statement.getLong(0),
                    statement.getText(1),
                    statement.getLong(2)
                )
            }

            assertEquals(1L, resultado.first)
            assertEquals("Actividad anterior", resultado.second)
            assertEquals(0L, resultado.third)
        } finally {
            connection.close()
        }
    }
}