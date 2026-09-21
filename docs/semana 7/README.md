# Semana 7 — Concurrencia y Estado Reactivo

## Objetivo

Implementar corrutinas, Flow, StateFlow, ViewModel y DataStore para
manejar el estado reactivo de la aplicación.

## Implementación

Se implementó:

- Room con Flow para observar actividades.
- ViewModel con StateFlow.
- Estados `Cargando`, `Contenido`, `Vacio` y `Error`.
- Estados de operación `Inactiva`, `EnCurso`, `Exitosa` y `Fallida`.
- Búsqueda con `debounce`, `distinctUntilChanged` y `mapLatest`.
- Preferencias persistentes mediante DataStore.
- Corrutinas mediante `viewModelScope`.
- Manejo correcto de `CancellationException`.

## Pruebas

### Flow
🟢 3 de 3 tests passed.

Evidencia:
`test-01-flow-3-de-3.png`

### ViewModel
🟢 4 de 4 tests passed.

Evidencia:
`test-02-viewmodel-4-de-4.png`

## Casos de aceptación

- CA-01: Lista vacía — ✅
- CA-02: Inserción reactiva — ✅
- CA-03: Persistencia del orden — ✅
- CA-04: Búsqueda rápida — ✅
- CA-05: Error y reintento — ✅
- CA-06: Cancelación — ✅
- CA-07: Recreación de pantalla — ✅
- CA-08: Pruebas deterministas — ✅

## Arquitectura

Compose → ViewModel → Repository → Room/DataStore → Flow → UI

## Conclusión

La aplicación ahora utiliza un estado reactivo y las actividades se
actualizan automáticamente cuando cambian los datos.