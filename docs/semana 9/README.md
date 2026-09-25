Mi Formación CTMA — Guía 9

Capacidades del dispositivo y seguridad

La aplicación permite asociar evidencias fotográficas a las actividades formativas.

Funcionalidades implementadas

- Selección de imágenes mediante Photo Picker.
- Captura mediante cámara.
- FileProvider para URI seguras.
- Almacenamiento interno de evidencias.
- Persistencia mediante Room.
- Asociación entre actividad y evidencia.
- Validación de MIME.
- Límite máximo de 5 MB.
- Estados de sincronización.
- Sincronización con API REST.
- Conservación local ante fallos de red.
- Eliminación de evidencias.
- Configuración por ambientes.
- HTTPS en producción.
- Control de permisos.
- Protección de información sensible en logs.

Estados de evidencia

LOCAL
↓
SUBIENDO
↓
SINCRONIZADA

En caso de error:

SUBIENDO
↓
FALLIDA

Almacenamiento

Las imágenes no se almacenan como bytes dentro de Room.

Room almacena:

- identificador;
- actividad asociada;
- URI;
- MIME;
- tamaño;
- estado;
- fecha.

Privacidad

La aplicación solicita únicamente los permisos necesarios para las capacidades utilizadas.

No utiliza permisos generales de galería.

No registra tokens ni URI privadas en Logcat.

Ambientes

Dev

Utilizado para desarrollo local.

Stage

Utilizado como ambiente de pruebas.

Prod

Configurado para utilizar HTTPS.

Pruebas

Se realizaron los casos CA01 a CA09 definidos en la guía de aprendizaje.

Rama

El desarrollo se mantiene en la rama actual:

"feat/Naurimar"