package dk.innotech.user.assertions;

public class Assertions {

    public static SwaggerAssert assertThat(Class<?> actual) {
        return new SwaggerAssert(actual);
    }

    public static JavaBeanAssert assertThatBean(Object actual) {
        var actualIsClass = actual.getClass().isAssignableFrom(Class.class);
        var bean = actualIsClass ? (Class<?>)actual : actual.getClass();
        return new JavaBeanAssert(bean);
    }
}
