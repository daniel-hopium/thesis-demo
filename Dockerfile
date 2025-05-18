# Build stage
FROM gradle:8.5-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

# Runtime image
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]