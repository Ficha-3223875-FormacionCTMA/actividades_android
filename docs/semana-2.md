# Semana 2 — Kotlin y reglas

Se conserva el modelo `ActividadFormativa` y la separación de reglas respecto de la UI.

## Evidencias técnicas
- `val` para datos que no necesitan reasignación.
- `data class` para representar actividades.
- `enum class Prioridad` para alternativas cerradas.
- Colecciones con `listOf`, `filter`, `map`, `average`, `sortedWith` y búsqueda.
- Null safety con `descripcion: String?` y `orEmpty()`.
- Validación de título, descripción, fecha y progreso.
- Reglas puras para estado, urgencia, promedio, búsqueda y ordenamiento.

## Casos manuales
1. Progreso 0 → PENDIENTE.
2. Progreso 50 → EN PROCESO.
3. Progreso 100 → COMPLETADA.
4. Fecha pasada con progreso incompleto → VENCIDA.
5. Lista vacía → promedio 0.0.
6. Descripción nula → se presenta "Sin descripción" sin `!!`.
7. Progreso 120 → error de validación.
8. Título vacío → error de validación.
