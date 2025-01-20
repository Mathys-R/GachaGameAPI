FROM maven:3.9.6-amazoncorretto-21 AS build

# Répertoire de travail
WORKDIR /app

# Copier les fichiers nécessaires
COPY pom.xml ./
RUN mvn dependency:go-offline
COPY src ./src

# Construction
ARG MODULE_PATH
RUN mvn clean package -DskipTests -pl :${MODULE_PATH} -am

# Étape de runtime
FROM amazoncorretto:21-alpine3.19

WORKDIR /app
COPY --from=build /app/src/target/${MODULE_PATH}*.jar app.jar
EXPOSE ${SERVER_PORT}
ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "/app/app.jar"]

