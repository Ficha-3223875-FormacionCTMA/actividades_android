package com.example.miformacionctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.miformacionctma.domain.ActividadFormativa
import com.example.miformacionctma.domain.Prioridad
import com.example.miformacionctma.domain.actividadesUrgentes
import com.example.miformacionctma.domain.promedioProgreso
import com.example.miformacionctma.ui.MiFormacionCTMATheme
import com.example.miformacionctma.ui.components.TarjetaActividad

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MiFormacionCTMATheme {
                PantallaInicio()
            }
        }
    }
}

@Composable
fun PantallaInicio(nombre: String = "Aprendiz") {

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

    val promedio = promedioProgreso(actividades)
    val urgentes = actividadesUrgentes(actividades)

    var mostrarCompromiso by remember {
        mutableStateOf(false)
    }

    Scaffold { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            item {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Mi Formación CTMA",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Hola, $nombre"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Aquí organizarás actividades y evidencias."
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Resumen de actividades",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Actividades registradas: ${actividades.size}"
                    )

                    Text(
                        text = "Promedio de progreso: ${"%.1f".format(promedio)}%"
                    )

                    Text(
                        text = "Actividades urgentes: ${urgentes.size}"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            mostrarCompromiso = !mostrarCompromiso
                        }
                    ) {
                        Text("Ver próximo compromiso")
                    }

                    if (mostrarCompromiso) {

                        Spacer(modifier = Modifier.height(12.dp))

                        Card {

                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {

                                Text(
                                    text = "Próximo compromiso",
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Spacer(
                                    modifier = Modifier.height(8.dp)
                                )

                                Text(
                                    text = "Completar la actividad de Android Studio."
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Actividades formativas",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

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