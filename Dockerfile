FROM adoptopenjdk/openjdk11:alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar", "-web -webAllowOthers -tcp -tcpAllowOthers -browser"]