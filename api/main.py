from fastapi import FastAPI, HTTPException, Security, UploadFile, File
from fastapi.security import APIKeyHeader
from pydantic import BaseModel
from typing import Optional
from datetime import datetime
from pathlib import Path
import uuid

app = FastAPI(
    title="Mi Formación CTMA API",
    description="API REST para gestionar actividades formativas de Mi Formación CTMA",
    version="1.1.0"
)

api_key_header = APIKeyHeader(name="Authorization", auto_error=False)

CARPETA_EVIDENCIAS = Path("evidencias")
CARPETA_EVIDENCIAS.mkdir(parents=True, exist_ok=True)


class LoginRequest(BaseModel):
    email: str
    password: str


class ActividadCreate(BaseModel):
    titulo: str
    descripcion: str
    aprendiz: str


class ActividadUpdate(BaseModel):
    titulo: str
    descripcion: str
    aprendiz: str
    estado: str


class EstadoUpdate(BaseModel):
    estado: str


usuarios = [
    {
        "id": "APR-01",
        "nombre": "Aprendiz CTMA",
        "email": "aprendiz@ctma.test",
        "password": "Demo123*",
        "rol": "APRENDIZ"
    },
    {
        "id": "INS-01",
        "nombre": "Instructor CTMA",
        "email": "instructor@ctma.test",
        "password": "Instructor123*",
        "rol": "INSTRUCTOR"
    }
]

actividades = [
    {
        "id": "ACT-001",
        "titulo": "Actividad de formación",
        "descripcion": "Actividad inicial del programa de formación",
        "aprendiz": "APR-01",
        "estado": "PENDIENTE",
        "createdAt": "2026-09-16T10:00:00"
    },
    {
        "id": "ACT-002",
        "titulo": "Actividad Semana 4",
        "descripcion": "Pruebas de API REST con Postman",
        "aprendiz": "APR-01",
        "estado": "EN_PROCESO",
        "createdAt": "2026-09-16T10:30:00"
    },
    {
        "id": "ACT-003",
        "titulo": "Persistencia local con Room",
        "descripcion": "Guardar actividades y consultar el caché local.",
        "aprendiz": "APR-01",
        "estado": "EN_PROCESO",
        "createdAt": "2026-09-17T09:00:00"
    },
    {
        "id": "ACT-004",
        "titulo": "Pruebas de resiliencia",
        "descripcion": "Validar errores HTTP, timeout y recuperación.",
        "aprendiz": "APR-01",
        "estado": "PENDIENTE",
        "createdAt": "2026-09-18T09:30:00"
    },
    {
        "id": "ACT-005",
        "titulo": "Documentación de Mi Formación CTMA",
        "descripcion": "Registrar arquitectura, pruebas y decisiones técnicas.",
        "aprendiz": "APR-01",
        "estado": "PENDIENTE",
        "createdAt": "2026-09-19T11:00:00"
    }
]

evidencias = []


@app.get("/")
def inicio():
    return {
        "mensaje": "Mi Formación CTMA API funcionando",
        "version": "1.1.0",
        "proyecto": "Mi Formación CTMA"
    }


@app.post("/api/auth/login")
def login(datos: LoginRequest):
    for usuario in usuarios:
        if usuario["email"] == datos.email and usuario["password"] == datos.password:
            return {
                "token": f"token-{usuario['id']}",
                "userId": usuario["id"],
                "nombre": usuario["nombre"],
                "rol": usuario["rol"]
            }

    raise HTTPException(status_code=401, detail="Credenciales inválidas")


def verificar_token(authorization: Optional[str]):
    if not authorization:
        raise HTTPException(status_code=401, detail="Token requerido")

    # Aceptamos tanto el formato usado originalmente por esta API
    # (Authorization: token-APR-01) como el formato HTTP habitual
    # (Authorization: Bearer token-APR-01).
    token = authorization.strip()
    if token.lower().startswith("bearer "):
        token = token[7:].strip()

    if token not in ["token-APR-01", "token-INS-01"]:
        raise HTTPException(status_code=401, detail="Token inválido")

    return token


def buscar_actividad(actividad_id: str):
    for actividad in actividades:
        if actividad["id"] == actividad_id:
            return actividad
    return None


def siguiente_id():
    numeros = []
    for actividad in actividades:
        try:
            numeros.append(int(actividad["id"].split("-")[-1]))
        except (ValueError, IndexError):
            pass
    siguiente = max(numeros, default=0) + 1
    return f"ACT-{siguiente:03d}"


@app.get("/api/actividades")
def listar_actividades(
    authorization: Optional[str] = Security(api_key_header)
):
    verificar_token(authorization)
    return {
        "total": len(actividades),
        "actividades": actividades
    }


@app.get("/api/actividades/{actividad_id}")
def obtener_actividad(
    actividad_id: str,
    authorization: Optional[str] = Security(api_key_header)
):
    verificar_token(authorization)
    actividad = buscar_actividad(actividad_id)

    if actividad is None:
        raise HTTPException(status_code=404, detail="Actividad no encontrada")

    return actividad


@app.post("/api/actividades", status_code=201)
def crear_actividad(
    datos: ActividadCreate,
    authorization: Optional[str] = Security(api_key_header)
):
    verificar_token(authorization)

    nueva_actividad = {
        "id": siguiente_id(),
        "titulo": datos.titulo.strip(),
        "descripcion": datos.descripcion.strip(),
        "aprendiz": datos.aprendiz,
        "estado": "PENDIENTE",
        "createdAt": datetime.now().isoformat()
    }

    actividades.append(nueva_actividad)
    return nueva_actividad


@app.put("/api/actividades/{actividad_id}")
def actualizar_actividad(
    actividad_id: str,
    datos: ActividadUpdate,
    authorization: Optional[str] = Security(api_key_header)
):
    verificar_token(authorization)

    actividad = buscar_actividad(actividad_id)

    if actividad is None:
        raise HTTPException(status_code=404, detail="Actividad no encontrada")

    estados_validos = ["PENDIENTE", "EN_PROCESO", "COMPLETADA", "CANCELADA"]
    if datos.estado not in estados_validos:
        raise HTTPException(status_code=422, detail="Estado no válido")

    actividad.update({
        "titulo": datos.titulo.strip(),
        "descripcion": datos.descripcion.strip(),
        "aprendiz": datos.aprendiz,
        "estado": datos.estado
    })

    return actividad


@app.delete("/api/actividades/{actividad_id}")
def eliminar_actividad(
    actividad_id: str,
    authorization: Optional[str] = Security(api_key_header)
):
    verificar_token(authorization)

    actividad = buscar_actividad(actividad_id)

    if actividad is None:
        raise HTTPException(status_code=404, detail="Actividad no encontrada")

    actividades.remove(actividad)

    # Las evidencias quedan asociadas al ID para conservar trazabilidad.
    return {
        "mensaje": "Actividad eliminada correctamente",
        "actividadId": actividad_id
    }


@app.patch("/api/actividades/{actividad_id}/estado")
def cambiar_estado(
    actividad_id: str,
    datos: EstadoUpdate,
    authorization: Optional[str] = Security(api_key_header)
):
    verificar_token(authorization)

    estados_validos = ["PENDIENTE", "EN_PROCESO", "COMPLETADA", "CANCELADA"]

    if datos.estado not in estados_validos:
        raise HTTPException(status_code=422, detail="Estado no válido")

    actividad = buscar_actividad(actividad_id)

    if actividad is None:
        raise HTTPException(status_code=404, detail="Actividad no encontrada")

    if actividad["estado"] == "CANCELADA" and datos.estado == "COMPLETADA":
        raise HTTPException(
            status_code=409,
            detail="Transición de estado no permitida"
        )

    actividad["estado"] = datos.estado
    return actividad


@app.post("/api/actividades/{actividad_id}/evidencias")
async def agregar_evidencia(
    actividad_id: str,
    archivo: UploadFile = File(...),
    authorization: Optional[str] = Security(api_key_header)
):
    verificar_token(authorization)

    if buscar_actividad(actividad_id) is None:
        raise HTTPException(status_code=404, detail="Actividad no encontrada")

    tipos_permitidos = ["image/jpeg", "image/png", "image/webp"]

    if archivo.content_type not in tipos_permitidos:
        raise HTTPException(status_code=422, detail="Tipo de archivo no permitido")

    contenido = await archivo.read()

    if len(contenido) > 5 * 1024 * 1024:
        raise HTTPException(status_code=413, detail="La imagen supera el tamaño máximo de 5 MB")

    if len(contenido) == 0:
        raise HTTPException(status_code=422, detail="El archivo está vacío")

    extensiones = {
        "image/jpeg": ".jpg",
        "image/png": ".png",
        "image/webp": ".webp"
    }

    extension = extensiones[archivo.content_type]
    nombre_unico = f"{actividad_id}_{uuid.uuid4()}{extension}"
    ruta_archivo = CARPETA_EVIDENCIAS / nombre_unico

    with open(ruta_archivo, "wb") as destino:
        destino.write(contenido)

    nueva_evidencia = {
        "id": str(uuid.uuid4()),
        "actividadId": actividad_id,
        "nombreArchivo": archivo.filename or nombre_unico,
        "nombreGuardado": nombre_unico,
        "tipoArchivo": archivo.content_type,
        "sizeBytes": len(contenido),
        "estado": "SINCRONIZADA",
        "createdAt": datetime.now().isoformat()
    }

    evidencias.append(nueva_evidencia)

    return {
        "mensaje": "Evidencia subida correctamente",
        "evidencia": nueva_evidencia
    }


@app.get("/api/actividades/{actividad_id}/evidencias")
def listar_evidencias(
    actividad_id: str,
    authorization: Optional[str] = Security(api_key_header)
):
    verificar_token(authorization)

    if buscar_actividad(actividad_id) is None:
        raise HTTPException(status_code=404, detail="Actividad no encontrada")

    resultado = [
        evidencia
        for evidencia in evidencias
        if evidencia["actividadId"] == actividad_id
    ]

    return {
        "total": len(resultado),
        "evidencias": resultado
    }


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
