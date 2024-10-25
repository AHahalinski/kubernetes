FROM openjdk:17

ARG JAR_FILE=target/post-application-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} /post-application.jar

ENTRYPOINT ["java", "-jar", "/post-application.jar"]