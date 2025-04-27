# reservas-restaurante
Solucion informatica para crear Reservas usando un frontend y BFF en modo de Rest API con Springboot.

Con capacidad de creacion de usuarios que puedan crear sus propias reservas y validar disponiblidad desde la comodidad de su casa


# API Endpoints

## Registro de usuarios
```POST /api/v1/sign-up```

```JSON
{
    "nombre": "Juan",
    "apellido": "Pérez",
    "email": "juan.perez@example.com",
    "contraseña": "idb+G1ilxlvhVqaMDGkStJRCClf3m", #hashed por un secreto antes de enviarse al backend
    "telefono": "+57 321 123 4567",
    "rol": "usuario_base",
    "fechaRegistro": "2023-10-01",
    "estado": "activo"
}
```

## Login de un usuario
```POST /api/v1/login```

```JSON
{
    "email": "juan.perez@example.com",
    "contraseña": "idb+G1ilxlvhVqaMDGkStJRCClf3m", #hashed por un secreto antes de enviarse al backend
}
```
Esto devolverá un JWT que se validará cada vez que se haga un request que requiera permisos con el schema Bearer.


## Usuario creando reserva
```POST /api/v1/create/reserva```

```JSON
{
    "cedulaUsuario": "123456789",
    "numeroMesa": 5,
    "idRestaurante": 2,
    "cantidadPersonas": 4,
    "estado": "confirmada",
    "comentariosAdicionales": "Preferencia por mesa cerca de la ventana",
    "fechaCreacionReserva": "2023-10-01T14:00:00Z",
    "fechaHora": "2023-10-05T19:30:00Z"
}
```



## Modificacion de una reserva
```POST /api/v1/update/reserva```

```JSON
{
    "idReserva" : 4121,
    "cedulaUsuario": "123456789",
    "numeroMesa": 7,
    "idRestaurante": 2,
    "cantidadPersonas": 5,
    "estado": "confirmada",
    "comentariosAdicionales": "Preferencia por mesa cerca de la ventana con silla para bebé",
    "fechaModificacionReserva": "2023-10-01T14:35:00Z",
    "fechaHora": "2023-10-05T19:30:00Z"
}
```


## Obtener todas las reservas de un usuario
```POST /api/v1/reservas/usuario```

```JSON
{
    "cedulaUsuario": "123456789",
}
```
La cedula del usuario ademas debe corresponder a la que se encuentra en el JWT



## Eliminacion de reserva
```Delete /api/v1/delete/reserva```

```JSON
{
    "idReserva" : 4121
}
```

# Seguridad

## Uso del JWT (Json Web Token)
Para manejar la seguridad de parte del servidor y no hacer modificaciones indebidas, se hace uso de un JWT, este consigo trae informacion relevante como fecha de creación, fecha de expiracion y como rol del usuario, cedula del usuario, entre otros datos para asi tener una mejor autorización a sus acciones al momento de hacer peticiones.

Payload del JWT
```JSON
{
  "sub": "1234567890", # Cedula del usuario
  "name": "John Doe",
  "role": "User",  # ["Admin", "User", "Concierge"]
  "iat": 1714156800, # Fecha de creacion del token
  "exp": 1714160400 # Fecha de expiracion del toker
}
```

De esta manera el frontend podrá usar esta informacion del usuario para rellenar en el frontend, mientras que aunque esta informacion fuera o no correcta, si el JWT no es valido, no podrá realizar acciones en el backend. 

## Acciones de Admin
* Puede ver todas las reservas de todos los usuarios y modificarlas.
* Puede crear nuevas reservas como cambiar disponibilidad de mesas, con la limitacion que no puede cambiar la disponibilidad de mesas con reservas ya creadas, para esto tendría que modificarla o eliminarla en su defecto.

## Acciones de un Concierge
* Puede crear nuevas reservas para otros usuarios.
* Puede modificar reservas no eliminarlas.

## Acciones de un User
* Puede crear nuevas reservas para si mismo.
* Puede modificar o eliminar sus propias reservas.