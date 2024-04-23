package com.cs180.restservice;

public record Greeting(long id, String content) { }

/* Notes:
* - example shows GET request to /greeting returns JSON with id and content
* - cd rest-service
* - mvn clean package
* - mvn spring-boot:run (equivalent to java -jar target/rest-service-0.0.1-SNAPSHOT.jar
* - http://localhost:8080/greeting
* - http://localhost:8080/greeting?name=Gina
* */