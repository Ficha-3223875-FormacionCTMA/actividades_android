package com.example.miformacionctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.miformacionctma.data.ActividadRepository
import com.example.miformacionctma.data.PreferencesRepository
import com.example.miformacionctma.data.RetrofitClient
import com.example.miformacionctma.data.local.database.DatabaseProvider
import com.example.miformacionctma.domain.ActividadFormativa
import com.example.miformacionctma.domain.actividadesUrgentes
import com.example.miformacionctma.domain.promedioProgreso
import com.example.miformacionctma.ui.MiFormacionCTMATheme
import com.example.miformacionctma.ui.components.TarjetaActividad

class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        val database =
            DatabaseProvider.getDatabase(
                applicationContext
            )

        val repository =
            ActividadRepository(
                api = RetrofitClient.apiService,
                dao = database.actividadDao(),
                evidenciaDao = database.evidenciaDao()
            )

        val preferencesRepository =
            PreferencesRepository(
                applicationContext
            )

        setContent {

            MiFormacionCTMATheme {

                AppNavigation(
                    repository = repository,
                    preferencesRepository =
                        preferencesRepository
                )
            }
        }
    }
}

@Composable
fun PantallaInicio(
    nombre: String = "Aprendiz",
    actividades: List<ActividadFormativa>,
    onCrearActividad: () -> Unit = {},
    onSeleccionarActividad: (Long) -> Unit = {},
    textoBusqueda: String = "",
    onTextoBusqueda: (String) -> Unit = {},
    ordenDescendente: Boolean = true,
    onCambiarOrden: () -> Unit = {}
) {

    val promedio =
        promedioProgreso(actividades)

    val urgentes =
        actividadesUrgentes(actividades)

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
                        style =
                            MaterialTheme
                                .typography
                                .headlineMedium
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = "Hola, $nombre"
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text =
                            "Aquí organizarás actividades y evidencias."
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    OutlinedTextField(
                        value = textoBusqueda,
                        onValueChange =
                            onTextoBusqueda,
                        modifier =
                            Modifier.fillMaxWidth(),
                        label = {
                            Text("Buscar actividad")
                        },
                        singleLine = true
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Button(
                        onClick = onCambiarOrden,
                        modifier =
                            Modifier.fillMaxWidth()
                    ) {

                        Text(
                            if (ordenDescendente) {
                                "Orden: más recientes"
                            } else {
                                "Orden: más antiguas"
                            }
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Text(
                        text =
                            "Resumen de actividades",
                        style =
                            MaterialTheme
                                .typography
                                .titleMedium
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text =
                            "Actividades registradas: ${actividades.size}"
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text =
                            "Promedio de progreso: ${
                                "%.1f".format(promedio)
                            }%"
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text =
                            "Actividades urgentes: ${urgentes.size}"
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Button(
                        onClick = onCrearActividad,
                        modifier =
                            Modifier.fillMaxWidth()
                    ) {
                        Text("Crear actividad")
                    }

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Button(
                        onClick = {
                            mostrarCompromiso =
                                !mostrarCompromiso
                        },
                        modifier =
                            Modifier.fillMaxWidth()
                    ) {

                        Text(
                            if (mostrarCompromiso) {
                                "Ocultar compromiso"
                            } else {
                                "Ver compromiso"
                            }
                        )
                    }

                    if (mostrarCompromiso) {

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Card {

                            Column(
                                modifier =
                                    Modifier.padding(16.dp)
                            ) {

                                Text(
                                    text =
                                        "Compromiso de formación",
                                    style =
                                        MaterialTheme
                                            .typography
                                            .titleMedium
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(8.dp)
                                )

                                Text(
                                    text =
                                        "Organiza tus actividades, registra tus avances y mantén actualizadas tus evidencias."
                                )
                            }
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Text(
                        text = "Mis actividades",
                        style =
                            MaterialTheme
                                .typography
                                .titleMedium
                    )
                }
            }

            items(
                items = actividades,
                key = { it.id }
            ) { actividad ->

                TarjetaActividad(
                    actividad = actividad,
                    onClick = {
                        onSeleccionarActividad(
                            actividad.id
                        )
                    }
                )
            }
        }
    }
}