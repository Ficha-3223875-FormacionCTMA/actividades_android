package com.example.miformacionctma.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "actividades",
    indices = [
        Index(value = ["aprendiz"]),
        Index(value = ["estado"])
    ]
)
data class ActividadEntity(

    @PrimaryKey
    val id: Long,

    val titulo: String,

    val descripcion: String,

    val aprendiz: String,

    val estado: String = "PENDIENTE",

    val createdAt: String,

    val resuelto: Boolean = false
)