# Etapa 1: construir el JAR con Maven
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests


# Etapa 2: ejecutar con JRE liviano
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/target/producto-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

USER nobody

ENTRYPOINT ["java", "-jar", "app.jar"]