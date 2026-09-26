package com.luciana.miformacionctma

import androidx.room3.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.luciana.miformacionctma.data.ActividadDao
import com.luciana.miformacionctma.data.ActividadEntity
import com.luciana.miformacionctma.data.AppDatabase
import com.luciana.miformacionctma.domain.Prioridad
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ActividadDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: ActividadDao

    @Before
    fun configurar() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()

        database = Room.inMemoryDatabaseBuilder<AppDatabase>(
            context
        )
            .setDriver(androidx.sqlite.driver.AndroidSQLiteDriver())
            .allowMainThreadQueries()
            .build()

        dao = database.actividadDao()
    }

    @After
    fun cerrar() {
        database.close()
    }

    @Test
    fun insertarYBuscarPorId_devuelveActividad() = runTest {
        val actividad = crearActividad(
            id = 1L,
            titulo = "Fundamentos de Kotlin"
        )

        dao.insertar(actividad)

        val resultado = dao.buscarPorId(1L)

        assertEquals(actividad, resultado)
    }

    @Test
    fun buscarPorTitulo_devuelveCoincidencias() = runTest {
        dao.insertar(
            crearActividad(
                id = 1L,
                titulo = "Fundamentos de Kotlin"
            )
        )

        dao.insertar(
            crearActividad(
                id = 2L,
                titulo = "Jetpack Compose"
            )
        )

        val resultados = dao.buscarPorTitulo("Kotlin").first()

        assertEquals(1, resultados.size)
        assertEquals("Fundamentos de Kotlin", resultados.first().titulo)
    }

    @Test
    fun actualizar_modificaLaActividad() = runTest {
        val actividad = crearActividad(
            id = 1L,
            titulo = "Actividad original",
            progreso = 20
        )

        dao.insertar(actividad)

        val actualizada = actividad.copy(
            titulo = "Actividad actualizada",
            progreso = 80
        )

        dao.actualizar(actualizada)

        val resultado = dao.buscarPorId(1L)

        assertEquals("Actividad actualizada", resultado?.titulo)
        assertEquals(80, resultado?.progreso)
    }

    @Test
    fun eliminarPorId_eliminaLaActividad() = runTest {
        dao.insertar(
            crearActividad(
                id = 1L,
                titulo = "Actividad para eliminar"
            )
        )

        dao.eliminarPorId(1L)

        val resultado = dao.buscarPorId(1L)

        assertNull(resultado)
    }

    @Test
    fun observarTodas_devuelveLasActividadesOrdenadas() = runTest {
        dao.insertar(
            crearActividad(
                id = 2L,
                titulo = "Segunda"
            )
        )

        dao.insertar(
            crearActividad(
                id = 1L,
                titulo = "Primera"
            )
        )

        val resultados = dao.observarTodas().first()

        assertEquals(2, resultados.size)
        assertEquals("Primera", resultados[0].titulo)
        assertEquals("Segunda", resultados[1].titulo)
    }

    private fun crearActividad(
        id: Long,
        titulo: String,
        progreso: Int = 50
    ): ActividadEntity {
        return ActividadEntity(
            id = id,
            titulo = titulo,
            descripcion = "Descripción de prueba",
            progreso = progreso,
            diasRestantes = 5,
            prioridad = Prioridad.MEDIA,
            fecha = "2026-09-20",
            resuelto = false
        )
    }
}