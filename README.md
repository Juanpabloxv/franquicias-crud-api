# franquicias-crud-api

API REST construida con Spring Boot para la gestión de franquicias, sus sucursales y productos.

## Descripción

Este proyecto es una solución para el manejo jerárquico de franquicias, donde:
- Una **franquicia** contiene varias **sucursales**
- Cada **sucursal** ofrece varios **productos**
- Cada producto contiene un nombre y un **stock**

La aplicación expone múltiples endpoints HTTP para realizar operaciones CRUD sobre estas entidades, y fue construida utilizando:
- Spring Boot
- MySQL (como sistema de persistencia)
- Docker (para contenerizar la app)
- Postman (para probar los endpoints)
- Swagger (para documentar los endpoints)

Además, se incluyen funcionalidades extra como actualización de nombres y consulta del producto con mayor stock por sucursal de una franquicia específica.

---

## Requisitos

- **Solo es necesario tener instalado Docker o Docker Desktop.**
> No necesitas tener Java, Maven ni ninguna dependencia técnica instalada, ya que Docker se encarga de contenerizar y gestionar todo lo necesario.

---

## Instalación y Uso

### 1. Clonar el Repositorio

```bash
git clone https://github.com/Juanpabloxv/franquicias-crud-api.git
cd franquicias-crud-api
```

### 2. Levantar el Proyecto con Docker

Asegúrate de tener Docker corriendo y luego ejecuta:

```bash
docker-compose up -d
```

> ⚠️ Asegúrate de ejecutar este comando en la misma ruta donde está el archivo `docker-compose.yml`.

Esto levantará tanto la base de datos MySQL como la aplicación Spring Boot.

- La base de datos se mantiene con **persistencia de datos** usando volúmenes de Docker.
- Si deseas reiniciar por completo (eliminar volúmenes y datos), puedes ejecutar:

```bash
docker compose down --volumes --rmi all
```

---

### 3. Probar la API

Puedes probar los endpoints de dos formas:

- Importando la colección `productos.postman_collection.json` en Postman (está en la misma carpeta del proyecto).
- Accediendo a la documentación con Swagger en:

[http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

Swagger expone todos los endpoints y permite probarlos directamente desde la interfaz.

> La aplicación implementa un CRUD completo (GET, POST, PUT, DELETE) para cada entidad: franquicia, sucursal y producto.

---

### 🌐 Versión en línea (Swagger en la nube)

Ya puedes probar esta API desde cualquier navegador accediendo al Swagger público desplegado en la nube:

🔗 **[http://89.116.26.26:8080/swagger-ui/index.html](http://89.116.26.26:8080/swagger-ui/index.html)**

> La API se encuentra desplegada y accesible desde esta URL, lo cual permite probarla desde cualquier cliente HTTP sin necesidad de instalar nada localmente.

---

### 4. Detener la Aplicación

```bash
docker-compose down
```

Si quieres eliminar volúmenes y reiniciar todo:
```bash
docker-compose down --volumes
```

---

## Información Adicional Importante

- El contenedor utiliza los siguientes puertos:
  - Aplicación: `8080`
  - MySQL en local: `3307` (host) → `3306` (contenedor)
- Asegúrate de que esos puertos estén libres antes de iniciar.

- Toda la aplicación fue desarrollada en **inglés** (nombres de clases, métodos, atributos) para seguir buenas prácticas.

- El archivo `.env` está incluido con fines de prueba. Ahí se definen puertos y credenciales usadas por los servicios.

- Aunque puedes ejecutar la aplicación directamente con Maven o Spring Boot, **se recomienda usar los contenedores Docker para facilitar el despliegue y asegurar compatibilidad**.

---

## Estructura del Proyecto

- `controller/` → Controladores REST
- `model/` → Entidades JPA
- `dto/` → Clases DTO para serialización
- `repository/` → Interfaces JPA Repository
- `service/` → Lógica de negocio
- `resources/` → Configuraciones y `application.properties`

---

## Características Implementadas

- [x] Crear, listar, actualizar y eliminar productos
- [x] Crear y listar sucursales
- [x] Crear y listar franquicias
- [x] Endpoint para producto con mayor stock por sucursal de una franquicia
- [x] Actualización de nombre para franquicia, sucursal y producto
- [x] Persistencia con MySQL y volúmenes Docker
- [x] Contenerización con Docker y Docker Compose
- [x] Documentación completa con Swagger
- [x] Archivo .env para facilitar configuración
- [x] Colección Postman para pruebas

---

## Contribuciones

Siéntete libre de contribuir o reportar errores en el [repositorio de GitHub](https://github.com/Juanpabloxv/franquicias-crud-api).

---

## Licencia

Creado por: Juan Pablo Herrera Herrera