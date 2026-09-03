package com.luciana.miformacionctma.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.luciana.miformacionctma.domain.ActividadFormativa
import com.luciana.miformacionctma.domain.Prioridad
import kotlinx.coroutines.flow.first
import org.json.JSONArray
import org.json.JSONObject

private val Context.dataStore by preferencesDataStore(
    name = "actividades"
)

class PreferenciasRepository(
    private val context: Context
) {

    private val actividadesKey =
        stringPreferencesKey("lista_actividades")

    suspend fun cargarActividades():
            List<ActividadFormativa>? {

        val preferencias =
            context.dataStore.data.first()

        val json = preferencias[actividadesKey]

        if (json.isNullOrEmpty()) {
            return null
        }

        return convertirDesdeJson(json)
    }

    suspend fun guardarActividades(
        actividades: List<ActividadFormativa>
    ) {

        context.dataStore.edit { preferencias ->

            preferencias[actividadesKey] =
                convertirAJson(actividades)
        }
    }

    private fun convertirAJson(
        actividades: List<ActividadFormativa>
    ): String {

        val array = JSONArray()

        actividades.forEach { actividad ->

            val objeto = JSONObject()

            objeto.put("id", actividad.id)
            objeto.put("titulo", actividad.titulo)
            objeto.put(
                "descripcion",
                actividad.descripcion ?: ""
            )
            objeto.put(
                "progreso",
                actividad.progreso
            )
            objeto.put(
                "diasRestantes",
                actividad.diasRestantes
            )
            objeto.put(
                "prioridad",
                actividad.prioridad.name
            )

            array.put(objeto)
        }

        return array.toString()
    }

    private fun convertirDesdeJson(
        json: String
    ): List<ActividadFormativa> {

        val array = JSONArray(json)

        val actividades =
            mutableListOf<ActividadFormativa>()

        for (i in 0 until array.length()) {

            val objeto =
                array.getJSONObject(i)

            actividades.add(
                ActividadFormativa(
                    id = objeto.getLong("id"),

                    titulo =
                        objeto.getString("titulo"),

                    descripcion =
                        objeto.getString(
                            "descripcion"
                        ).ifEmpty {
                            null
                        },

                    progreso =
                        objeto.getInt("progreso"),

                    diasRestantes =
                        objeto.getInt(
                            "diasRestantes"
                        ),

                    prioridad =
                        Prioridad.valueOf(
                            objeto.getString(
                                "prioridad"
                            )
                        )
                )
            )
        }

        return actividades
    }
}

