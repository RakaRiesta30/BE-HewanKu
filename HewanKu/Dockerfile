FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /app
# COPY openapi.yaml .
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENV PORT=7860
EXPOSE 7860
ENTRYPOINT ["java", "-jar", "-Dserver.port=7860", "app.jar"]