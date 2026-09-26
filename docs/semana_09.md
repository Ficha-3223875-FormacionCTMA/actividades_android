# Semana 09 — Capacidades del dispositivo y seguridad

## Implementación

Este incremento extiende la Semana 8 sin reemplazar Room como fuente local canónica.

### Evidencia fotográfica
- `EvidenciaEntity` relacionada con `ActividadEntity`.
- Migración Room 2 → 3.
- Persistencia de URI `content://` y metadatos.
- Límite de 5 MB.
- Tipos permitidos: JPEG, PNG y WEBP.
- Validación del MIME y lectura del contenido.
- Photo Picker para elegir una imagen.
- `FileProvider` + `TakePicture` para captura delegada a la cámara.
- Vista previa desde la URI local.
- Reemplazo mediante una nueva evidencia y eliminación de la anterior.
- Estados `LOCAL`, `SUBIENDO`, `SINCRONIZADA` y `FALLIDA`.
- Reintento después de un fallo.
- Subida multipart al endpoint de evidencias.

### Sincronización
La acción `Sincronizar actividades desde API` ejecuta ahora el flujo real:

`UI → ViewModel → Repository → RemoteDataSource → Retrofit → FastAPI → Room → UI`

Un timeout, error HTTP o pérdida de red no elimina el caché local.

### Red y ambientes
- `dev`: `http://10.0.2.2:8000/` para el emulador.
- `stage`: URL HTTPS de ejemplo.
- `prod`: URL HTTPS de ejemplo.
- El tráfico HTTP claro queda limitado al entorno dev.
- La URL se obtiene mediante `BuildConfig.API_BASE_URL`.

### Notificaciones
Se solicita `POST_NOTIFICATIONS` únicamente desde la acción explícita `Activar recordatorios`. Una negativa no bloquea el resto de la aplicación.

## Variante recomendada para probar localmente

En Android Studio selecciona:

`devDebug`

y ejecuta la API desde:

```text
api/
python -m uvicorn main:app --reload --host 0.0.0.0 --port 8000
```

Luego abre la app y pulsa **Sincronizar actividades desde API**.

## Casos de aceptación cubiertos

CA01 selección de imagen válida.
CA02 cancelación del selector/cámara.
CA03 captura mediante cámara externa con `content://`.
CA04 rechazo por tipo o tamaño.
CA05 persistencia local mediante Room.
CA06 fallo de subida conserva evidencia y permite reintentar.
CA07 rechazo de notificaciones no bloquea la app.
CA08 eliminación de evidencia local.
CA09 configuración HTTPS para stage/prod y HTTP limitado a dev.
