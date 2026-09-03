package com.luciana.miformacionctma.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luciana.miformacionctma.domain.ActividadFormativa
import com.luciana.miformacionctma.domain.Prioridad
import com.luciana.miformacionctma.domain.buscarPorTitulo
import com.luciana.miformacionctma.domain.promedioProgreso
import com.luciana.miformacionctma.ui.components.TarjetaActividad

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaActividades(
    actividades: List<ActividadFormativa>,
    onActividadClick: (ActividadFormativa) -> Unit = {},
    onCrearActividad: () -> Unit = {}
) {

    var textoBusqueda by remember {
        mutableStateOf("")
    }

    val actividadesFiltradas =
        if (textoBusqueda.isBlank()) {
            actividades
        } else {
            buscarPorTitulo(
                actividades = actividades,
                texto = textoBusqueda
            )
        }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Mi Formación CTMA")
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            Text(
                text = "Actividades formativas",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(
                    top = 16.dp,
                    bottom = 4.dp
                )
            )

            Text(
                text = "Consulta tus actividades, estados y progreso.",
                style = MaterialTheme.typography.bodyMedium
            )

            Button(
                onClick = onCrearActividad,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Text("Crear actividad")
            }

            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = {
                    textoBusqueda = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                label = {
                    Text("Buscar actividad")
                },
                placeholder = {
                    Text("Escribe un título...")
                },
                singleLine = true
            )

            Text(
                text = "Total: ${actividades.size} actividades",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = "Mostrando: ${actividadesFiltradas.size}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Promedio de progreso: ${
                    "%.1f".format(
                        promedioProgreso(actividades)
                    )
                }%",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            if (actividadesFiltradas.isEmpty()) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        Text(
                            text = "No se encontraron actividades",
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Text(
                            text = "Prueba con otro título."
                        )

                        Button(
                            onClick = {
                                textoBusqueda = ""
                            }
                        ) {
                            Text("Mostrar todas")
                        }
                    }
                }

            } else {

                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {

                    if (maxWidth < 600.dp) {

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            items(
                                items = actividadesFiltradas,
                                key = { it.id }
                            ) { actividad ->

                                TarjetaActividad(
                                    actividad = actividad,
                                    onActividadClick = onActividadClick
                                )
                            }
                        }

                    } else {

                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(
                                minSize = 280.dp
                            ),
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            items(
                                items = actividadesFiltradas,
                                key = { it.id }
                            ) { actividad ->

                                TarjetaActividad(
                                    actividad = actividad,
                                    onActividadClick = onActividadClick
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    name = "Pantalla actividades",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewPantallaActividades() {

    PantallaActividades(
        actividades = listOf(

            ActividadFormativa(
                id = 1L,
                titulo = "Fundamentos de Kotlin",
                descripcion = "Repasar Kotlin.",
                progreso = 80,
                diasRestantes = 2,
                prioridad = Prioridad.ALTA
            ),

            ActividadFormativa(
                id = 2L,
                titulo = "Jetpack Compose",
                descripcion = "Construir interfaces.",
                progreso = 60,
                diasRestantes = 5,
                prioridad = Prioridad.MEDIA
            ),

            ActividadFormativa(
                id = 3L,
                titulo = "Diseño adaptable",
                descripcion = "Adaptar la interfaz.",
                progreso = 30,
                diasRestantes = 8,
                prioridad = Prioridad.BAJA
            )
        )
    )
}
