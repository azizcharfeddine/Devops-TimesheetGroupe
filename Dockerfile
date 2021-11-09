FROM dquintela/openjdk-8-jdk-alpine
add target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
