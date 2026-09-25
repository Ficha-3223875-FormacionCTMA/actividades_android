package com.example.miformacionctma.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.miformacionctma.data.local.dao.ActividadDao
import com.example.miformacionctma.data.local.dao.EvidenciaDao
import com.example.miformacionctma.data.local.entity.ActividadEntity
import com.example.miformacionctma.data.local.entity.EvidenciaEntity

@Database(
    entities = [
        ActividadEntity::class,
        EvidenciaEntity::class
    ],
    version = 3,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun actividadDao(): ActividadDao

    abstract fun evidenciaDao(): EvidenciaDao

}