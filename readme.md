# Dependencias
JDK 16.

# Arrancar el servicio

Ejecuta `./mvnw spring-boot:run`

# Base de datos

La base de datos se inicializa siempre en memoria. El fichero `src/main/resources/import.sql` contiene el script de inicialización.

# Documentación de la API REST

Una vez lanzado el servicio acceda a `\` o `/swagger-ui.html` en el navegador. Por ejemplo `http://localhost:8080/swagger-ui.html`.

# Testing
Existen tantos test unitarios como de integración. Ámbos son ejecutables mediante `./mvnw test`.
Los test de integración están basados en Cucumber. 

La carpeta `src/test/resources/features` contiene los ficheros Gherkin.
Tras ejecutar los tests, el informe de covertura se encuentra en la carpeta `target\site\jacoco\index.html` 

# Crear el ejecutable del proyecto

Ejecuta `./mvnw clean package` para generar el binario `.jar`.

# Detalles de arquitectura
Aunque la aplicación es pequeña en tamaño, exiten 3 clases principales que establecen una separación de responsabilidades.
1. `PriceRestController`: maneja la API REST para comunicación con clientes. Devuelve DTOs a los clientes externos.
1. `PriceServiceImpl`: contiene la lógica de negocio para manejar precios. No trabaja a nivel de DTOs.
1. `PriceRepository`: basado en Spring Data para acceder a la base de datos mediante JPA

# Validación de los argumentos de entrada
Las clases del paquete `es.commerce.prices.core.validator` contienen la lógica para comprobar si existe el `Brand` y el `Product` dados en la consulta.