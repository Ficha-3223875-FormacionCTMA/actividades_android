package com.example.miformacionctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.miformacionctma.model.*
import com.example.miformacionctma.ui.screens.*
import com.example.miformacionctma.ui.theme.MiFormacionCTMATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MiFormacionCTMATheme { MiFormacionApp() } }
    }
}

@Composable
fun MiFormacionApp() {
    var actividades by remember { mutableStateOf(datosIniciales()) }
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "lista") {
        composable("lista") {
            ListaScreen(actividades = actividades, onCrear = { navController.navigate("crear") }, onActividadClick = { id -> navController.navigate("detalle/$id") })
        }
        composable("crear") {
            FormularioRoute(actividad = null, onGuardar = { nueva -> actividades = actividades + nueva; navController.popBackStack("lista", inclusive = false) }, onCancelar = { navController.popBackStack() })
        }
        composable("detalle/{id}", arguments = listOf(navArgument("id") { type = NavType.LongType })) { entry ->
            val id = entry.arguments?.getLong("id") ?: -1L
            val actividad = actividades.firstOrNull { it.id == id }
            DetalleScreen(actividad, onBack = { navController.popBackStack() }, onEditar = { navController.navigate("editar/$id") })
        }
        composable("editar/{id}", arguments = listOf(navArgument("id") { type = NavType.LongType })) { entry ->
            val id = entry.arguments?.getLong("id") ?: -1L
            val actividad = actividades.firstOrNull { it.id == id }
            FormularioRoute(actividad = actividad, onGuardar = { actualizada -> actividades = actividades.map { if (it.id == actualizada.id) actualizada else it }; navController.popBackStack("lista", false) }, onCancelar = { navController.popBackStack() })
        }
    }
}

fun datosIniciales() = listOf(
    ActividadFormativa(1, "Configurar Android Studio", "Preparar el entorno de desarrollo", 80, -1, Prioridad.ALTA, "2026-09-01"),
    ActividadFormativa(2, "Aprender Kotlin básico", "Practicar variables, funciones y condiciones", 50, 2, Prioridad.MEDIA, "2026-09-05"),
    ActividadFormativa(3, "Crear proyecto Android", "Construir la base de Mi Formación CTMA", 100, 0, Prioridad.ALTA, "2026-09-10"),
    ActividadFormativa(4, "Aplicar null safety", "Usar operadores seguros y valores opcionales", 40, 3, Prioridad.MEDIA, "2026-09-06"),
    ActividadFormativa(5, "Construir tarjetas Compose", "Diseñar TarjetaActividad reutilizable", 65, 4, Prioridad.ALTA, "2026-09-07"),
    ActividadFormativa(6, "Implementar LazyColumn", "Mostrar la colección con claves estables", 30, 5, Prioridad.MEDIA, "2026-09-08"),
    ActividadFormativa(7, "Revisar accesibilidad", "Comprobar texto, semántica y zonas táctiles", 20, 6, Prioridad.ALTA, "2026-09-09"),
    ActividadFormativa(8, "Diseñar formulario", "Crear y validar una nueva actividad", 10, 7, Prioridad.ALTA, "2026-09-10"),
    ActividadFormativa(9, "Probar navegación", "Lista, crear, detalle y retorno", 0, 8, Prioridad.MEDIA, "2026-09-11"),
    ActividadFormativa(10, "Preparar sustentación", "Explicar decisiones y resultados", 0, 10, Prioridad.BAJA, "2026-09-13")
)
