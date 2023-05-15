FROM openjdk:17-alpine

COPY bot/target/bot-1.0-SNAPSHOT.jar /bot.jar

CMD ["java", "-jar", "/bot.jar"]