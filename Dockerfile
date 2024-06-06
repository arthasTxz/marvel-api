FROM openjdk:17-jdk-slim

WORKDIR /app

COPY ./target/marvel-api-0.0.1-SNAPSHOT.jar .

EXPOSE 8001

ENTRYPOINT [ "java", "-jar", "marvel-api-0.0.1-SNAPSHOT.jar" ]