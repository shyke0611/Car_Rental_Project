# Stage 1: Build the application
FROM openjdk:17-slim AS build

# Install necessary packages for building the application
RUN apt-get update && apt-get install -y \
    maven \
    wget \
    unzip \
    && rm -rf /var/lib/apt/lists/*

# Set the working directory
WORKDIR /app

# Copy the entire project to the container
COPY . .

# Build the application
RUN mvn clean package

# Download JavaFX SDK
RUN wget https://gluonhq.com/download/javafx-17.0.2-sdk-linux/ -O openjfx-17.0.2_linux-x64_bin-sdk.zip && \
    unzip openjfx-17.0.2_linux-x64_bin-sdk.zip && \
    mv javafx-sdk-17.0.2 /opt/javafx-sdk && \
    rm openjfx-17.0.2_linux-x64_bin-sdk.zip

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

# Copy the built JAR file and JavaFX SDK from the build stage
COPY --from=build /app/target/car_rental_book_and_manage-1.0.jar .
COPY --from=build /opt/javafx-sdk /opt/javafx-sdk

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Start Xvfb and run the application
ENTRYPOINT ["sh", "-c", "Xvfb :99 -screen 0 1024x768x16 & export DISPLAY=:99 && java --module-path /opt/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -jar car_rental_book_and_manage-1.0.jar"]
