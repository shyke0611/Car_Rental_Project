# Use a base image containing Java runtime and Maven for building the application
FROM maven:3.8.5-openjdk-17-slim as build

# Set the working directory
WORKDIR /app

# Copy the entire project to the container
COPY . .

# Build the application
RUN mvn clean package

# Use a smaller base image for running the application
FROM openjdk:17-slim

# Install necessary packages for running JavaFX in headless mode and downloading JavaFX SDK
RUN apt-get update && apt-get install -y \
    libx11-6 \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libfreetype6 \
    xauth \
    xvfb \
    wget \
    unzip \
    && rm -rf /var/lib/apt/lists/*

# Download and extract JavaFX SDK
RUN wget https://download2.gluonhq.com/openjfx/21.0.2/openjfx-21.0.2_linux-x64_bin-sdk.zip \
    && unzip openjfx-21.0.2_linux-x64_bin-sdk.zip -d /opt \
    && rm openjfx-21.0.2_linux-x64_bin-sdk.zip

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/car_rental_book_and_manage-1.0.jar .

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Start Xvfb and run the application
ENTRYPOINT ["sh", "-c", "Xvfb :99 -screen 0 1024x768x16 & export DISPLAY=:99 && java --module-path /opt/javafx-sdk-21.0.2/lib --add-modules javafx.controls,javafx.fxml -jar car_rental_book_and_manage-1.0.jar"]
