# Step 1: Use a Java Runtime Environment
FROM eclipse-temurin:17-jdk-alpine

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the JAR file from your target folder to the container
# Note: You must run 'mvn clean package' before building the docker image
COPY target/portal-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Open port 8080
EXPOSE 8080

# Step 5: Command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]