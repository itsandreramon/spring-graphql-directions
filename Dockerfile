FROM gradle:7-jdk16 as Builder
COPY . /app
WORKDIR /app
RUN gradle generateJava test bootJar

FROM openjdk:16-jdk-slim
COPY --from=Builder app/build/libs/spring-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 80
ENTRYPOINT ["java","-jar","/app.jar"]
