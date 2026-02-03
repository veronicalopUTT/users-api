# Imagen base con Java 17
FROM eclipse-temurin:17-jdk-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el jar generado
COPY target/users-api-0.0.1-SNAPSHOT.jar app.jar

# Puerto que expone la app
EXPOSE 8082

# Comando para correr la app
ENTRYPOINT ["java", "-jar", "app.jar"]
