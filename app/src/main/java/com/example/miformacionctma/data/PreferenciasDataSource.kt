package com.example.miformacionctma.data

import kotlinx.coroutines.flow.Flow

interface PreferenciasDataSource {

    fun observarOrdenDescendente(): Flow<Boolean>

    suspend fun guardarOrdenDescendente(
        descendente: Boolean
    )
}