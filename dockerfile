FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests
RUN ls -l /app/target
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/WealthWise-0.0.1-SNAPSHOT.jar /app/WealthWise
EXPOSE 8080
RUN ls -l /app
CMD ["java", "-jar", "WealthWise"]