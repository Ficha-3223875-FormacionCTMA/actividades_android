# Mi Formación CTMA

Aplicación Android desarrollada con Kotlin y Jetpack Compose para registrar, consultar y sincronizar actividades formativas.

## Semanas implementadas

- **Semana 1:** base del proyecto Android y Compose.
- **Semana 2:** dominio de actividades, estados, prioridad, búsqueda, resumen y ordenamiento.
- **Semana 3:** Material 3, componentes reutilizables, listas con claves estables, cuadrícula adaptable, accesibilidad y Previews.
- **Semana 4:** UDF/State Hoisting, `rememberSaveable`, validaciones de formulario, fecha válida/no vencida, navegación y protección contra doble guardado.
- **Semana 6:** Room, migración y DataStore.
- **Semana 7:** corrutinas, Flow, StateFlow, `viewModelScope` y cancelación cooperativa.
- **Semana 8:** Retrofit, Kotlin Serialization, DTO, RemoteDataSource, clasificación de errores, caché offline-first, autenticación conceptual Bearer y MockWebServer.

## Arquitectura

```text
Compose UI
   ↓ eventos / estado
ViewModel
   ↓
Repository
   ├── Room (fuente canónica para UI)
   └── RemoteDataSource → Retrofit/OkHttp
                              ↓
                           DTO/JSON
```

La UI no llama Retrofit directamente. Las respuestas remotas válidas se transforman a dominio y se persisten en Room. Los errores de red no eliminan el caché válido.

## API REST local simulada

La app ya no depende de JSONPlaceholder. Para la demostración de Semana 8 levanta una API REST simulada dentro del mismo dispositivo/emulador en `http://127.0.0.1:8080/`. La implementación está en `app/src/main/java/com/luciana/miformacionctma/data/api/LocalApiServer.kt`.

Endpoints principales:

- `GET /v1/actividades?limite=10`
- `GET /v1/actividades/{id}`

El contrato usa `id`, `titulo`, `descripcion`, `progreso`, `prioridad`, `competencia_id`, `fecha_limite`, `completada` y `actualizado_en`.

El flujo es: API local → Retrofit → `ActividadDto` → mapper → dominio → Room → Flow → UI. De esta forma, al pulsar **Sincronizar**, la aplicación consulta datos propios de Mi Formación CTMA sin depender de Internet ni de un servicio externo.

En pruebas, Retrofit también puede apuntar a MockWebServer para simular 200, 401, 500, JSON inválido, timeout y otros escenarios de forma determinista.

## Resiliencia

- Connect timeout: 10 s.
- Read/write timeout: 20 s.
- Call timeout: 30 s.
- Sin retry automático de negocio.
- Errores tipados: `NoConnection`, `Timeout`, `Unauthorized`, `NotFound`, `Server`, `InvalidPayload`, `Unknown`.
- `CancellationException` no se muestra como error de negocio.
- El refresco remoto anterior se cancela cuando comienza uno nuevo.

## Caché

Room es la fuente canónica de contenido de la interfaz. Una actualización remota válida se persiste de forma agrupada; si la red falla, los datos existentes permanecen disponibles. Una respuesta vacía se considera válida pero no elimina el caché existente; la API local se usa para demostrar refresco, no como operación destructiva del contenido local.

## Seguridad

La API local usa HTTP únicamente porque el servidor está dentro del mismo dispositivo. No debe usarse esta configuración de texto plano como arquitectura de producción. El proyecto no contiene tokens reales ni credenciales. La autorización se abstrae con `TokenProvider` y `BearerTokenInterceptor`. El proveedor incluido no entrega ningún token por defecto.

## Pruebas

Las pruebas unitarias están en `app/src/test` y las instrumentadas en `app/src/androidTest`.

Semana 8 agrega:

- fixtures JSON válidas, vacías e inválidas;
- MockWebServer;
- 200;
- 200 vacío;
- 401;
- 500;
- JSON inválido;
- timeout;
- encabezado Bearer;
- pruebas de mapeo y persistencia del Repository;
- cancelación cooperativa.

## Documentación

- `docs/semana_03.md`
- `docs/semana_04.md`
- `docs/semana_06.md`
- `docs/semana_07.md`
- `docs/semana_08.md`
- `docs/semana_08_casos_aceptacion.md`
- `docs/contrato_api.md`
- `docs/entrega_checklist.md`

## Verificación final

Ejecutar desde Android Studio/terminal del equipo de desarrollo:

```bash
./gradlew test
./gradlew assembleDebug
./gradlew connectedAndroidTest
```

La ejecución final en un equipo con Android SDK/emulador y acceso a los repositorios Maven es necesaria para generar las capturas de evidencia de la entrega.


## Semana 8 — API REST y caché

Esta versión integra **Mi Formación CTMA** con una API REST propia desarrollada con FastAPI.

### Arquitectura

```text
FastAPI → Retrofit → DTO → RemoteDataSource → Repository → Room → Flow → ViewModel → Compose
```

### Funcionalidades REST

- Login con token de prueba.
- Consultar actividades.
- Consultar una actividad.
- Crear actividad.
- Editar actividad.
- Eliminar actividad.
- Cambiar estado.
- Subir evidencias.
- Consultar evidencias.

### Caché y resiliencia

Room conserva la última información válida. Si una sincronización falla por conexión, timeout, 401, 404, 5xx o JSON inválido, la información que ya estaba en Room no se borra.

### API incluida

La carpeta `api/` contiene el servidor FastAPI utilizado por la aplicación.

Para ejecutarlo:

```bash
cd api
python -m pip install -r requirements.txt
python -m uvicorn main:app --reload --host 0.0.0.0 --port 8000
```

En Android Emulator, Retrofit utiliza `http://10.0.2.2:8000/`.



## Semana 09

La implementación de capacidades del dispositivo y seguridad está documentada en `docs/semana_09.md`. Para probar la API local usa la variante `devDebug`. El flujo de sincronización usa la API y mantiene Room como caché local.
