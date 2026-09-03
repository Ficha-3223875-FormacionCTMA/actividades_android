# Historias de usuario para crear en GitHub Issues

> Estas historias están adaptadas a **Mi Formación CTMA**. La guía del instructor usa PréstamoLab como caso de ejemplo, pero este repositorio corresponde al caso acumulativo Mi Formación CTMA de las semanas anteriores. La finalidad aquí es aplicar el mismo mecanismo de backlog, criterios, riesgo y trazabilidad sin reemplazar tu proyecto.

## HU-01 — Consultar actividades
**Como** aprendiz, **quiero** consultar mis actividades formativas, **para** conocer qué compromisos tengo pendientes.

### Criterios de aceptación
- **CA-01.1:** Dado que existen actividades, cuando abro la pantalla principal, entonces se muestran en una lista.
- **CA-01.2:** Cada actividad muestra título, fecha, prioridad, estado y progreso.
- **CA-01.3:** Si no existen actividades visibles, se muestra un estado vacío con una acción para crear.

## HU-02 — Crear actividad
**Como** aprendiz, **quiero** crear una actividad con datos válidos, **para** registrar un nuevo compromiso formativo.

### Criterios
- **CA-02.1:** Título obligatorio y entre 3 y 80 caracteres.
- **CA-02.2:** Descripción opcional y máximo 240 caracteres.
- **CA-02.3:** Progreso dentro de 0..100.
- **CA-02.4:** Con datos válidos y Guardar, se crea una sola actividad y aparece en la lista.
- **CA-02.5:** Si hay errores, no se guarda y los mensajes indican el campo afectado.

## HU-03 — Editar actividad
**Como** aprendiz, **quiero** editar una actividad existente, **para** mantener actualizados sus datos.

### Criterios
- **CA-03.1:** Desde el detalle puedo abrir Editar.
- **CA-03.2:** El formulario carga los datos actuales.
- **CA-03.3:** Guardar reemplaza la actividad por la versión actualizada sin crear un duplicado.

## HU-04 — Consultar detalle
**Como** aprendiz, **quiero** abrir el detalle de una actividad, **para** revisar toda su información antes de modificarla.

### Criterios
- **CA-04.1:** Al seleccionar una actividad se navega usando su ID.
- **CA-04.2:** El detalle muestra información completa y el estado textual.
- **CA-04.3:** Un ID inexistente muestra un estado recuperable y no cierra la aplicación.

## HU-05 — Buscar actividades
**Como** aprendiz, **quiero** buscar por título, **para** localizar rápidamente una actividad.

### Criterios
- **CA-05.1:** La búsqueda no distingue mayúsculas/minúsculas.
- **CA-05.2:** Solo se muestran coincidencias.
- **CA-05.3:** Si no hay coincidencias, se muestra el estado vacío.

## HU-06 — Identificar progreso y estado
**Como** aprendiz, **quiero** ver el progreso y estado de cada actividad, **para** decidir qué compromiso atender primero.

### Criterios
- **CA-06.1:** 0% se identifica como pendiente.
- **CA-06.2:** 1–99% se identifica como en proceso.
- **CA-06.3:** 100% se identifica como completada.
- **CA-06.4:** Una actividad vencida e incompleta se identifica como urgente/vencida según las reglas del proyecto.

## HU-07 — Usar fuente aumentada
**Como** aprendiz con necesidad de texto grande, **quiero** que la información y acciones esenciales sigan siendo utilizables, **para** completar mis tareas sin perder controles.

### Criterios
- **CA-07.1:** Con fuente 1.5× los textos esenciales siguen siendo comprensibles.
- **CA-07.2:** Las acciones principales permanecen accesibles.
- **CA-07.3:** El estado no depende exclusivamente del color.

## HU-08 — Conservar el formulario
**Como** aprendiz, **quiero** conservar el borrador del formulario ante una recreación de pantalla, **para** no perder lo que estaba diligenciando.

### Criterios
- **CA-08.1:** Los campos diligenciados se conservan ante recreaciones soportadas por el estado guardable.
- **CA-08.2:** El formulario no pierde silenciosamente el contenido antes de guardar.
