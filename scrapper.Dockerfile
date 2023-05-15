FROM openjdk:17-alpine

COPY scrapper/target/scrapper-1.0-SNAPSHOT.jar /scrapper.jar

CMD ["java", "-jar", "/scrapper.jar"]