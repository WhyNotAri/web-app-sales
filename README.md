## Web App sales
This project is meant to be a fully functional Web App for personal sales. It is built with backend on Spring Boot, using React as the frontend framework all connecting to a database on PostgreSQL. Trying to use the best development practices

## Requirements
- Java 17
- Maven
- Supabase

## Config
As it is connected to supabase you need to connect to be part of the project in order to connect to the database. The 'application.yml' is hidden and replaced for an 'application-example.yml' to show the configuration used in the project such as the database, JPA, etc. but without sensitive information like usernames and passwords

## Local Setup
1. Clone the repo
2. Create the 'application.yml' file and copy the configuration on the example with the appropiate values
3. Run the project

## API Endpoints
To access the endpoints, on the web browser go to the base URL:
http://localhost:8080

For developing purposes the security credentials are:
- username: user
- password: *(the one it generates and shows on the console when you run the program)*

## Code Flow
Model -> Repo -> DTOs -> Service -> Controller

## Project Structure
- src/main/java/com/ari/webapp/model -> entities from database
- src/main/java/com/ari/webapp/dto -> data transfer objects
- src/main/java/com/ari/webapp/repository -> repositories for entities
- src/main/java/com/ari/webapp/service -> business logic
- src/main/java/com/ari/webapp/controller -> http requests services
- src/main/java/resources -> config files

## Notes
- application.yaml is required in order to configure the project and access the database; the file is ignored by Git