# Semana 6 — Persistencia local y fuente única de verdad

## 1. Objetivo

Implementar persistencia local para las actividades de formación utilizando Room y un repositorio como fuente única de acceso a los datos.

La aplicación permite guardar, consultar, buscar, actualizar y eliminar actividades sin depender únicamente de la memoria temporal de la aplicación.

---

## 2. Arquitectura

La estructura implementada sigue el siguiente flujo:

Compose → Repository → Room → Flow → UI

### Componentes principales

- **Compose:** muestra las pantallas y recibe las acciones del usuario.
- **ActividadRepository:** centraliza el acceso a los datos.
- **Room:** almacena las actividades de forma persistente.
- **DAO:** contiene las operaciones de consulta, inserción y eliminación.
- **Flow:** permite observar los cambios de la base de datos.
- **SharedPreferences:** almacena la preferencia de orden de las actividades.

---

## 3. Persistencia con Room

Se creó la entidad:

`ActividadEntity`

La tabla utilizada es:

`actividades`

La entidad contiene:

- id
- titulo
- descripcion
- aprendiz
- estado
- createdAt
- resuelto

El campo `id` es la clave primaria.

También se agregaron índices para los campos:

- aprendiz
- estado

---

## 4. DAO

Se implementó `ActividadDao` con operaciones para:

- Observar todas las actividades.
- Buscar actividades por texto.
- Consultar una actividad por ID.
- Guardar una actividad.
- Guardar varias actividades.
- Actualizar una actividad.
- Eliminar una actividad.
- Eliminar todas las actividades.

Las consultas observables utilizan `Flow`.

---

## 5. Repository

Se creó:

`ActividadRepository`

El repositorio centraliza el acceso a Room y FastAPI.

Las pantallas no realizan directamente operaciones SQL ni acceden directamente a la base de datos.

El repositorio permite:

- Observar actividades locales.
- Buscar actividades.
- Guardar actividades.
- Actualizar actividades.
- Eliminar actividades.
- Sincronizar actividades desde FastAPI.
- Crear actividades mediante la API y almacenarlas posteriormente en Room.

---

## 6. Persistencia de preferencias

Se creó:

`PreferencesRepository`

Actualmente se utiliza `SharedPreferences` para conservar la preferencia de orden de las actividades.

La aplicación puede recordar si el usuario desea visualizar:

- Las actividades más recientes primero.
- Las actividades más antiguas primero.

Esta preferencia permanece después de cerrar y volver a abrir la aplicación.

---

## 7. Migración de base de datos

La base de datos pasó de:

Versión 1 → Versión 2

En la versión 2 se agregó el campo:

`resuelto`

El valor predeterminado es:

`false`

La migración se implementó mediante:

`MIGRATION_1_2`

No se utiliza una migración destructiva.

Los datos existentes se conservaron durante la actualización de la estructura de la base de datos.

---

## 8. Pruebas realizadas

### Pruebas del DAO

Se ejecutaron pruebas instrumentadas para comprobar:

- Guardar y consultar una actividad.
- Buscar una actividad por texto.
- Actualizar una actividad.
- Eliminar una actividad.

Resultado:

**4 de 4 pruebas pasadas.**

Evidencia:

`test-01-dao-4-de-4.png`

---

### Prueba relacionada con la migración

Se ejecutó:

`MigracionDatabaseTest`

Resultado:

**1 prueba pasada.**

Evidencia:

`test-02-migracion-1-pasado.png`

Además, la estructura de la base de datos fue comprobada mediante Database Inspector.

---

## 9. Database Inspector

Se utilizó Database Inspector para comprobar que:

- La base de datos `mi_formacion_ctma.db` existe.
- La tabla `actividades` contiene los registros.
- Existe la nueva columna `resuelto`.
- Los registros existentes conservan sus datos.
- El valor inicial de `resuelto` es `0`, equivalente a `false`.

Evidencias:

`test-03-database-inspector.png`

`test-04-datos-conservados.png`

---

## 10. Criterios de aceptación comprobados

### PA-01 — Persistencia

Las actividades permanecen almacenadas después de cerrar y volver a abrir la aplicación.

### PA-02 — CRUD

Se comprobó guardar, consultar, actualizar y eliminar actividades.

### PA-03 — Actualización automática

La interfaz observa los datos mediante `Flow`, por lo que los cambios de Room pueden reflejarse en la lista.

### PA-04 — Búsqueda

Se implementó búsqueda por título y descripción.

### PA-05 — Preferencia

El orden seleccionado por el usuario se almacena mediante `SharedPreferences`.

### PA-06 — Migración

Se agregó el campo `resuelto` mediante una migración de versión 1 a versión 2 y se verificó la conservación de los datos existentes.

### PA-07 — Pruebas

Las pruebas instrumentadas del DAO finalizaron correctamente con:

**4/4 pruebas pasadas.**

### PA-08 — Acciones críticas

Las operaciones principales de creación, edición y eliminación se mantienen disponibles y los datos se almacenan localmente.

---

## 11. Evidencias

| Evidencia | Descripción |
|---|---|
| test-01-dao-4-de-4.png | Pruebas instrumentadas del DAO |
| test-02-migracion-1-pasado.png | Prueba relacionada con migración |
| test-03-database-inspector.png | Columna `resuelto` en Database Inspector |
| test-04-datos-conservados.png | Datos existentes conservados |

---

## 12. Decisiones técnicas

Se utilizó Room como almacenamiento local principal de las actividades.

Se utilizó un Repository para centralizar el acceso a los datos.

Se utilizó Flow para observar los cambios de la base de datos.

Para las preferencias de orden se utilizó SharedPreferences.

La comunicación con el backend FastAPI se mantiene mediante Retrofit.

---

## 13. Limitaciones

La implementación actual utiliza SharedPreferences para las preferencias en lugar de DataStore.

La prueba automatizada de migración preparada para Room 3 comprueba el esquema actual y el campo `resuelto`; la migración real 1 → 2 fue verificada adicionalmente mediante Database Inspector.

---

## 14. Conclusión

La aplicación cuenta con persistencia local mediante Room, operaciones CRUD, búsqueda, observación mediante Flow, preferencias persistentes y una migración de base de datos de la versión 1 a la versión 2.

Las pruebas instrumentadas del DAO fueron ejecutadas correctamente y la estructura de la base de datos fue comprobada mediante Database Inspector.