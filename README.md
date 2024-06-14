# AWS Connect Supervisor App

Repository for UCI ICS CS 180A/B Capstone Project with AWS Sponsor

Prerequisites:
- Java 17
- Maven

## Steps to Run Backend

Login with your IAM credentials (to access Connect, S3, and Bedrock):
```bash
aws sso login --profile [AccessProfileName]
```

Navigate to the right folder and clean up any target files:
```bash
cd rest-service
mvn clean package
```
Run the Spring Boot application using Maven:
```bash
mvn spring-boot:run
```
Alternatively, you can run the packaged JAR file directly:
```bash
java -jar target/rest-service-0.0.1-SNAPSHOT.jar
```

Access the application at this URL for JSON output: http://localhost:8080/insights

## Notes
- The provided Maven commands are for running the application locally.
- Ensure that you have Maven and Java 17 installed on your system.
- Adjust the URL according to your local environment setup.
