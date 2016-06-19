# boot-service-api
This module is mainly used for offering RESTful APIs to the front-end pages.

Used technologies, like Java 8, Spring Boot, Junit, Mockito, Maven, and hsqldb etc., in this module.


## Prerequisite
Your local machine must be installed JRE/JDK 8 and Maven 3.* to run this app successfully.

Using `java -version` and `mvn -v` to check your JDK and Maven versions respectively.


## To Build
`mvn clean install` or use command `mvn clean install -Dmaven.test.skip=true` or `mvn clean install -DskipTests` to skip all unit tests.

## To Run 
`mvn spring-boot:run` or `java -jar PROJECT_ROOT_PATH/target/service-api-*.jar`
