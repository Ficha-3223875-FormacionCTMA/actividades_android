# Casos de prueba — Mi Formación CTMA

| ID | Escenario | Resultado esperado | Técnica |
|---|---|---|---|
| CP-01 | Crear actividad válida | Se crea una sola actividad y aparece en lista. | Caso de uso |
| CP-02 | Título vacío | No guarda y muestra error. | Equivalencia |
| CP-03 | Título de 2 caracteres | No guarda. | Límite |
| CP-04 | Título de 3 caracteres | Puede guardar si lo demás es válido. | Límite |
| CP-05 | Título de 80 caracteres | Puede guardar. | Límite |
| CP-06 | Título de 81 caracteres | No permite exceder el máximo. | Límite |
| CP-07 | Descripción de 241 caracteres | No permite guardar texto fuera de límite. | Límite |
| CP-08 | Progreso fuera de 0..100 | No permite un valor inválido. | Límite |
| CP-09 | Progreso 100% | Estado textual: completada. | Equivalencia |
| CP-10 | ID inexistente | Estado recuperable, sin cierre. | Negativa |
| CP-11 | Doble pulsación Guardar | Una sola actividad creada. | Riesgo |
| CP-12 | Buscar por título | Solo aparecen coincidencias. | Caso de uso |
| CP-13 | Fecha vencida + incompleta | Se identifica como vencida/urgente según regla. | Decisión |
| CP-14 | Progreso 0% | Estado textual: pendiente. | Equivalencia |
| CP-15 | Fuente 1.5× | Contenido y acciones esenciales utilizables. | Accesibilidad |
| CP-16 | Lista sin coincidencias | Estado vacío visible y comprensible. | Estado |
| CP-17 | Volver desde detalle/formulario | Back stack correcto. | Navegación |
| CP-18 | Recreación/rotación durante formulario | Borrador conservado según estado guardable. | Estado |
| CP-19 | Editar actividad | Cambios sustituyen la actividad, sin duplicar. | Caso de uso |
| CP-20 | Crear → detalle → editar → lista | Flujo completo termina en lista con datos actualizados. | Caso de uso |
| CP-21 | Cancelar formulario | No crea ni modifica actividad. | Flujo negativo |
| CP-22 | Descripción vacía | Se acepta como opcional. | Equivalencia |
| CP-23 | Prioridades | La prioridad seleccionada se conserva. | Equivalencia |
| CP-24 | Pantalla ancha | La colección se adapta sin perder contenido. | Adaptación |

La guía del instructor usa como referencia una suite de al menos 16 casos y destaca navegación, límites, duplicación, accesibilidad y regresión. fileciteturn6file0L263-L277
