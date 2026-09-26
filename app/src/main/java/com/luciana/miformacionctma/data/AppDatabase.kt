package com.luciana.miformacionctma.data

import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.room3.migration.Migration
import androidx.sqlite.driver.AndroidSQLiteDriver
import android.content.Context
import androidx.sqlite.execSQL

@Database(
    entities = [ActividadEntity::class, EvidenciaEntity::class],
    version = 3,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun actividadDao(): ActividadDao
    abstract fun evidenciaDao(): EvidenciaDao

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
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()
                    .also { instancia = it }
            }
        }

        val MIGRATION_1_2 = Migration(1, 2) { connection ->
            connection.execSQL(
                "ALTER TABLE actividades ADD COLUMN resuelto INTEGER NOT NULL DEFAULT 0"
            )
        }

        val MIGRATION_2_3 = Migration(2, 3) { connection ->
            connection.execSQL(
                "CREATE TABLE IF NOT EXISTS evidencias (" +
                    "id TEXT NOT NULL, " +
                    "actividadId INTEGER NOT NULL, " +
                    "localUri TEXT NOT NULL, " +
                    "mimeType TEXT NOT NULL, " +
                    "sizeBytes INTEGER NOT NULL, " +
                    "estado TEXT NOT NULL, " +
                    "creadaEnEpochMillis INTEGER NOT NULL, " +
                    "PRIMARY KEY(id), " +
                    "FOREIGN KEY(actividadId) REFERENCES actividades(id) ON DELETE CASCADE" +
                ")"
            )
            connection.execSQL(
                "CREATE INDEX IF NOT EXISTS index_evidencias_actividadId " +
                    "ON evidencias(actividadId)"
            )
        }
    }
}
