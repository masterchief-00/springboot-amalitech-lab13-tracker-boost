FROM openjdk:21-jdk-slim

WORKDIR /app

COPY .env ./

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
