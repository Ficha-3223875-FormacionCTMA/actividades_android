package com.luciana.miformacionctma.data

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.luciana.miformacionctma.domain.ActividadFormativa
import com.luciana.miformacionctma.domain.Prioridad

@Entity(tableName = "actividades")
data class ActividadEntity(
    @PrimaryKey
    val id: Long,
    val titulo: String,
    val descripcion: String?,
    val progreso: Int,
    val diasRestantes: Int,
    val prioridad: Prioridad,
    val fecha: String,
    val resuelto: Boolean = false
)

fun ActividadEntity.toDomain() = ActividadFormativa(
    id = id,
    titulo = titulo,
    descripcion = descripcion,
    progreso = progreso,
    diasRestantes = diasRestantes,
    prioridad = prioridad,
    fecha = fecha,
    resuelto = resuelto
)

fun ActividadFormativa.toEntity(resuelto: Boolean = this.resuelto) = ActividadEntity(
    id = id,
    titulo = titulo,
    descripcion = descripcion,
    progreso = progreso,
    diasRestantes = diasRestantes,
    prioridad = prioridad,
    fecha = fecha,
    resuelto = resuelto
)
