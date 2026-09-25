package com.example.miformacionctma.util

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns

object EvidenciaValidator {

    private const val MAX_SIZE_BYTES = 5 * 1024 * 1024

    private val MIME_TYPES_PERMITIDOS = setOf(
        "image/jpeg",
        "image/png",
        "image/webp"
    )

    data class ResultadoValidacion(
        val esValida: Boolean,
        val mensaje: String
    )

    fun validar(
        contentResolver: ContentResolver,
        uri: Uri
    ): ResultadoValidacion {

        val mimeType = contentResolver.getType(uri)

        if (mimeType !in MIME_TYPES_PERMITIDOS) {
            return ResultadoValidacion(
                esValida = false,
                mensaje = "El archivo seleccionado no es una imagen válida."
            )
        }

        val sizeBytes = obtenerTamano(
            contentResolver = contentResolver,
            uri = uri
        )

        if (sizeBytes <= 0L) {
            return ResultadoValidacion(
                esValida = false,
                mensaje = "No se pudo leer la imagen seleccionada."
            )
        }

        if (sizeBytes > MAX_SIZE_BYTES) {
            return ResultadoValidacion(
                esValida = false,
                mensaje = "La imagen supera el tamaño máximo permitido de 5 MB."
            )
        }

        return ResultadoValidacion(
            esValida = true,
            mensaje = "Imagen válida."
        )
    }

    fun obtenerTamano(
        contentResolver: ContentResolver,
        uri: Uri
    ): Long {

        return try {
            contentResolver.query(
                uri,
                arrayOf(OpenableColumns.SIZE),
                null,
                null,
                null
            )?.use { cursor ->

                val columna = cursor.getColumnIndex(OpenableColumns.SIZE)

                if (columna >= 0 && cursor.moveToFirst()) {
                    cursor.getLong(columna)
                } else {
                    -1L
                }
            } ?: -1L

        } catch (e: Exception) {
            -1L
        }
    }

    fun obtenerMimeType(
        contentResolver: ContentResolver,
        uri: Uri
    ): String {
        return contentResolver.getType(uri) ?: "application/octet-stream"
    }
}