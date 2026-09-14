# Mi Formación CTMA

## Descripción del proyecto

Mi Formación CTMA es una aplicación móvil desarrollada en Android Studio utilizando Kotlin y Jetpack Compose.

La aplicación tiene como propósito ayudar a los aprendices a organizar sus actividades, compromisos y evidencias de formación de una manera sencilla y ordenada.

## Problema identificado

Los aprendices pueden tener dificultades para organizar sus actividades, recordar sus compromisos y guardar sus evidencias de formación. Esto puede ocasionar olvidos, desorden y retrasos en la entrega de trabajos.

Por esta razón, se propone desarrollar una aplicación móvil que permita consultar actividades, registrar compromisos y organizar evidencias relacionadas con la formación del aprendiz.

## Objetivo general

Desarrollar una aplicación móvil que ayude a los aprendices CTMA a organizar sus actividades, compromisos y evidencias de formación.

## Tipos de usuarios

### 1. Aprendiz

Puede consultar sus actividades, revisar compromisos y organizar sus evidencias de formación.

### 2. Instructor

Puede orientar las actividades de los aprendices y consultar la información relacionada con sus compromisos y evidencias.

## Historias de usuario

### Historia de usuario 1: Consultar actividades

Como aprendiz, quiero consultar mis actividades de formación, para saber qué trabajos debo realizar.

**Criterios de aceptación:**

- El aprendiz puede visualizar sus actividades.
- Cada actividad muestra un título y una descripción.
- La información se presenta de forma clara y ordenada.

### Historia de usuario 2: Registrar compromisos

Como aprendiz, quiero registrar mis compromisos, para recordar las actividades que debo realizar.

**Criterios de aceptación:**

- El aprendiz puede escribir un compromiso.
- El compromiso puede mostrar una fecha o descripción.
- La información se presenta en una tarjeta o sección visible.

### Historia de usuario 3: Organizar evidencias

Como aprendiz, quiero organizar mis evidencias de formación, para tener un mejor control de mis trabajos.

**Criterios de aceptación:**

- El aprendiz puede consultar sus evidencias.
- Las evidencias se muestran de manera organizada.
- La información debe ser fácil de consultar.

## Tecnologías utilizadas

- Kotlin
- Android Studio
- Jetpack Compose
- Material Design
- Gradle
- Git y GitHub

## Funcionalidades iniciales

- Pantalla principal de bienvenida.
- Título “Mi Formación CTMA”.
- Mensaje de bienvenida al aprendiz.
- Información sobre actividades y evidencias.
- Tarjeta para mostrar el próximo compromiso.
- Diseño realizado con Jetpack Compose.

## Estructura inicial del proyecto

```text
MiFormacionCTMA/
│
├── app/
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com.example.miformacionctma/
│           │       └── MainActivity.kt
│           │
│           └── AndroidManifest.xml
│
├── build.gradle.kts
├── settings.gradle.kts
└── README.md