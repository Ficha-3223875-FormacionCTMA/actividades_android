package com.luciana.miformacionctma.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.luciana.miformacionctma.ui.screens.PantallaActividades
import com.luciana.miformacionctma.ui.screens.PantallaCrearActividad
import com.luciana.miformacionctma.ui.screens.PantallaDetalleActividad
import com.luciana.miformacionctma.ui.screens.PantallaEditarActividad
import com.luciana.miformacionctma.ui.state.FormularioActividadUiState
import com.luciana.miformacionctma.viewmodel.ActividadViewModel
import com.luciana.miformacionctma.viewmodel.ListadoUiState

@Composable
fun AppNavigation(
    viewModel: ActividadViewModel
) {
    val navController = rememberNavController()

    val estado = viewModel.estadoLista.collectAsStateWithLifecycle().value
    val busqueda = viewModel.busqueda.collectAsStateWithLifecycle().value
    val vista = viewModel.vista.collectAsStateWithLifecycle().value
    val operacion = viewModel.operacion.collectAsStateWithLifecycle().value

    val actividades = when (estado) {
        is ListadoUiState.Contenido -> estado.actividades
        else -> emptyList()
    }

    NavHost(
        navController = navController,
        startDestination = "lista"
    ) {

        composable("lista") {
            PantallaActividades(
                estado = estado,
                textoBusqueda = busqueda,
                vista = vista,
                operacion = operacion,
                onVistaChange = viewModel::cambiarVista,
                onBusquedaChange = viewModel::cambiarBusqueda,

                onActividadClick = { actividad ->
                    navController.navigate("detalle/${actividad.id}")
                },

                onCrearActividad = {
                    navController.navigate("crear")
                },

                onReintentar = viewModel::repetir
            )
        }

        composable("crear") {
            PantallaCrearActividad(
                onGuardar = { formulario: FormularioActividadUiState ->
                    viewModel.agregar(formulario)
                    navController.popBackStack()
                },

                onCancelar = {
                    navController.popBackStack()
                }
            )
        }

        composable("editar/{actividadId}") { backStackEntry ->

            val id = backStackEntry
                .arguments
                ?.getString("actividadId")
                ?.toLongOrNull()

            val actividad = actividades.find { it.id == id }

            if (actividad != null) {
                PantallaEditarActividad(
                    actividadId = actividad.id,
                    tituloInicial = actividad.titulo,
                    descripcionInicial = actividad.descripcion ?: "",
                    fechaInicial = actividad.fecha,
                    prioridadInicial = actividad.prioridad,
                    progresoInicial = actividad.progreso,

                    onGuardar = { formulario ->
                        viewModel.editar(
                            actividad.id,
                            formulario
                        )

                        navController.popBackStack()
                    },

                    onCancelar = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable("detalle/{actividadId}") { backStackEntry ->

            val id = backStackEntry
                .arguments
                ?.getString("actividadId")
                ?.toLongOrNull()

            val actividad = actividades.find { it.id == id }

            PantallaDetalleActividad(
                actividad = actividad,

                onVolver = {
                    navController.popBackStack()
                },

                onEditar = {
                    if (actividad != null) {
                        navController.navigate(
                            "editar/${actividad.id}"
                        )
                    }
                },

                onEliminar = {
                    if (actividad != null) {

                        viewModel.eliminar(actividad.id)

                        navController.navigate("lista") {
                            popUpTo("lista") {
                                inclusive = false
                            }

                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}