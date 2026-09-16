Mapa de navegación

La aplicación tiene tres destinos principales.

                 ┌──────────────┐
                 │    LISTA     │
                 └──────┬───────┘
                        │
              ┌─────────┴─────────┐
              │                   │
              ▼                   ▼
       ┌──────────────┐    ┌──────────────┐
       │    CREAR     │    │   DETALLE    │
       └──────┬───────┘    └──────┬───────┘
              │                   │
           Guardar              Volver
              │                   │
              └─────────┬─────────┘
                        ▼
                 ┌──────────────┐
                 │    LISTA     │
                 └──────────────┘

Destinos

Lista

Ruta:

lista

Permite:

- Visualizar las actividades.
- Crear una nueva actividad.
- Seleccionar una actividad para consultar su detalle.

Crear

Ruta:

crear

Permite:

- Introducir título.
- Introducir descripción.
- Validar los datos.
- Guardar una actividad.
- Cancelar la operación.

Al guardar correctamente se utiliza:

popBackStack()

para regresar a la lista.

Detalle

Ruta:

detalle/{actividadId}

Recibe el identificador de la actividad.

La aplicación busca la actividad mediante su "id".

Si existe, muestra sus datos.

Si no existe, muestra:

La actividad no existe.

Flujo principal

Lista → Crear → Guardar → Lista

Lista → Crear → Cancelar → Lista

Lista → Detalle → Volver → Lista