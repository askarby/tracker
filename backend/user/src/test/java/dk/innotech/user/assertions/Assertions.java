package dk.innotech.user.assertions;

public class Assertions {

    public static SwaggerAssert assertThat(Class<?> actual) {
        return new SwaggerAssert(actual);
    }
}
