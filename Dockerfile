FROM amazoncorretto:17.0.10-alpine-jdk

COPY target/product-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar", "/app.jar"]