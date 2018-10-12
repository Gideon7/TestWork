FROM openjdk:8
ADD target/REST-example-0.1.jar REST-example-0.1.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/REST-example-0.1.jar"]