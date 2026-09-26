package com.luciana.miformacionctma.ui.screens

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.luciana.miformacionctma.data.EvidenciaEstado
import com.luciana.miformacionctma.data.EvidenciaLocal
import com.luciana.miformacionctma.domain.ActividadFormativa
import com.luciana.miformacionctma.domain.estadoActividad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalleActividad(
    actividad: ActividadFormativa?,
    evidencias: List<EvidenciaLocal> = emptyList(),
    mensajeEvidencia: String? = null,
    onCargarEvidencias: (Long) -> Unit = {},
    onElegirEvidencia: (Long, Uri) -> Unit = { _, _ -> },
    onPrepararCamara: () -> Uri? = { null },
    onCancelarCamara: (Uri) -> Unit = {},
    onSubirEvidencia: (String) -> Unit = {},
    onEliminarEvidencia: (String) -> Unit = {},
    onLimpiarMensajeEvidencia: () -> Unit = {},
    onVolver: () -> Unit,
    onEditar: () -> Unit = {},
    onEliminar: () -> Unit = {},
    onCambiarResuelto: () -> Unit = {}
) {

    var mostrarDialogoEliminar by remember {
        mutableStateOf(false)
    }

    var mostrarDialogoEliminarEvidencia by remember {
        mutableStateOf<String?>(null)
    }

    var cameraUri by remember {
        mutableStateOf<Uri?>(null)
    }

    // Selector de imágenes
    val picker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->

        if (uri != null && actividad != null) {
            onElegirEvidencia(
                actividad.id,
                uri
            )
        }
    }

    // Cámara
    val camera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { ok ->

        val uri = cameraUri

        if (ok && uri != null && actividad != null) {

            onElegirEvidencia(
                actividad.id,
                uri
            )

        } else if (!ok && uri != null) {

            onCancelarCamara(uri)
        }

        cameraUri = null
    }

    // Cargar evidencias cuando cambia la actividad
    LaunchedEffect(actividad?.id) {

        actividad?.let {
            onCargarEvidencias(it.id)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Detalle de actividad")
                }
            )
        }
    ) { paddingValues ->

        if (actividad == null) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    text = "Actividad no encontrada",
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = "No existe una actividad con el identificador solicitado."
                )

                Button(
                    onClick = onVolver
                ) {
                    Text("Volver")
                }
            }

        } else {

            val estado = estadoActividad(actividad)

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // Información de la actividad
                item {

                    Text(
                        text = actividad.titulo,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Text(
                        text = actividad.descripcion ?: "Sin descripción"
                    )

                    Text(
                        text = "Progreso: ${actividad.progreso}%"
                    )

                    Text(
                        text = "Días restantes: ${actividad.diasRestantes}"
                    )

                    Text(
                        text = "Fecha de entrega: ${
                            actividad.fecha.ifBlank {
                                "No definida"
                            }
                        }"
                    )

                    Text(
                        text = "Prioridad: ${actividad.prioridad.name}"
                    )

                    Text(
                        text = "Estado: ${estado.name}"
                    )

                    Text(
                        text = "Resuelto: ${
                            if (actividad.resuelto) {
                                "Sí"
                            } else {
                                "No"
                            }
                        }"
                    )

                    Button(
                        onClick = onCambiarResuelto
                    ) {

                        Text(
                            if (actividad.resuelto) {
                                "Marcar como pendiente"
                            } else {
                                "Marcar como resuelta"
                            }
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    // Evidencia
                    Text(
                        text = "Evidencia fotográfica",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = "Puedes elegir una imagen o tomar una foto. " +
                                "Se valida y guarda localmente antes de sincronizar."
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        // Elegir imagen
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = {

                                picker.launch("image/*")
                            }
                        ) {

                            Text("Elegir imagen")
                        }

                        // Tomar foto
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = {

                                val uri = onPrepararCamara()

                                if (uri != null) {

                                    cameraUri = uri
                                    camera.launch(uri)
                                }
                            }
                        ) {

                            Text("Tomar foto")
                        }
                    }

                    // Mensaje de evidencia
                    mensajeEvidencia?.let { mensaje ->

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = mensaje,
                            color = MaterialTheme.colorScheme.error
                        )

                        TextButton(
                            onClick = onLimpiarMensajeEvidencia
                        ) {

                            Text("Cerrar mensaje")
                        }
                    }
                }

                // Lista de evidencias
                items(
                    items = evidencias,
                    key = { it.id }
                ) { evidencia ->

                    EvidenciaCard(
                        evidencia = evidencia,
                        onSubir = {
                            onSubirEvidencia(evidencia.id)
                        },
                        onEliminar = {
                            mostrarDialogoEliminarEvidencia = evidencia.id
                        }
                    )
                }

                // Botones de actividad
                item {

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Button(
                        onClick = onEditar,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text("Editar actividad")
                    }

                    Button(
                        onClick = {
                            mostrarDialogoEliminar = true
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text("Eliminar actividad")
                    }

                    Button(
                        onClick = onVolver,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text("Volver")
                    }
                }
            }

            // Dialogo eliminar actividad
            if (mostrarDialogoEliminar) {

                AlertDialog(

                    onDismissRequest = {
                        mostrarDialogoEliminar = false
                    },

                    title = {
                        Text("Eliminar actividad")
                    },

                    text = {
                        Text(
                            "¿Estás segura de que deseas eliminar " +
                                    "\"${actividad.titulo}\"?"
                        )
                    },

                    confirmButton = {

                        TextButton(
                            onClick = {
                                mostrarDialogoEliminar = false
                                onEliminar()
                            }
                        ) {

                            Text("Eliminar")
                        }
                    },

                    dismissButton = {

                        TextButton(
                            onClick = {
                                mostrarDialogoEliminar = false
                            }
                        ) {

                            Text("Cancelar")
                        }
                    }
                )
            }

            // Dialogo eliminar evidencia
            mostrarDialogoEliminarEvidencia?.let { id ->

                AlertDialog(

                    onDismissRequest = {
                        mostrarDialogoEliminarEvidencia = null
                    },

                    title = {
                        Text("Eliminar evidencia")
                    },

                    text = {
                        Text(
                            "La evidencia local se eliminará del dispositivo."
                        )
                    },

                    confirmButton = {

                        TextButton(
                            onClick = {

                                onEliminarEvidencia(id)

                                mostrarDialogoEliminarEvidencia = null
                            }
                        ) {

                            Text("Eliminar")
                        }
                    },

                    dismissButton = {

                        TextButton(
                            onClick = {
                                mostrarDialogoEliminarEvidencia = null
                            }
                        ) {

                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}


@Composable
private fun EvidenciaCard(
    evidencia: EvidenciaLocal,
    onSubir: () -> Unit,
    onEliminar: () -> Unit
) {

    val context = LocalContext.current

    val bitmap by produceState<android.graphics.Bitmap?>(
        initialValue = null,
        key1 = evidencia.localUri
    ) {

        value = withContext(Dispatchers.IO) {

            runCatching {

                context.contentResolver
                    .openInputStream(
                        Uri.parse(evidencia.localUri)
                    )
                    .use { stream ->

                        if (stream == null) {
                            null
                        } else {
                            BitmapFactory.decodeStream(stream)
                        }
                    }

            }.getOrNull()
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            // Vista previa
            bitmap?.let { imagen ->

                Image(
                    bitmap = imagen.asImageBitmap(),
                    contentDescription = "Vista previa de evidencia",
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 240.dp)
                )
            }

            Text(
                text = "Estado: ${evidencia.estado.name}"
            )

            Text(
                text = "Tipo: ${evidencia.mimeType} · " +
                        "${evidencia.sizeBytes / 1024} KB"
            )

            when (evidencia.estado) {

                EvidenciaEstado.LOCAL,
                EvidenciaEstado.FALLIDA -> {

                    Button(
                        onClick = onSubir,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            if (evidencia.estado == EvidenciaEstado.FALLIDA) {
                                "Reintentar"
                            } else {
                                "Sincronizar evidencia"
                            }
                        )
                    }
                }

                EvidenciaEstado.SUBIENDO -> {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )

                        Text("Subiendo…")
                    }
                }

                EvidenciaEstado.SINCRONIZADA -> {

                    Text(
                        "La API confirmó la recepción."
                    )
                }
            }

            TextButton(
                onClick = onEliminar
            ) {

                Text("Eliminar evidencia")
            }
        }
    }
}