package dk.innotech.user.assertions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class AnnotationSelector {

    private Class<?> classType;

    private AnnotationSelector(Class<?> classType) {
        this.classType = classType;
    }

    public static AnnotationSelector of(Class<?> classType) {
        return new AnnotationSelector(classType);
    }

    public <T extends Annotation> T getClassAnnotation(Class<T> annotationType) {
        return getSingular(() -> getClassAnnotations(annotationType));
    }

    public <T extends Annotation> List<T> getClassAnnotations(Class<?>... annotationTypes) {
        var annotations = Arrays.stream(classType.getAnnotations())
                .filter(ofAnnotationType(annotationTypes))
                .collect(toList());
        if (annotations.isEmpty()) {
            var types = Arrays.stream(annotationTypes).map(Class::getCanonicalName).collect(Collectors.joining(", "));
            var message = String.format("Expected class %s to have annotation(s) of type(s) %s",
                    classType.getCanonicalName(), types);
            throw new IllegalArgumentException(message);
        }
        return (List<T>) annotations;
    }

    public <T extends Annotation> T getMethodAnnotation(String methodName, Class<T> annotationType) {
        return getSingular(() -> getMethodAnnotations(methodName, annotationType));
    }

    public <T extends Annotation> List<T> getMethodAnnotations(String methodName, Class<?>... annotationTypes) {
        var methods = Arrays.stream(classType.getDeclaredMethods())
                .filter(candidate -> candidate.getName().equals(methodName))
                .collect(toList());

        if (methods.size() != 1) {
            var message = String.format("Expected class %s to have singular method named: '%s'",
                    classType.getCanonicalName(), methodName);
            throw new IllegalArgumentException(message);
        }

        var annotations = Arrays.stream(methods.get(0).getAnnotations())
                .filter(ofAnnotationType(annotationTypes))
                .collect(toList());
        if (annotations.isEmpty()) {
            var types = Arrays.stream(annotationTypes).map(Class::getName).collect(Collectors.joining(", "));
            var message = String.format("Expected class %s to have method (named: '%s') with annotation(s) of type(s) %s",
                    classType.getCanonicalName(), methodName, types);
            throw new IllegalArgumentException(message);
        }
        return (List<T>) annotations;
    }

    public <T extends Annotation> T getFieldAnnotation(String fieldName, Class<T> annotationType) {
        return getSingular(() -> getFieldAnnotations(fieldName, annotationType));
    }

    public <T extends Annotation> List<T> getFieldAnnotations(String fieldName, Class<?>... annotationTypes) {
        Field field;
        try {
            field = classType.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            var message = String.format("Expected class %s to have field named: '%s'",
                    classType.getCanonicalName(), fieldName);
            throw new IllegalArgumentException(message);
        }

        var annotations = Arrays.stream(field.getAnnotations())
                .filter(ofAnnotationType(annotationTypes))
                .collect(toList());
        if (annotations.isEmpty()) {
            var types = Arrays.stream(annotationTypes).map(Class::getCanonicalName).collect(Collectors.joining(", "));
            var message = String.format("Expected class %s to have field (named: '%s') with annotation(s) of type(s) %s",
                    classType.getCanonicalName(), fieldName, types);
            throw new IllegalArgumentException(message);
        }
        return (List<T>) annotations;
    }

    private Predicate<Annotation> ofAnnotationType(Class<?>[] annotationTypes) {
        return each -> {
            for (var annotationType : annotationTypes) {
                if (each.annotationType().isAssignableFrom(annotationType)) {
                    return true;
                }
            }
            return false;
        };
    }

    public <T extends Annotation> T getSingular(Supplier<List<T>> supplier) {
        var annotations = supplier.get();
        var size = annotations.size();
        if (size != 1) {
            var message = String.format("Multiple annotations (%d) qualified", size);
            throw new IllegalArgumentException(message);
        }
        return annotations.get(0);
    }
}
