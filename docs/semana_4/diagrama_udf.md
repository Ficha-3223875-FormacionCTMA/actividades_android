Diagrama UDF

Flujo de datos unidireccional

La aplicación utiliza un flujo de datos unidireccional (UDF).

┌─────────────────────┐
│      Usuario        │
└──────────┬──────────┘
│
│ eventos
▼
┌─────────────────────┐
│ FormularioActividad │
│       Screen        │
└──────────┬──────────┘
│
│ actualiza
▼
┌─────────────────────┐
│ FormularioActividad │
│      UiState        │
└──────────┬──────────┘
│
│ estado actualizado
▼
┌─────────────────────┐
│        UI           │
│   Compose renderiza │
└─────────────────────┘

Ejemplo

Cuando el usuario escribe un título:

Usuario escribe
↓
onValueChange
↓
actualización del estado
↓
FormularioActividadUiState
↓
Compose recompone
↓
se muestra el nuevo título

Responsabilidad del estado

"FormularioActividadUiState" contiene:

- título
- descripción
- error del título
- error de descripción
- indicador de intento de guardado
- posibilidad de guardar

Las validaciones se realizan mediante funciones independientes y puras.

Ventaja

Este flujo permite separar:

- La interfaz.
- Los datos del formulario.
- Las reglas de validación.
- Los eventos realizados por el usuario.

Esto facilita el mantenimiento y las pruebas de la aplicación.