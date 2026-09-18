package com.example.miformacionctma.data

data class Actividad(
    val id: Long = 0L,
    val titulo: String,
    val descripcion: String,
    val aprendiz: String = "APR-01"
)