# Semana 8 — Matriz de aceptación CA-01 a CA-08

| Caso | Implementación | Evidencia automatizada |
|---|---|---|
| CA-01 200 con actividades | DTO → dominio → Room → Flow | `ca01_200_valida_seConvierte` + RepositoryTest |
| CA-02 200 vacío | respuesta vacía válida; caché no se borra | `ca02_200Vacio_esRespuestaValida` |
| CA-03 timeout con caché | `NetworkError.Timeout`; Room no se limpia | `ca03_timeout_seClasificaComoTimeout` + política del Repository |
| CA-04 sin red y sin caché | error recuperable y botón Reintentar | clasificación `NoConnection` + UI de reintento |
| CA-05 401 | `Unauthorized`; token solo por interceptor | `ca05_401_seClasificaComoUnauthorized` + test Bearer |
| CA-06 500/JSON inválido | `Server` / `InvalidPayload`; caché no se limpia | `ca06_500_seClasificaComoServer`, `ca06_jsonInvalido_seClasificaComoInvalidPayload` |
| CA-07 dos refresh rápidos | Job anterior se cancela antes del siguiente | `sincronizacionJob?.cancel()` en ViewModel |
| CA-08 cancelación de ViewModel | `CancellationException` se relanza | `ActividadApiCancellationTest` + catch específico del ViewModel |

## Nota

Los casos que dependen de Room real, ciclo de vida del ViewModel o emulador deben ejecutarse también en Android Studio para obtener evidencia visual. Los tests unitarios no deben presentarse como sustituto de una prueba manual si la guía exige captura de pantalla.
