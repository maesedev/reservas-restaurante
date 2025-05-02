from datetime import datetime, timedelta, timezone
from typing import List
import os
import json
import jwt
import random
import string

from fastapi import FastAPI, Header, Request, status, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel, EmailStr

# -----------------------------------------------------------------------------
# ───────────────────────— Config ————————
# -----------------------------------------------------------------------------
SECRET_KEY = "THIS_IS_A_DEMO_SECRET_CHANGE_ME"
ALGORITHM = "HS256"
TOKEN_TTL_MINUTES = 60  # 1 hour

app = FastAPI(
    title="Reservas Restaurante – Demo estática",
    version="1.0.0",
    description="API mock que devuelve respuestas fijas y genera un JWT nuevo en cada request.",
)

origins = [
    "http://localhost:3000",
]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["GET", "POST"],
    allow_headers=["*"],
)

# -----------------------------------------------------------------------------
# ─────────────────——— Modelos ————————
# -----------------------------------------------------------------------------
class SignUpRequest(BaseModel):
    nombre: str
    apellido: str
    email: EmailStr
    contraseña: str
    telefono: str
    rol: str
    fechaRegistro: str
    estado: str

class LoginRequest(BaseModel):
    email: EmailStr
    password: str

class TokenResponse(BaseModel):
    token: str

class Reserva(BaseModel):
    id: int
    cedulaUsuario: str
    numeroMesa: int
    idRestaurante: int
    cantidadPersonas: int
    estado: str
    fechaCreacionReserva: str 
    comentariosAdicionales: str | None = None
    fechaModificacionReserva: str | None = None
    fechaHora: str

class ReservaCreateRequest(BaseModel):
    cantidadPersonas: int
    cedulaUsuario: str
    comentariosAdicionales: str
    estado: str
    fechaCreacionReserva: str
    fechaHora: str
    idRestaurante: int
    numeroMesa: int

class ReservaIdRequest(BaseModel):
    idReserva: int

class CedulaRequest(BaseModel):
    cedulaUsuario: str

class MesasDisponiblesResponse(BaseModel):
    idRestaurante: int
    mesasDisponibles: List[int]

class DetalleMesaResponse(BaseModel):   
    idMesa: int
    idRestaurante: int
    capacidad: int
    ubicacion: str

class Restaurante(BaseModel):
    idRestaurante: int
    nombre: str
    direccion: str
    telefono: str
    tipoComida: str
    calificacion: float
    estado: str

class ReservaResponse(BaseModel):
    idReserva: int

# -----------------------------------------------------------------------------
# ─────────────— Utilidades archivo reservas.json ————————
# -----------------------------------------------------------------------------
RESERVAS_FILE = "reservas.json"

def load_reservas() -> List[Reserva]:
    if os.path.exists(RESERVAS_FILE):
        with open(RESERVAS_FILE, "r") as f:
            try:
                data = json.load(f)
                return [Reserva.parse_obj(item) for item in data]
            except json.JSONDecodeError:
                return []
    else:
        return []

def save_reservas(reservas: List[Reserva]) -> None:
    with open(RESERVAS_FILE, "w") as f:
        json.dump([reserva.dict() for reserva in reservas], f, indent=2)

# -----------------------------------------------------------------------------
# ─────────────────— Utilidades JWT ————————
# -----------------------------------------------------------------------------
def make_jwt(sub: str, name: str, ip: str) -> str:
    now = datetime.now(timezone.utc)
    payload = {
        "sub": sub,
        "name": name,
        "role": "User",
        "iat": int(now.timestamp()),
        "exp": int((now + timedelta(minutes=TOKEN_TTL_MINUTES)).timestamp()),
        "ip": ip,
    }
    return jwt.encode(payload, SECRET_KEY, algorithm=ALGORITHM)

def static_token(ip) -> TokenResponse:
    return TokenResponse(token=make_jwt("1234567890", "John Doe", ip))

# -----------------------------------------------------------------------------
# ─────────────────────——— Endpoints ————————
# -----------------------------------------------------------------------------
@app.get("/", tags=["root"])
def root():
    return {"message": "API mock para reservas de restaurante"}

@app.post("/api/v1/login", response_model=TokenResponse)
def login(_: LoginRequest, request: Request):
    return static_token(request.client.host)

@app.get("/api/v1/consult/restaurantes", response_model=List[Restaurante])
def get_restaurantes():
    return [
        Restaurante(
            idRestaurante=1,
            nombre="Restaurante A",
            direccion="Calle 123",
            telefono="123456789",
            tipoComida="Italiana",
            calificacion=4.5,
            estado="abierto",
        ),
        Restaurante(
            idRestaurante=2,
            nombre="Restaurante B",
            direccion="Avenida 456",
            telefono="987654321",
            tipoComida="Mexicana",
            calificacion=4.0,
            estado="cerrado",
        ),
    ]

@app.get("/api/v1/consult/restaurante/mesas", response_model=MesasDisponiblesResponse)
def mesas_disponibles(idRestaurante: int, fechaHora: str):
    return MesasDisponiblesResponse(
        idRestaurante=idRestaurante,
        mesasDisponibles=[1, 2, 3, 4, 5],
    )

@app.get("/api/v1/consult/detail/mesa", response_model=DetalleMesaResponse)
def detalle_mesa(idMesa: int):
    return DetalleMesaResponse(
        idMesa=idMesa,
        idRestaurante=1,
        capacidad=random.randint(1, 7),
        ubicacion="Cerca de la ventana",
    )

@app.post("/api/v1/create/reserva", response_model=ReservaResponse)
def create_reserva(body: ReservaCreateRequest, authorization: str | None = Header(default=None)):
    reservas = load_reservas()
    new_id = len(reservas) + 1
    reserva = Reserva(
        id=new_id,
        cedulaUsuario=body.cedulaUsuario,
        numeroMesa=body.numeroMesa,
        idRestaurante=body.idRestaurante,
        cantidadPersonas=body.cantidadPersonas,
        estado=body.estado,
        fechaCreacionReserva=body.fechaCreacionReserva,
        comentariosAdicionales=body.comentariosAdicionales,
        fechaModificacionReserva=None,
        fechaHora=body.fechaHora,
    )
    reservas.append(reserva)
    save_reservas(reservas)
    return ReservaResponse(idReserva=new_id)

@app.post("/api/v1/update/reserva", response_model=Reserva)
def update_reserva(body: Reserva, authorization: str | None = Header(default=None)):
    reservas = load_reservas()
    for idx, reserva in enumerate(reservas):
        if reserva.id == body.id:
            reservas[idx] = body
            save_reservas(reservas)
            return body
    raise HTTPException(status_code=404, detail="Reserva no encontrada")

@app.get("/api/v1/consult/reserva", response_model=Reserva)
def get_reserva(idReserva: int):
    reservas = load_reservas()
    for reserva in reservas:
        if reserva.id == idReserva:
            return reserva
    raise HTTPException(status_code=404, detail="Reserva no encontrada")

@app.get("/api/v1/consult/usuario/reservas", response_model=List[Reserva])
def get_reservas_usuario(cedulaUsuario: str):
    reservas = load_reservas()
    return [reserva for reserva in reservas if reserva.cedulaUsuario == cedulaUsuario]

@app.post("/api/v1/reservas/usuario", response_model=List[Reserva])
def reservas_usuario(body: CedulaRequest):
    reservas = load_reservas()
    return [reserva for reserva in reservas if reserva.cedulaUsuario == body.cedulaUsuario]

@app.delete("/api/v1/delete/reserva", status_code=status.HTTP_204_NO_CONTENT)
def delete_reserva(body: ReservaIdRequest, authorization: str | None = Header(default=None)):
    reservas = load_reservas()
    for i, reserva in enumerate(reservas):
        if reserva.id == body.idReserva:
            del reservas[i]
            save_reservas(reservas)
            return
    raise HTTPException(status_code=404, detail="Reserva no encontrada")
