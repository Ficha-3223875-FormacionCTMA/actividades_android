package com.example.miformacionctma

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.miformacionctma.domain.ActividadFormativa
import com.example.miformacionctma.domain.Prioridad
import com.example.miformacionctma.ui.screens.DetalleActividadScreen
import com.example.miformacionctma.ui.screens.FormularioActividadScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    var actividades by remember {
        mutableStateOf(
            listOf(
                ActividadFormativa(
                    id = 1L,
                    titulo = "Introducción a Android",
                    descripcion = "Conocer las herramientas básicas de Android Studio.",
                    progreso = 100,
                    diasRestantes = 0,
                    prioridad = Prioridad.ALTA
                ),
                ActividadFormativa(
                    id = 2L,
                    titulo = "Jetpack Compose",
                    descripcion = "Crear interfaces utilizando componentes declarativos.",
                    progreso = 80,
                    diasRestantes = 2,
                    prioridad = Prioridad.ALTA
                ),
                ActividadFormativa(
                    id = 3L,
                    titulo = "Material 3",
                    descripcion = "Aplicar componentes y estilos de Material Design.",
                    progreso = 70,
                    diasRestantes = 3,
                    prioridad = Prioridad.MEDIA
                ),
                ActividadFormativa(
                    id = 4L,
                    titulo = "Layouts en Compose",
                    descripcion = "Trabajar con Column, Row y otros layouts.",
                    progreso = 60,
                    diasRestantes = 4,
                    prioridad = Prioridad.MEDIA
                ),
                ActividadFormativa(
                    id = 5L,
                    titulo = "Componentes reutilizables",
                    descripcion = "Crear componentes que puedan utilizarse en diferentes pantallas.",
                    progreso = 50,
                    diasRestantes = 5,
                    prioridad = Prioridad.MEDIA
                ),
                ActividadFormativa(
                    id = 6L,
                    titulo = "Accesibilidad",
                    descripcion = "Mejorar la experiencia de usuarios con diferentes necesidades.",
                    progreso = 40,
                    diasRestantes = 6,
                    prioridad = Prioridad.ALTA
                ),
                ActividadFormativa(
                    id = 7L,
                    titulo = "Diseño adaptable",
                    descripcion = "Preparar la interfaz para diferentes tamaños de pantalla.",
                    progreso = 30,
                    diasRestantes = 7,
                    prioridad = Prioridad.MEDIA
                ),
                ActividadFormativa(
                    id = 8L,
                    titulo = "Previews",
                    descripcion = "Probar los componentes mediante vistas previas.",
                    progreso = 25,
                    diasRestantes = 8,
                    prioridad = Prioridad.BAJA
                ),
                ActividadFormativa(
                    id = 9L,
                    titulo = "Listas con LazyColumn",
                    descripcion = "Mostrar varias actividades de manera eficiente.",
                    progreso = 20,
                    diasRestantes = 9,
                    prioridad = Prioridad.MEDIA
                ),
                ActividadFormativa(
                    id = 10L,
                    titulo = "Pruebas de interfaz",
                    descripcion = "Verificar el funcionamiento de la aplicación.",
                    progreso = 10,
                    diasRestantes = 10,
                    prioridad = Prioridad.BAJA
                )
            )
        )
    }

    NavHost(
        navController = navController,
        startDestination = "lista"
    ) {

        composable("lista") {
            PantallaInicio(
                actividades = actividades,
                onCrearActividad = {
                    navController.navigate("crear")
                },
                onSeleccionarActividad = { actividadId ->
                    navController.navigate("detalle/$actividadId")
                }
            )
        }

        composable("crear") {

            FormularioActividadScreen(
                onGuardar = { titulo, descripcion ->

                    val nuevaActividad = ActividadFormativa(
                        id = (actividades.maxOfOrNull { it.id } ?: 0L) + 1L,
                        titulo = titulo,
                        descripcion = descripcion,
                        progreso = 0,
                        diasRestantes = 0,
                        prioridad = Prioridad.MEDIA
                    )

                    actividades = actividades + nuevaActividad

                    navController.popBackStack()
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
                actividades.find { it.id == actividadId }

            DetalleActividadScreen(
                actividad = actividad,
                onVolver = {
                    navController.popBackStack()
                }
            )
        }
    }
}