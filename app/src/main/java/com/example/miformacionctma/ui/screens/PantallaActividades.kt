package com.example.miformacionctma.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.miformacionctma.domain.ActividadFormativa
import com.example.miformacionctma.domain.Prioridad
import com.example.miformacionctma.ui.components.EncabezadoFormacion
import com.example.miformacionctma.ui.components.TarjetaActividad
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.unit.dp

@Composable
fun PantallaActividades() {

    val actividades = listOf(
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

    Scaffold { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            EncabezadoFormacion(
                nombre = "Aprendiz",
                resumen = "Consulta tus actividades formativas."
            )

            BoxWithConstraints(
                modifier = Modifier.fillMaxSize()
            ) {

                if (actividades.isEmpty()) {

                    Text(
                        text = "No hay actividades registradas.",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )

                } else if (maxWidth < 600.dp) {

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = actividades,
                            key = { it.id }
                        ) { actividad ->

                            TarjetaActividad(
                                actividad = actividad
                            )
                        }
                    }

                } else {

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = actividades,
                            key = { it.id }
                        ) { actividad ->

                            TarjetaActividad(
                                actividad = actividad
                            )
                        }
                    }
                }
            }
        }
    }
}