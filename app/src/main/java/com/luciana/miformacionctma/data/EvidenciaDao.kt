package com.luciana.miformacionctma.data

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EvidenciaDao {
    @Query("SELECT * FROM evidencias WHERE actividadId = :actividadId ORDER BY creadaEnEpochMillis DESC")
    fun observarPorActividad(actividadId: Long): Flow<List<EvidenciaEntity>>

    @Query("SELECT * FROM evidencias WHERE id = :id LIMIT 1")
    suspend fun buscarPorId(id: String): EvidenciaEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(evidencia: EvidenciaEntity)

    @Query("UPDATE evidencias SET estado = :estado WHERE id = :id")
    suspend fun actualizarEstado(id: String, estado: String)

    @Query("DELETE FROM evidencias WHERE id = :id")
    suspend fun eliminarPorId(id: String)
}
