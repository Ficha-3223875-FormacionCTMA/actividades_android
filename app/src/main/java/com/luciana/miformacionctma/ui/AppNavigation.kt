package com.luciana.miformacionctma.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.luciana.miformacionctma.domain.ActividadFormativa
import com.luciana.miformacionctma.ui.screens.PantallaActividades
import com.luciana.miformacionctma.ui.screens.PantallaCrearActividad
import com.luciana.miformacionctma.ui.screens.PantallaDetalleActividad
import com.luciana.miformacionctma.ui.screens.PantallaEditarActividad
import com.luciana.miformacionctma.ui.state.FormularioActividadUiState

@Composable
fun AppNavigation(
    actividades: MutableList<ActividadFormativa>,
    onAgregarActividad: (FormularioActividadUiState) -> Unit = {},
    onEditarActividad: (
        Long,
        FormularioActividadUiState
    ) -> Unit = { _, _ -> },
    onEliminarActividad: (Long) -> Unit = {}
) {


    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "lista"
    ) {

        // LISTA
        composable("lista") {

            PantallaActividades(
                actividades = actividades,

                onActividadClick = { actividad ->
                    navController.navigate(
                        "detalle/${actividad.id}"
                    )
                },

                onCrearActividad = {
                    navController.navigate("crear")
                }
            )
        }

        // CREAR
        composable("crear") {

            PantallaCrearActividad(

                onGuardar = { formulario ->

                    onAgregarActividad(formulario)

                    navController.popBackStack()
                },

                onCancelar = {
                    navController.popBackStack()
                }
            )
        }

        // EDITAR
        composable("editar/{actividadId}") { backStackEntry ->

            val actividadId = backStackEntry.arguments
                ?.getString("actividadId")
                ?.toLongOrNull()

            val actividad = actividades.find {
                it.id == actividadId
            }

            if (actividad != null) {

                PantallaEditarActividad(

                    actividadId = actividad.id,

                    tituloInicial = actividad.titulo,

                    descripcionInicial =
                        actividad.descripcion ?: "",

                    fechaInicial = actividad.fecha,

                    prioridadInicial =
                        actividad.prioridad,

                    progresoInicial =
                        actividad.progreso,

                    onGuardar = { formulario ->

                        onEditarActividad(
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


// DETALLE
        composable("detalle/{actividadId}") { backStackEntry ->

            val actividadId = backStackEntry.arguments
                ?.getString("actividadId")
                ?.toLongOrNull()

            val actividad = actividades.find {
                it.id == actividadId
            }

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

                        onEliminarActividad(actividad.id)

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

