package com.example.miformacionctma

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.miformacionctma.data.Actividad
import com.example.miformacionctma.data.ActividadRepository
import com.example.miformacionctma.data.AuthManager
import com.example.miformacionctma.data.PreferencesRepository
import com.example.miformacionctma.domain.ActividadFormativa
import com.example.miformacionctma.domain.Prioridad
import com.example.miformacionctma.ui.screens.DetalleActividadScreen
import com.example.miformacionctma.ui.screens.FormularioActividadScreen
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(
    repository: ActividadRepository,
    preferencesRepository: PreferencesRepository
) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    /*
     * ACTIVIDADES GUARDADAS EN ROOM
     */
    val actividadesLocales by repository
        .observarActividadesLocales()
        .collectAsState(initial = emptyList<Actividad>())

    /*
     * TEXTO DE BÚSQUEDA
     */
    var textoBusqueda by remember {
        mutableStateOf("")
    }

    /*
     * ACTIVIDADES FILTRADAS DESDE ROOM
     */
    val actividadesFiltradas by repository
        .buscarActividadesLocales(textoBusqueda)
        .collectAsState(initial = emptyList<Actividad>())

    /*
     * PREFERENCIA DE ORDEN
     *
     * Se guarda usando SharedPreferences.
     */
    var ordenDescendente by remember {
        mutableStateOf(
            preferencesRepository.obtenerOrdenDescendente()
        )
    }

    /*
     * SINCRONIZAR CON FASTAPI AL ABRIR LA APP
     */
    LaunchedEffect(Unit) {
        try {
            repository.sincronizarDesdeApi(
                token = AuthManager.token
            )
        } catch (e: Exception) {
            println(
                "ERROR SINCRONIZANDO ROOM: ${e.message}"
            )
        }
    }

    /*
     * LISTA QUE SE MOSTRARÁ EN PANTALLA
     */
    val listaParaMostrar: List<Actividad> =
        if (textoBusqueda.isBlank()) {
            if (ordenDescendente) {
                actividadesLocales
            } else {
                actividadesLocales.reversed()
            }
        } else {
            if (ordenDescendente) {
                actividadesFiltradas
            } else {
                actividadesFiltradas.reversed()
            }
        }

    /*
     * CONVERTIR Actividad DE DATOS
     * A ActividadFormativa PARA LA INTERFAZ
     */
    val actividadesFormativas: List<ActividadFormativa> =
        listaParaMostrar.map { actividad ->

            ActividadFormativa(
                id = actividad.id
                    .removePrefix("ACT-")
                    .toLongOrNull()
                    ?: 0L,

                titulo = actividad.titulo,

                descripcion = actividad.descripcion,

                progreso = when (actividad.estado) {
                    "COMPLETADA" -> 100
                    "EN_PROCESO" -> 50
                    else -> 0
                },

                diasRestantes = 5,

                prioridad = Prioridad.MEDIA
            )
        }

    /*
     * NAVEGACIÓN
     */
    NavHost(
        navController = navController,
        startDestination = "lista"
    ) {

        /*
         * PANTALLA PRINCIPAL
         */
        composable("lista") {

            PantallaInicio(
                nombre = "Aprendiz",

                actividades = actividadesFormativas,

                textoBusqueda = textoBusqueda,

                onTextoBusqueda = {
                    textoBusqueda = it
                },

                ordenDescendente = ordenDescendente,

                onCambiarOrden = {

                    val nuevoOrden =
                        !ordenDescendente

                    preferencesRepository
                        .guardarOrdenDescendente(
                            nuevoOrden
                        )

                    ordenDescendente =
                        nuevoOrden
                },

                onCrearActividad = {
                    navController.navigate("crear")
                },

                onSeleccionarActividad = { id ->

                    navController.navigate(
                        "detalle/$id"
                    )
                }
            )
        }

        /*
         * PANTALLA CREAR ACTIVIDAD
         */
        composable("crear") {

            FormularioActividadScreen(

                onGuardar = { titulo, descripcion ->

                    val nuevaActividad =
                        Actividad(
                            titulo = titulo,
                            descripcion = descripcion,
                            aprendiz = "APR-01",
                            estado = "PENDIENTE",
                            createdAt = ""
                        )

                    scope.launch {

                        try {

                            repository.crearActividad(
                                token = AuthManager.token,
                                actividad = nuevaActividad
                            )

                            navController.popBackStack()

                        } catch (e: Exception) {

                            println(
                                "ERROR CREANDO ACTIVIDAD: " +
                                        e.message
                            )
                        }
                    }
                },

                onCancelar = {
                    navController.popBackStack()
                }
            )
        }

        /*
         * PANTALLA DETALLE
         */
        composable(
            route = "detalle/{actividadId}",

            arguments = listOf(
                navArgument("actividadId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->

            val id =
                backStackEntry.arguments
                    ?.getLong("actividadId")
                    ?: 0L

            val actividad =
                actividadesFormativas.firstOrNull {
                    it.id == id
                }

            DetalleActividadScreen(

                actividad = actividad,

                onActualizar = {
                        nuevoTitulo,
                        nuevaDescripcion ->

                    if (actividad != null) {

                        scope.launch {

                            val actividadActualizada =
                                Actividad(
                                    id = "ACT-${
                                        actividad.id
                                            .toString()
                                            .padStart(
                                                3,
                                                '0'
                                            )
                                    }",

                                    titulo = nuevoTitulo,

                                    descripcion =
                                        nuevaDescripcion,

                                    aprendiz = "APR-01",

                                    estado = when {

                                        actividad.progreso ==
                                                100 ->
                                            "COMPLETADA"

                                        actividad.progreso >
                                                0 ->
                                            "EN_PROCESO"

                                        else ->
                                            "PENDIENTE"
                                    },

                                    createdAt = ""
                                )

                            repository.actualizarLocal(
                                actividadActualizada
                            )

                            navController.popBackStack()
                        }
                    }
                },

                onEliminar = {

                    if (actividad != null) {

                        scope.launch {

                            val actividadEliminar =
                                Actividad(
                                    id = "ACT-${
                                        actividad.id
                                            .toString()
                                            .padStart(
                                                3,
                                                '0'
                                            )
                                    }",

                                    titulo =
                                        actividad.titulo,

                                    descripcion =
                                        actividad.descripcion
                                            ?: "",

                                    aprendiz =
                                        "APR-01",

                                    estado =
                                        "PENDIENTE",

                                    createdAt = ""
                                )

                            repository.eliminarLocal(
                                actividadEliminar
                            )

                            navController.popBackStack()
                        }
                    }
                },

                onVolver = {
                    navController.popBackStack()
                }
            )
        }
    }
}