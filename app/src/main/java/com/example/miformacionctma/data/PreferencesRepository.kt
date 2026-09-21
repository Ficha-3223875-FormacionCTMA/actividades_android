package com.example.miformacionctma.data

import android.content.Context

class PreferencesRepository(
    private val context: Context
) {

    fun obtenerOrdenDescendente(): Boolean {
        val prefs = context.getSharedPreferences(
            "preferencias_ctma",
            Context.MODE_PRIVATE
        )

        return prefs.getBoolean(
            "orden_descendente",
            true
        )
    }

    fun guardarOrdenDescendente(
        descendente: Boolean
    ) {
        val prefs = context.getSharedPreferences(
            "preferencias_ctma",
            Context.MODE_PRIVATE
        )

        prefs.edit()
            .putBoolean(
                "orden_descendente",
                descendente
            )
            .apply()
    }
}