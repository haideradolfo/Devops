# Dockerfile - Personnalisé pour votre projet Spring Boot 3.5.6 + Java 17
FROM eclipse-temurin:17-jre-alpine

# Labels pour identification
LABEL maintainer="haiderschenato02"
LABEL version="1.0"
LABEL description="Application Spring Boot TPCafe"

# Créer un utilisateur non-root pour sécurité (version Alpine)
RUN adduser -D -s /bin/sh springuser
USER springuser

# Répertoire de travail
WORKDIR /app

# Copier le JAR
COPY target/TPCafe_Schenato_Haider-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port Spring Boot
EXPOSE 8080

# Commande optimisée pour conteneurs
ENTRYPOINT ["java", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-jar", \
    "app.jar"]