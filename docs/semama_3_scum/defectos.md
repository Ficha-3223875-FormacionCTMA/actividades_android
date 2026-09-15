Semana 3 — Gestión de Defectos

1. Objetivo

Registrar, analizar y realizar seguimiento a los posibles defectos encontrados durante las pruebas de la aplicación Mi Formación CTMA.

La gestión de defectos permite identificar problemas, determinar su impacto, establecer su prioridad y controlar su solución hasta realizar nuevamente la prueba.

---

2. Definición de defecto

Un defecto es un comportamiento de la aplicación que no cumple con un criterio de aceptación, resultado esperado o requisito definido para el proyecto.

Los defectos deben registrarse de manera clara y reproducible para facilitar su análisis y corrección.

---

3. Ciclo de vida de un defecto

El ciclo utilizado para gestionar los defectos es:

NUEVO → TRIAGE/ANÁLISIS → ASIGNADO → EN CORRECCIÓN → RESUELTO → LISTO PARA REPRUEBA → CERRADO

Si después de la reprueba el problema continúa:

LISTO PARA REPRUEBA → REABIERTO → EN CORRECCIÓN

Estados

Estado| Descripción
NUEVO| Se identifica y registra el defecto.
TRIAGE/ANÁLISIS| Se analiza el problema y se determina su impacto.
ASIGNADO| Se asigna la corrección al responsable.
EN CORRECCIÓN| Se está trabajando en la solución.
RESUELTO| El responsable indica que el problema fue solucionado.
LISTO PARA REPRUEBA| El defecto está disponible para ser probado nuevamente.
CERRADO| La solución fue comprobada y el defecto ya no se presenta.
REABIERTO| El problema continúa después de la reprueba.

---

4. Severidad y prioridad

La severidad representa el impacto que tiene el defecto sobre la aplicación.

La prioridad representa qué tan urgente es corregirlo.

Severidad

Nivel| Descripción
Crítica| Impide utilizar una función principal de la aplicación.
Alta| Afecta una funcionalidad importante.
Media| Afecta parcialmente la presentación o funcionamiento.
Baja| Tiene poco impacto y no impide utilizar la aplicación.

Prioridad

Nivel| Descripción
Alta| Debe corregirse antes de la entrega.
Media| Debe corregirse, pero puede atenderse después de los problemas principales.
Baja| Puede corregirse como mejora posterior.

---

5. Registro de defectos

Durante la ejecución de las pruebas se utilizará el siguiente registro:

ID| Título| Referencia| Severidad| Prioridad| Estado
DEF-01| Pendiente de ejecución| CP correspondiente| Por determinar| Por determinar| NUEVO
DEF-02| Pendiente de ejecución| CP correspondiente| Por determinar| Por determinar| NUEVO
DEF-03| Pendiente de ejecución| CP correspondiente| Por determinar| Por determinar| NUEVO

Los registros anteriores funcionan como estructura para documentar los defectos reales encontrados durante la ejecución.

No se deben registrar defectos como reales si no fueron observados durante las pruebas.

---

6. Formato para reporte de defecto

Cuando se encuentre un defecto se utilizará la siguiente estructura:

DEF-XX — Título del defecto

Fecha: Por registrar
Autor: Aprendiz del proyecto
Ambiente: Android Studio / dispositivo o emulador utilizado
Referencia: CP-XX / CA-XX

Contexto

Descripción breve de la situación en la que se presentó el problema.

Precondiciones

Condiciones necesarias para reproducir el defecto.

Pasos para reproducir

1. Abrir la aplicación.
2. Acceder a la funcionalidad correspondiente.
3. Realizar la acción que produce el problema.
4. Observar el resultado.

Resultado esperado

Descripción de lo que debería ocurrir de acuerdo con el criterio de aceptación.

Resultado obtenido

Descripción de lo que realmente ocurrió.

Evidencia

Captura de pantalla o evidencia correspondiente.

Severidad

Por determinar de acuerdo con el impacto.

Prioridad

Por determinar de acuerdo con la urgencia.

Estado

NUEVO

---

7. Ejemplo de defecto

El siguiente ejemplo sirve como simulación para practicar el proceso de gestión de defectos.

DEF-EJ-01 — Texto de actividad cortado

Fecha: Ejemplo académico
Autor: Aprendiz
Ambiente: Android Studio / emulador Android
Referencia: CP-08 / CA-08

Contexto

Se prueba una actividad que contiene un título de longitud extensa.

Precondición

La aplicación se encuentra instalada y la pantalla de actividades está disponible.

Pasos para reproducir

1. Abrir la aplicación.
2. Acceder a la pantalla de actividades.
3. Buscar una actividad con un título extenso.
4. Observar la tarjeta.

Resultado esperado

El título debe permanecer visible y la información no debe aparecer cortada ni superpuesta.

Resultado obtenido

El texto aparece parcialmente cortado.

Evidencia

Captura de pantalla de la tarjeta donde se observa el problema.

Severidad

Media.

Prioridad

Media.

Estado

NUEVO.

«Este registro es únicamente un ejemplo académico. Si durante la prueba real el problema no ocurre, no debe registrarse como un defecto real.»

---

8. Ejemplo de ciclo de corrección

Si se detecta un defecto durante las pruebas:

1. Se registra como NUEVO.
2. Se analiza el problema en TRIAGE/ANÁLISIS.
3. Se asigna al responsable.
4. Se realiza la corrección.
5. Se marca como RESUELTO.
6. Se realiza nuevamente la prueba.
7. Si funciona correctamente, pasa a CERRADO.
8. Si el problema continúa, pasa a REABIERTO y vuelve al proceso de corrección.

---

9. Relación entre pruebas y defectos

Cada defecto debe estar relacionado con el caso de prueba que permitió identificarlo.

Ejemplo:

Caso de prueba| Criterio| Posible defecto| Estado
CP-08| CA-08| Información cortada o superpuesta| Por verificar
CP-11| CA-11| Elemento relacionado no responde| Por verificar
CP-12| CA-12| Problema de organización visual| Por verificar

Esta relación permite mantener la trazabilidad entre las pruebas ejecutadas y los problemas encontrados.

---

10. Criterios para cerrar un defecto

Un defecto puede pasar a CERRADO cuando:

- La corrección fue realizada.
- Se ejecutó nuevamente el caso de prueba.
- El resultado obtenido coincide con el resultado esperado.
- El problema ya no se presenta.
- La evidencia de la reprueba fue registrada.

---

11. Conclusión

La gestión de defectos permite controlar de manera ordenada los problemas encontrados durante las pruebas de Mi Formación CTMA.

El uso de estados, severidad, prioridad y reportes reproducibles facilita identificar los problemas, realizar su corrección y comprobar posteriormente que hayan sido solucionados.

Los defectos reales se registrarán únicamente después de ejecutar las pruebas correspondientes y contar con evidencia.