# Semana 4 — Estado, formularios y navegación

## UI state
`FormularioActividadUiState` concentra título, descripción, fecha, prioridad, progreso, errores, intento de guardado y estado de guardado.

## Flujo unidireccional
Estado ↓ hacia los composables. Eventos ↑ mediante callbacks. El contenedor decide las modificaciones y la navegación.

## Validaciones
- Título: obligatorio, 3–80 caracteres.
- Descripción: opcional, máximo 240.
- Fecha: formato AAAA-MM-DD y no anterior a hoy.
- Progreso: 0–100.
- Guardar vuelve a validar aunque el botón esté habilitado.

## Navegación
- `lista`
- `crear`
- `detalle/{id}`
- `editar/{id}`

Solo se pasa el identificador; el destino resuelve la actividad desde la fuente de verdad local.

## Casos obligatorios
1. Título vacío.
2. Título de 2 y 81 caracteres.
3. Descripción de 240/241 caracteres.
4. Rotación con borrador.
5. Doble toque en Guardar.
6. Lista → Crear → Atrás.
7. Lista → Detalle(id válido).
8. Detalle(id inexistente).
9. Atrás repetido sin duplicar Lista.
10. Fuente 1.5x y mensajes largos.
