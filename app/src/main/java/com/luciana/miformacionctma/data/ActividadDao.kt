package com.luciana.miformacionctma.data

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ActividadDao {

    @Query("SELECT * FROM actividades ORDER BY id ASC")
    fun observarTodas(): Flow<List<ActividadEntity>>

    @Query("SELECT * FROM actividades WHERE titulo LIKE '%' || :texto || '%' ORDER BY id ASC")
    fun buscarPorTitulo(texto: String): Flow<List<ActividadEntity>>

    @Query("SELECT * FROM actividades WHERE id = :id LIMIT 1")
    suspend fun buscarPorId(id: Long): ActividadEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(actividad: ActividadEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodas(actividades: List<ActividadEntity>)

    @Update
    suspend fun actualizar(actividad: ActividadEntity)

    @Delete
    suspend fun eliminar(actividad: ActividadEntity)

    @Query("DELETE FROM actividades WHERE id = :id")
    suspend fun eliminarPorId(id: Long)

    @Query("SELECT COUNT(*) FROM actividades")
    suspend fun contar(): Int
}
