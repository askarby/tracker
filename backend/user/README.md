# User Service

A [Spring Boot][spring-boot] service responsible for performing basic:

- User authentication
- Creation of users, roles and relations between these.

# Development

Project files for this service are "checked into Git", hence this directory can be "opened" from IntelliJ directly.

The project can be build using [Gradle][gradle], commonly (useful) targets are:

| Command | Description |
| ------------- |-------------|
| `./gradlew build` | Builds the project |
| `./gradlew test` | Runs the tests |

# Discovery

Discovery of endpoint functionality should be performed through [Swagger][swagger].

Endpoints of this service will be accessible through [Swagger's UI](http://localhost:8080/swagger), or by accessing the
API-docs directly, from http://localhost:8080/v2/api-docs.

# Libraries and dependencies

The service utilizes the following (prominent) dependencies:

| Dependency | Description |
| ------------- |-------------|
| [Spring Boot][spring-boot] | Service / Application framework |
| [Spring Security][spring-security] | Security library building "on top" of [Spring Boot][spring-boot] |
| [Spring Data JPA][spring-data-jpa] | Data Access bridge building "on top" of [Spring Boot][spring-boot] |
| [Project Lombok][project-lombok] | Code-generation library to avoid writing triviel code |
| [Mapstruct][mapstruct] | Code-generation library to avoid writing mapping code |

[spring-boot]: https://spring.io/projects/spring-boot
[spring-security]: https://spring.io/projects/spring-security
[spring-data-jpa]: https://spring.io/projects/spring-data-jpa
[project-lombok]: https://projectlombok.org/
[mapstruct]: https://mapstruct.org/
[swagger]: https://swagger.io/
[gradle]: https://gradle.org/