FROM eclipse-temurin:17-jre-alpine

RUN apk upgrade --no-cache

RUN addgroup -S app && adduser -S -D -s /bin/false app app
USER app

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]