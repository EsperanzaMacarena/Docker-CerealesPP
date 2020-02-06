# -DockerizeASpringBootApp
 Dockerización de una aplicación de Spring Boot orientada a producción
 
 ## Primeros pasos.
 En primer lugar, vamos a crear un contenedor de Postgres ver.10 para alojar la base de datos de nuestro proyecto. Para ello debemos ejecutar el siguiente comando:
 ```
 docker pull postgres:10
 ```
 
 Cuando se haya descargado la imagen de Postgres, debemos crear el contenedor:
 ```
 docker run --name cerealesppsql -p 5432:5432 -e POSTGRES_PASSWORD=postgresql -d postgres:10
 ```
 
 Ahora arrancamos el contendor con:
 ```
 docker start cerealesppsql
 ```
 
 ## Incluir Postgres en nuestro proyecto Spring Boot.
 Ahora debemos escribir en nuestro proyecto la dependencia de Posgres y unas cuantas propiedades en nuestro proyecto. En primer lugar, debes incluir la siguiente dependencia en el pom.xml:
 ```
 <dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<scope>runtime</scope>
		</dependency>
 
 ```
 
 Ahora agrega las siguientes líneas en el properties:
 ```
 
# URL jdbc de conexión a la base de datos. La IP debe ser la de nuestro ordenador, pero en mi caso utilizaré 
# la que viene por defecto en DockerToolBox
spring.datasource.url=jdbc:postgresql://192.168.99.100:5432/postgres
spring.datasource.initialization-mode=always

# Usuario y contraseña de la base de datos
spring.datasource.username=postgres
spring.datasource.password=postgresql

# Le indicamos a JPA/Hibernate que se encargue de generar el DDL
spring.jpa.hibernate.ddl-auto=create-drop

# Habilitamos la consola de H2
# http://localhost:{server.port}/h2-console
# En nuestro caso http://localhost:9000/h2-console
spring.h2.console.enabled=true

# Habilitamos los mensajes sql en el log
spring.jpa.show-sql=true
spring.datasource.tomcat.connection-properties=useUnicode=true;characterEncoding=utf-8;


spring.profiles.active=dev

 ```
 
 Finalizamos con un Maven Clean y un Maven Install para generar el .jar
 
 
 ## Crear una imagen de nuestro .jar
 Primero creamos una carpeta en target, a la que llamaremos dependency y dentro de la carpeta ejecutamos el siguiente comando:
 ```
 jar -xf ../*.jar
 ```
 
 Ahora tendremos 3 carpetas en dependency (BOOT-INF. META-INF, rg).
 
 Llegados a este punto podemos crear un dockerfile, en la raíz del proyecto, que contendrá lo siguiente:
 ```
 FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.salesianostriana.cerealespp.CerealesppApplication"]
 ```
 
 Ahora ejecutamos el siguiente comando para realizar la imagen:
 ```
 docker build -t espe/cerealespp .
 ```
 
 ## Creación de un archivo Docker-Compose para enlazar nuestra app con la base de datos postgres.
 
 En este apartado debemos crear un archivo Docker-Compose en la raíz del proyecto:
``` 
version: '2'
services:
    app:
        build: .
        image: espe/cerealespp
        ports: 
            - "9000:9000"
        depends_on: 
            - dbpostgres
        environment: 
            - SPRING_PROFILES_ACTIVE=prod
    
    dbpostgres:
        image: postgres:10
        ports: 
            - "5432:5432"
        environment: 
            - POSTGRES_PASSWORD=postgresql
            - POSTGRES_USER=postgres
            - POSTGRES_DB=postgres```
 
 ```
 
 Como ahora tenemos un servicio con nombre, podemos quitar, en los properties de nuestra app, en la url de nuestra base de datos la IP dada  y poner el nombre del servicio dbpostgres, de manera que quedaría de la siguiente manera:
 
 ```
 spring.datasource.url=jdbc:postgresql:dbpostgres:5432/postgres
 ```
 
Finalizamos levantando los servicios con el comando:
```
docker-compose up -d
```
Si tienes algún error con este comando puede ser por dos motivos:
1. El puerto esta ocupado, lo que significa que debes parar el contenedor cerealesppsql.
2. Que haya conflicto entre redes y contenedores antiguos, en cuyo caso debes ejecutar:
   ```
   docker-compose down
   ```
   Acaba ejecutando de nuevo docker-compose up -d
   
## Arrancar nuestra app web
Inicia los contenedores creados por el docker-compose up, en mi caso:
```
docker start cerealespp_app_1
docker start cerealespp_dbpostgres_1
```

Abre tu navegador preferido y escribe localhost:9000 o bien, si tienes DockerToolBox, la IP que traiga por defecto:9000.



 
