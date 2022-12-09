#실행하기 위한 환경만 필요하면 jre, 개발까지면 jdk
FROM openjdk:11-jre
ARG JAR_FILE=build/libs/santaletter-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]