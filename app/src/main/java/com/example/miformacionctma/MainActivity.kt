
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.miformacionctma.ui.theme.MiFormacionCTMATheme

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

    var mostrarCompromiso by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Mi Formación CTMA",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Hola, $nombre"
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Aquí organizarás actividades y evidencias."
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                mostrarCompromiso = !mostrarCompromiso
            }
        ) {
            Text(text = "Ver próximo compromiso")
        }

        if (mostrarCompromiso) {

            Spacer(modifier = Modifier.height(16.dp))

            Card {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Próximo compromiso",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Completar la actividad de Android Studio."
                    )
                }
            }
        }
    }
}