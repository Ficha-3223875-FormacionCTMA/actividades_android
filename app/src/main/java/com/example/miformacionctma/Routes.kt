package com.example.miformacionctma

import kotlinx.serialization.Serializable

@Serializable
data object ListaRoute

@Serializable
data object CrearRoute

@Serializable
data class DetalleRoute(
    val actividadId: Long
)