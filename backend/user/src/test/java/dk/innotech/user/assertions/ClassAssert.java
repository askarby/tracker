package dk.innotech.user.assertions;

import org.assertj.core.api.AbstractAssert;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        var annotations = getMethodAnnotations(methodName, annotationType);
        if (annotations.size() != 1) {
            failWithMessage("Expected class to have method (named: '%s') with annotation of type %s",
                    methodName, annotationType);
        }
        return (T)annotations.get(0);
    }

    protected <T extends Annotation> List<T> getMethodAnnotations(String methodName, Class<?>... annotationTypes) {
        var methods = Arrays.stream(actual.getMethods())
                .filter(candidate -> candidate.getName().equals(methodName))
                .collect(toList());

        if (methods.size() != 1) {
            failWithMessage("Expected class to have singular method named: '%s'", methodName);
        }

        var annotations = Arrays.stream(methods.get(0).getAnnotations())
                .filter(each -> {
                    for (var annotationType : annotationTypes) {
                        if (each.annotationType().isAssignableFrom(annotationType)) {
                            return true;
                        }
                    }
                    return false;
                })
                .collect(toList());
        if (annotations.isEmpty()) {
            var types = Arrays.stream(annotationTypes).map(Class::getName).collect(Collectors.joining(", "));
            failWithMessage("Expected class to have method (named: '%s') with annotation(s) of type(s) %s",
                    methodName, types);
        }
        return (List<T>) annotations;
    }
}
