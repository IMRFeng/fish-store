# boot-service-api
This module is mainly used for offering RESTful APIs to the front-end pages.

Used technologies, like Java 8, Spring Boot, Junit, Mockito, Maven, and hsqldb etc., in this module.


## Prerequisite
Your local machine must be installed JRE 8 or JDK 8 to run this app successfully.
Using `java -version` to check your JDK version.


## To Build
`mvn clean install` or use command `mvn clean install -Dmaven.test.skip=true` or `mvn clean install -DskipTests` to skip all unit tests.

## To Run 
`mvn spring-boot:run` or `java -jar PROJECT_ROOT_PATH/target/service-api-*.jar`
