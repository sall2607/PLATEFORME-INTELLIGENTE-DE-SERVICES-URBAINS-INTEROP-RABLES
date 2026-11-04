# On part d'une image Linux légère avec Java 17
FROM eclipse-temurin:17-jdk-alpine

# On se place dans le dossier /app du conteneur
WORKDIR /app

# On copie le JAR généré (le fichier principal, pas le .original)
COPY target/graphql-route-service-1.0.0.jar app.jar
EXPOSE 8083
# Commande de lancement
ENTRYPOINT ["java", "-jar", "app.jar"]