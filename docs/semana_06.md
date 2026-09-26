# Semana 6 — Persistencia local

## Implementación

- Room 3 con `ActividadEntity`, `ActividadDao` y `AppDatabase`.
- Repository como frontera de acceso a datos.
- Migración de base de datos 1 → 2 para `resuelto`.
- DataStore para preferencias pequeñas: búsqueda y tipo de vista.
- Room conserva las actividades al cerrar y abrir la aplicación.
- DAO expone `Flow<List<ActividadEntity>>` para cambios reactivos.
- Pruebas instrumentadas para DAO y migración.

## Evidencia

La persistencia debe demostrarse en emulador: crear/editar una actividad, cerrar/reabrir la aplicación y comprobar que permanece.
