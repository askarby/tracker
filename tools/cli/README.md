# CLI

A [Picocli][picocli] Command-line Interface application producing functionality for...

Kubernetes tooling:
- ...
- ...

Various utilities:
- BCrypt encryption of passwords
- Conversion of time/date to epoch timestamps (with varying precision) 

# Development

Project files for this service are "checked into Git", hence this directory can be "opened" from IntelliJ directly.

The project can be build using [Gradle][gradle], commonly (useful) targets are:

| Command | Description |
| ------------- |-------------|
| `./gradlew build` | Builds the project |
| `./gradlew test` | Runs the tests |
| `./gradlew native-image` | Builds a native-executable of the product |

# Libraries and dependencies

The service utilizes the following (prominent) dependencies:

| Dependency | Description |
| ------------- |-------------|
| [Picocli][picocli] | CLI framework |
| [GraalVM][graalvm] | Tool for producing native-images (amongst others) of application |

[picocli]: https://picocli.info/
[graalvm]: https://mapstruct.org/
[gradle]: https://gradle.org/