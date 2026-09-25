package com.example.miformacionctma.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.miformacionctma.data.local.entity.EvidenciaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EvidenciaDao {

    @Query(
        "SELECT * FROM evidencias " +
                "WHERE actividadId = :actividadId " +
                "ORDER BY creadaEnEpochMillis DESC"
    )
    fun observarPorActividad(actividadId: Long): Flow<List<EvidenciaEntity>>

    @Query("SELECT * FROM evidencias WHERE id = :id LIMIT 1")
    suspend fun obtenerPorId(id: String): EvidenciaEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardar(evidencia: EvidenciaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarTodas(evidencias: List<EvidenciaEntity>)

    @Update
    suspend fun actualizar(evidencia: EvidenciaEntity)

    @Delete
    suspend fun eliminar(evidencia: EvidenciaEntity)

    @Query("DELETE FROM evidencias WHERE id = :id")
    suspend fun eliminarPorId(id: String)

    @Query("DELETE FROM evidencias WHERE actividadId = :actividadId")
    suspend fun eliminarPorActividad(actividadId: Long)

}