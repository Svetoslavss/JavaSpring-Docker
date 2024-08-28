FROM maven:3.9.9-eclipse-temurin-22 as build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:22-jdk-alpine
WORKDIR /app

COPY --from=build /app/target/UserRestApi-0.0.1-SNAPSHOT.jar /app/UserRestApi.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app/UserRestApi.jar"]
