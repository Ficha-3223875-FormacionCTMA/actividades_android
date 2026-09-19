package com.example.miformacionctma.data

data class Actividad(
    val id: String = "",
    val titulo: String,
    val descripcion: String,
    val aprendiz: String = "APR-01",
    val estado: String = "PENDIENTE",
    val createdAt: String = ""
)