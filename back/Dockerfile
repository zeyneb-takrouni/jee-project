# Build stage: use Maven to compile and package the application
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom and source
COPY pom.xml ./
COPY src ./src

# Package the application (skip tests to speed up build)
RUN mvn -B -DskipTests clean package

# Runtime stage: use a slim JDK image to run the jar
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy the packaged jar from the build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]