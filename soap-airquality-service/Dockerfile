FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/AirSOAPJakarta17-1.0.jar app.jar
# Le port exposé par votre code Java SOAP
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "app.jar"]