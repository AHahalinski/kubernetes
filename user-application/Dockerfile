FROM openjdk:17

ARG JAR_FILE=target/user-application-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} /user-application.jar

ENTRYPOINT ["java", "-jar", "/user-application.jar"]