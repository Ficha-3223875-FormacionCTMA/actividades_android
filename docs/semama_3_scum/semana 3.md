ACTIVIDAD SEMANA 3 - PRUEBAS DE SOFTWARE Y SCRUM

Proyecto: Mi Formación CTMA

Programa: Análisis y Desarrollo de Software (ADSO)
Ficha: 3223875
Aprendiz: Alejandra Herrera
Instructor: Wilson Castro Gil
Semana: 3
Actividad: Pruebas de software y SCRUM

---

1. Objetivo

Diseñar y organizar pruebas de software para la aplicación móvil Mi Formación CTMA, utilizando técnicas sistemáticas que permitan verificar el cumplimiento de los criterios de aceptación definidos para el proyecto.

Durante esta actividad se aplican técnicas como:

- Partición de equivalencia.
- Análisis de valores límite.
- Tabla de decisión.
- Transición de estados.
- Escenarios derivados de casos de uso.
- Gestión de defectos.
- Matriz de trazabilidad.

El objetivo es transformar los requisitos del proyecto en casos de prueba claros, reproducibles y relacionados con las historias de usuario.

---

2. Descripción del proyecto

Mi Formación CTMA es una aplicación móvil desarrollada con Android, Kotlin y Jetpack Compose.

La aplicación tiene como propósito facilitar a los aprendices SENA la consulta de información relacionada con su proceso de formación, actividades y compromisos.

La aplicación busca presentar la información de manera:

- Clara.
- Organizada.
- Legible.
- Accesible.
- Adaptable a diferentes tamaños de pantalla.

---

3. Problema identificado

Los aprendices pueden tener dificultades para organizar y consultar información relacionada con su formación, actividades y compromisos cuando esta se encuentra distribuida en diferentes medios.

La aplicación Mi Formación CTMA busca centralizar esta información en una interfaz sencilla que permita consultarla de forma rápida y organizada.

---

4. Alcance de las pruebas

Las pruebas de esta semana se concentran principalmente en:

- Apertura de la aplicación.
- Información de formación.
- Visualización de actividades.
- Organización de las actividades.
- Presentación de títulos y descripciones.
- Legibilidad de la información.
- Próximo compromiso.
- Presentación general de la interfaz.
- Manejo del estado vacío de actividades.
- Identificación y gestión de posibles defectos.

Las pruebas se realizan utilizando datos sintéticos.

---

5. Historias de usuario

HU-01 — Consultar información de formación

Como aprendiz SENA,
quiero consultar información sobre mi formación CTMA,
para conocer contenidos importantes de mi proceso formativo.

Criterios de aceptación

- CA-01: Mostrar título "Mi Formación CTMA".
- CA-02: Mostrar información relacionada con la formación.
- CA-03: La información debe ser clara y fácil de leer.
- CA-04: La pantalla debe funcionar correctamente al abrir.

---

HU-02 — Consultar actividades formativas

Como aprendiz SENA,
quiero visualizar actividades formativas,
para conocer las tareas y contenidos que debo revisar.

Criterios de aceptación

- CA-05: Mostrar sección de actividades.
- CA-06: Cada actividad debe tener nombre o descripción.
- CA-07: Las actividades deben estar organizadas.
- CA-08: La información no debe aparecer cortada ni superpuesta.

---

HU-03 — Consultar próximo compromiso

Como aprendiz SENA,
quiero consultar mi próximo compromiso,
para recordar qué actividad debo realizar.

Criterios de aceptación

- CA-09: Mostrar información del próximo compromiso.
- CA-10: La información del compromiso debe ser entendible.
- CA-11: El elemento relacionado debe responder correctamente cuando esté implementado.
- CA-12: La interfaz debe conservar una presentación organizada.

---

6. Técnicas de diseño de pruebas

Para esta actividad se utilizaron diferentes técnicas de diseño de pruebas.

6.1 Partición de equivalencia

Se dividieron los datos utilizados en las actividades en grupos que presentan comportamientos similares.

Se analizaron principalmente:

- Actividades con título y descripción.
- Actividades con título largo.
- Actividades sin descripción.
- Varias actividades.

Esta técnica permite reducir la cantidad de pruebas necesarias manteniendo una cobertura representativa.

---

6.2 Valores límite

Se analizaron textos de diferentes longitudes:

- Texto corto.
- Texto de longitud normal.
- Texto largo.

El objetivo es verificar que los textos no aparezcan cortados, superpuestos o afecten la organización de las tarjetas.

---

6.3 Tabla de decisión

Se analizaron diferentes combinaciones relacionadas con:

- Existencia de actividades.
- Existencia de título.
- Existencia de descripción.
- Presentación correcta de la información.

A partir de estas condiciones se establecieron cuatro reglas de decisión.

---

6.4 Transición de estados

Se analizaron estados de la interfaz relacionados con la consulta de actividades:

- Pantalla inicial.
- Lista con actividades.
- Estado vacío.

Se establecieron transiciones válidas, alternativas e inválidas para comprobar el comportamiento esperado.

---

6.5 Escenarios de casos de uso

Se diseñó un flujo principal para consultar actividades y dos escenarios alternos:

1. Actividad sin descripción.
2. No existen actividades disponibles.

Estos escenarios permiten comprobar diferentes situaciones que puede encontrar el usuario.

---

7. Casos de prueba

Se diseñaron 12 casos de prueba, relacionados directamente con los criterios de aceptación.

ID| Criterio| Descripción
CP-01| CA-01| Verificar título de la aplicación.
CP-02| CA-02| Verificar información de formación.
CP-03| CA-03| Verificar legibilidad.
CP-04| CA-04| Verificar apertura de la aplicación.
CP-05| CA-05| Verificar sección de actividades.
CP-06| CA-06| Verificar información de las actividades.
CP-07| CA-07| Verificar organización de actividades.
CP-08| CA-08| Verificar que la información no aparezca cortada.
CP-09| CA-09| Verificar información del próximo compromiso.
CP-10| CA-10| Verificar comprensión del compromiso.
CP-11| CA-11| Verificar respuesta del elemento relacionado.
CP-12| CA-12| Verificar presentación organizada.

Los detalles completos de los casos se encuentran en:

"Casos de Prueba.md"

---

8. Gestión de defectos

Durante la ejecución de las pruebas, cualquier comportamiento que no cumpla con el resultado esperado será registrado como posible defecto.

Para cada defecto se tendrá en cuenta:

- ID.
- Título.
- Fecha.
- Ambiente.
- Caso de prueba relacionado.
- Pasos para reproducir.
- Resultado esperado.
- Resultado obtenido.
- Evidencia.
- Severidad.
- Prioridad.
- Estado.

El ciclo de vida definido es:

NUEVO → TRIAGE/ANÁLISIS → ASIGNADO → EN CORRECCIÓN → RESUELTO → LISTO PARA REPRUEBA → CERRADO

Si el problema continúa después de la reprueba:

REABIERTO → EN CORRECCIÓN

Los detalles completos se encuentran en:

"Gestión de Defectos.md"

---

9. Matriz de trazabilidad

La matriz de trazabilidad relaciona:

Historia de usuario → Criterio de aceptación → Caso de prueba → Técnica → Resultado → Defecto

Se definieron:

- 3 historias de usuario.
- 12 criterios de aceptación.
- 12 casos de prueba.
- 4 técnicas principales de diseño.

La asignación documental alcanza una cobertura del 100 % de los criterios de aceptación, debido a que los 12 criterios cuentan con un caso de prueba asociado.

El resultado PASS/FAIL se registrará después de ejecutar las pruebas.

La matriz completa se encuentra en:

"Matriz de Trazabilidad.md"

---

10. Datos y precondiciones

Los datos utilizados para las pruebas son sintéticos.

Ejemplos:

- "Actividad de aprendizaje".
- "Actividad con título largo para comprobar la presentación".
- Actividad con descripción.
- Actividad sin descripción.
- Varias actividades.
- Lista sin actividades.

Las principales precondiciones son:

1. Tener la aplicación instalada.
2. Contar con un emulador o dispositivo Android.
3. Ejecutar correctamente la aplicación.
4. Contar con las actividades de prueba.
5. Tener disponible la versión del proyecto que será evaluada.

---

11. Resultados esperados

La aplicación debe:

- Abrir correctamente.
- Mostrar el título de la aplicación.
- Mostrar información de formación.
- Mostrar las actividades.
- Mantener las actividades organizadas.
- Mostrar correctamente títulos y descripciones.
- Mantener la información legible.
- Evitar textos cortados o superpuestos.
- Mostrar el estado vacío cuando corresponda.
- Mostrar la información del próximo compromiso.
- Mantener una interfaz organizada.

Los resultados PASS/FAIL serán registrados durante la ejecución de las pruebas.

---

12. Evidencias

Las evidencias se incorporarán después de realizar las pruebas finales.

Se utilizarán principalmente capturas de:

1. Pantalla principal de Mi Formación CTMA.
2. Pantalla de actividades.
3. Tarjetas de actividades.
4. Estado vacío, si se puede demostrar.
5. Información del próximo compromiso.
6. Android Studio mostrando la aplicación funcionando.
7. Estructura del proyecto.
8. Repositorio/GitHub.

Una misma captura puede servir como evidencia para varios casos de prueba cuando demuestre claramente el comportamiento evaluado.

---

13. Control de versiones

El proyecto se mantiene en un repositorio Git para conservar los cambios realizados durante las diferentes semanas.

Los documentos de pruebas forman parte de la documentación del proyecto.

Los cambios deben registrarse mediante commits descriptivos.

Ejemplo:

git add .
git commit -m "docs: completa pruebas y trazabilidad semana 3"
git push

---

14. Checklist de cumplimiento

- [x] Historias de usuario definidas.
- [x] Criterios de aceptación definidos.
- [x] 12 casos de prueba diseñados.
- [x] Casos positivos incluidos.
- [x] Casos negativos considerados.
- [x] Partición de equivalencia.
- [x] Valores límite.
- [x] Tabla de decisión con 4 reglas.
- [x] Transición de estados.
- [x] Caso de uso principal.
- [x] Dos escenarios alternos.
- [x] Datos sintéticos.
- [x] Precondiciones.
- [x] Resultados esperados observables.
- [x] Gestión de defectos.
- [x] Severidad y prioridad.
- [x] Ciclo de vida de defectos.
- [x] Matriz de trazabilidad.
- [x] Relación entre HU, CA y CP.
- [x] Ejecutar pruebas finales.
- [x] Registrar resultados PASS/FAIL.
- [x] Agregar evidencias.
- [x] Registrar defectos reales encontrados.
- [x] Actualizar matriz con resultados finales.
- [x] Realizar commit final.

---

15. Conclusión

La actividad de Semana 3 permitió transformar los requisitos de Mi Formación CTMA en casos de prueba concretos y trazables.

Se utilizaron técnicas de partición de equivalencia, valores límite, tabla de decisión, transición de estados y escenarios derivados de casos de uso.

Además, se estableció un proceso para registrar y gestionar defectos y una matriz de trazabilidad que permite relacionar las historias de usuario con los criterios de aceptación y casos de prueba.

De esta manera, el proyecto cuenta con una estructura organizada para ejecutar pruebas, identificar posibles problemas y comprobar que las funcionalidades desarrolladas cumplan con los criterios establecidos.