package com.example.miformacionctma.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.example.miformacionctma.data.ActividadRepository
import com.example.miformacionctma.domain.Evidencia
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID

@Composable
fun EvidenciaScreen(
    actividadId: Long,
    repository: ActividadRepository,
    onVolver: () -> Unit
) {
    val context = LocalContext.current

    val evidencias by repository
        .observarEvidencias(actividadId)
        .collectAsState(initial = emptyList())

    var error by remember { mutableStateOf<String?>(null) }
    var mensaje by remember { mutableStateOf<String?>(null) }

    var evidenciaParaEliminar by remember {
        mutableStateOf<Evidencia?>(null)
    }

    var uriTemporalCamara by remember {
        mutableStateOf<Uri?>(null)
    }

    var cargando by remember {
        mutableStateOf(false)
    }

    val token = remember {
        "token-APR-01"
    }

    /*
     * ---------------------------------------------------------
     * PROCESAR EVIDENCIA
     * ---------------------------------------------------------
     */
    fun procesarEvidencia(
        uri: Uri,
        desdeCamara: Boolean
    ) {
        cargando = true
        error = null
        mensaje = null

        CoroutineScope(Dispatchers.IO).launch {

            try {

                /*
                 * 1. Validar MIME.
                 *
                 * Los videos serán rechazados aquí.
                 */
                val mimeType = obtenerMimeType(
                    context = context,
                    uri = uri
                )

                validarMimeType(mimeType)

                /*
                 * 2. Copiar al almacenamiento interno permanente.
                 */
                val archivo =
                    copiarImagenAPermanente(
                        context = context,
                        uri = uri,
                        desdeCamara = desdeCamara
                    )

                /*
                 * 3. Validar tamaño.
                 */
                validarTamano(archivo)

                /*
                 * 4. Generar URI content:// para Room.
                 *
                 * Nunca usamos Uri.fromFile().
                 */
                val uriPersistente =
                    FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.fileprovider",
                        archivo
                    )

                /*
                 * 5. Crear evidencia LOCAL.
                 */
                val evidencia =
                    Evidencia(
                        id = UUID.randomUUID().toString(),
                        actividadId = actividadId,
                        localUri = uriPersistente.toString(),
                        mimeType = mimeType,
                        sizeBytes = archivo.length(),
                        estado = "LOCAL"
                    )

                /*
                 * 6. Guardar primero en Room.
                 */
                repository.guardarEvidencia(evidencia)

                /*
                 * 7. Cambiar estado a SUBIENDO.
                 */
                repository.actualizarEstadoEvidencia(
                    id = evidencia.id,
                    estado = "SUBIENDO"
                )

                /*
                 * 8. ID compatible con FastAPI.
                 */
                val actividadIdApi =
                    "ACT-${actividadId.toString().padStart(3, '0')}"

                /*
                 * 9. Subir a FastAPI.
                 */
                val subida =
                    repository.subirEvidenciaAlServidor(
                        actividadId = actividadIdApi,
                        archivo = archivo,
                        mimeType = mimeType,
                        token = token
                    )

                /*
                 * 10. Actualizar estado.
                 */
                if (subida) {

                    repository.actualizarEstadoEvidencia(
                        id = evidencia.id,
                        estado = "SINCRONIZADA"
                    )

                } else {

                    repository.actualizarEstadoEvidencia(
                        id = evidencia.id,
                        estado = "FALLIDA"
                    )
                }

                withContext(Dispatchers.Main) {

                    cargando = false
                    uriTemporalCamara = null

                    if (subida) {

                        mensaje =
                            if (desdeCamara) {
                                "Foto guardada y sincronizada correctamente"
                            } else {
                                "Imagen guardada y sincronizada correctamente"
                            }

                    } else {

                        mensaje =
                            "La evidencia quedó guardada localmente. Puedes reintentar la subida."
                    }
                }

            } catch (e: CancellationException) {

                /*
                 * Si se cancela la operación,
                 * conservamos la evidencia local.
                 */
                withContext(Dispatchers.Main) {

                    cargando = false
                    uriTemporalCamara = null

                    mensaje =
                        "Operación cancelada. La evidencia local se conserva."
                }

            } catch (e: Exception) {

                withContext(Dispatchers.Main) {

                    cargando = false
                    uriTemporalCamara = null

                    error =
                        e.message
                            ?: "No se pudo guardar la evidencia"
                }
            }
        }
    }

    /*
     * ---------------------------------------------------------
     * PHOTO PICKER
     * ---------------------------------------------------------
     *
     * IMPORTANTE:
     * Permitimos seleccionar FOTOS y VIDEOS para poder probar
     * el rechazo de archivos que no son imágenes.
     *
     * NO se necesita permiso READ_MEDIA_IMAGES.
     */
    val galeriaLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) { uri: Uri? ->

            /*
             * Cancelar el selector no modifica
             * la evidencia anterior.
             */
            if (uri == null) {

                mensaje =
                    "Selección cancelada. No se modificó la evidencia."

                return@rememberLauncherForActivityResult
            }

            procesarEvidencia(
                uri = uri,
                desdeCamara = false
            )
        }

    /*
     * ---------------------------------------------------------
     * CÁMARA
     * ---------------------------------------------------------
     */
    val camaraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture()
        ) { guardada ->

            val uri = uriTemporalCamara

            if (!guardada || uri == null) {

                /*
                 * Si la persona cancela la cámara,
                 * eliminamos solamente el archivo temporal.
                 */
                uri?.let {
                    eliminarArchivoTemporal(
                        context,
                        it
                    )
                }

                uriTemporalCamara = null

                mensaje =
                    "Captura cancelada. No se modificó la evidencia."

                return@rememberLauncherForActivityResult
            }

            procesarEvidencia(
                uri = uri,
                desdeCamara = true
            )
        }

    /*
     * ---------------------------------------------------------
     * PERMISO DE CÁMARA
     * ---------------------------------------------------------
     */
    val permisoCamaraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { concedido ->

            if (concedido) {

                try {

                    val uri = crearUriTemporal(context)

                    uriTemporalCamara = uri

                    camaraLauncher.launch(uri)

                } catch (e: Exception) {

                    error =
                        e.message
                            ?: "No se pudo abrir la cámara"
                }

            } else {

                error =
                    "Permiso de cámara denegado. Puedes continuar usando la selección de imágenes."
            }
        }

    fun abrirCamara() {

        val permiso =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            )

        if (permiso == PackageManager.PERMISSION_GRANTED) {

            try {

                val uri = crearUriTemporal(context)

                uriTemporalCamara = uri

                camaraLauncher.launch(uri)

            } catch (e: Exception) {

                error =
                    e.message
                        ?: "No se pudo abrir la cámara"
            }

        } else {

            permisoCamaraLauncher.launch(
                Manifest.permission.CAMERA
            )
        }
    }

    /*
     * ---------------------------------------------------------
     * PANTALLA
     * ---------------------------------------------------------
     */
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Evidencias",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Actividad: ACT-${
                actividadId.toString().padStart(3, '0')
            }"
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Button(
                modifier = Modifier.weight(1f),
                enabled = !cargando,
                onClick = {

                    /*
                     * AQUÍ ESTÁ EL CAMBIO PRINCIPAL:
                     *
                     * ImageAndVideo permite escoger fotos y videos.
                     *
                     * Luego validarMimeType() rechazará los videos.
                     */
                    galeriaLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageAndVideo
                        )
                    )
                }
            ) {

                Text("Seleccionar foto ")
            }

            Button(
                modifier = Modifier.weight(1f),
                enabled = !cargando,
                onClick = {
                    abrirCamara()
                }
            ) {

                Text("Tomar foto")
            }
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        if (cargando) {

            Text(
                text = "Guardando y sincronizando evidencia..."
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }

        mensaje?.let {

            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }

        error?.let {

            Text(
                text = "Error: $it",
                color = MaterialTheme.colorScheme.error
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }

        HorizontalDivider()

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        if (evidencias.isEmpty()) {

            Text(
                text = "Todavía no hay evidencias."
            )

        } else {

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(
                    items = evidencias,
                    key = { it.id }
                ) { evidencia ->

                    EvidenciaGuardada(
                        evidencia = evidencia,
                        onEliminar = {
                            evidenciaParaEliminar = evidencia
                        }
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onVolver
        ) {

            Text("Volver")
        }
    }

    /*
     * ---------------------------------------------------------
     * DIÁLOGO ELIMINAR
     * ---------------------------------------------------------
     */
    evidenciaParaEliminar?.let { evidencia ->

        AlertDialog(
            onDismissRequest = {
                evidenciaParaEliminar = null
            },

            title = {
                Text("Eliminar evidencia")
            },

            text = {
                Text(
                    "¿Deseas eliminar esta evidencia del dispositivo?"
                )
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        evidenciaParaEliminar = null

                        CoroutineScope(Dispatchers.IO).launch {

                            try {

                                repository.eliminarEvidencia(
                                    evidencia
                                )

                                eliminarArchivoDeEvidencia(
                                    context = context,
                                    localUri = evidencia.localUri
                                )

                                withContext(Dispatchers.Main) {

                                    mensaje =
                                        "Evidencia eliminada correctamente."
                                }

                            } catch (e: CancellationException) {

                                throw e

                            } catch (e: Exception) {

                                withContext(Dispatchers.Main) {

                                    error =
                                        e.message
                                            ?: "No se pudo eliminar"
                                }
                            }
                        }
                    }
                ) {

                    Text("Eliminar")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        evidenciaParaEliminar = null
                    }
                ) {

                    Text("Cancelar")
                }
            }
        )
    }
}


/*
 * -------------------------------------------------------------
 * TARJETA
 * -------------------------------------------------------------
 */
@Composable
private fun EvidenciaGuardada(
    evidencia: Evidencia,
    onEliminar: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            AsyncImage(
                model = evidencia.localUri,
                contentDescription = "Evidencia fotográfica",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Estado: ${evidencia.estado}"
            )

            Text(
                text = "Tipo: ${evidencia.mimeType}"
            )

            Text(
                text = "Tamaño: ${evidencia.sizeBytes} bytes"
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            TextButton(
                onClick = onEliminar
            ) {

                Text("Eliminar")
            }
        }
    }
}


/*
 * -------------------------------------------------------------
 * CREAR URI TEMPORAL DE CÁMARA
 * -------------------------------------------------------------
 */
private fun crearUriTemporal(
    context: Context
): Uri {

    val carpeta =
        File(
            context.filesDir,
            "evidencias"
        ).apply {
            mkdirs()
        }

    val archivo =
        File(
            carpeta,
            "temporal_${UUID.randomUUID()}.jpg"
        )

    if (!archivo.exists()) {
        archivo.createNewFile()
    }

    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        archivo
    )
}


/*
 * -------------------------------------------------------------
 * COPIAR IMAGEN A ALMACENAMIENTO PERMANENTE
 * -------------------------------------------------------------
 */
private fun copiarImagenAPermanente(
    context: Context,
    uri: Uri,
    desdeCamara: Boolean
): File {

    val carpeta =
        File(
            context.filesDir,
            "evidencias"
        ).apply {
            mkdirs()
        }

    val prefijo =
        if (desdeCamara) {
            "foto"
        } else {
            "evidencia"
        }

    val extension =
        extensionSegura(
            obtenerMimeType(
                context = context,
                uri = uri
            )
        )

    val destino =
        File(
            carpeta,
            "${prefijo}_${UUID.randomUUID()}$extension"
        )

    context.contentResolver
        .openInputStream(uri)
        .use { entrada ->

            requireNotNull(entrada) {
                "No se pudo abrir la imagen seleccionada."
            }

            destino.outputStream().use { salida ->

                entrada.copyTo(salida)
            }
        }

    return destino
}


/*
 * -------------------------------------------------------------
 * VALIDAR MIME
 * -------------------------------------------------------------
 */
private fun validarMimeType(
    mimeType: String
) {

    val permitidos =
        setOf(
            "image/jpeg",
            "image/png",
            "image/webp"
        )

    require(mimeType in permitidos) {

        "Tipo de archivo no permitido. " +
                "Solo se aceptan imágenes JPG, PNG o WEBP."
    }
}


/*
 * -------------------------------------------------------------
 * VALIDAR TAMAÑO
 * -------------------------------------------------------------
 */
private fun validarTamano(
    archivo: File
) {

    val maximo =
        5L * 1024L * 1024L

    require(archivo.length() <= maximo) {

        "La imagen supera el tamaño máximo permitido de 5 MB."
    }

    require(archivo.length() > 0) {

        "La imagen está vacía."
    }
}


/*
 * -------------------------------------------------------------
 * MIME
 * -------------------------------------------------------------
 */
private fun obtenerMimeType(
    context: Context,
    uri: Uri
): String {

    return context.contentResolver
        .getType(uri)
        ?: throw IllegalArgumentException(
            "No se pudo determinar el tipo de archivo."
        )
}


/*
 * -------------------------------------------------------------
 * EXTENSIÓN
 * -------------------------------------------------------------
 */
private fun extensionSegura(
    mimeType: String
): String {

    return when (mimeType) {

        "image/jpeg" -> ".jpg"

        "image/png" -> ".png"

        "image/webp" -> ".webp"

        else -> ".jpg"
    }
}


/*
 * -------------------------------------------------------------
 * ELIMINAR ARCHIVO TEMPORAL
 * -------------------------------------------------------------
 */
private fun eliminarArchivoTemporal(
    context: Context,
    uri: Uri
) {

    try {

        val archivo =
            File(
                context.filesDir,
                "evidencias/${uri.lastPathSegment ?: ""}"
            )

        if (archivo.exists()) {
            archivo.delete()
        }

    } catch (_: Exception) {
        // No bloquear la aplicación por limpieza temporal.
    }
}


/*
 * -------------------------------------------------------------
 * ELIMINAR ARCHIVO DE EVIDENCIA
 * -------------------------------------------------------------
 */
private fun eliminarArchivoDeEvidencia(
    context: Context,
    localUri: String
) {

    try {

        val uri = Uri.parse(localUri)

        if (uri.scheme != "content") {
            return
        }

        val archivo =
            uri.path
                ?.substringAfterLast("evidencias/")
                ?.let { nombre ->

                    File(
                        context.filesDir,
                        "evidencias/$nombre"
                    )
                }

        archivo?.let {

            if (it.exists()) {
                it.delete()
            }
        }

    } catch (_: Exception) {
        // La eliminación del archivo no debe bloquear la UI.
    }
}