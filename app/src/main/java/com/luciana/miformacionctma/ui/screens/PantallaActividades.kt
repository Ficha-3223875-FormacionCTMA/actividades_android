package com.luciana.miformacionctma.ui.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.luciana.miformacionctma.viewmodel.ListadoUiState
import com.luciana.miformacionctma.viewmodel.OperacionUiState
import com.luciana.miformacionctma.viewmodel.RefreshUiState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaActividades(
    estado: ListadoUiState,
    textoBusqueda: String,
    vista: String = "lista",
    operacion: OperacionUiState = OperacionUiState.Inactiva,
    refreshState: RefreshUiState = RefreshUiState.Idle,
    onVistaChange: (String) -> Unit = {},
    onBusquedaChange: (String) -> Unit,
    onActividadClick: (ActividadFormativa) -> Unit = {},
    onCrearActividad: () -> Unit = {},
    onSincronizar: () -> Unit = {},
    onReintentarSincronizacion: () -> Unit = {},
    onReintentar: () -> Unit = {}
) {
    val notificationLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { /* La negativa no bloquea la aplicación. */ }

    val actividades =
        (estado as? ListadoUiState.Contenido)?.actividades.orEmpty()

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
                text = "Consulta tus actividades, estados y progreso."
            )

            Button(
                onClick = onCrearActividad,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Text("Crear actividad")
            }

            TextButton(
                onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Activar recordatorios")
            }

            TextButton(
                onClick = onSincronizar,
                enabled = operacion !is OperacionUiState.Sincronizando,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sincronizar actividades desde API")
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                Text("Vista")

                Column {

                    TextButton(
                        onClick = {
                            onVistaChange("lista")
                        }
                    ) {
                        Text(
                            if (vista == "lista") {
                                "✓ Lista"
                            } else {
                                "Lista"
                            }
                        )
                    }

                    TextButton(
                        onClick = {
                            onVistaChange("cuadricula")
                        }
                    ) {
                        Text(
                            if (vista == "cuadricula") {
                                "✓ Cuadrícula"
                            } else {
                                "Cuadrícula"
                            }
                        )
                    }
                }
            }

            when (operacion) {
                OperacionUiState.EnCurso -> Text(
                    text = "Guardando cambios...",
                    modifier = Modifier.padding(top = 4.dp)
                )
                else -> Unit
            }

            when (refreshState) {
                RefreshUiState.Running -> Text(
                    text = "Sincronizando con la API...",
                    modifier = Modifier.padding(top = 4.dp)
                )
                is RefreshUiState.Success -> {
                    val fecha = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                        .format(Date(refreshState.atMillis))
                    Text(
                        text = "Actualizado: $fecha (${refreshState.cantidad} actividades)",
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                is RefreshUiState.Failed -> {
                    Text(
                        text = refreshState.mensaje,
                        modifier = Modifier.padding(top = 4.dp),
                        color = MaterialTheme.colorScheme.error
                    )
                    TextButton(
                        onClick = onReintentarSincronizacion,
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Reintentar sincronización") }
                }
                RefreshUiState.Idle -> Unit
            }

            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = onBusquedaChange,
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

            when (estado) {

                ListadoUiState.Cargando -> {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            CircularProgressIndicator()

                            Text(
                                text = "Cargando actividades...",
                                modifier = Modifier.padding(top = 12.dp)
                            )
                        }
                    }
                }

                ListadoUiState.Vacio -> {

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
                                text = if (textoBusqueda.isBlank()) {
                                    "Todavía no hay actividades."
                                } else {
                                    "Prueba con otro título."
                                }
                            )

                            if (textoBusqueda.isNotBlank()) {

                                Button(
                                    onClick = {
                                        onBusquedaChange("")
                                    }
                                ) {
                                    Text("Mostrar todas")
                                }
                            }
                        }
                    }
                }

                is ListadoUiState.Error -> {

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
                                text = "No se pudieron cargar las actividades"
                            )

                            Text(
                                text = estado.mensaje
                            )

                            Button(
                                onClick = onReintentar
                            ) {
                                Text("Reintentar")
                            }
                        }
                    }
                }

                is ListadoUiState.Contenido -> {

                    Text(
                        text = "Total: ${actividades.size} actividades",
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Text(
                        text = "Mostrando: ${actividades.size}"
                    )

                    Text(
                        text = "Promedio de progreso: ${
                            "%.1f".format(
                                promedioProgreso(actividades)
                            )
                        }%",
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {

                        BoxWithConstraints(Modifier.fillMaxSize()) {
                            val pantallaAmplia = maxWidth >= 600.dp
                            val usarCuadricula = vista == "cuadricula" || pantallaAmplia

                            if (!usarCuadricula) {

                                /* MODO COMPACTO: lista vertical. */
                                LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {

                                items(
                                    items = actividades,
                                    key = { it.id }
                                ) { actividad ->

                                    TarjetaActividad(
                                        actividad,
                                        onActividadClick
                                    )
                                }
                            }

                            } else {

                                /* MODO ADAPTABLE: dos columnas desde 600.dp. */
                                LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {

                                items(
                                    items = actividades,
                                    key = { it.id }
                                ) { actividad ->

                                    TarjetaActividad(
                                        actividad,
                                        onActividadClick
                                    )
                                }
                            }
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
        estado = ListadoUiState.Contenido(
            listOf(
                ActividadFormativa(
                    1L,
                    "Fundamentos de Kotlin",
                    "Repasar Kotlin.",
                    80,
                    2,
                    Prioridad.ALTA
                ),

                ActividadFormativa(
                    2L,
                    "Jetpack Compose",
                    "Construir interfaces.",
                    60,
                    5,
                    Prioridad.MEDIA
                ),

                ActividadFormativa(
                    3L,
                    "Diseño adaptable",
                    "Adaptar la interfaz.",
                    30,
                    8,
                    Prioridad.BAJA
                )
            )
        ),
        textoBusqueda = "",
        onBusquedaChange = {}
    )
}
@Preview(name = "Fuente 150%", fontScale = 1.5f, showBackground = true)
@Composable
fun PreviewPantallaActividadesFuenteGrande() = PreviewPantallaActividades()

@Preview(name = "Pantalla ancha", widthDp = 700, heightDp = 900, showBackground = true)
@Composable
fun PreviewPantallaActividadesAncha() = PreviewPantallaActividades()
