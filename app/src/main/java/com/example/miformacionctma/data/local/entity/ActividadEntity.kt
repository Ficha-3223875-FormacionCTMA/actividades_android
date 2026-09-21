package com.example.miformacionctma.data.local.entity

import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(
    tableName = "actividades",
    indices = [
        Index(value = ["aprendiz"]),
        Index(value = ["estado"])
    ]
)
data class ActividadEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val titulo: String,

    val descripcion: String,

    val aprendiz: String,

    val estado: String = "PENDIENTE",

    val createdAt: String,

    // NUEVO CAMPO DE LA VERSIÓN 2
    val resuelto: Boolean = false
)