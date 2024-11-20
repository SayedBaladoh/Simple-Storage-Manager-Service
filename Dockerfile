# Stage 1: Build stage
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copy Maven wrapper files (if used) and pom.xml
COPY mvnw* pom.xml ./
COPY .mvn .mvn

# Download dependencies
RUN ./mvnw dependency:go-offline

COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests


# Stage 2: Runtime stage
FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app

COPY --from=build /app/target/storage-manager-*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]

HEALTHCHECK --start-period=10s CMD curl -f http://localhost:8081/actuator/health || exit 1