package dk.innotech.user.assertions;

import org.assertj.core.api.AbstractAssert;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import static java.util.stream.Collectors.toList;

public abstract class ClassAssert<SELF extends AbstractAssert<SELF, Class<?>>> extends AbstractAssert<SELF, Class<?>> {
    public ClassAssert(Class<?> actual, Class<?> selfType) {
        super(actual, selfType);
    }

    protected <T extends Annotation> T getClassAnnotation(Class<T> annotationType) {
        isNotNull();

        var annotation = actual.getAnnotation(annotationType);
        if (annotation == null) {
            failWithMessage("Expected class to have annotation of type %s", annotationType);
        }
        return annotation;
    }

    protected <T extends Annotation> T getMethodAnnotation(String methodName, Class<T> annotationType) {
        var methods = Arrays.stream(actual.getMethods())
                .filter(candidate -> candidate.getName().equals(methodName))
                .collect(toList());

        if (methods.size() != 1) {
            failWithMessage("Expected class to have singular method named: '%s'", methodName);
        }

        var annotation = methods.get(0).getAnnotation(annotationType);
        if (annotation == null) {
            failWithMessage("Expected class to have method (named: '%s') with annotation of type %s",
                    methodName, annotationType);
        }
        return annotation;
    }
}
