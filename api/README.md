# API FastAPI — Mi Formación CTMA

## Ejecutar

```bash
cd api
python -m pip install -r requirements.txt
python -m uvicorn main:app --reload --host 0.0.0.0 --port 8000
```

Swagger: `http://127.0.0.1:8000/docs`

Para el emulador Android, la aplicación usa `http://10.0.2.2:8000/`.
En un teléfono físico cambia `BASE_URL` por la IP local del computador.

## Credenciales de prueba

- Aprendiz: `aprendiz@ctma.test` / `Demo123*`
- Instructor: `instructor@ctma.test` / `Instructor123*`

El encabezado usado por esta API es:

`Authorization: token-APR-01`

## Endpoints

- `POST /api/auth/login`
- `GET /api/actividades`
- `GET /api/actividades/{id}`
- `POST /api/actividades`
- `PUT /api/actividades/{id}`
- `DELETE /api/actividades/{id}`
- `PATCH /api/actividades/{id}/estado`
- `POST /api/actividades/{id}/evidencias`
- `GET /api/actividades/{id}/evidencias`
