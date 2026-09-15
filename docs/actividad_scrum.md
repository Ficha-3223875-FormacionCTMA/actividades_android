# ACTIVIDAD DE SCRUM

## PROYECTO MI FORMACIÓN CTMA

**Programa:** Análisis y Desarrollo de Software (ADSO)  
**Ficha:** 3223875  
**Aprendiz:** Alejandra Herrera  
**Instructor:** Wilson Castro Gil  
**Centro:** CTMA – SENA  
**Fecha:** 15 de septiembre de 2026

---

# 1. Contexto del proyecto

## 1.1 Nombre del proyecto

**Mi Formación CTMA**

## 1.2 Descripción del proyecto

Mi Formación CTMA es una aplicación móvil Android desarrollada con Android Studio, Kotlin y Jetpack Compose. Su propósito es permitir que los aprendices SENA consulten de manera organizada información relacionada con su formación, actividades y compromisos.

La aplicación busca ofrecer una interfaz sencilla, clara y fácil de utilizar, que permita al aprendiz encontrar la información de su proceso formativo de manera organizada.

## 1.3 Situación problema

Los aprendices suelen administrar actividades, enlaces, evidencias y fechas en diferentes canales. Esto puede producir olvidos, duplicación de información y poca trazabilidad.

Por esta razón, se plantea desarrollar Mi Formación CTMA como una aplicación Android que evolucione progresivamente durante las diferentes semanas del proyecto.

## 1.4 Objetivo

Desarrollar una aplicación móvil Android que permita al aprendiz consultar información relacionada con su formación CTMA, actividades formativas y próximos compromisos mediante una interfaz sencilla, clara y organizada.

## 1.5 Usuarios

| Usuario | Necesidad |
|---|---|
| Aprendiz | Consultar compromisos y registrar avance. |
| Instructor | Comunicar actividades y criterios. |
| Equipo de desarrollo | Evolucionar una base sin romperla. |

## 1.6 Valor esperado

| Usuario | Valor esperado |
|---|---|
| Aprendiz | Organización y visibilidad. |
| Instructor | Trazabilidad formativa. |
| Equipo de desarrollo | Calidad y colaboración. |

## 1.7 Product Goal

> Facilitar al aprendiz la consulta organizada de información de su formación CTMA, actividades y compromisos mediante una aplicación móvil.

---

# 2. Product Backlog

El Product Backlog reúne las necesidades y funcionalidades identificadas para **Mi Formación CTMA**. Los elementos se organizan por prioridad para orientar el desarrollo del producto.

| ID | Necesidad / Funcionalidad | Prioridad |
|---|---|---|
| PB-01 | Mostrar nombre y propósito de la aplicación | Alta |
| PB-02 | Mostrar información de formación CTMA | Alta |
| PB-03 | Mostrar actividades formativas | Alta |
| PB-04 | Mostrar próximo compromiso | Alta |
| PB-05 | Organizar información mediante tarjetas | Media |
| PB-06 | Consultar diferentes contenidos de formación | Media |
| PB-07 | Mejorar el diseño visual | Media |
| PB-08 | Realizar pruebas de funcionamiento | Alta |
| PB-09 | Documentar el proyecto y las evidencias | Alta |

## 2.1 Priorización del Product Backlog

Los elementos **PB-01, PB-02, PB-03 y PB-04** tienen prioridad alta porque representan las funcionalidades principales relacionadas con la consulta de información, actividades y compromisos del aprendiz.

Los elementos **PB-05, PB-06 y PB-07** tienen prioridad media porque complementan la organización, consulta y presentación de la aplicación.

Los elementos **PB-08 y PB-09** tienen prioridad alta porque permiten verificar el funcionamiento del producto y mantener documentado el proceso de desarrollo y sus evidencias.

---

# 3. Historias de Usuario

## HU-01 – Consultar información de formación

**Como** aprendiz SENA,  
**quiero** consultar información sobre mi formación CTMA,  
**para** conocer contenidos importantes de mi proceso formativo.

### Criterio de aceptación

- **CA-01:** La aplicación debe mostrar el título **"Mi Formación CTMA"**.

---

## HU-02 – Consultar actividades formativas

**Como** aprendiz SENA,  
**quiero** visualizar las actividades formativas,  
**para** conocer las tareas y contenidos que debo revisar.

### Criterio de aceptación

- **CA-05:** La aplicación debe mostrar una sección destinada a las actividades formativas.

---

## HU-03 – Consultar próximo compromiso

**Como** aprendiz SENA,  
**quiero** consultar mi próximo compromiso,  
**para** recordar qué actividad debo realizar.

### Criterio de aceptación

- **CA-09:** La aplicación debe mostrar una tarjeta correspondiente al próximo compromiso.

---

# 4. Criterios de aceptación ampliados

Los criterios definidos inicialmente en esta etapa se amplían posteriormente para permitir pruebas más detalladas del producto.

## HU-01 – Consultar información de formación

| ID | Criterio |
|---|---|
| CA-01 | Mostrar título "Mi Formación CTMA". |
| CA-02 | Mostrar información relacionada con la formación. |
| CA-03 | La información debe ser clara y fácil de leer. |
| CA-04 | La pantalla debe funcionar correctamente al abrir. |

## HU-02 – Consultar actividades formativas

| ID | Criterio |
|---|---|
| CA-05 | Mostrar sección de actividades. |
| CA-06 | Cada actividad debe tener nombre o descripción. |
| CA-07 | Las actividades deben estar organizadas. |
| CA-08 | La información no debe aparecer cortada ni superpuesta. |

## HU-03 – Consultar próximo compromiso

| ID | Criterio |
|---|---|
| CA-09 | Mostrar tarjeta del próximo compromiso. |
| CA-10 | La tarjeta debe incluir información entendible. |
| CA-11 | El botón o elemento relacionado debe responder correctamente. |
| CA-12 | La interfaz debe conservar una presentación organizada. |

---

# 5. Sprint Goal

> Desarrollar y organizar la pantalla principal de Mi Formación CTMA con información de formación, actividades y tarjeta de próximo compromiso.

El objetivo del Sprint es construir una primera versión funcional de la aplicación que permita al aprendiz visualizar información importante de su proceso formativo de manera clara y organizada.

---

# 6. Sprint Backlog

| ID | Tarea | Estado |
|---|---|---|
| SB-01 | Crear y configurar el proyecto Android | Realizada |
| SB-02 | Configurar Kotlin y Jetpack Compose | Realizada |
| SB-03 | Crear la pantalla principal | Realizada |
| SB-04 | Agregar el título de la aplicación | Realizada |
| SB-05 | Agregar información de formación | Realizada |
| SB-06 | Crear tarjeta de próximo compromiso | Realizada |
| SB-07 | Crear clases para las actividades | Realizada |
| SB-08 | Organizar los archivos del proyecto | Realizada |
| SB-09 | Ejecutar y probar la aplicación | En verificación |
| SB-10 | Crear evidencias y documentación | Realizada |
| SB-11 | Subir los cambios al repositorio GitHub | Pendiente de confirmar |

---

# 7. Definition of Done

Una tarea se considera terminada cuando cumple con las siguientes condiciones:

- El código está implementado correctamente.
- La funcionalidad cumple con los criterios de aceptación.
- El proyecto compila correctamente.
- La funcionalidad puede ejecutarse en Android.
- La interfaz presenta la información de manera organizada.
- Se realizan pruebas en Android Studio.
- No existen errores que impidan la ejecución de la aplicación.
- Los archivos se encuentran organizados dentro del proyecto.
- Se cuenta con las evidencias correspondientes.
- Los cambios importantes se registran mediante Git.
- El proyecto se encuentra respaldado en GitHub.

---

# 8. Roles Scrum

Para el desarrollo del proyecto se tienen en cuenta los siguientes roles:

| Rol | Responsabilidad |
|---|---|
| Product Owner | Define y prioriza las necesidades del producto. |
| Scrum Master | Apoya la organización y aplicación de Scrum. |
| Equipo de desarrollo | Diseña, programa, prueba y documenta la aplicación. |

En el desarrollo académico, una misma persona puede asumir los diferentes roles de acuerdo con las actividades realizadas.

---

# 9. Riesgos del proyecto

| ID | Riesgo | Probabilidad | Impacto | Prevención |
|---|---|---|---|---|
| R-01 | El proyecto no compila correctamente. | Media | Alto | Revisar errores y realizar Gradle Sync. |
| R-02 | La interfaz puede quedar desorganizada. | Media | Medio | Probar la aplicación en diferentes tamaños de pantalla. |
| R-03 | Pérdida de cambios realizados. | Baja | Alto | Guardar los cambios y utilizar Git/GitHub. |
| R-04 | Una funcionalidad no responde correctamente. | Media | Alto | Realizar pruebas antes de la entrega. |

---

# 10. Plan inicial de pruebas

Para verificar el funcionamiento de **Mi Formación CTMA** se plantean las siguientes pruebas:

| ID | Prueba | Resultado esperado |
|---|---|---|
| PR-01 | Abrir la aplicación | La aplicación inicia correctamente. |
| PR-02 | Verificar el título | Se muestra "Mi Formación CTMA". |
| PR-03 | Verificar información de formación | La información se muestra correctamente. |
| PR-04 | Verificar actividades | Las actividades aparecen organizadas. |
| PR-05 | Verificar tarjeta de compromiso | Se muestra la información del compromiso. |
| PR-06 | Revisar textos | Los textos no aparecen cortados ni superpuestos. |
| PR-07 | Probar en emulador o dispositivo | La aplicación mantiene una presentación correcta. |
| PR-08 | Revisar compilación | El proyecto compila sin errores que impidan su ejecución. |
| PR-09 | Revisar estructura | Los archivos están organizados en sus respectivos paquetes. |

---

# 11. Evidencias

Como parte de la documentación del proyecto se deben conservar evidencias del desarrollo y las pruebas realizadas.

Las evidencias pueden incluir:

- Captura de la pantalla principal.
- Captura de la tarjeta de próximo compromiso.
- Captura de la sección de actividades.
- Captura de la aplicación ejecutándose en Android Studio.
- Captura de la estructura de carpetas del proyecto.
- Captura de las pruebas realizadas.
- Evidencia de los cambios realizados mediante Git.
- Evidencia del repositorio GitHub.
- Archivo de documentación del proyecto.

Las evidencias se organizan dentro de la carpeta:

```text
Docs/