# Integración de las semanas 6 y 7

Se integró la persistencia local y el manejo de estado reactivo siguiendo las guías trabajadas.

## Semana 6

- Room como fuente de verdad para las actividades.
- DAO con Flow para observar cambios.
- Repository como punto de acceso a los datos.
- DataStore para preferencias pequeñas.
- Base de datos con versión 2.
- Migración 1 a 2 agregando `resuelto` con valor `false`.

## Semana 7

- ViewModel y `viewModelScope`.
- `StateFlow` para el estado actual.
- `flatMapLatest` para la búsqueda.
- `WhileSubscribed(5_000)` para el estado compartido.
- Manejo de `CancellationException`.
- Estados de carga, contenido, vacío y error.
- Estado de operación para guardar y eliminar.
- `collectAsStateWithLifecycle()` en Compose.
- Prueba de flujo sin `Thread.sleep`.

## Casos para comprobar manualmente

1. Abrir la aplicación sin datos y verificar la carga inicial.
2. Crear una actividad y comprobar que aparece sin actualizar manualmente.
3. Cerrar y abrir la aplicación y comprobar que la actividad continúa guardada.
4. Buscar una actividad y cerrar/abrir para comprobar que la búsqueda queda guardada.
5. Cambiar entre lista y cuadrícula y comprobar que la selección queda guardada.
6. Editar una actividad y comprobar el cambio en el detalle y la lista.
7. Eliminar una actividad y comprobar que desaparece.
8. Buscar rápidamente diferentes textos y comprobar que queda el último resultado.
