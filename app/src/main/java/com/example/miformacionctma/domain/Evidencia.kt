package com.example.miformacionctma.domain

data class Evidencia(
    val id: String,
    val actividadId: Long,
    val localUri: String,
    val mimeType: String,
    val sizeBytes: Long,
    val estado: String = "LOCAL",
    val creadaEnEpochMillis: Long = System.currentTimeMillis()
)