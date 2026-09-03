# Mi Formación CTMA

Aplicación Android desarrollada con Kotlin y Jetpack Compose para organizar actividades, compromisos y evidencias del proceso formativo.

## Alcance de las semanas 1 a 4

- Semana 1: ambiente Android, proyecto Compose, Git, problema, usuarios, historias y criterios de aceptación.
- Semana 2: Kotlin, tipos, variables, funciones, colecciones, null safety, modelo `ActividadFormativa` y reglas de negocio.
- Semana 3: UI declarativa con Material 3, tarjetas, `LazyColumn`, `LazyVerticalGrid`, estado vacío, previews, accesibilidad y diseño adaptable.
- Semana 4: estado observable, state hoisting, `rememberSaveable`, formulario, validaciones, Navigation Compose, creación, detalle y edición.

## Requisitos

- Android Studio compatible con el proyecto.
- JDK 11.
- SDK Android 36.
- Gradle se sincroniza mediante el wrapper incluido.

## Ejecución

1. Abrir esta carpeta raíz en Android Studio.
2. Esperar Gradle Sync y resolver cualquier descarga solicitada por Android Studio.
3. Seleccionar un emulador o dispositivo autorizado.
4. Ejecutar `app`.

## Flujo de la aplicación

Lista → Crear → Lista

Lista → Detalle → Editar → Lista

El detalle recibe únicamente el `id` de la actividad y resuelve la información desde la lista en memoria.

## Nota de persistencia

La guía de Semana 4 permite estado local para el laboratorio. El borrador pequeño del formulario usa `rememberSaveable`; la persistencia de negocio con Room corresponde a semanas posteriores.

## Evidencias

La carpeta `docs/` contiene las decisiones, matrices, diagramas y casos de prueba que corresponden a las guías. La carpeta `evidencias/` contiene espacios para las capturas que deben realizarse en Android Studio, emulador/dispositivo y pruebas reales.

## Guía integradora: Scrum + pruebas + GitHub

El proyecto incluye artefactos para aplicar la guía integradora sobre el caso **Mi Formación CTMA** sin reemplazar el proyecto por otro dominio. Consulta `docs/checklist-guia-integrada.md`, `docs/historias-usuario-github.md`, `docs/plan-pruebas-v1-integrador.md` y `docs/matriz-trazabilidad-integrador.md`.

Las historias deben crearse como Issues en el repositorio GitHub del aprendiz y relacionarse con PR/commits. El repositorio incluye plantillas de Issues y Pull Request y un workflow de GitHub Actions para build, pruebas unitarias y lint.
