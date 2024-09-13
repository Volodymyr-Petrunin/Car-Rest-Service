# Car-Rest-Service

Welcome to the Car Rest Service! This project focuses on building REST API endpoints with Keycloak-based security and Dockerization.
 
Additionally, the database is populated using CSV file imports.

# Application Description

The `Car-Rest-Service` application is a service for selecting cars. For example, if you have a CSV file with ten thousand entries, you can run the application, and all the cars with their data will be imported into your database. Convenient, right?
 
Additionally, Car-Rest-Service has implemented endpoints that can be connected to any front-end. Security is also implemented using Keycloak tokens.

# Technologies

+ Java
+ Spring (Boot, Data, Security, Web)
+ PostgreSQL
+ Flyway
+ Keycloak
+ Docker
+ Lombok, MapStruct
+ OpenCSV
+ OpenAPI
+ Mapstruct
+ JUnit, Mockito

# Running the Application
+ Ensure you have Java and Maven installed.
+ Clone the repository.
+ Build and run the application using Docker:
  `docker compose up`
+ Wait for the following log message to appear: `Started CarRestServiceApplication in 18.992 seconds`  (time may vary)
**It's important to note that since we are running Keycloak in Docker, the token should be obtained from there.**
Keycloak is launched pre-configured using the realm-export.json, so there is no need to manually configure it or create users.
+ I suggest the following method: Open the car-rest-service container, navigate to the terminal, and execute the command:

```
curl -X POST http://keycloak:8080/realms/MyFirstKeyCloak/protocol/openid-connect/token \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "client_id=my-spring-boot-client" \
    -d "client_secret=**********" \
    -d "username=admin" \
    -d "password=password" \
    -d "grant_type=password"
```
This will be your token. Now, you can view the OpenAPI schema at: http://localhost:8080/swagger-ui/index.html

# That’s all you need to know. Good luck! :)
