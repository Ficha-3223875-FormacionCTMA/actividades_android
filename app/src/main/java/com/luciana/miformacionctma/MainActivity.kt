package com.luciana.miformacionctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.luciana.miformacionctma.domain.ActividadFormativa
import com.luciana.miformacionctma.domain.Prioridad
import com.luciana.miformacionctma.domain.buscarPorTitulo
import com.luciana.miformacionctma.domain.estadoActividad
import com.luciana.miformacionctma.domain.generarResumen
import com.luciana.miformacionctma.domain.ordenarActividades
import com.luciana.miformacionctma.domain.validarActividad
import com.luciana.miformacionctma.ui.theme.MiFormacionCTMATheme
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val datos = crearDatosPantalla()

        setContent {
            MiFormacionCTMATheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->

                    ContenidoSemana2(
                        datos = datos,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

private data class PantallaDatos(
    val resumen: String,
    val actividadPrioritaria: String,
    val busqueda: String,
    val validacion: String
)

private fun crearDatosPantalla(): PantallaDatos {

    val actividades = listOf(
        ActividadFormativa(
            id = 1L,
            titulo = "Configurar Android Studio",
            descripcion = "Preparar el entorno de desarrollo",
            progreso = 100,
            diasRestantes = -2,
            prioridad = Prioridad.ALTA
        ),
        ActividadFormativa(
            id = 2L,
            titulo = "Kotlin básico",
            descripcion = "Practicar variables y condiciones",
            progreso = 80,
            diasRestantes = 1,
            prioridad = Prioridad.ALTA
        ),
        ActividadFormativa(
            id = 3L,
            titulo = "Null safety",
            descripcion = null,
            progreso = 40,
            diasRestantes = 2,
            prioridad = Prioridad.MEDIA
        ),
        ActividadFormativa(
            id = 4L,
            titulo = "Entregar evidencia",
            descripcion = "Subir las capturas del proyecto",
            progreso = 20,
            diasRestantes = -1,
            prioridad = Prioridad.ALTA
        ),
        ActividadFormativa(
            id = 5L,
            titulo = "Repasar colecciones",
            descripcion = null,
            progreso = 0,
            diasRestantes = 5,
            prioridad = Prioridad.BAJA
        )
    )

    val ordenadas = ordenarActividades(actividades)
    val primera = ordenadas.firstOrNull()

    val actividadPrioritaria = if (primera != null) {

        val descripcion = primera.descripcion
            ?.takeIf { it.isNotBlank() }
            ?: "Sin descripción registrada"

        """
            ${primera.titulo}
            Estado: ${estadoActividad(primera)}
            Prioridad: ${primera.prioridad}
            Días restantes: ${primera.diasRestantes}
            Descripción: $descripcion
        """.trimIndent()

    } else {
        "No hay actividades registradas"
    }

    val coincidencias = buscarPorTitulo(
        actividades = actividades,
        texto = " kotlin "
    )

    val resultadoBusqueda = coincidencias.firstOrNull()?.let {
        "Coincidencia encontrada: ${it.titulo}"
    } ?: "No se encontraron coincidencias"

    val errores = validarActividad(
        titulo = " ",
        progreso = 120
    )

    val resultadoValidacion = if (errores.isEmpty()) {
        "Datos válidos"
    } else {
        errores.joinToString(separator = "\n")
    }

    return PantallaDatos(
        resumen = generarResumen(actividades),
        actividadPrioritaria = actividadPrioritaria,
        busqueda = resultadoBusqueda,
        validacion = resultadoValidacion
    )
}

@Composable
private fun ContenidoSemana2(
    datos: PantallaDatos,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Mi Formación CTMA",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Semana 2 · Fundamentos de Kotlin",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(18.dp))

        TarjetaInformacion(
            titulo = "Resumen de actividades",
            contenido = datos.resumen
        )

        Spacer(modifier = Modifier.height(12.dp))

        TarjetaInformacion(
            titulo = "Actividad prioritaria",
            contenido = datos.actividadPrioritaria
        )

        Spacer(modifier = Modifier.height(12.dp))

        TarjetaInformacion(
            titulo = "Prueba de búsqueda",
            contenido = datos.busqueda
        )

        Spacer(modifier = Modifier.height(12.dp))

        TarjetaInformacion(
            titulo = "Prueba de validación",
            contenido = datos.validacion
        )

        Spacer(modifier = Modifier.height(12.dp))

        TarjetaInformacion(
            titulo = "Valores del Manifiesto Ágil",
            contenido = """
• Individuos e interacciones sobre procesos y herramientas.
• Software funcionando sobre documentación extensa.
• Colaboración con el cliente sobre negociación contractual.
• Respuesta ante el cambio sobre seguir un plan.
""".trimIndent()
        )

        Spacer(modifier = Modifier.height(12.dp))

        TarjetaInformacion(
            titulo = "Principios del Manifiesto Ágil",
            contenido = """
1. Satisfacer al cliente.
2. Aceptar cambios.
3. Entregar software funcionando frecuentemente.
4. Colaboración diaria.
5. Personas motivadas.
6. Comunicación efectiva.
7. Software funcionando como medida del progreso.
8. Ritmo sostenible.
9. Excelencia técnica.
10. Simplicidad.
11. Equipos autoorganizados.
12. Mejora continua.
""".trimIndent()
        )

        Spacer(modifier = Modifier.height(20.dp))

        TarjetaInformacion(
            titulo = "¿Qué es Scrum?",
            contenido = """
Scrum es un marco de trabajo ágil que permite desarrollar proyectos de forma organizada e incremental mediante Sprints, entregando valor al cliente en cada iteración.
""".trimIndent()
        )

        Spacer(modifier = Modifier.height(12.dp))

        TarjetaInformacion(
            titulo = "Roles de Scrum",
            contenido = """
• Product Owner: Define y prioriza los requisitos del producto.
• Scrum Master: Facilita el trabajo del equipo y elimina impedimentos.
• Equipo de Desarrollo: Diseña, programa, prueba y entrega el producto.
""".trimIndent()
        )

        Spacer(modifier = Modifier.height(12.dp))

        TarjetaInformacion(
            titulo = "Artefactos de Scrum",
            contenido = """
• Product Backlog: Lista de requisitos del proyecto.
• Sprint Backlog: Tareas seleccionadas para un Sprint.
• Incremento: Versión funcional del producto al finalizar el Sprint.
""".trimIndent()
        )

        Spacer(modifier = Modifier.height(12.dp))

        TarjetaInformacion(
            titulo = "Ceremonias de Scrum",
            contenido = """
• Sprint.
• Sprint Planning.
• Daily Scrum.
• Sprint Review.
• Sprint Retrospective.
""".trimIndent()
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun TarjetaInformacion(
    titulo: String,
    contenido: String,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = contenido)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContenidoSemana2Preview() {
    MiFormacionCTMATheme {
        ContenidoSemana2(
            datos = PantallaDatos(
                resumen = """
                    Total de actividades: 5
                    Promedio: 48.0 %
                    Completadas: 1
                    Vencidas: 1
                    Urgentes: 3
                """.trimIndent(),
                actividadPrioritaria = """
                    Entregar evidencia
                    Estado: VENCIDA
                    Prioridad: ALTA
                    Días restantes: -1
                """.trimIndent(),
                busqueda = "Coincidencia encontrada: Kotlin básico",
                validacion = """
                    El título es obligatorio
                    El progreso debe estar entre 0 y 100
                """.trimIndent()
            )
        )
    }
}