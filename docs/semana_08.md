# Semana 8 — Servicios web, caché y resiliencia

## Arquitectura implementada

```text
Retrofit/HTTP
    ↓
ActividadDto
    ↓ mapper puro
ActividadFormativa
    ↓
ActividadEntity
    ↓
Room (fuente canónica)
    ↓ Flow
Repository → ViewModel → Compose
```

Retrofit no es consumido directamente por Compose.

## Red

- Retrofit 3.
- Kotlin Serialization en lugar de Gson.
- `Response<T>` para inspeccionar el resultado HTTP.
- OkHttp con connect timeout de 10 s, read/write de 20 s y call timeout de 30 s.
- Reintento automático de conexión desactivado; no se confunde timeout con una política de retry.
- Interceptor Bearer preparado mediante `TokenProvider`; no se incluye ningún secreto real.

## DTO y dominio

`ActividadDto` está separado de `ActividadFormativa`. El DTO admite el contrato de Semana 8 y compatibilidad con la API pública de demostración usada por la aplicación actual.

## Caché offline-first

- La UI observa Room.
- Una respuesta HTTP válida se mapea antes de persistirse.
- El lote se escribe dentro de una transacción cuando el Repository dispone de la instancia de `AppDatabase`.
- Los errores de red no borran Room.
- Una respuesta remota vacía se considera válida, pero no elimina el caché porque la API pública de demostración no representa un snapshot completo del usuario.

## Errores

Se clasifican como:

- NoConnection
- Timeout
- Unauthorized
- NotFound
- Server(code)
- InvalidPayload
- Unknown

`CancellationException` se relanza para respetar la cancelación cooperativa.

## Pruebas

`ActividadRemoteDataSourceMockWebServerTest` cubre servidor simulado para 200, 200 vacío, 401, 500, JSON inválido, timeout y encabezado Bearer. Las fixtures están en `app/src/test/resources/fixtures`.

Los casos CA-01 a CA-08 están trazados en `semana_08_casos_aceptacion.md`.
