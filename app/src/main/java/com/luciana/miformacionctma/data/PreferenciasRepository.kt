package com.luciana.miformacionctma.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "preferencias")

class PreferenciasRepository(
    private val context: Context
) {

    private val busquedaKey = stringPreferencesKey("busqueda")
    private val vistaKey = stringPreferencesKey("vista")

    val busqueda: Flow<String> = context.dataStore.data.map { preferencias ->
        preferencias[busquedaKey] ?: ""
    }

    val vista: Flow<String> = context.dataStore.data.map { preferencias ->
        preferencias[vistaKey] ?: "lista"
    }

    suspend fun guardarBusqueda(texto: String) {
        context.dataStore.edit { preferencias ->
            preferencias[busquedaKey] = texto
        }
    }

    suspend fun guardarVista(vista: String) {
        context.dataStore.edit { preferencias ->
            preferencias[vistaKey] = vista
        }
    }
}
