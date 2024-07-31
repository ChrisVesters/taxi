FROM openjdk:17-oracle
COPY target/taxi-dispatcher-0.0.1-SNAPSHOT.jar taxi-dispatcher.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/taxi-dispatcher.jar"]