# TEMPLATE-MICROSERVICES

El objetivo del proyecto es definir una plantilla de un proyecto de un microservicio
en Scala utilizando las librería Http4s, ZIO y Quill.

## 1.- Structure database 

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

## 2.- Ensamblar el proyecto

```
sbt assembly
```

## 3.- Arrancar el proyecto desde línea de comando
```
scala ./target/scala-2.13/template-microserives.jar es.ams.api.app.App
```

## 4.- Pruebas

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
