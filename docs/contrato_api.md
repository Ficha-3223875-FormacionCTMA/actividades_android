# Contrato API REST local — Mi Formación CTMA

## Base URL

La aplicación ya no depende de JSONPlaceholder. Para la demostración de Semana 8 levanta una API REST simulada dentro del mismo dispositivo/emulador:

`http://127.0.0.1:8080/`

La implementación está en `data/api/LocalApiServer.kt`.

## Endpoints

### GET /v1/actividades

Devuelve las actividades formativas de demostración del proyecto.

Admite el parámetro opcional `limite`:

`GET /v1/actividades?limite=10`

### GET /v1/actividades/{id}

Devuelve una actividad específica por su identificador, por ejemplo:

`GET /v1/actividades/CTMA-001`

## Contrato JSON

```json
{
  "id": "CTMA-001",
  "titulo": "Analizar requerimientos del proyecto móvil",
  "descripcion": "Identificar necesidades del usuario y requisitos funcionales.",
  "progreso": 80,
  "prioridad": "ALTA",
  "competencia_id": "ADSO-01",
  "fecha_limite": "2026-10-05",
  "completada": false,
  "actualizado_en": "2026-09-23T18:00:00-05:00"
}
```

## Flujo de datos

`API local → Retrofit → ActividadDto → Mapper → ActividadFormativa → Room → Flow → UI`

La UI no consume Retrofit directamente. Room continúa siendo la fuente canónica para mostrar el contenido.

## Por qué es local

La API simulada permite demostrar el flujo REST sin depender de Internet, de JSONPlaceholder ni de un servidor externo. Los datos están definidos como fixtures de demostración dentro de `LocalApiServer`.
