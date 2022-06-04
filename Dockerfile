FROM openjdk:11
EXPOSE 8080
ADD target/diploma.jar dimploma.jar
ENTRYPOINT ["java", "-jar", "/diploma.jar"]