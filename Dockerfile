# Use a base image containing Java runtime
FROM openjdk:17-slim

# Install necessary packages for running JavaFX in headless mode and Maven
RUN apt-get update && apt-get install -y \
    libx11-6 \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libfreetype6 \
    xauth \
    xvfb \
    maven \
    && rm -rf /var/lib/apt/lists/*

# Set the working directory
WORKDIR /app

# Copy the entire project to the container
COPY . .

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Ensure Xvfb is properly set up before running the JavaFX application
ENTRYPOINT ["sh", "-c", "rm -f /tmp/.X99-lock && Xvfb :99 -screen 0 1024x768x16 & export DISPLAY=:100 && mvn clean install && mvn javafx:run -Djava.awt.headless=true"]
