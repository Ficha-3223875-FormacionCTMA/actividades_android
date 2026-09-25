Casos de aceptación — Guía 9

CA01 — Imagen válida

Acción: seleccionar una imagen mediante Photo Picker.

Resultado esperado:

- Se muestra la evidencia.
- Se guarda en Room.
- Se almacena URI y metadatos.
- No se solicita permiso general de galería.

Resultado: APROBADO.

---

CA02 — Cancelar selección o cámara

Acción: abrir Photo Picker o cámara y cancelar.

Resultado esperado:

- La operación se cancela.
- La evidencia anterior permanece intacta.
- No se muestra un mensaje alarmista.

Resultado: APROBADO.

---

CA03 — Cámara externa

Acción: tomar una fotografía utilizando la cámara.

Resultado esperado:

- La cámara recibe una URI "content://".
- La fotografía se copia al almacenamiento interno.
- No se utiliza "Uri.fromFile()".

Resultado: APROBADO.

---

CA04 — Tipo o tamaño inválido

Acción: intentar utilizar un archivo que no sea una imagen permitida o supere 5 MB.

Resultado esperado:

- La aplicación rechaza el archivo.
- Se muestra un mensaje comprensible.
- El archivo inválido no se guarda como evidencia.

Resultado: APROBADO.

---

CA05 — Reinicio con evidencia local

Acción: guardar una evidencia y cerrar completamente la aplicación.

Resultado esperado:

- Al volver a abrir la aplicación aparece la evidencia.
- Room conserva URI y metadatos.

Resultado: APROBADO.

---

CA06 — Fallo de subida

Acción: apagar FastAPI y seleccionar una imagen.

Resultado esperado:

- La evidencia queda guardada localmente.
- El estado cambia a FALLIDA.
- La imagen continúa disponible.

Resultado: APROBADO.

---

CA07 — Denegación de notificaciones

Acción: no conceder el permiso de notificaciones.

Resultado esperado:

- La aplicación continúa funcionando.
- No se bloquea el flujo principal.
- No se solicita repetidamente sin una acción contextual.

Resultado: APROBADO.

---

CA08 — Eliminar evidencia

Acción: seleccionar Eliminar y confirmar.

Resultado esperado:

- Se elimina el registro de Room.
- Se elimina el archivo propio cuando corresponde.
- La evidencia desaparece de la pantalla.

Resultado: APROBADO.

---

CA09 — Configuración de producción

Acción: revisar la variante "prod".

Resultado esperado:

- La URL de producción utiliza HTTPS.
- No existen tokens o secretos dentro de la configuración.
- No se registran Authorization ni URI sensibles.

Resultado: APROBADO.