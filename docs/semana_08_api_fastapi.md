# Semana 8 — API FastAPI + Retrofit + Room + resiliencia

## Arquitectura

```text
FastAPI
  ↓
Retrofit / OkHttp
  ↓
DTO
  ↓
RemoteDataSource
  ↓
Repository
  ↓
Room (caché)
  ↓
Flow
  ↓
ViewModel
  ↓
Compose
```

## API

El servidor está en `api/main.py`.

En el emulador Android se usa:

`http://10.0.2.2:8000/`

En un teléfono físico se debe cambiar `BASE_URL` por la IP LAN del PC.

## Operaciones

- GET actividades
- GET actividad por ID
- POST crear actividad
- PUT actualizar actividad
- DELETE eliminar actividad
- PATCH cambiar estado
- POST subir evidencia
- GET listar evidencias

## Resiliencia

- Timeouts: conexión 10 s, lectura 20 s, escritura 20 s, llamada 30 s.
- `CancellationException` se vuelve a lanzar.
- Los errores remotos no eliminan el caché.
- Una respuesta 200 válida actualiza Room.
- Room es la fuente que observa la UI.
- La sincronización no muestra un token en pantalla.
