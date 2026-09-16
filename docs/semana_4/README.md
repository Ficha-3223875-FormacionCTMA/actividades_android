Mi Formación CTMA

Descripción

Aplicación Android desarrollada con Kotlin y Jetpack Compose para organizar actividades formativas del aprendiz.

El proyecto corresponde al incremento desarrollado durante la Semana 4, continuando el trabajo realizado en la Semana 3.

Tecnologías utilizadas

- Kotlin
- Android Studio
- Jetpack Compose
- Material 3
- Navigation Compose
- Android SDK
- Git y GitHub

Funcionalidades

La aplicación permite:

- Visualizar una lista de actividades formativas.
- Consultar información resumida de cada actividad.
- Crear nuevas actividades.
- Validar el título de una actividad.
- Validar la descripción.
- Mostrar contadores de caracteres.
- Evitar el doble guardado de una actividad.
- Consultar el detalle de una actividad.
- Volver entre las diferentes pantallas mediante navegación.
- Mantener los datos escritos en el formulario durante una rotación de pantalla.
- Manejar de forma controlada una actividad que no exista.

Estructura principal

app/
└── src/
└── main/
└── java/
└── com.example.miformacionctma/
├── MainActivity.kt
├── AppNavigation.kt
│
├── domain/
│   ├── ActividadFormativa.kt
│   ├── Prioridad.kt
│   ├── FormularioActividadUiState.kt
│   └── Validaciones.kt
│
└── ui/
├── components/
│   └── TarjetaActividad.kt
│
└── screens/
├── FormularioActividadScreen.kt
└── DetalleActividadScreen.kt

Estado de la aplicación

El formulario utiliza un estado "FormularioActividadUiState" para representar la información introducida por el usuario y los posibles errores de validación.

Se utiliza "rememberSaveable" para conservar el contenido del formulario durante cambios de configuración, como la rotación de pantalla.

Validaciones

Título

El título debe:

- Ser obligatorio.
- Tener mínimo 3 caracteres.
- Tener máximo 80 caracteres.

Descripción

La descripción puede tener como máximo 240 caracteres.

Navegación

La aplicación cuenta con tres destinos principales:

Lista
│
├──> Crear
│      │
│      └──> Guardar ──> Lista
│
└──> Detalle
│
└──> Volver ──> Lista

Para abrir el detalle se utiliza el identificador de la actividad y no se envía el objeto completo.

Pruebas realizadas

Se verificaron los siguientes casos:

Caso| Resultado
Título vacío| Aprobado
Título con 2 caracteres| Aprobado
Título válido| Aprobado
Descripción superior a 240 caracteres| Aprobado
Creación de actividad| Aprobado
Doble pulsación de Guardar| Aprobado
Rotación de pantalla| Aprobado
Navegación Lista → Crear → Lista| Aprobado
Navegación Lista → Detalle| Aprobado
Actividad inexistente| Controlada

Ejecución

Para ejecutar el proyecto:

1. Abrir el proyecto en Android Studio.
2. Esperar la sincronización de Gradle.
3. Seleccionar un emulador o dispositivo Android.
4. Ejecutar la aplicación con el botón Run.

Evidencias

Las evidencias de las pruebas se encuentran en la carpeta:

Docs/

Autor

Proyecto académico de Mi Formación CTMA.