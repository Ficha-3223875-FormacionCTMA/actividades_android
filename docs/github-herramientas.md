# GitHub para la trazabilidad del proyecto

La guía entregada por el instructor propone usar GitHub Issues para historias/bugs/tareas, GitHub Projects para backlog/estado/iteraciones, Pull Requests para relacionar historias con implementación, Commits para evolución y GitHub Actions para build y pruebas. fileciteturn3file0L19-L57

## Lo que debes crear en tu repositorio

### 1. Issues — Historias de usuario
Crear 8 Issues:
- `HU-01: Consultar actividades`
- `HU-02: Crear actividad válida`
- `HU-03: Editar actividad`
- `HU-04: Consultar detalle`
- `HU-05: Buscar actividades`
- `HU-06: Identificar progreso y estado`
- `HU-07: Usar fuente aumentada`
- `HU-08: Conservar el formulario`

Usa el contenido de `docs/historias-usuario-github.md`.

### 2. Labels
Crear:
- `historia`
- `bug`
- `tarea`
- `prioridad-alta`
- `prioridad-media`
- `prioridad-baja`
- `guia-2`
- `guia-3`
- `guia-4`

### 3. Project
Crear un GitHub Project tipo tablero con columnas:
`Backlog → Ready → In Progress → Review → Done`.

### 4. Pull Requests
Cada incremento debe relacionar Issue y PR. Ejemplo en la descripción:
`Closes #12`

### 5. Commits
Usar mensajes claros, por ejemplo:
- `feat: completa formulario de actividades`
- `test: agrega casos de validacion`
- `docs: agrega matriz de trazabilidad`
- `fix: corrige navegacion por id inexistente`

### 6. Actions
El repositorio puede ejecutar automáticamente build, unit tests y lint. La guía señala GitHub Actions como mecanismo para automatizar Gradle, `testDebugUnitTest` y `lintDebug`. fileciteturn3file0L44-L61

## Importante
No subas `local.properties`, tokens, contraseñas, claves ni archivos generados. La guía de la Semana 1 lo indica explícitamente. fileciteturn8file1L90-L101
