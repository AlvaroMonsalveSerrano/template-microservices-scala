FROM java:8-jre

RUN mkdir /app

WORKDIR /app

EXPOSE 8080

COPY ./target/scala-2.13/template-microserives.jar /app

ENTRYPOINT ["java", "-jar", "template-microserives.jar"]