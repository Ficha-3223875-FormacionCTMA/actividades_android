package com.luciana.miformacionctma
import com.luciana.miformacionctma.domain.calcularDiasRestantes
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.luciana.miformacionctma.data.PreferenciasRepository
import com.luciana.miformacionctma.domain.ActividadFormativa
import com.luciana.miformacionctma.domain.Prioridad
import com.luciana.miformacionctma.ui.AppNavigation
import com.luciana.miformacionctma.ui.state.FormularioActividadUiState
import com.luciana.miformacionctma.ui.theme.MiFormacionCTMATheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MiFormacionCTMATheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    MiFormacionApp()
                }
            }
        }
    }
}

@Composable
fun MiFormacionApp() {

    val context = LocalContext.current

    val repository = remember {
        PreferenciasRepository(context)
    }

    val actividades = remember {
        mutableStateListOf<ActividadFormativa>()
    }

    val scope = rememberCoroutineScope()

    var cargando by remember {
        mutableStateOf(true)
    }

    /*
     * Cargar las actividades guardadas al iniciar la aplicación.
     */
    LaunchedEffect(Unit) {

        val actividadesGuardadas =
            repository.cargarActividades()

        if (actividadesGuardadas == null) {

            // Primera ejecución: crear actividades iniciales.
            actividades.addAll(
                listOf(
                    ActividadFormativa(
                        id = 1L,
                        titulo = "Introducción a Android Studio",
                        descripcion = "Configurar el entorno de desarrollo.",
                        progreso = 100,
                        diasRestantes = 0,
                        prioridad = Prioridad.ALTA
                    ),
                    ActividadFormativa(
                        id = 2L,
                        titulo = "Fundamentos de Kotlin",
                        descripcion = "Repasar variables, funciones y clases.",
                        progreso = 80,
                        diasRestantes = 2,
                        prioridad = Prioridad.ALTA
                    ),
                    ActividadFormativa(
                        id = 3L,
                        titulo = "Jetpack Compose",
                        descripcion = "Construir interfaces utilizando Compose.",
                        progreso = 60,
                        diasRestantes = 5,
                        prioridad = Prioridad.ALTA
                    ),
                    ActividadFormativa(
                        id = 4L,
                        titulo = "Componentes Material 3",
                        descripcion = "Utilizar componentes visuales de Material 3.",
                        progreso = 40,
                        diasRestantes = 7,
                        prioridad = Prioridad.MEDIA
                    ),
                    ActividadFormativa(
                        id = 5L,
                        titulo = "Listas con LazyColumn",
                        descripcion = "Implementar listas eficientes en Compose.",
                        progreso = 20,
                        diasRestantes = 3,
                        prioridad = Prioridad.MEDIA
                    ),
                    ActividadFormativa(
                        id = 6L,
                        titulo = "Accesibilidad en aplicaciones móviles",
                        descripcion = "Aplicar buenas prácticas de accesibilidad.",
                        progreso = 0,
                        diasRestantes = 10,
                        prioridad = Prioridad.MEDIA
                    ),
                    ActividadFormativa(
                        id = 7L,
                        titulo = "Diseño adaptable",
                        descripcion = "Adaptar la interfaz a diferentes tamaños de pantalla.",
                        progreso = 30,
                        diasRestantes = 8,
                        prioridad = Prioridad.BAJA
                    ),
                    ActividadFormativa(
                        id = 8L,
                        titulo = "Pruebas de la interfaz",
                        descripcion = "Realizar pruebas de los componentes Compose.",
                        progreso = 0,
                        diasRestantes = 4,
                        prioridad = Prioridad.ALTA
                    ),
                    ActividadFormativa(
                        id = 9L,
                        titulo = "Documentación del proyecto",
                        descripcion = "Registrar las decisiones y funcionalidades implementadas.",
                        progreso = 50,
                        diasRestantes = 6,
                        prioridad = Prioridad.BAJA
                    ),
                    ActividadFormativa(
                        id = 10L,
                        titulo = "Entrega de la Semana 3",
                        descripcion = "Preparar el proyecto para la entrega.",
                        progreso = 10,
                        diasRestantes = 1,
                        prioridad = Prioridad.ALTA
                    )
                )
            )

            repository.guardarActividades(actividades)

        } else {

            // Cargar las actividades que ya estaban guardadas.
            actividades.addAll(actividadesGuardadas)
        }

        cargando = false
    }

    if (cargando) {
        return
    }

    AppNavigation(
        actividades = actividades,

        /*
         * CREAR ACTIVIDAD
         */
        onAgregarActividad = { formulario: FormularioActividadUiState ->

            val nuevoId =
                (actividades.maxOfOrNull { it.id } ?: 0L) + 1L

            val nuevaActividad = ActividadFormativa(
                id = nuevoId,
                titulo = formulario.titulo,
                descripcion = formulario.descripcion,
                progreso = formulario.progreso.toIntOrNull() ?: 0,
                diasRestantes = calcularDiasRestantes(formulario.fecha),
                prioridad = formulario.prioridad
            )

            actividades.add(nuevaActividad)

            scope.launch {
                repository.guardarActividades(actividades)
            }
        },

        /*
         * EDITAR ACTIVIDAD
         */
        onEditarActividad = { id, formulario ->

            val indice =
                actividades.indexOfFirst { it.id == id }

            if (indice != -1) {

                actividades[indice] =
                    actividades[indice].copy(
                        titulo = formulario.titulo,
                        descripcion = formulario.descripcion,
                        progreso =
                            formulario.progreso.toIntOrNull() ?: 0,
                        prioridad = formulario.prioridad
                    )

                scope.launch {
                    repository.guardarActividades(actividades)
                }
            }
        },

        /*
         * ELIMINAR ACTIVIDAD
         */
        onEliminarActividad = { id ->

            actividades.removeAll {
                it.id == id
            }

            scope.launch {
                repository.guardarActividades(actividades)
            }
        }
    )
}
