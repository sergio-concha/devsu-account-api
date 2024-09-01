# Usa una imagen base de Java
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo en el contenedor
WORKDIR /opt/devsu

# Copia el archivo JAR de tu aplicación en el contenedor
COPY target/devsu-account-api-0.0.1.jar devsu-account-api-0.0.1.jar

# Expone el puerto en el que tu aplicación se ejecutará
EXPOSE 8090

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "devsu-account-api-0.0.1.jar"]