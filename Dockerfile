FROM adoptopenjdk/openjdk11:alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app.jar", "-web -webAllowOthers -tcp -tcpAllowOthers -browser"]