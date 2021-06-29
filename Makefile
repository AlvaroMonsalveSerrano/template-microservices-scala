test:
	sbt test

assembly:
	sbt clean compile assembly

build: assembly
	docker image build -t alvaroms/template-microservices:v1 .

run:
	docker container run -d --name ms-scala -p 8082:7070 alvaroms/template-microservices:v1

stop:
	docker container stop ms-scala

rm: stop
	docker container rm ms-scala

logs:
	docker container logs ms-scala

exec:
	docker container exec -it ms-scala /bin/bash

all: build run