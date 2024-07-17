# Usa una imagen base de Java
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo JAR del proyecto al contenedor
COPY target/product-0.0.1-SNAPSHOT.jar /app/mi-app.jar

# Expone el puerto en el que se ejecuta la aplicación
EXPOSE 8088

# Define el comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/mi-app.jar"]