# Utiliser une image Java officielle
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier JAR de l'application dans le conteneur
COPY target/GachaGameApiApplication-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port sur lequel Spring Boot écoute (par défaut 8080)
EXPOSE 8080

# Commande pour lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
