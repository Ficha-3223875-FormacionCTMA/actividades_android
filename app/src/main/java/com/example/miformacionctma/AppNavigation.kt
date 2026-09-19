package com.example.miformacionctma

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.miformacionctma.data.Actividad
import com.example.miformacionctma.data.ActividadRepository
import com.example.miformacionctma.domain.ActividadFormativa
import com.example.miformacionctma.domain.Prioridad
import com.example.miformacionctma.ui.screens.DetalleActividadScreen
import com.example.miformacionctma.ui.screens.FormularioActividadScreen
import kotlinx.coroutines.launch

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val repository = remember { ActividadRepository() }
    val scope = rememberCoroutineScope()

    var actividades by remember {
        mutableStateOf<List<ActividadFormativa>>(emptyList())
    }

    var cargando by remember {
        mutableStateOf(true)
    }

    var error by remember {
        mutableStateOf<String?>(null)
    }

    fun convertirActividad(
        actividad: Actividad
    ): ActividadFormativa {

        val idNumerico = actividad.id
            .removePrefix("ACT-")
            .toLongOrNull()
            ?: 0L

        val progreso = when (actividad.estado) {
            "COMPLETADA" -> 100
            "EN_PROCESO" -> 50
            else -> 0
        }

        val prioridad = when (actividad.estado) {
            "COMPLETADA" -> Prioridad.BAJA
            "EN_PROCESO" -> Prioridad.MEDIA
            else -> Prioridad.ALTA
        }

        return ActividadFormativa(
            id = idNumerico,
            titulo = actividad.titulo,
            descripcion = actividad.descripcion,
            progreso = progreso,
            diasRestantes = 0,
            prioridad = prioridad
        )
    }

    fun cargarActividades() {

        scope.launch {

            cargando = true
            error = null

            try {

                val resultado = repository.obtenerActividades()

                actividades = resultado.map {
                    convertirActividad(it)
                }

            } catch (e: Exception) {

                error = e.message
                    ?: "No se pudieron cargar las actividades."

            } finally {

                cargando = false
            }
        }
    }

    LaunchedEffect(Unit) {
        cargarActividades()
    }

    NavHost(
        navController = navController,
        startDestination = "lista"
    ) {

        composable("lista") {

            when {

                cargando -> {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                error != null -> {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Error al conectar con FastAPI:\n$error"
                        )
                    }
                }

                else -> {

                    PantallaInicio(
                        actividades = actividades,

                        onCrearActividad = {
                            navController.navigate("crear")
                        },

                        onSeleccionarActividad = { actividadId ->

                            navController.navigate(
                                "detalle/$actividadId"
                            )
                        }
                    )
                }
            }
        }

        composable("crear") {

            FormularioActividadScreen(

                onGuardar = { titulo, descripcion ->

                    scope.launch {

                        try {

                            error = null

                            val nuevaActividad =
                                Actividad(
                                    id = "",
                                    titulo = titulo,
                                    descripcion = descripcion,
                                    aprendiz = "APR-01"
                                )

                            repository.crearActividad(
                                nuevaActividad
                            )

                            cargarActividades()

                            navController.popBackStack()

                        } catch (e: Exception) {

                            error = e.message
                                ?: "No se pudo crear la actividad."
                        }
                    }
                },

                onCancelar = {
                    navController.popBackStack()
                }
            )
        }

        composable("detalle/{actividadId}") { backStackEntry ->

            val actividadId =
                backStackEntry.arguments
                    ?.getString("actividadId")
                    ?.toLongOrNull()

            val actividad =
                actividades.find {
                    it.id == actividadId
                }

            DetalleActividadScreen(
                actividad = actividad,

                onVolver = {
                    navController.popBackStack()
                }
            )
        }
    }
}