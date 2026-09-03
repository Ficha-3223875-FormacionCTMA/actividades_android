package com.example.miformacionctma.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.miformacionctma.model.ActividadFormativa
import com.example.miformacionctma.model.actividadesUrgentes
import com.example.miformacionctma.model.buscarPorTitulo
import com.example.miformacionctma.model.ordenarActividades
import com.example.miformacionctma.model.promedioProgreso
import com.example.miformacionctma.ui.components.EstadoVacio
import com.example.miformacionctma.ui.components.ListaActividades
import com.example.miformacionctma.ui.components.TarjetaActividad

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaScreen(
    actividades: List<ActividadFormativa>,
    onCrear: () -> Unit,
    onActividadClick: (Long) -> Unit,
    onEliminar: (Long) -> Unit = {},
    modifier: Modifier = Modifier
) {

    var busqueda by remember {
        mutableStateOf("")
    }

    val visibles = ordenarActividades(
        buscarPorTitulo(actividades, busqueda)
    )

    val promedio = promedioProgreso(actividades)

    val urgentes = actividadesUrgentes(actividades).size

    // Detecta si estamos en celular o pantalla ancha
    val configuration = LocalConfiguration.current
    val pantallaAncha = configuration.screenWidthDp >= 600

    Scaffold(
        modifier = modifier,

        topBar = {
            TopAppBar(
                title = {
                    Text("Mi Formación CTMA")
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = onCrear
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar actividad"
                )
            }
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Hola, Aprendiz 👋",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Organiza tus compromisos y evidencias formativas.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // -----------------------------
            // RESUMEN DE ACTIVIDADES
            // -----------------------------

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.primaryContainer
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {

                    Column {
                        Text(
                            text = "Progreso",
                            style = MaterialTheme.typography.labelLarge
                        )

                        Text(
                            text = "%.0f%%".format(promedio),
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }

                    Column {
                        Text(
                            text = "Urgentes",
                            style = MaterialTheme.typography.labelLarge
                        )

                        Text(
                            text = urgentes.toString(),
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }

                    Column {
                        Text(
                            text = "Total",
                            style = MaterialTheme.typography.labelLarge
                        )

                        Text(
                            text = actividades.size.toString(),
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // -----------------------------
            // BUSCADOR
            // -----------------------------

            OutlinedTextField(
                value = busqueda,
                onValueChange = {
                    busqueda = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Buscar actividad")
                },
                placeholder = {
                    Text("Ej: Kotlin")
                },
                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // -----------------------------
            // ESTADO VACÍO
            // -----------------------------

            if (visibles.isEmpty()) {

                EstadoVacio(
                    onCrear = onCrear,
                    modifier = Modifier.weight(1f)
                )

            } else {

                // -----------------------------
                // CELULAR: LISTA
                // -----------------------------

                if (!pantallaAncha) {

                    ListaActividades(
                        actividades = visibles,
                        onActividadClick = onActividadClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )

                } else {

                    // -----------------------------
                    // TABLET / PANTALLA ANCHA: GRID
                    // -----------------------------

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentPadding =
                            PaddingValues(vertical = 8.dp),
                        horizontalArrangement =
                            Arrangement.spacedBy(12.dp),
                        verticalArrangement =
                            Arrangement.spacedBy(12.dp)
                    ) {

                        items(
                            items = visibles,
                            key = { actividad ->
                                actividad.id
                            }
                        ) { actividad ->

                            TarjetaActividad(
                                actividad = actividad,
                                onClick = {
                                    onActividadClick(
                                        actividad.id
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}