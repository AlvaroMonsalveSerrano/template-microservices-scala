# TEMPLATE-MICROSERVICES

El objetivo del proyecto es definir un esquema de un proyecto de un microservicio
en Scala utilizando las librería Http4s, ZIO y Quill.

## 1. Vista arquitectónicas y de base de datos

### 1.1. Vista arquitectónica de las capas


#### 1.1.1. Capa api

![Vista estática de la capa api](./doc/template-microservices-api.jpg)


#### 1.1.2. Capa de services

![Vista estática de la capa servicios](./doc/template-microservices-services.jpg)


#### 1.1.3. Capa de persistencia

![Vista estática de la capa de persistencia](./doc/template-microservices-persistence.jpg)


#### 1.1.4. Capa del modelo

![Vista estática de la capa de modelo](./doc/template-microservices-model.jpg)


#### 1.1.5. Diagrama de componenetes

![diagrama de componenetes](./doc/template-microservices-componentes.jpg)




### 1.2 Estructura de basae ded datos 

La base de datos utilizada es una base de daatos PostgreSQL.

La estructara de tablas de la entidad Base es la siguiente:
```
CREATE TABLE IF NOT EXISTS Base (
	id_rec serial PRIMARY KEY,
	length_rec int,
	width_rec int
);

insert into base (length_rec, width_rec) values (11, 11);
insert into base (length_rec, width_rec) values (22, 22);

SELECT r.* FROM base r;

```

## 2. Herramientas de gestión del proyecto.

Se dispone de un fichero Makefile con los siguientes comandos:

+ **make test**

```
sbt test
```

+ **make assembly**

```
sbt clean compile assembly
```


+ **make build**

```
assembly
docker image build -t alvaroms/template-microservices:v1 .
```

+ **make run**

```
docker container run -d -p 8082:8080 --name ms-scala  alvaroms/template-microservices:v1
```

+ **make stop**

```
docker container stop ms-scala
```

+ **make rm**

```
stop
docker container rm ms-scala
```

+ **make logs**

```
docker container logs ms-scala
```

+ **make exec**

```
docker container exec -it ms-scala /bin/bash
```

+ **make all**

```
build run
```

## 3. Arrancar el proyecto desde línea de comando

Las variables de entorno con el contenedor son las siguientes:
+ POSTGRESQL_HOST=172.17.0.1;POSTGRESQL_PORT=5436;POSTGRESQL_DATABASE=prueba;POSTGRESQL_USER=postgres;POSTGRESQL_PWD=password

Las variables de entorno desde la línea de comando son las siguientes:
+ POSTGRESQL_HOST=localhost;POSTGRESQL_PORT=5436;POSTGRESQL_DATABASE=prueba;POSTGRESQL_USER=postgres;POSTGRESQL_PWD=password

```
export POSTGRESQL_HOST=localhost \
export POSTGRESQL_PORT=5436 \
export POSTGRESQL_DATABASE=prueba \
export POSTGRESQL_USER=postgres \
export POSTGRESQL_PWD=password \
scala ./target/scala-2.13/template-microserives.jar es.ams.api.app.App

POSTGRESQL_HOST=localhost POSTGRESQL_PORT=5436 POSTGRESQL_DATABASE=prueba POSTGRESQL_USER=postgres POSTGRESQL_PWD=password scala ./target/scala-2.13/template-microserives.jar es.ams.api.app.App
```



## 4. Pruebas

+ Rediness
```  
curl -X GET http://localhost:8080/rediness
```

+ Liveness
```   
curl -X GET http://localhost:8080/liveness
``` 

+ Inserción
```
curl -X POST -d "param1=3&param2=3" http://localhost:8080/basic
```

+ Listar
```
curl -X GET  http://localhost:8080/basic/list
```

+ Borrado
```
curl -X DELETE http://localhost:8080/basic/1
```

+ Modificación
```
curl -X PUT -d "id=4&param1=44&param2=44" http://localhost:8080/basic
```

## Referencias

[Ejemplo de Dockerfile](https://gist.github.com/gyndav/c8d65b59793566ee73ed2aa25aa10497)