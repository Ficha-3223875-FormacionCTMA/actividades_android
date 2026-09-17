package com.luciana.miformacionctma.data

import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.room3.migration.Migration
import androidx.sqlite.driver.AndroidSQLiteDriver
import android.content.Context
import androidx.sqlite.execSQL

@Database(
    entities = [ActividadEntity::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun actividadDao(): ActividadDao

    companion object {
        @Volatile
        private var instancia: AppDatabase? = null

        fun obtener(context: Context): AppDatabase {
            return instancia ?: synchronized(this) {
                instancia ?: Room.databaseBuilder<AppDatabase>(
                    context.applicationContext,
                    "mi_formacion.db"
                )
                    .setDriver(AndroidSQLiteDriver())
                    .addMigrations(MIGRATION_1_2)
                    .build()
                    .also { instancia = it }
            }
        }

        val MIGRATION_1_2 = Migration(1, 2) { connection ->
            connection.execSQL(
                "ALTER TABLE actividades ADD COLUMN resuelto INTEGER NOT NULL DEFAULT 0"
            )
        }
    }
}
