FROM openjdk:17

RUN echo "Africa/Harare" > /etc/timezone

# Run the jar
ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
