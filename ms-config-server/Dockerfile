FROM adoptopenjdk/openjdk11:x86_64-alpine-jdk-11.0.19_7

COPY target/ms-config-sever-0.0.1-SNAPSHOT.jar service.jar

ENTRYPOINT ["java","-jar","/service.jar"]
