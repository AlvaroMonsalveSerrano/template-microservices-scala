test:
	sbt test

assembly:
	sbt clean compile assembly

build: assembly
	docker image build -t alvaroms/template-microservices:v1 .

run:
	docker container run -d -p 8082:8080 --name ms-scala  alvaroms/template-microservices:v1

stop:
	docker container stop ms-scala

rm: stop
	docker container rm ms-scala

logs:
	docker container logs ms-scala

exec:
	docker container exec -it ms-scala /bin/bash

all: build run