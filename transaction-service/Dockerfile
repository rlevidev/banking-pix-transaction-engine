# Runtime stage
FROM docker.io/library/eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copy the already built jar from the target directory
COPY target/*.jar app.jar
# Expose the application port
EXPOSE 8083
# Run the application with profile "prod"
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
