# Backend

Contains the backend implementation (micro-services and shared libs)

# Structure

The structure (and contents) of the folder is as follows:

| Path | Description |
| ------------- |-------------|
| [legacy](./legacy) | Legacy (monolithic) implementation of backend - to be removed! (implemented using [Micronaut][micronaut]) |
| [user](./user) | Microservice with user (and authentication) functionality |

All microservices are implemented using [Spring Boot][spring-boot], unless otherwise mentioned.

[micronaut]: https://micronaut.io/
[spring-boot]: https://spring.io/projects/spring-boot
