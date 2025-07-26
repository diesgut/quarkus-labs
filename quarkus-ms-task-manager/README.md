# quarkus-ms-task-manager

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/.

Key Features and Design Principles
This project has been designed following a series of architectural principles and patterns to ensure its maintainability, scalability, and robustness:

* Active Record Pattern: For data persistence, the Active Record pattern has been adopted. This facilitates direct interaction with the database from the domain entities, optimizing rapid development and code readability for CRUD operations.
* Test-Driven Development (TDD): The project follows a TDD methodology, with a strong focus on integral (integration) tests that ensure the correct functionality of features and facilitate confident refactoring.
* Domain-Driven Design (DDD): The project's architecture is strongly influenced by DDD principles, focusing on a domain model that clearly and explicitly reflects the business.

## Project Structure
The project is organized in a modular manner, following a clear structure that separates concerns and facilitates navigation and maintenance.
```
quarkus-ms-task-manager/
├── src/
│   ├── main/
│   │   ├── docker/                     # Docker files for containerization.
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── diesgut/
│   │   │           ├── common/         # Common classes and utilities (e.g., ApiConstants, PanacheBaseEntity, RestExceptionHandler).
│   │   │           ├── domain/         # Domain Layer (Business Core).
│   │   │           │   ├── auth/
│   │   │           │   │   ├── imp/
│   │   │           │   │   │   └── AuthServiceImpl.java    # Authentication service implementation.
│   │   │           │   │   ├── AuthDto.java                # DTOs for authentication.
│   │   │           │   │   ├── AuthResource.java           # REST endpoint for authentication.
│   │   │           │   │   └── AuthService.java            # Authentication service interface.
│   │   │           │   ├── person/
│   │   │           │   │   ├── dto/                        # Person-specific DTOs.
│   │   │           │   │   │   ├── CreatePersonDto.java
│   │   │           │   │   │   ├── PersonDto.java
│   │   │           │   │   │   └── UpdatePersonDto.java
│   │   │           │   │   ├── imp/
│   │   │           │   │   │   ├── PersonServiceImpl.java  # Person service implementation.
│   │   │           │   │   │   └── PersonConstants.java    # Constants related to Person.
│   │   │           │   │   ├── PersonEntity.java           # Person Entity (Active Record).
│   │   │           │   │   └── PersonService.java          # Person service interface.
│   │   │           │   ├── project/
│   │   │           │   │   ├── dto/                        # Project-specific DTOs.
│   │   │           │   │   │   ├── CreateProjectDto.java
│   │   │           │   │   │   ├── ProjectDto.java
│   │   │           │   │   │   └── UpdateProjectDto.java
│   │   │           │   │   ├── imp/
│   │   │           │   │   │   └── ProjectServiceImpl.java # Project service implementation.
│   │   │           │   │   ├── mapper/                     # Mappers for DTO and Entity conversion.
│   │   │           │   │   │   ├── CreateProjectEntityMapper.java
│   │   │           │   │   │   ├── ProjectEntityMapper.java
│   │   │           │   │   │   └── UpdateProjectEntityMapper.java
│   │   │           │   │   ├── ProjectEntity.java          # Project Entity (Active Record).
│   │   │           │   │   ├── ProjectResource.java        # REST endpoint for Projects.
│   │   │           │   │   └── ProjectService.java         # Project service interface.
│   │   │           │   ├── task/
│   │   │           │   │   ├── dto/                        # Task-specific DTOs.
│   │   │           │   │   │   ├── CreateTaskDto.java
│   │   │           │   │   │   ├── TaskDto.java
│   │   │           │   │   │   └── UpdateTaskDto.java
│   │   │           │   │   ├── imp/
│   │   │           │   │   │   └── TaskServiceImpl.java    # Task service implementation.
│   │   │           │   │   ├── mapper/                     # Mappers for DTO and Entity conversion.
│   │   │           │   │   │   ├── CreateTaskEntityMapper.java
│   │   │           │   │   │   ├── TaskEntityMapper.java
│   │   │           │   │   │   └── UpdateTaskEntityMapper.java
│   │   │           │   │   ├── TaskEntity.java             # Task Entity (Active Record).
│   │   │           │   │   ├── TaskResource.java           # REST endpoint for Tasks.
│   │   │           │   │   └── TaskService.java            # Task service interface.
│   │   │           │   └── user/
│   │   │           │       ├── dto/                        # User-specific DTOs.
│   │   │           │       │   ├── CreateUserDto.java
│   │   │           │       │   ├── PasswordChangeDto.java
│   │   │           │       │   ├── UpdateUserDto.java
│   │   │           │       │   └── UserDto.java
│   │   │           │       ├── imp/
│   │   │           │       │   └── UserServiceImpl.java    # User service implementation.
│   │   │           │       ├── mapper/                     # Mappers for DTO and Entity conversion.
│   │   │           │       │   ├── CreateUserEntityMapper.java
│   │   │           │       │   ├── UserEntityMapper.java
│   │   │           │       │   └── UpdateUserEntityMapper.java
│   │   │           │       ├── UserEntity.java             # User Entity (Active Record).
│   │   │           │       ├── UserResource.java           # REST endpoint for Users.
│   │   │           │       └── UserService.java            # User service interface.
│   │   └── resources/
│   │       ├── application.properties    # Quarkus configuration file.
│   │       ├── import-dev.sql            # Database initialization script for development.
│   │       └── jwt/                      # JWT keys for authentication.
│   │           ├── private-key.pem
│   │           ├── public-key.pem
│   │           └── rsa-private-key.pem
│   └── test/
│       └── java/
│           └── com/
│               └── diesgut/
│                   └── domain/
│                       ├── auth/
│                       │   └── AuthResourceTest.java     # Integration tests for the authentication resource.
│                       └── user/
│                           └── UserResourceTest.java     # Integration tests for the user resource.
├── .dockerignore
├── .mvn/
├── docker-compose.yml                  # Docker Compose configuration.
├── pom.xml                             # Maven configuration file.
├── README.md                           # This file.
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```![img.png](img.png)

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkus-ms-task-manager-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)


