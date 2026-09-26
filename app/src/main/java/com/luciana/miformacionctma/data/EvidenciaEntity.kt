package com.luciana.miformacionctma.data

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(
    tableName = "evidencias",
    foreignKeys = [
        ForeignKey(
            entity = ActividadEntity::class,
            parentColumns = ["id"],
            childColumns = ["actividadId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("actividadId")]
)
data class EvidenciaEntity(
    @PrimaryKey val id: String,
    val actividadId: Long,
    val localUri: String,
    val mimeType: String,
    val sizeBytes: Long,
    val estado: String,
    val creadaEnEpochMillis: Long
)
