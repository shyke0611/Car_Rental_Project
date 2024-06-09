# Stage 1: Build the application
FROM openjdk:17-slim AS build

# Install necessary packages for building the application
RUN apt-get update && apt-get install -y \
    maven \
    && rm -rf /var/lib/apt/lists/*

# Set the working directory
WORKDIR /app

# Copy the entire project to the container
COPY . .

# Build the application
RUN mvn clean package

# Stage 2: Run the application
FROM openjdk:17-slim

# Install necessary packages for running JavaFX in headless mode
RUN apt-get update && apt-get install -y \
    libx11-6 \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libfreetype6 \
    xauth \
    xvfb \
    && rm -rf /var/lib/apt/lists/*

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/car_rental_book_and_manage-1.0-shaded.jar .

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Start Xvfb and run the application
ENTRYPOINT ["sh", "-c", "Xvfb :99 -screen 0 1024x768x16 & export DISPLAY=:99 && java --module-path /opt/javafx-sdk-17.0.2/lib --add-modules javafx.controls,javafx.fxml -jar car_rental_book_and_manage-1.0.jar"]
