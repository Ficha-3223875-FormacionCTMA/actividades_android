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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luciana.miformacionctma.domain.ActividadFormativa
import com.luciana.miformacionctma.domain.Prioridad
import com.luciana.miformacionctma.domain.promedioProgreso
import com.luciana.miformacionctma.ui.components.TarjetaActividad


// =========================================================
// PANTALLA PRINCIPAL
// =========================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaActividades(
    actividades: List<ActividadFormativa>,
    onActividadClick: (ActividadFormativa) -> Unit = {}
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Mi Formación CTMA"
                    )
                }
            )
        }
    ) { paddingValues ->

        // =================================================
        // ESTADO VACÍO
        // =================================================

        if (actividades.isEmpty()) {

            EstadoVacio(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )

        } else {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {

                // =================================================
                // ENCABEZADO
                // =================================================

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

                Text(
                    text = "Total: ${actividades.size} actividades",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    text = "Promedio de progreso: ${
                        "%.1f".format(promedioProgreso(actividades))
                    }%",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // =================================================
                // FASE 10 - ADAPTACIÓN A PANTALLA ANCHA
                // =================================================

                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {

                    // =============================================
                    // PANTALLA ESTRECHA
                    // Menor a 600.dp → LazyColumn
                    // =============================================

                    if (maxWidth < 600.dp) {

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {

                            items(
                                items = actividades,
                                key = { it.id }
                            ) { actividad ->

                                TarjetaActividad(
                                    actividad = actividad,
                                    onActividadClick = onActividadClick
                                )
                            }
                        }

                    } else {

                        // =============================================
                        // PANTALLA ANCHA
                        // 600.dp o más → LazyVerticalGrid
                        // =============================================

                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(
                                minSize = 280.dp
                            ),
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            items(
                                items = actividades,
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


// =========================================================
// ESTADO VACÍO
// =========================================================

@Composable
private fun EstadoVacio(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier.padding(24.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = "No hay actividades",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Todavía no tienes actividades formativas registradas."
            )

            Button(
                onClick = { }
            ) {

                Text(
                    text = "Volver a intentar"
                )
            }
        }
    }
}


// =========================================================
// FASE 9 - PREVIEW NORMAL
// =========================================================

@Preview(
    name = "Normal",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewPantallaActividadesNormal() {

    PantallaActividades(
        actividades = listOf(

            ActividadFormativa(
                id = 1L,
                titulo = "Desarrollo de aplicaciones móviles",
                descripcion = "Actividad de Android con Jetpack Compose.",
                progreso = 60,
                diasRestantes = 5,
                prioridad = Prioridad.MEDIA
            ),

            ActividadFormativa(
                id = 2L,
                titulo = "Diseño de interfaces",
                descripcion = "Construcción de interfaces para aplicaciones.",
                progreso = 80,
                diasRestantes = 3,
                prioridad = Prioridad.BAJA
            ),

            ActividadFormativa(
                id = 3L,
                titulo = "Arquitectura de software",
                descripcion = "Organización y estructura del proyecto.",
                progreso = 35,
                diasRestantes = 10,
                prioridad = Prioridad.ALTA
            )
        )
    )
}


// =========================================================
// FASE 9 - PREVIEW TÍTULO LARGO
// =========================================================

@Preview(
    name = "Título largo",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewPantallaActividadesTituloLargo() {

    PantallaActividades(
        actividades = listOf(

            ActividadFormativa(
                id = 4L,
                titulo = "Desarrollo de aplicaciones móviles utilizando Jetpack Compose y arquitectura moderna para proyectos de software",
                descripcion = "Esta actividad permite comprobar el comportamiento de la interfaz cuando el título tiene una longitud considerable.",
                progreso = 50,
                diasRestantes = 7,
                prioridad = Prioridad.ALTA
            )
        )
    )
}


// =========================================================
// FASE 9 - PREVIEW PROGRESO LÍMITE
// =========================================================

@Preview(
    name = "Progreso límite",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewPantallaActividadesProgresoLimite() {

    PantallaActividades(
        actividades = listOf(

            // Progreso mínimo
            ActividadFormativa(
                id = 5L,
                titulo = "Actividad sin iniciar",
                descripcion = "Actividad con progreso mínimo.",
                progreso = 0,
                diasRestantes = 15,
                prioridad = Prioridad.BAJA
            ),

            // Progreso máximo
            ActividadFormativa(
                id = 6L,
                titulo = "Actividad completada",
                descripcion = "Actividad con progreso máximo.",
                progreso = 100,
                diasRestantes = 0,
                prioridad = Prioridad.ALTA
            )
        )
    )
}


// =========================================================
// FASE 9 - PREVIEW ESTADO VACÍO
// =========================================================

@Preview(
    name = "Estado vacío",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewPantallaActividadesEstadoVacio() {

    PantallaActividades(
        actividades = emptyList()
    )
}


// =========================================================
// FASE 10 - PREVIEW ANCHO AMPLIADO
// =========================================================

@Preview(
    name = "Ancho ampliado",
    widthDp = 800,
    heightDp = 600,
    showBackground = true
)
@Composable
fun PreviewPantallaActividadesAnchoAmpliado() {

    PantallaActividades(
        actividades = listOf(

            ActividadFormativa(
                id = 7L,
                titulo = "Actividad 1",
                descripcion = "Actividad de prueba.",
                progreso = 20,
                diasRestantes = 10,
                prioridad = Prioridad.BAJA
            ),

            ActividadFormativa(
                id = 8L,
                titulo = "Actividad 2",
                descripcion = "Actividad de prueba.",
                progreso = 45,
                diasRestantes = 7,
                prioridad = Prioridad.MEDIA
            ),

            ActividadFormativa(
                id = 9L,
                titulo = "Actividad 3",
                descripcion = "Actividad de prueba.",
                progreso = 70,
                diasRestantes = 5,
                prioridad = Prioridad.ALTA
            ),

            ActividadFormativa(
                id = 10L,
                titulo = "Actividad 4",
                descripcion = "Actividad de prueba.",
                progreso = 100,
                diasRestantes = 0,
                prioridad = Prioridad.ALTA
            )
        )
    )
}