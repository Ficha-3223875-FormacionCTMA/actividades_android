import sys

from pathlib import Path

from fastapi.testclient import TestClient


# Ubicación del backend FastAPI
BACKEND = Path(r"C:\Users\herre\Downloads\EntregaSeguraAPI")

sys.path.insert(0, str(BACKEND))

from main import app


client = TestClient(app)


def headers_autenticacion():
    return {
        "Authorization": "token-APR-01"
    }


def test_listar_actividades():
    respuesta = client.get(
        "/api/actividades",
        headers=headers_autenticacion()
    )

    assert respuesta.status_code == 200

    datos = respuesta.json()

    assert "total" in datos
    assert "actividades" in datos
    assert isinstance(datos["actividades"], list)


def test_crear_actividad():
    actividad = {
        "titulo": "Prueba automatizada",
        "descripcion": "Actividad creada mediante pytest",
        "aprendiz": "APR-01"
    }

    respuesta = client.post(
        "/api/actividades",
        json=actividad,
        headers=headers_autenticacion()
    )

    assert respuesta.status_code == 201

    datos = respuesta.json()

    assert datos["titulo"] == "Prueba automatizada"
    assert datos["descripcion"] == "Actividad creada mediante pytest"
    assert datos["aprendiz"] == "APR-01"
    assert datos["estado"] == "PENDIENTE"


def test_acceso_sin_autenticacion():
    respuesta = client.get(
        "/api/actividades"
    )

    assert respuesta.status_code == 401