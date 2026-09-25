package com.example.miformacionctma.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.miformacionctma.data.local.entity.ActividadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActividadDao {

    @Query("SELECT * FROM actividades ORDER BY id DESC")
    fun observarActividades(): Flow<List<ActividadEntity>>

    @Query("SELECT * FROM actividades WHERE id = :id LIMIT 1")
    suspend fun obtenerPorId(id: Long): ActividadEntity?

    @Query(
        """
        SELECT * FROM actividades
        WHERE titulo LIKE '%' || :texto || '%'
        OR descripcion LIKE '%' || :texto || '%'
        ORDER BY id DESC
        """
    )
    fun buscar(texto: String): Flow<List<ActividadEntity>>

    @Upsert
    suspend fun guardar(actividad: ActividadEntity)

    @Upsert
    suspend fun guardarTodas(
        actividades: List<ActividadEntity>
    )

    @Query("DELETE FROM actividades")
    suspend fun eliminarTodas()

    @Query("DELETE FROM actividades WHERE id = :id")
    suspend fun eliminarPorId(id: Long)
}