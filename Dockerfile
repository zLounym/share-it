FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine

RUN apk --no-cache --update add fontconfig ttf-dejavu

EXPOSE 8000

ADD target/*-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java", "-Djava.net.preferIPv4Stack=true", "-jar", "/app.jar"]