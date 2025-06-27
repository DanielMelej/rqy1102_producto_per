# Microservicio de Productos

Este microservicio, desarrollado con Spring Boot, gestiona el catálogo de productos, incluyendo su creación, consulta, actualización y eliminación.  
Es parte de un sistema distribuido de múltiples microservicios y funciona como proveedor de información para el microservicio de ventas.

---

## Tecnologías

- Java 17
- Spring Boot
- Spring Data JPA (Hibernate)
- PostgreSQL
- Lombok

---

## Estructura del proyecto

```
src/
 └── main/
     ├── java/com/productos/vmproductos/
     │   ├── controller/         # Controladores REST
     │   ├── model/              # Entidad Producto
     │   ├── repository/         # Repositorio JPA
     │   └── service/            # Lógica de negocio
     └── resources/
         └── application.properties
```

---

## Funcionalidades principales

- Listar todos los productos.
- Consultar un producto por ID.
- Registrar nuevos productos con validaciones.
- Actualizar atributos de productos existentes (excepto el ID).
- Eliminar productos del sistema.
- Búsqueda por nombre
- Restricciones de negocio como:
  - El stock no puede ser negativo ni cero.
  - El nombre tiene un límite de 250 caracteres.
  - El tamaño debe ser mayor a cero y la unidad es ml.

---

## Configuración local

**Archivo `application.properties`:**

```properties
spring.application.name=vmproductos
spring.datasource.url=jdbc:postgresql://35.223.30.52:5432/bb_dd
spring.datasource.username=usuario
spring.datasource.password=password
spring.datasource.hikari.maximum-pool-size=1
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
server.port=8000
app.api.base-url=/api/v1
```

> Este servicio corre en el puerto **8000** y expone sus endpoints bajo la ruta base `/api/v1/productos`.

---

## Cómo ejecutar

1. Clona el repositorio:

```bash
git clone https://github.com/DanielMelej/rqy1102_producto_per.git
cd rqy1102_producto_per
```

2. Asegúrate de tener PostgreSQL disponible y configurado.

3. Ejecuta la aplicación:

```bash
./mvnw spring-boot:run
```

> Alternativamente, puedes ejecutar desde tu IDE preferido (como IntelliJ o Eclipse).

---

## Endpoints disponibles

| Método | Endpoint                     | Descripción                       |
|--------|------------------------------|-----------------------------------|
| GET    | `/api/v1/productos`          | Listar todos los productos        |
| GET    | `/api/v1/productos/{id}`     | Obtener producto por ID           |
| GET    | `/api/v1/productos?nombre=`  | Búsqueda por nombre       |
| POST   | `/api/v1/productos`          | Crear un nuevo producto           |
| PUT    | `/api/v1/productos/{id}`     | Actualizar producto existente     |
| DELETE | `/api/v1/productos/{id}`     | Eliminar un producto              |

---

## Consideraciones

- No se permite crear productos sin nombre, stock o tamaño.
- El stock debe ser mayor a cero para aceptar un nuevo producto.
- En la actualización, **no se permite cambiar el ID**.
- Si se intenta actualizar atributos con valores inválidos, se devuelven mensajes claros y específicos.
- El tamaño es un número mayor a cero y su unidad es ml.

---

## Autor

- Fernanda Miranda
- Daniel Melej
- Francisco Monsalve
- Nicolás Romo