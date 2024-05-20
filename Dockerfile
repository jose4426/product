# Usa una imagen base de Maven para construir la aplicación
FROM maven:3.8.4-openjdk-17 AS build

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo pom.xml y descarga las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia el resto de la aplicación y construye el proyecto
COPY src ./src
RUN mvn clean package -DskipTests

# Usa una imagen base de OpenJDK para correr la aplicación
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el JAR de la aplicación desde el contenedor de construcción
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto en el que la aplicación está corriendo
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
