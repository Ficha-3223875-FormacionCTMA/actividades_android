package com.example.miformacionctma.data.local.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
ALTER TABLE actividades
ADD COLUMN resuelto INTEGER NOT NULL DEFAULT 0
""".trimIndent()
        )
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
CREATE TABLE IF NOT EXISTS evidencias (
id TEXT NOT NULL,
actividadId INTEGER NOT NULL,
localUri TEXT NOT NULL,
mimeType TEXT NOT NULL,
sizeBytes INTEGER NOT NULL,
estado TEXT NOT NULL,
creadaEnEpochMillis INTEGER NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY(actividadId)
REFERENCES actividades(id)
ON DELETE CASCADE
)
""".trimIndent()
        )

        database.execSQL(
            """
        CREATE INDEX IF NOT EXISTS index_evidencias_actividadId
        ON evidencias(actividadId)
        """.trimIndent()
        )
    }

}