FROM java:8-jre-alpine

LABEL es.ams.version=v1.0

ENV POSTGRESQL_HOST=172.17.0.1
ENV POSTGRESQL_PORT=5436
ENV POSTGRESQL_DATABASE=prueba
ENV POSTGRESQL_USER=postgres
ENV POSTGRESQL_PWD=password

RUN mkdir /app

WORKDIR /app

EXPOSE 8080

COPY ./target/scala-2.13/template-microserives.jar /app

ENTRYPOINT ["java", "-jar", "template-microserives.jar"]