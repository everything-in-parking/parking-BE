FROM amazoncorretto:17-alpine

COPY build/libs/parking-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]
