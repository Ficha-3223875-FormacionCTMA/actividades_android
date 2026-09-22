# Semana 8 — Servicios web, caché y resiliencia

## Proyecto

**Mi Formación CTMA**

## Objetivo

Integrar la aplicación Android con una API REST desarrollada con FastAPI,
utilizando Retrofit, DTO, autenticación mediante token, manejo de errores
y almacenamiento local con Room.

## Arquitectura implementada

El flujo de datos utilizado es:

**API REST → Retrofit → DTO → Repository → Room → Flow → ViewModel → UI**

### Componentes principales

- **Retrofit:** comunicación con la API REST.
- **DTO:** representación de los datos enviados y recibidos por la API.
- **Repository:** coordina la información remota y local.
- **Room:** almacenamiento local y caché.
- **Flow:** actualización reactiva de la interfaz.
- **ViewModel:** manejo del estado de la aplicación.
- **AuthManager:** administración del token de autenticación.
- **Interceptor:** agrega el token a las peticiones HTTP.
- **DataError:** clasificación de errores.
- **ErrorClassifier:** manejo de errores HTTP y errores de conexión.

## API utilizada

Servidor:

```text
http://192.168.1.6:8000/

Endpoints utilizados

Obtener actividades

GET /api/actividades

Crear actividad

POST /api/actividades

Autenticación

La API utiliza el encabezado:

Authorization: token-APR-01

El token es administrado desde AuthManager y enviado mediante BearerTokenInterceptor.

En este proyecto el token se envía directamente en el encabezado Authorization.

Caché local

Las actividades obtenidas correctamente desde la API se almacenan en la base de datos local mediante Room.

Cuando el servidor no está disponible, la aplicación puede mostrar las actividades almacenadas previamente en la caché local.

Esto permite conservar la información aunque temporalmente no exista conexión con el servidor.

Manejo de errores

Se implementaron diferentes tipos de error:

Sin conexión.

Tiempo de espera agotado (Timeout).

Error 401 — No autorizado.

Error 404 — Recurso no encontrado.

Error 500 — Error del servidor.

JSON inválido.

Error desconocido.


Los errores son procesados mediante ErrorClassifier y se muestran a través de los estados de la interfaz.

Pruebas realizadas

Evidencia	Prueba	Resultado

EV-01	GET de actividades	Correcto
EV-02	POST de creación de actividad	Correcto
EV-03	Caché con servidor apagado	Correcto
EV-04	Timeout / error de conexión	Correcto
EV-05	Token inválido / HTTP 401	Correcto
EV-06	Error interno HTTP 500	Correcto
EV-07	Cancelación de creación	Correcto


Evidencias

Las capturas de las pruebas se encuentran en:

docs/semana 8/evidencias/

Archivos:

EV-01-get-actividades.png
EV-02-post-crear-actividad.png
EV-03-cache-offline.png
EV-04-error-timeout.png
EV-05-error-401.png
EV-06-error-500.png
EV-07-cancelar-creacion.png

Prueba GET

Se realizó una petición GET para obtener las actividades registradas en el servidor.

La aplicación recibió correctamente la información y la mostró en la lista de actividades.

Resultado: Correcto.

Prueba POST

Se realizó la creación de una nueva actividad desde la aplicación Android.

La actividad fue enviada mediante Retrofit a la API FastAPI y posteriormente se almacenó localmente en Room.

Resultado: Correcto.

Prueba de caché offline

Se apagó temporalmente el servidor FastAPI.

Después se abrió nuevamente la aplicación y se verificó que las actividades almacenadas anteriormente continuaran disponibles.

Resultado: Correcto.

Prueba de Timeout

Con el servidor apagado se intentó realizar una nueva sincronización.

La aplicación detectó que el servidor no respondía y mostró el mensaje correspondiente al tiempo de espera agotado.

Resultado: Correcto.

Prueba de autenticación 401

Se utilizó temporalmente un token inválido:

token-INVALIDO

La API rechazó la petición y respondió con:

HTTP 401 Unauthorized

Después de realizar la prueba se restauró el token válido:

token-APR-01

Resultado: Correcto.

Prueba de error 500

Se simuló temporalmente un error interno en el endpoint de actividades del servidor FastAPI.

La API respondió:

HTTP 500 Internal Server Error

Después de realizar la prueba se restauró el endpoint original.

Resultado: Correcto.

Prueba de cancelación

Se inició la creación de una actividad introduciendo título y descripción.

Posteriormente se seleccionó Cancelar en lugar de Guardar.

Se verificó que la actividad no apareciera en la lista.

Resultado: Correcto.

Recuperación del servicio

Después de realizar las pruebas con el servidor apagado, se volvió a iniciar FastAPI y se realizó nuevamente la sincronización.

La aplicación volvió a obtener correctamente las actividades desde la API.

Resultado: Correcto.

Resiliencia

La aplicación implementa mecanismos de resiliencia mediante:

Caché local con Room.

Manejo de errores de red.

Manejo de timeout.

Manejo de errores HTTP.

Conservación de información local cuando la API no está disponible.

Sincronización nuevamente cuando el servidor vuelve a estar disponible.


Tecnologías utilizadas

Kotlin

Jetpack Compose

Android Studio

Retrofit

Kotlin Serialization

OkHttp

Room

Coroutines

Flow

ViewModel

FastAPI

Python

REST API


Conclusión

La aplicación Mi Formación CTMA cuenta con integración con una API REST mediante Retrofit y FastAPI.

También cuenta con autenticación mediante token, almacenamiento local mediante Room, actualización reactiva mediante Flow y manejo de errores de conexión y errores HTTP.

Las pruebas realizadas permitieron comprobar el funcionamiento de la aplicación tanto cuando el servidor está disponible como cuando existen problemas de conexión, errores de autenticación o errores internos del servidor.

**Haz solo esto ahora:** copia todo → pégalo en `docs/semana 8/README.md` → guarda.