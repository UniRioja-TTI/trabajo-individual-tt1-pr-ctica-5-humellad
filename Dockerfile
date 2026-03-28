FROM openjdk:17-jdk-alpine
COPY proyecto-tt1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]