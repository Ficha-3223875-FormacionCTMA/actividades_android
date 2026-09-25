package com.example.miformacionctma

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.example.miformacionctma.data.Actividad
import com.example.miformacionctma.data.ActividadRepository
import com.example.miformacionctma.data.PreferencesRepository
import com.example.miformacionctma.domain.ActividadFormativa
import com.example.miformacionctma.domain.Prioridad
import com.example.miformacionctma.ui.EvidenciaScreen
import com.example.miformacionctma.ui.screens.DetalleActividadScreen
import com.example.miformacionctma.ui.screens.FormularioActividadScreen
import com.example.miformacionctma.ui.viewmodel.ActividadViewModel
import com.example.miformacionctma.ui.viewmodel.ListadoUiState

@Composable
fun AppNavigation(
    repository: ActividadRepository,
    preferencesRepository: PreferencesRepository
) {
    val navController = rememberNavController()

    val viewModel: ActividadViewModel =
        viewModel(
            factory = ActividadViewModelFactory(
                repository,
                preferencesRepository
            )
        )

    val listadoUiState by viewModel
        .listadoUiState
        .collectAsStateWithLifecycle()

    val operacionUiState by viewModel
        .operacionUiState
        .collectAsStateWithLifecycle()

    val textoBusqueda by viewModel
        .obtenerTextoBusqueda()
        .collectAsStateWithLifecycle()

    val ordenDescendente by viewModel
        .obtenerOrdenDescendente()
        .collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sincronizar()
    }

    NavHost(
        navController = navController,
        startDestination = "lista"
    ) {

        // =========================================
        // LISTA PRINCIPAL
        // =========================================

        composable("lista") {

            when (val estado = listadoUiState) {

                ListadoUiState.Cargando -> {
                    EstadoCargando()
                }

                ListadoUiState.Vacio -> {

                    EstadoVacio(
                        onCrearActividad = {

                            viewModel.reiniciarEstadoOperacion()

                            navController.navigate("crear")
                        }
                    )
                }

                is ListadoUiState.Error -> {

                    EstadoError(
                        mensaje = estado.mensaje,
                        onReintentar = {
                            viewModel.sincronizar()
                        }
                    )
                }

                is ListadoUiState.Contenido -> {

                    val actividades = estado.actividades

                    val actividadesFormativas =
                        actividades.map { actividad ->

                            val idNumerico =
                                actividad.id
                                    .removePrefix("ACT-")
                                    .toLongOrNull()
                                    ?: 0L

                            ActividadFormativa(
                                id = idNumerico,
                                titulo = actividad.titulo,
                                descripcion = actividad.descripcion,
                                progreso =
                                    when (actividad.estado) {
                                        "COMPLETADA" -> 100
                                        "EN_PROCESO" -> 50
                                        else -> 0
                                    },
                                diasRestantes = 5,
                                prioridad = Prioridad.MEDIA
                            )
                        }

                    PantallaInicio(
                        nombre = "Aprendiz",
                        actividades = actividadesFormativas,
                        textoBusqueda = textoBusqueda,

                        onTextoBusqueda = { texto ->
                            viewModel.cambiarBusqueda(texto)
                        },

                        ordenDescendente = ordenDescendente,

                        onCambiarOrden = {
                            viewModel.cambiarOrden()
                        },

                        onCrearActividad = {

                            viewModel.reiniciarEstadoOperacion()

                            navController.navigate("crear")
                        },

                        onSeleccionarActividad = { id ->

                            navController.navigate(
                                "detalle/$id"
                            )
                        }
                    )
                }
            }
        }

        // =========================================
        // CREAR ACTIVIDAD
        // =========================================

        composable("crear") {

            LaunchedEffect(Unit) {
                viewModel.reiniciarEstadoOperacion()
            }

            FormularioActividadScreen(

                onGuardar = { titulo, descripcion ->

                    viewModel.crearActividad(
                        titulo = titulo,
                        descripcion = descripcion,

                        onCompletado = {
                            navController.popBackStack()
                        }
                    )
                },

                onCancelar = {
                    navController.popBackStack()
                },

                operacionUiState = operacionUiState
            )
        }

        // =========================================
        // DETALLE DE ACTIVIDAD
        // =========================================

        composable(
            route = "detalle/{actividadId}",

            arguments = listOf(
                navArgument("actividadId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->

            val id =
                backStackEntry.arguments?.getLong("actividadId")
                    ?: 0L

            // Actividad obtenida directamente desde Room.
            val actividadActual by viewModel
                .observarActividadLocal(id)
                .collectAsState(initial = null)

            // Evidencias obtenidas desde Room.
            val evidencias by repository
                .observarEvidencias(id)
                .collectAsState(initial = emptyList())

            val actividadFormativa =
                actividadActual?.let { item ->

                    ActividadFormativa(

                        id = id,

                        titulo = item.titulo,

                        descripcion = item.descripcion,

                        progreso =
                            when (item.estado) {
                                "COMPLETADA" -> 100
                                "EN_PROCESO" -> 50
                                else -> 0
                            },

                        diasRestantes = 5,

                        prioridad = Prioridad.MEDIA
                    )
                }

            DetalleActividadScreen(

                actividad = actividadFormativa,

                evidencias = evidencias,

                onActualizar = {
                        nuevoTitulo,
                        nuevaDescripcion ->

                    val actividad =
                        actividadActual

                    if (actividad != null) {

                        val actualizada = Actividad(

                            id = actividad.id,

                            titulo = nuevoTitulo,

                            descripcion = nuevaDescripcion,

                            aprendiz = actividad.aprendiz,

                            estado = actividad.estado,

                            createdAt = actividad.createdAt
                        )

                        viewModel.actualizarActividad(
                            actualizada
                        )

                        navController.popBackStack()
                    }
                },

                onEliminar = {

                    val actividad =
                        actividadActual

                    if (actividad != null) {

                        viewModel.eliminarActividad(
                            actividad
                        )

                        navController.popBackStack()
                    }
                },

                onAgregarEvidencia = {

                    navController.navigate(
                        "evidencia/$id"
                    )
                },

                onVolver = {

                    navController.popBackStack()
                }
            )
        }

        // =========================================
        // EVIDENCIA FOTOGRÁFICA
        // =========================================

        composable(
            route = "evidencia/{actividadId}",

            arguments = listOf(
                navArgument("actividadId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->

            val actividadId =
                backStackEntry.arguments?.getLong("actividadId")
                    ?: 0L

            EvidenciaScreen(

                actividadId = actividadId,

                repository = repository,

                onVolver = {
                    navController.popBackStack()
                }
            )
        }
    }
}

// =========================================
// ESTADO CARGANDO
// =========================================

@Composable
private fun EstadoCargando() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center
    ) {

        CircularProgressIndicator()

        Text(
            text = "Cargando actividades...",

            modifier = Modifier.padding(
                top = 16.dp
            ),

            style = MaterialTheme.typography.bodyLarge
        )
    }
}

// =========================================
// BASE DE DATOS VACÍA
// =========================================

@Composable
private fun EstadoVacio(
    onCrearActividad: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "No hay actividades todavía",

            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Puedes crear tu primera actividad.",

            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 16.dp
            )
        )

        Button(
            onClick = onCrearActividad
        ) {

            Text(
                text = "Crear actividad"
            )
        }
    }
}

// =========================================
// ESTADO ERROR
// =========================================

@Composable
private fun EstadoError(
    mensaje: String,
    onReintentar: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "No se pudieron cargar las actividades",

            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = mensaje,

            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 16.dp
            )
        )

        Button(
            onClick = onReintentar
        ) {

            Text(
                text = "Reintentar"
            )
        }
    }
}

// =========================================
// FACTORY DEL VIEWMODEL
// =========================================

class ActividadViewModelFactory(
    private val repository: ActividadRepository,
    private val preferencesRepository: PreferencesRepository
) : androidx.lifecycle.ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : androidx.lifecycle.ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (
            modelClass.isAssignableFrom(
                ActividadViewModel::class.java
            )
        ) {

            return ActividadViewModel(
                repository,
                preferencesRepository
            ) as T
        }

        throw IllegalArgumentException(
            "ViewModel desconocido"
        )
    }
}