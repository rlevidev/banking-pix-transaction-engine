# Stage 1: Build
FROM docker.io/library/maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the pom.xml and build dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copy the rest of the application
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the built application from the build stage
COPY --from=build /app/target/*.jar /app/app.jar

# Expose the application port
EXPOSE 8080

# Run the application with profile "prod"
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
