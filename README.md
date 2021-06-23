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