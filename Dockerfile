# Stage 1: Build Stage
FROM maven:3.8.7-eclipse-temurin-17 as builder

# Set the working directory inside the container
WORKDIR /app

# Copy pom.xml to download dependencies and leverage caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the project source files
COPY src ./src

# Build the project and create the jar file, skipping tests for faster build
RUN mvn clean package -DskipTests

# Stage 2: Run Stage
FROM amazoncorretto:17

# Set the working directory
WORKDIR /app

# Copy the built jar file from the build stage
COPY --from=builder /app/target/*.jar app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
