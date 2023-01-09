# Mi negocio

## Acerca de la aplicación

Aplicacion de reclutamiento realizada para unexustech.

---

## Requerimientos

- Tested on JDK 19
- Postgresql
- Maven

Antes de ejecutar el proyecto maven es necesario crear 2 base de datos `minegocio` y `minegocio-test`. Estas se pueden crear ejecutando el archivo `setup_db.cmd`.

setup_db.cmd:

```cmd
@echo off

set PGUSER=admin
set PGPASSWORD=admin

cd C:/opt/pgsql/bin

initdb.exe -D ^"C^:^\opt^\pgsql^\data^" -U admin -W -E UTF8 -A scram-sha-256
pg_ctl.exe -D ^"C^:^\opt^\pgsql^\data^" -l ^"C^:^\opt^\pgsql-data^\logs^" start
createdb.exe --encoding=UTF8 -U admin minegocio
createdb.exe --encoding=UTF8 -U admin minegocio-test
```

sin embargo este archivo define las rutas de postgresql en la ruta `C:/opt/pgsql`, se puede cambiar la ruta. Este archivo asume que la distribución de postgresql fue instalada a partir de un zip (https://www.enterprisedb.com/download-postgresql-binaries) y colocada en la ruta `C:/opt/pgsql` posteriormente crea los datos necesarios en `C:/opt/pgsql-data`.

si ya dispone de postgresql instalado via **.msi** o **.exe** puede utilizar las consultas para crear las base de datos necesarias:

```sql
CREATE DATABASE minegocio WITH ENCODING 'UTF8';
CREATE DATABASE minegocio-test WITH ENCODING 'UTF8';
```

## Generacion del schema de base de datos al inicio (Spring Boot database initialization)

La aplicación esta configurada para que al iniciar cree el esquema necesario (tablas, tipos, etc) y también inserte valores por defecto, sin embargo los datos se pierden por que ocurre varios `DROP TABLE`. Se puede modificar `aplication.properties` para que no se ejecute el schema denuevo.

```
spring.sql.init.mode=never
```

---
## POSTMAN & rutas de la API

Se incluye un archivo de exportacion de una colección de POSTMAN para que se pueda utilizar, esta en el repositorio y su nombre es `mi negocio api.postman_collection.json`.

Esta colección incluye las peticiones para las rutas de la api:

- **GET** `/api/users` : obtiene todos los usuarios
- **GET** `/api/users?names=<any>` : obtiene todos los usuarios que coincidan con un nombre
- **GET** `/api/users?identificationValue=<any>` : obtiene todos los usuarios que coincidan con un numero de identificacion
- **POST** `/api/users` : crea un nuevo usuario
- **PUT** `/api/users/{id}` edita un usuario por id
- **DELETE** `/api/users/{id}` elimina un usuario por id
- **POST** `/api/users/{id}/addresses` agrega una direccion a un usuario.

al insertar un usuario con el endpoint **POST** `api/users` para insertar la ciudad y provincia de la dirección principal, se requieren los campos `addressCityId`, `addressProvinceId`, El campo que describe la dirección es `addressDescription`.

**Ejemplo de un json para crear un nuevo usuario:**

```json
{
    "identificationType": "CI",
    "identificationValue": "1765987425",
    "names": "Paul Doe",
    "email": "pauldoe@gmail.com",
    "phone": "0998591832",
    "addressProvinceId": 17,
    "addressCityId": 178,
    "addressDescription": "Centro Historico Calle Galapagos"
}
```

El id de provincia `17` pertenece a la provincia **Pichincha** y el id de ciudad `178` pertenece a la ciudad **Quito**. Estos valores pueden verse en la base de datos.

**TO-DO:** crear un endpoint para obtener ciudades (GET /api/cities)

---

## Coleccion de POSTMAN:

![Coleccion de postman](https://i.imgur.com/lc0WLBB.png "Coleccion de postman")

---

## Compatibilidad con IDE

El proyecto debería ser compatible con la mayoría de IDEs sin embargo, se incluye un archivo .iml para facilitar el uso en **Jetbrains intellij IDEA** IDE en el cual fue desarrollado el proyecto.