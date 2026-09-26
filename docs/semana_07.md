# Semana 7 — Corrutinas y Flow

## Implementación

- Operaciones de Repository marcadas como `suspend`.
- `viewModelScope` para operaciones del ViewModel.
- `Flow` desde Room y `StateFlow` para estado de UI.
- `debounce(250)`, `distinctUntilChanged()` y `flatMapLatest()` para búsqueda reactiva.
- `collectAsStateWithLifecycle()` en Compose.
- `CancellationException` se vuelve a lanzar y no se trata como error de negocio.
- El refresco remoto conserva su Job para cancelar un refresco anterior.

## Flujo

Room → Flow → Repository → ViewModel → StateFlow → Compose.

La prueba `ActividadApiCancellationTest` comprueba cancelación cooperativa; en Semana 8 se agrega cancelación sobre el flujo remoto simulado.
