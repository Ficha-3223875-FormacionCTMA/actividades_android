# Semana 4 — Estado, formularios y navegación

## Implementación cerrada

- Estado del formulario con `FormularioActividadUiState`.
- UDF/State Hoisting: la UI emite eventos y recibe un estado explícito.
- `rememberSaveable` para conservar campos ante recreación.
- Validación de título (3–80 caracteres).
- Validación de descripción (máximo 240 caracteres).
- Validación de progreso (0–100).
- Validación de fecha `dd/MM/yyyy`, fecha real y fecha no anterior a hoy.
- `puedeGuardar` incluye la validación de fecha.
- Protección contra doble toque mediante `guardando` en crear/editar.
- Navegación por rutas y paso de `actividadId`.
- Detalle, edición, eliminación, creación y regreso mediante back stack.

## Casos de verificación

1. Crear actividad válida.
2. Rechazar título menor de 3 caracteres.
3. Rechazar título mayor de 80 caracteres.
4. Rechazar descripción mayor de 240 caracteres.
5. Rechazar progreso fuera de 0–100.
6. Rechazar fecha inválida o anterior a hoy.
7. Conservar campos con `rememberSaveable` durante recreación.
8. Evitar doble guardado y conservar el flujo de navegación.

Las reglas de fecha tienen pruebas unitarias en `ReglasActividadFechaTest`.
