# Mi Formación CTMA

Aplicación Android para registrar y consultar actividades formativas.

## Qué tiene el proyecto

- Crear, editar, consultar y eliminar actividades.
- Búsqueda por título.
- Persistencia de actividades con Room.
- Preferencias de búsqueda y tipo de vista con DataStore.
- ViewModel para manejar el estado de la pantalla.
- Flow y StateFlow para actualizar la información automáticamente.
- Corrutinas para las operaciones de guardar y eliminar.
- Estados de carga, contenido, vacío y error.
- Migración de base de datos de la versión 1 a la 2.

## Estructura

`ui` contiene las pantallas y la navegación.

`viewmodel` contiene el estado y las operaciones de la aplicación.

`data` contiene Room, DataStore y el repositorio.

`domain` contiene las actividades y las reglas del proyecto.

## Persistencia

Las actividades se guardan en Room. DataStore se usa solamente para preferencias pequeñas como la búsqueda y la vista seleccionada.

## Pruebas

Las pruebas principales están en `app/src/test` y las pruebas instrumentadas en `app/src/androidTest`.

Antes de la entrega final se debe ejecutar en Android Studio:

- `test`
- `connectedAndroidTest`
- `assembleDebug`

## Nota

El proyecto mantiene el paquete `com.luciana.miformacionctma` y la funcionalidad que ya tenía antes de integrar persistencia y estado reactivo.
