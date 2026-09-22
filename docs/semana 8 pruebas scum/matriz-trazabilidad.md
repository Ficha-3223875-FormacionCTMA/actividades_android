# MATRIZ DE TRAZABILIDAD — SEMANA 8
## Pruebas de Software y SCRUM

| ID | Requisito / criterio | Riesgo asociado | Prueba manual | Prueba automatizada | Resultado |
|---|---|---|---|---|---|
| TR-01 | Una actividad debe permitir cambios de estado válidos. | Que una actividad no cambie correctamente de estado. | Cambiar una actividad de PENDIENTE a EN_PROCESO. | `actividad-status.test.js` — cambio PENDIENTE → EN_PROCESO. | APROBADO |
| TR-02 | No se debe permitir una transición de estado no válida. | Que una actividad CANCELADA pase a COMPLETADA. | Intentar cambiar CANCELADA a COMPLETADA. | `actividad-status.test.js` — rechazo CANCELADA → COMPLETADA. | APROBADO |
| TR-03 | Los estados utilizados por las actividades deben ser válidos. | Que el sistema acepte estados inexistentes. | Registrar o cambiar una actividad usando un estado inválido. | `actividad-status.test.js` — estado no válido. | APROBADO |
| TR-04 | La creación de una actividad debe validar sus datos básicos. | Que se creen actividades con título vacío, muy corto o descripción demasiado larga. | Crear una actividad con datos inválidos. | `actividad-status.test.js` — validaciones de título y descripción. | APROBADO |
| TR-05 | El servicio debe actualizar correctamente una actividad existente. | Que el cambio de estado no se guarde correctamente. | Cambiar el estado de una actividad existente. | `actividad-service.test.js` — actualización de estado. | APROBADO |
| TR-06 | El sistema debe informar cuando una actividad no existe. | Que se intente modificar una actividad inexistente. | Buscar o modificar una actividad con un ID inexistente. | `actividad-service.test.js` — actividad inexistente. | APROBADO |
| TR-07 | La API debe permitir consultar las actividades con autenticación. | Que un usuario autenticado no pueda consultar las actividades. | Ejecutar GET `/api/actividades` con token válido. | `pruebas_api/test_api.py::test_listar_actividades`. | APROBADO |
| TR-08 | La API debe permitir crear una actividad con datos válidos. | Que una actividad válida no sea registrada correctamente. | Ejecutar POST `/api/actividades` con datos válidos. | `pruebas_api/test_api.py::test_crear_actividad`. | APROBADO |
| TR-09 | La API debe impedir el acceso sin autenticación. | Que usuarios sin token puedan consultar información protegida. | Ejecutar GET `/api/actividades` sin token. | `pruebas_api/test_api.py::test_acceso_sin_autenticacion`. | APROBADO |
| TR-10 | Las pruebas deben ser repetibles. | Que los resultados cambien entre ejecuciones. | Ejecutar la suite varias veces. | `npm test` ejecutado 3 veces. | APROBADO |

---

## Relación entre pruebas y riesgos

### Riesgo 1 — Transiciones de estado incorrectas
Se cubre mediante las pruebas de:

- `actividad-status.test.js`
- `actividad-status-parametrized.test.js`
- `actividad-service.test.js`

Estas pruebas verifican tanto transiciones permitidas como transiciones rechazadas.

### Riesgo 2 — Datos inválidos al crear actividades
Se cubre mediante las pruebas de validación de:

- título válido;
- título vacío;
- título demasiado corto;
- descripción demasiado larga.

Archivo:

`actividad-status.test.js`

### Riesgo 3 — Actividad inexistente
Se cubre mediante:

`actividad-service.test.js`

La prueba verifica que el servicio genere el error correspondiente y que no intente guardar una actividad inexistente.

### Riesgo 4 — Acceso no autorizado
Se cubre mediante:

`pruebas_api/test_api.py`

La prueba verifica que una petición sin autenticación obtenga HTTP 401.

### Riesgo 5 — Fallos en la API
Se cubre mediante pruebas automatizadas utilizando:

- FastAPI TestClient;
- pytest;
- GET de actividades;
- POST de actividades;
- validación de autenticación.

---

## Resumen de cobertura de pruebas

| Área | Pruebas |
|---|---:|
| Estados de actividades | 11 |
| Servicio de actividades | 3 |
| API FastAPI | 3 |
| **Total** | **17 casos principales** |

> Nota: las pruebas parametrizadas generan 5 escenarios adicionales dentro de una misma prueba parametrizada. Por eso el reporte de Vitest registra 18 pruebas ejecutadas.

---

## Herramientas utilizadas

### Pruebas unitarias

- Vitest 3.2.7
- JavaScript
- Vitest Mock (`vi.fn`)

### Pruebas de API

- pytest 9.1.1
- FastAPI TestClient
- httpx

### Cobertura

- Vitest
- V8

---

## Evidencias relacionadas

- `EV-01-pruebas-unitarias.txt`
- `EV-02-cobertura.txt`
- `EV-03-repetibilidad.txt`
- `EV-04-api-fastapi.txt`

---

## Conclusión

La matriz permite relacionar los requisitos y riesgos identificados con sus respectivas pruebas manuales y automatizadas.

Las pruebas automatizadas verifican reglas de negocio, validaciones, cambios de estado, manejo de actividades inexistentes y autenticación de la API.

Los resultados obtenidos hasta el momento muestran pruebas unitarias y de API aprobadas, además de una ejecución repetible de la suite.