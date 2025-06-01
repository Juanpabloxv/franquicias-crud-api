# Etapa de construcción
FROM maven:3.8.5-openjdk-17-slim as maven_image
WORKDIR /app

# Copiamos el descriptor de dependencias
COPY pom.xml .

# Descargamos dependencias offline (mejora el cacheo)
RUN mvn dependency:go-offline

# Copiamos el código fuente
COPY src ./src

# Compilamos y generamos el JAR (sin tests)
RUN mvn package -DskipTests

# Mostramos lo que hay en target (opcional para debug)
RUN ls /app/target

# Etapa de ejecución
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copiamos el jar desde la imagen anterior
COPY --from=maven_image /app/target/*.jar app.jar

# Exponemos el puerto
EXPOSE 8080

# Comando para ejecutar la app
CMD ["java", "-jar", "app.jar"]
