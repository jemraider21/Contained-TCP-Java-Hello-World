FROM eclipse-temurin:17-jre-alpine

WORKDIR /usr/src/myapp

COPY target/*-jar-with-dependencies.jar /usr/src/myapp/app.jar

CMD ["java", "-cp", "app.jar", "org.ozan.Main"]