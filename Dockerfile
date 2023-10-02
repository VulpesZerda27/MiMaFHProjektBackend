# Use the official OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory in the Docker image
WORKDIR /app

# Copy the JAR file into the Docker image
COPY target/*.jar app.jar

# Specify the command to run when the Docker container starts
CMD ["java", "-jar", "app.jar"]
