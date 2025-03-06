# Use an official OpenJDK runtime as the base image
FROM openjdk:17

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target folder into the container
COPY target/untitled-1.0-SNAPSHOT.jar app.jar

# Run the JAR file when the container starts
CMD ["java", "-jar", "app.jar"]
