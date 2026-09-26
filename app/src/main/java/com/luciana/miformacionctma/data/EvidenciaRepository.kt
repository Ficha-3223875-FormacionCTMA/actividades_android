package com.luciana.miformacionctma.data

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.luciana.miformacionctma.data.api.ActividadRemoteDataSource
import com.luciana.miformacionctma.data.api.RetrofitClient
import kotlinx.coroutines.CancellationException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source
import java.io.File
import java.io.IOException
import java.util.UUID

enum class EvidenciaEstado { LOCAL, SUBIENDO, SINCRONIZADA, FALLIDA }

data class EvidenciaLocal(
    val id: String,
    val actividadId: Long,
    val localUri: String,
    val mimeType: String,
    val sizeBytes: Long,
    val estado: EvidenciaEstado
)

class EvidenciaRepository(
    private val context: Context,
    private val dao: EvidenciaDao,
    private val remote: ActividadRemoteDataSource = ActividadRemoteDataSource(RetrofitClient.actividadApi)
) {
    companion object {
        const val MAX_BYTES = 5L * 1024 * 1024
        private val TIPOS = setOf("image/jpeg", "image/png", "image/webp")
    }

    fun observar(actividadId: Long) =
        dao.observarPorActividad(actividadId)

    fun crearUriCaptura(): Uri {
        val dir = File(context.filesDir, "evidencias").apply { mkdirs() }
        val file = File(dir, "evidencia_${UUID.randomUUID()}.jpg")
        file.createNewFile()
        return Uri.fromFile(file).let {
            androidx.core.content.FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
        }
    }

    fun eliminarArchivoTemporal(uri: Uri) {
        val nombre = uri.lastPathSegment?.substringAfterLast('/') ?: return
        runCatching {
            File(context.filesDir, "evidencias/$nombre").delete()
        }
    }

    suspend fun guardarDesdeUri(
        actividadId: Long,
        uri: Uri
    ): Result<EvidenciaLocal> = runCatching {
        val resolver = context.contentResolver
        val mime = resolver.getType(uri)
            ?: error("No se pudo identificar el tipo de imagen.")
        require(mime in TIPOS) { "Solo se permiten imágenes JPG, PNG o WEBP." }

        val size = querySize(resolver, uri)
        require(size in 1..MAX_BYTES) {
            "La imagen debe pesar entre 1 byte y 5 MB."
        }

        val source = resolver.openInputStream(uri)
            ?: error("No fue posible leer la imagen.")

        val id = UUID.randomUUID().toString()
        val dir = File(context.filesDir, "evidencias").apply { mkdirs() }
        val ext = when (mime) {
            "image/png" -> ".png"
            "image/webp" -> ".webp"
            else -> ".jpg"
        }
        val target = File(dir, "evidencia_$id$ext")

        source.use { input ->
            target.outputStream().use { output -> input.copyTo(output) }
        }

        val actualSize = target.length()
        require(actualSize in 1..MAX_BYTES) {
            target.delete()
            "La imagen supera el máximo de 5 MB."
        }

        val localUri = androidx.core.content.FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            target
        )

        val evidencia = EvidenciaEntity(
            id = id,
            actividadId = actividadId,
            localUri = localUri.toString(),
            mimeType = mime,
            sizeBytes = actualSize,
            estado = EvidenciaEstado.LOCAL.name,
            creadaEnEpochMillis = System.currentTimeMillis()
        )
        dao.insertar(evidencia)
        evidencia.toLocal()
    }

    suspend fun subir(id: String): Result<Unit> = runCatching {
        val local = dao.buscarPorId(id) ?: error("Evidencia inexistente.")
        dao.actualizarEstado(id, EvidenciaEstado.SUBIENDO.name)

        try {
            val uri = Uri.parse(local.localUri)
            val resolver = context.contentResolver
            val body = object : RequestBody() {
                override fun contentType() = local.mimeType.toMediaTypeOrNull()
                override fun contentLength() = local.sizeBytes
                override fun writeTo(sink: BufferedSink) {
                    resolver.openInputStream(uri)?.use { input ->
                        input.source().use { source ->
                            sink.writeAll(source)
                        }
                    } ?: throw IOException("No se pudo abrir la evidencia local.")
                }
            }
            val part = MultipartBody.Part.createFormData(
                "archivo",
                "evidencia_${local.id}",
                body
            )
            val remoteId = "ACT-${local.actividadId.toString().padStart(3, '0')}"
            remote.subirEvidencia(remoteId, part)
            dao.actualizarEstado(id, EvidenciaEstado.SINCRONIZADA.name)
        } catch (cancelled: CancellationException) {
            dao.actualizarEstado(id, EvidenciaEstado.LOCAL.name)
            throw cancelled
        } catch (failure: Throwable) {
            dao.actualizarEstado(id, EvidenciaEstado.FALLIDA.name)
            throw failure
        }
    }

    suspend fun eliminar(id: String) {
        val local = dao.buscarPorId(id) ?: return
        val uri = Uri.parse(local.localUri)
        val nombre = uri.lastPathSegment?.substringAfterLast('/')
        if (!nombre.isNullOrBlank()) {
            File(context.filesDir, "evidencias/$nombre").delete()
        }
        dao.eliminarPorId(id)
    }

    private fun querySize(resolver: ContentResolver, uri: Uri): Long {
        resolver.query(uri, arrayOf(OpenableColumns.SIZE), null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val index = cursor.getColumnIndex(OpenableColumns.SIZE)
                if (index >= 0 && !cursor.isNull(index)) return cursor.getLong(index)
            }
        }
        return resolver.openInputStream(uri)?.use { it.available().toLong() }
            ?: throw IOException("No se pudo determinar el tamaño.")
    }

    private fun EvidenciaEntity.toLocal() = EvidenciaLocal(
        id = id,
        actividadId = actividadId,
        localUri = localUri,
        mimeType = mimeType,
        sizeBytes = sizeBytes,
        estado = runCatching { EvidenciaEstado.valueOf(estado) }.getOrDefault(EvidenciaEstado.LOCAL)
    )
}
