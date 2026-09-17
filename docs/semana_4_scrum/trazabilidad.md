Matriz de Trazabilidad — Semana 4

Proyecto

Mi Formación CTMA

Objetivo

Relacionar los requisitos funcionales del proyecto con los casos de prueba, su ejecución, resultado y evidencia correspondiente.

ID| Requisito / funcionalidad| Caso de prueba| Resultado esperado| Resultado obtenido| Evidencia| Defecto
EV-01| Autenticación de usuario| Login correcto| El usuario inicia sesión con credenciales válidas| PASS — respuesta 200| EV-01| Ninguno
EV-02| Consulta de actividades| Listar actividades| El sistema devuelve las actividades registradas| PASS — respuesta 200| EV-02| Ninguno
EV-03| Validación de actividad inexistente| Consultar actividad inexistente| El sistema informa que la actividad no existe| PASS — respuesta esperada| EV-03| Ninguno
EV-04| Consulta de actividad| Consultar actividad existente| El sistema muestra la información de la actividad| PASS| EV-04| Ninguno
EV-05| Gestión de estados| Consultar/cambiar estado de actividad| El sistema permite consultar o actualizar el estado| PASS| EV-05| Ninguno
EV-06| Validación de estado| Estado no válido| El sistema rechaza un estado no permitido| PASS| EV-06| Ninguno
EV-07| Registro de evidencias| Registrar evidencia| El sistema registra correctamente la evidencia| PASS| EV-07| Ninguno
EV-08| Consulta de evidencias| Consultar evidencias| El sistema muestra las evidencias registradas| PASS| EV-08| Ninguno
EV-09| Consulta de actividad| Consultar actividad| El sistema devuelve la información solicitada| PASS — respuesta 200| EV-09| Ninguno
EV-10| Validación de datos| Envío de datos incorrectos| El sistema rechaza los datos inválidos| PASS — respuesta esperada| EV-10| Ninguno
EV-11| Gestión de actividades| Operación sobre actividad| El sistema procesa correctamente la operación| PASS| EV-11| Ninguno
EV-12| Estados de actividad| Actualización de estado| El sistema actualiza el estado correctamente| PASS| EV-12| Ninguno
EV-13| Validación de estados| Estado incorrecto| El sistema rechaza el estado no permitido| PASS| EV-13| Ninguno
EV-14| Autenticación| Login con credenciales incorrectas| El sistema rechaza el acceso| PASS — respuesta 401 esperada| EV-14| Ninguno
EV-15| Validación de actividad| Operación con datos no válidos| El sistema responde con la validación correspondiente| PASS| EV-15| Ninguno

Resumen de ejecución

- Casos de prueba: 15
- Casos ejecutados: 15
- Casos con resultado esperado: 15
- Casos con defecto confirmado: 0
- Autenticación: validada con credenciales correctas e incorrectas.
- Actividades: validación de consulta y operaciones.
- Estados: validación de consulta y actualización.
- Evidencias: registro y consulta.
- Pruebas negativas: incluidas dentro de los casos EV-03, EV-06, EV-10, EV-13 y EV-14.

Evidencias de Semana 4

Evidencia| Descripción
EV-S4-01-devtools-red.png| Inspección de solicitudes mediante DevTools — Network
EV-S4-02-devtools-consola.png| Revisión de la consola del navegador
EV-S4-03-smoke-test.png| Ejecución de pruebas Smoke
EV-S4-04-exploratoria.png| Sesión de prueba exploratoria
EV-S4-05-regresion.png| Ejecución de pruebas de regresión

Observaciones

Las pruebas fueron realizadas sobre la API de Mi Formación CTMA utilizando Postman, Swagger y las herramientas de desarrollo del navegador.

Los resultados registrados corresponden a las respuestas observadas durante la ejecución. Las respuestas de error utilizadas en las pruebas negativas se consideran correctas cuando coinciden con el comportamiento esperado del caso de prueba.

La integración de la aplicación Android con la API queda como actividad posterior y será validada mediante pruebas adicionales una vez realizada la conexión.