Semana 4 — Pruebas de Software

1. Información del proyecto

Proyecto: Mi Formación CTMA
Actividad: Pruebas de software y aseguramiento de calidad
Semana: 4

2. Introducción

Durante la Semana 4 se realizaron diferentes actividades relacionadas con las pruebas de software del proyecto Mi Formación CTMA.

Se utilizaron herramientas como Postman, Swagger y las herramientas de desarrollo de Google Chrome para verificar el funcionamiento de la API, validar respuestas y documentar los resultados obtenidos.

3. Objetivos

Objetivo general

Verificar el funcionamiento de las principales funcionalidades de la API de Mi Formación CTMA mediante diferentes tipos de pruebas de software.

Objetivos específicos

- Ejecutar casos de prueba positivos y negativos.
- Validar la autenticación de usuarios.
- Comprobar las operaciones relacionadas con actividades.
- Verificar la gestión de estados.
- Validar el registro y consulta de evidencias.
- Realizar pruebas Smoke.
- Realizar pruebas exploratorias.
- Realizar pruebas de regresión.
- Utilizar DevTools para observar el comportamiento de las solicitudes.
- Documentar los resultados y evidencias obtenidas.

4. Herramientas utilizadas

- FastAPI: implementación y ejecución de la API.
- Postman: ejecución de solicitudes y validación mediante aserciones.
- Swagger: exploración y prueba de los endpoints.
- Google Chrome DevTools: revisión de Network y Console.
- Android Studio: desarrollo de la aplicación móvil.
- Git/GitHub: control de versiones del proyecto.

5. Casos de prueba

Se trabajó con un conjunto de 15 casos de prueba, identificados desde EV-01 hasta EV-15.

Los casos incluyen:

- Autenticación.
- Consulta de actividades.
- Validación de actividades inexistentes.
- Gestión de estados.
- Registro y consulta de evidencias.
- Validaciones de datos.
- Escenarios positivos y negativos.

Los detalles completos se encuentran en:

"docs/semana_4/casos_de_prueba.md"

6. Pruebas Smoke

Se realizó un Smoke Test utilizando cinco casos principales:

- EV-01 — Login correcto.
- EV-02 — Listar actividades.
- EV-05 — Consultar estados.
- EV-07 — Registrar evidencia.
- EV-09 — Consultar actividad.

El objetivo fue comprobar rápidamente que las funcionalidades principales estuvieran disponibles y funcionando.

7. Prueba exploratoria

Se realizó una sesión de prueba exploratoria utilizando Swagger.

Se utilizaron datos no habituales para observar el comportamiento de la API y comprobar sus mecanismos de validación.

La evidencia se encuentra en:

"docs/semana_4/evidencias/EV-S4-04-exploratoria.png"

8. Pruebas de regresión

Se ejecutaron nuevamente seis casos previamente comprobados:

- EV-01
- EV-02
- EV-05
- EV-07
- EV-09
- EV-14

La prueba permitió verificar que las funcionalidades continuaran respondiendo de acuerdo con lo esperado.

En EV-14 se obtuvo una respuesta HTTP 401 Unauthorized, correspondiente al comportamiento esperado ante credenciales incorrectas.

9. DevTools

Se utilizaron las herramientas de desarrollo de Chrome para revisar las solicitudes y el comportamiento de la aplicación.

Se revisaron principalmente:

Network

Permitió observar:

- URL de la solicitud.
- Método HTTP.
- Código de estado.
- Dirección remota.
- Encabezados.

Console

Permitió comprobar la presencia o ausencia de errores registrados por el navegador.

Las evidencias se encuentran en:

"docs/semana_4/evidencias/"

10. Trazabilidad

Se creó una matriz de trazabilidad para relacionar los requisitos y funcionalidades con los casos de prueba, resultados y evidencias.

Archivo:

"docs/semana_4/trazabilidad.md"

11. Bitácora

Se registraron las ejecuciones realizadas durante la semana, incluyendo las pruebas funcionales, Smoke, exploratorias y de regresión.

Archivo:

"docs/semana_4/bitacora.md"

12. Registro de defectos

Los resultados fueron revisados para diferenciar errores reales de respuestas esperadas en pruebas negativas.

No se confirmaron defectos funcionales durante la ejecución documentada.

Archivo:

"docs/semana_4/defectos.md"

13. Evidencias

Las evidencias se encuentran organizadas en:

"docs/semana_4/evidencias/"

Archivos:

1. "EV-S4-01-devtools-red.png"
2. "EV-S4-02-devtools-consola.png"
3. "EV-S4-03-smoke-test.png"
4. "EV-S4-04-exploratoria.png"
5. "EV-S4-05-regresion.png"

14. Estado actual del proyecto

La API de Mi Formación CTMA fue probada mediante Postman y Swagger.

La aplicación Android continúa en desarrollo y queda pendiente realizar la integración entre Android y la API FastAPI.

Una vez realizada esta integración, se deberán ejecutar pruebas adicionales desde la aplicación móvil para verificar el consumo real de los servicios.

15. Conclusión

Las actividades realizadas durante la Semana 4 permitieron aplicar diferentes técnicas de pruebas de software sobre el proyecto Mi Formación CTMA.

Se verificaron funcionalidades mediante casos positivos y negativos, pruebas Smoke, exploratorias y de regresión. También se utilizaron herramientas de inspección del navegador y se organizaron las evidencias y documentación correspondiente.

Como actividad posterior queda la integración de la aplicación Android con la API FastAPI y la ejecución de pruebas finales sobre dicha integración.