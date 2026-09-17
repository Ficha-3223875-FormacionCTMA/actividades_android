Bitácora de Ejecución — Semana 4

1. Información general

Proyecto: Mi Formación CTMA
Actividad: Pruebas de software — Semana 4
Herramientas: FastAPI, Postman, Swagger y DevTools de Chrome.

2. Objetivo

Registrar las actividades realizadas durante la ejecución de las pruebas de software del proyecto, incluyendo pruebas funcionales, negativas, Smoke, exploratorias y de regresión.

3. Registro de ejecución

ID / Actividad| Prueba realizada| Resultado| Observación
EV-01| Login correcto| PASS| Se obtuvo respuesta 200 con credenciales válidas.
EV-02| Listar actividades| PASS| Se obtuvieron las actividades registradas.
EV-03| Actividad inexistente| PASS| Se validó el comportamiento esperado para una actividad que no existe.
EV-04| Consultar actividad| PASS| Se obtuvo la información de la actividad.
EV-05| Gestión de estados| PASS| Se validó la operación relacionada con el estado.
EV-06| Estado no válido| PASS| Se validó el rechazo del estado incorrecto.
EV-07| Registrar evidencia| PASS| La evidencia fue registrada correctamente.
EV-08| Consultar evidencia| PASS| Se validó la consulta de evidencias.
EV-09| Consultar actividad| PASS| La consulta respondió correctamente.
EV-10| Datos incorrectos| PASS| La API realizó la validación correspondiente.
EV-11| Operación de actividad| PASS| La operación fue procesada correctamente.
EV-12| Actualización de estado| PASS| Se validó el cambio de estado.
EV-13| Estado incorrecto| PASS| Se validó el comportamiento ante un estado no permitido.
EV-14| Login incorrecto| PASS| Se obtuvo 401, respuesta esperada para credenciales incorrectas.
EV-15| Validación de actividad| PASS| Se verificó la validación correspondiente.

4. Smoke Test

Se realizaron pruebas rápidas sobre las funcionalidades principales para verificar que el sistema mantuviera su funcionamiento básico.

Casos utilizados:

- EV-01 — Login correcto.
- EV-02 — Listar actividades.
- EV-05 — Consultar estados.
- EV-07 — Registrar evidencia.
- EV-09 — Consultar actividad.

Resultado: PASS.

5. Prueba exploratoria

Se realizó una sesión exploratoria sobre la API utilizando Swagger, realizando una entrada no habitual para observar el comportamiento del sistema.

Se verificó la respuesta generada por la API y se registró la evidencia correspondiente.

Resultado: sesión ejecutada.

6. Prueba de regresión

Se ejecutaron nuevamente casos previamente validados para comprobar que las funcionalidades continuaran funcionando después de las pruebas realizadas.

Casos utilizados:

- EV-01
- EV-02
- EV-05
- EV-07
- EV-09
- EV-14

Resultado: PASS.

En EV-14 se obtuvo código 401, correspondiente al comportamiento esperado ante credenciales incorrectas.

7. Herramientas de desarrollo

Se utilizaron las herramientas de desarrollo de Chrome para revisar el comportamiento de la aplicación y sus solicitudes.

Se revisaron principalmente:

- Network.
- Console.

Las capturas correspondientes fueron almacenadas en la carpeta de evidencias de Semana 4.

8. Resultado general

Las pruebas realizadas permitieron verificar las principales funcionalidades de la API de Mi Formación CTMA y validar tanto escenarios positivos como negativos.

No se registraron defectos confirmados durante esta ejecución.

9. Pendiente

Queda pendiente realizar la integración entre la aplicación Android y la API FastAPI.

Una vez realizada la integración, se ejecutarán pruebas adicionales desde Android para verificar el consumo de los servicios de la API.