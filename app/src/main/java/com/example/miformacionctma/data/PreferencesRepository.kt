package com.example.miformacionctma.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "preferencias_ctma"
)

class PreferencesRepository(
    private val context: Context
) : PreferenciasDataSource {

    companion object {
        private val ORDEN_DESCENDENTE =
            booleanPreferencesKey("orden_descendente")
    }

    override fun observarOrdenDescendente(): Flow<Boolean> {
        return context.dataStore.data.map { preferencias ->
            preferencias[ORDEN_DESCENDENTE] ?: true
        }
    }

    override suspend fun guardarOrdenDescendente(
        descendente: Boolean
    ) {
        context.dataStore.edit { preferencias ->
            preferencias[ORDEN_DESCENDENTE] =
                descendente
        }
    }
}