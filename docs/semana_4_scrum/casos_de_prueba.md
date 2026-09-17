Casos de Prueba — Semana 4

1. Información general

Proyecto: Mi Formación CTMA
Objetivo: Documentar los casos utilizados para verificar las funcionalidades de la API.

2. Casos de prueba

ID| Nombre| Tipo| Resultado esperado| Resultado
EV-01| Login correcto| Positiva| Permitir autenticación con credenciales válidas| PASS
EV-02| Listar actividades| Positiva| Mostrar las actividades registradas| PASS
EV-03| Actividad inexistente| Negativa| Informar que la actividad no existe| PASS
EV-04| Consultar actividad| Positiva| Mostrar los datos de la actividad| PASS
EV-05| Consultar/cambiar estado| Funcional| Procesar correctamente el estado de la actividad| PASS
EV-06| Estado no válido| Negativa| Rechazar un estado no permitido| PASS
EV-07| Registrar evidencia| Positiva| Registrar correctamente la evidencia| PASS
EV-08| Consultar evidencia| Positiva| Mostrar las evidencias registradas| PASS
EV-09| Consultar actividad| Positiva| Obtener correctamente la información| PASS
EV-10| Datos incorrectos| Negativa| Rechazar datos que no cumplen las validaciones| PASS
EV-11| Operación sobre actividad| Funcional| Procesar correctamente la operación| PASS
EV-12| Actualización de estado| Funcional| Actualizar correctamente el estado| PASS
EV-13| Estado incorrecto| Negativa| Rechazar el estado no permitido| PASS
EV-14| Login incorrecto| Negativa| Rechazar credenciales incorrectas| PASS
EV-15| Validación de actividad| Negativa| Aplicar correctamente las validaciones| PASS

3. Pruebas positivas

Las pruebas positivas verifican que las funcionalidades principales funcionen cuando se proporcionan datos válidos.

Casos principales:

- EV-01
- EV-02
- EV-04
- EV-07
- EV-08
- EV-09
- EV-11
- EV-12

4. Pruebas negativas

Las pruebas negativas verifican que el sistema responda adecuadamente ante datos incorrectos o situaciones no permitidas.

Casos:

- EV-03
- EV-06
- EV-10
- EV-13
- EV-14
- EV-15

En EV-14 se validó que un intento de inicio de sesión con credenciales incorrectas genere una respuesta 401 Unauthorized.

5. Smoke Test

Los casos seleccionados para Smoke Test fueron:

- EV-01 — Login correcto.
- EV-02 — Listar actividades.
- EV-05 — Consultar estados.
- EV-07 — Registrar evidencia.
- EV-09 — Consultar actividad.

El objetivo fue comprobar rápidamente las funcionalidades principales de la API.

6. Prueba exploratoria

Se realizó una prueba exploratoria utilizando Swagger para comprobar el comportamiento de la API ante una entrada no habitual.

La sesión permitió observar la respuesta del sistema y registrar la evidencia correspondiente.

7. Regresión

Los casos utilizados para la prueba de regresión fueron:

- EV-01
- EV-02
- EV-05
- EV-07
- EV-09
- EV-14

Estos casos se ejecutaron nuevamente para verificar que las funcionalidades previamente comprobadas continuaran funcionando.

8. Evidencias

Las evidencias generales de Semana 4 se encuentran en:

"docs/semana_4/evidencias/"

Archivos:

- "EV-S4-01-devtools-red.png"
- "EV-S4-02-devtools-consola.png"
- "EV-S4-03-smoke-test.png"
- "EV-S4-04-exploratoria.png"
- "EV-S4-05-regresion.png"