# Guía de Scrum aplicada al proyecto Mi Formación CTMA

## 1. Nombre del proyecto

**Mi Formación CTMA**

## 2. Descripción del proyecto

“Mi Formación CTMA” es una aplicación móvil desarrollada en Android Studio utilizando Kotlin y Jetpack Compose.

Su propósito es brindar a los aprendices del SENA un espacio donde puedan consultar información relacionada con su formación, visualizar actividades y conocer compromisos importantes de su proceso formativo.

La aplicación cuenta con una interfaz sencilla, organizada y fácil de utilizar.

## 3. Problema identificado

Los aprendices pueden tener dificultades para organizar y consultar la información relacionada con su formación, actividades y compromisos.

En ocasiones, la información se encuentra distribuida en diferentes medios, lo que puede generar olvidos o confusión.

Por esta razón, se propone desarrollar una aplicación móvil que permita mostrar información importante de la formación CTMA de manera clara, organizada y fácil de consultar.

## 4. Objetivo del proyecto

Desarrollar una aplicación móvil para Android que permita a los aprendices consultar información de su formación CTMA, visualizar actividades y compromisos, y acceder a una interfaz sencilla y organizada.

---

## 5. Product Backlog

El Product Backlog contiene las funcionalidades que se desean desarrollar en la aplicación, organizadas por prioridad.

| ID | Necesidad o funcionalidad | Prioridad |
|---|---|---|
| PB-01 | Mostrar el nombre y propósito de la aplicación | Alta |
| PB-02 | Mostrar información de la formación CTMA | Alta |
| PB-03 | Mostrar actividades formativas | Alta |
| PB-04 | Mostrar el próximo compromiso del aprendiz | Alta |
| PB-05 | Organizar la información mediante tarjetas | Media |
| PB-06 | Consultar diferentes contenidos de formación | Media |
| PB-07 | Mejorar el diseño visual de la aplicación | Media |
| PB-08 | Realizar pruebas de funcionamiento | Alta |
| PB-09 | Documentar el proyecto y sus evidencias | Alta |

---

## 6. Historias de usuario

### HU-01: Consultar información de formación

**Como** aprendiz del SENA,  
**quiero** consultar información sobre mi formación CTMA,  
**para** conocer los contenidos importantes de mi proceso formativo.

#### Criterios de aceptación

- La aplicación debe mostrar el título “Mi Formación CTMA”.
- Debe mostrar información relacionada con la formación.
- La información debe ser clara y fácil de leer.
- La pantalla debe funcionar correctamente al abrir la aplicación.

---

### HU-02: Consultar actividades formativas

**Como** aprendiz,  
**quiero** visualizar mis actividades formativas,  
**para** conocer las tareas o contenidos que debo revisar.

#### Criterios de aceptación

- La aplicación debe mostrar una sección de actividades.
- Cada actividad debe tener un nombre o descripción.
- Las actividades deben mostrarse de manera organizada.
- La información no debe aparecer cortada ni superpuesta.

---

### HU-03: Consultar el próximo compromiso

**Como** aprendiz,  
**quiero** consultar mi próximo compromiso,  
**para** recordar qué actividad debo realizar.

#### Criterios de aceptación

- La aplicación debe mostrar una tarjeta con el próximo compromiso.
- La tarjeta debe incluir información entendible.
- El botón o elemento relacionado debe responder correctamente.
- La interfaz debe conservar una presentación organizada.

---

## 7. Sprint Goal

### Objetivo del Sprint

Desarrollar y organizar la pantalla principal de la aplicación “Mi Formación CTMA”, incorporando información de la formación, actividades y una tarjeta para mostrar el próximo compromiso del aprendiz.

---

## 8. Sprint Backlog

Estas son las tareas planeadas para el Sprint:

| ID | Tarea | Estado |
|---|---|---|
| SB-01 | Crear y configurar el proyecto Android | Realizada |
| SB-02 | Configurar Kotlin y Jetpack Compose | Realizada |
| SB-03 | Crear la pantalla principal | Realizada |
| SB-04 | Agregar el título “Mi Formación CTMA” | Realizada |
| SB-05 | Agregar información de la formación | Realizada |
| SB-06 | Crear la tarjeta del próximo compromiso | Realizada |
| SB-07 | Crear las clases relacionadas con actividades | Realizada |
| SB-08 | Organizar los archivos del proyecto | Realizada |
| SB-09 | Ejecutar y probar la aplicación | En verificación |
| SB-10 | Crear evidencias y documentación | Realizada |
| SB-11 | Subir los cambios al repositorio GitHub | Pendiente de confirmar |

---

## 9. Definition of Done

Una tarea se considera terminada cuando cumple las siguientes condiciones:

- El código fue escrito correctamente.
- El proyecto compila sin errores.
- La funcionalidad cumple con lo solicitado.
- La interfaz se visualiza correctamente.
- Se realizaron pruebas en Android Studio.
- No existen errores que impidan ejecutar la aplicación.
- Los archivos están organizados en las carpetas correspondientes.
- Se agregó la evidencia cuando era necesaria.
- Los cambios fueron guardados en Git.
- Los cambios fueron enviados a GitHub cuando corresponde.

---

## 10. Roles de Scrum

| Rol | Responsabilidad |
|---|---|
| Product Owner | Define las necesidades y prioridades del proyecto. |
| Scrum Master | Organiza el trabajo y verifica la aplicación de Scrum. |
| Equipo de desarrollo | Diseña, programa, prueba y documenta la aplicación. |

Como el proyecto es individual, una misma persona puede asumir los tres roles.

### Roles aplicados al proyecto

- **Product Owner:** aprendiz encargada de definir las funcionalidades.
- **Scrum Master:** aprendiz encargada de organizar las tareas y el Sprint.
- **Equipo de desarrollo:** aprendiz encargada de programar, probar y documentar la aplicación.

---

## 11. Riesgos del proyecto

| ID | Riesgo | Probabilidad | Impacto | Acción preventiva |
|---|---|---|---|---|
| R-01 | Que el proyecto no compile | Media | Alto | Revisar errores y ejecutar Gradle Sync. |
| R-02 | Que la interfaz se vea desorganizada | Media | Medio | Probar la aplicación en el emulador o celular. |
| R-03 | Que se pierdan los cambios del proyecto | Baja | Alto | Guardar los cambios y utilizar GitHub. |
| R-04 | Que una funcionalidad no responda | Media | Alto | Realizar pruebas antes de entregar. |

---

## 12. Pruebas realizadas

Para validar el proyecto se deben realizar las siguientes pruebas:

- Abrir la aplicación.
- Verificar que aparezca el título “Mi Formación CTMA”.
- Comprobar que la información se muestre correctamente.
- Verificar que aparezca la tarjeta del próximo compromiso.
- Revisar que los textos no estén cortados.
- Probar la aplicación en el emulador o dispositivo Android.
- Revisar que no existan errores de compilación.
- Confirmar que los archivos estén organizados.

---

## 13. Evidencias

Como evidencias del trabajo se pueden incluir:

- Captura de la pantalla principal.
- Captura de la tarjeta del próximo compromiso.
- Captura de las actividades formativas.
- Captura de Android Studio ejecutando la aplicación.
- Captura de la estructura de carpetas.
- Captura del repositorio GitHub.
- Archivo de documentación dentro de la carpeta `docs`.

---

## 14. Conclusión

La metodología Scrum permite organizar el desarrollo de la aplicación “Mi Formación CTMA” mediante un Product Backlog, historias de usuario, criterios de aceptación, un Sprint Goal y un Sprint Backlog.

Esta organización ayuda a identificar las funcionalidades principales, establecer prioridades y comprobar que cada tarea cumpla con los requisitos definidos.

De esta manera, Scrum facilita la planificación, el desarrollo, las pruebas y la documentación del proyecto.