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
import com.example.miformacionctma.ui.screens.DetalleActividadScreen
import com.example.miformacionctma.ui.screens.FormularioActividadScreen
import com.example.miformacionctma.ui.viewmodel.ActividadViewModel
import com.example.miformacionctma.ui.viewmodel.ListadoUiState

@Composable
fun AppNavigation(
    repository: ActividadRepository,
    preferencesRepository: PreferencesRepository
) {
    val navController =
        rememberNavController()

    val viewModel: ActividadViewModel =
        viewModel(
            factory =
                ActividadViewModelFactory(
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

            when (
                val estado =
                    listadoUiState
            ) {

                // ---------------------------------
                // CARGANDO
                // ---------------------------------

                ListadoUiState.Cargando -> {

                    EstadoCargando()
                }

                // ---------------------------------
                // BASE DE DATOS REALMENTE VACÍA
                // ---------------------------------

                ListadoUiState.Vacio -> {

                    EstadoVacio(
                        onCrearActividad = {

                            viewModel
                                .reiniciarEstadoOperacion()

                            navController
                                .navigate("crear")
                        }
                    )
                }

                // ---------------------------------
                // ERROR
                // ---------------------------------

                is ListadoUiState.Error -> {

                    EstadoError(
                        mensaje = estado.mensaje,

                        onReintentar = {
                            viewModel.sincronizar()
                        }
                    )
                }

                // ---------------------------------
                // CONTENIDO
                //
                // IMPORTANTE:
                // Puede contener una lista vacía
                // cuando la búsqueda no encuentra
                // coincidencias.
                //
                // La pantalla principal sigue
                // apareciendo porque aquí siempre
                // usamos PantallaInicio.
                // ---------------------------------

                is ListadoUiState.Contenido -> {

                    val actividades =
                        estado.actividades

                    val actividadesFormativas =
                        actividades.map {
                                actividad ->

                            val idNumerico =
                                actividad.id
                                    .removePrefix(
                                        "ACT-"
                                    )
                                    .toLongOrNull()
                                    ?: 0L

                            ActividadFormativa(
                                id = idNumerico,

                                titulo =
                                    actividad.titulo,

                                descripcion =
                                    actividad.descripcion,

                                progreso =
                                    when (
                                        actividad.estado
                                    ) {
                                        "COMPLETADA" ->
                                            100

                                        "EN_PROCESO" ->
                                            50

                                        else ->
                                            0
                                    },

                                diasRestantes = 5,

                                prioridad =
                                    Prioridad.MEDIA
                            )
                        }

                    PantallaInicio(
                        nombre = "Aprendiz",

                        actividades =
                            actividadesFormativas,

                        textoBusqueda =
                            textoBusqueda,

                        onTextoBusqueda = {
                                texto ->

                            viewModel
                                .cambiarBusqueda(
                                    texto
                                )
                        },

                        ordenDescendente =
                            ordenDescendente,

                        onCambiarOrden = {

                            viewModel
                                .cambiarOrden()
                        },

                        onCrearActividad = {

                            viewModel
                                .reiniciarEstadoOperacion()

                            navController
                                .navigate("crear")
                        },

                        onSeleccionarActividad = {
                                id ->

                            navController
                                .navigate(
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

                viewModel
                    .reiniciarEstadoOperacion()
            }

            FormularioActividadScreen(

                onGuardar = {
                        titulo,
                        descripcion ->

                    viewModel.crearActividad(

                        titulo = titulo,

                        descripcion = descripcion,

                        onCompletado = {

                            navController
                                .popBackStack()
                        }
                    )
                },

                onCancelar = {

                    navController
                        .popBackStack()
                },

                operacionUiState =
                    operacionUiState
            )
        }

        // =========================================
        // DETALLE
        // =========================================

        composable(
            route =
                "detalle/{actividadId}",

            arguments =
                listOf(
                    navArgument(
                        "actividadId"
                    ) {
                        type =
                            NavType.LongType
                    }
                )
        ) { backStackEntry ->

            val id =
                backStackEntry
                    .arguments
                    ?.getLong(
                        "actividadId"
                    )
                    ?: 0L

            val actividades:
                    List<Actividad> =

                if (
                    listadoUiState
                            is ListadoUiState.Contenido
                ) {

                    (
                            listadoUiState
                                    as ListadoUiState.Contenido
                            ).actividades

                } else {

                    emptyList()
                }

            val actividad =
                actividades.firstOrNull {
                        item ->

                    val idNumerico =
                        item.id
                            .removePrefix(
                                "ACT-"
                            )
                            .toLongOrNull()
                            ?: 0L

                    idNumerico == id
                }

            val actividadFormativa =
                if (
                    actividad != null
                ) {

                    ActividadFormativa(

                        id = id,

                        titulo =
                            actividad.titulo,

                        descripcion =
                            actividad.descripcion,

                        progreso =
                            when (
                                actividad.estado
                            ) {

                                "COMPLETADA" ->
                                    100

                                "EN_PROCESO" ->
                                    50

                                else ->
                                    0
                            },

                        diasRestantes = 5,

                        prioridad =
                            Prioridad.MEDIA
                    )

                } else {

                    null
                }

            DetalleActividadScreen(

                actividad =
                    actividadFormativa,

                onActualizar = {
                        nuevoTitulo,
                        nuevaDescripcion ->

                    if (
                        actividad != null
                    ) {

                        val actualizada =
                            Actividad(

                                id =
                                    actividad.id,

                                titulo =
                                    nuevoTitulo,

                                descripcion =
                                    nuevaDescripcion,

                                aprendiz =
                                    actividad.aprendiz,

                                estado =
                                    actividad.estado,

                                createdAt =
                                    actividad.createdAt
                            )

                        viewModel
                            .actualizarActividad(
                                actualizada
                            )

                        navController
                            .popBackStack()
                    }
                },

                onEliminar = {

                    if (
                        actividad != null
                    ) {

                        viewModel
                            .eliminarActividad(
                                actividad
                            )

                        navController
                            .popBackStack()
                    }
                },

                onVolver = {

                    navController
                        .popBackStack()
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
        modifier =
            Modifier
                .fillMaxSize()
                .padding(24.dp),

        horizontalAlignment =
            Alignment.CenterHorizontally,

        verticalArrangement =
            Arrangement.Center
    ) {

        CircularProgressIndicator()

        Text(
            text =
                "Cargando actividades...",

            modifier =
                Modifier.padding(
                    top = 16.dp
                ),

            style =
                MaterialTheme
                    .typography
                    .bodyLarge
        )
    }
}

// =========================================
// BASE DE DATOS REALMENTE VACÍA
// =========================================

@Composable
private fun EstadoVacio(
    onCrearActividad: () -> Unit
) {

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(24.dp),

        horizontalAlignment =
            Alignment.CenterHorizontally,

        verticalArrangement =
            Arrangement.Center
    ) {

        Text(
            text =
                "No hay actividades todavía",

            style =
                MaterialTheme
                    .typography
                    .headlineSmall
        )

        Text(
            text =
                "Puedes crear tu primera actividad.",

            modifier =
                Modifier.padding(
                    top = 8.dp,
                    bottom = 16.dp
                )
        )

        Button(
            onClick =
                onCrearActividad
        ) {

            Text(
                text =
                    "Crear actividad"
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
        modifier =
            Modifier
                .fillMaxSize()
                .padding(24.dp),

        horizontalAlignment =
            Alignment.CenterHorizontally,

        verticalArrangement =
            Arrangement.Center
    ) {

        Text(
            text =
                "No se pudieron cargar las actividades",

            style =
                MaterialTheme
                    .typography
                    .headlineSmall
        )

        Text(
            text =
                mensaje,

            modifier =
                Modifier.padding(
                    top = 8.dp,
                    bottom = 16.dp
                )
        )

        Button(
            onClick =
                onReintentar
        ) {

            Text(
                text =
                    "Reintentar"
            )
        }
    }
}

// =========================================
// FACTORY DEL VIEWMODEL
// =========================================

class ActividadViewModelFactory(
    private val repository:
    ActividadRepository,

    private val preferencesRepository:
    PreferencesRepository
) : androidx.lifecycle.ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T :
    androidx.lifecycle.ViewModel> create(
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