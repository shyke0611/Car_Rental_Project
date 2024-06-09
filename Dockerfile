# Use a base image containing Java runtime
FROM openjdk:17-slim

# Install necessary packages for Maven
RUN apt-get update && apt-get install -y \
    maven \
    && rm -rf /var/lib/apt/lists/*

# Set the working directory
WORKDIR /app

# Copy the entire project to the container
COPY . .

# Build the application
RUN mvn clean package

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "target/car_rental_book_and_manage-1.0.jar"]
