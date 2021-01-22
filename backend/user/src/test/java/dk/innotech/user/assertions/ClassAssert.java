package dk.innotech.user.assertions;

import org.assertj.core.api.AbstractAssert;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.function.Supplier;

public abstract class ClassAssert<SELF extends AbstractAssert<SELF, Class<?>>> extends AbstractAssert<SELF, Class<?>> {
    private AnnotationSelector selector;

    public ClassAssert(Class<?> actual, Class<?> selfType) {
        super(actual, selfType);
        selector = AnnotationSelector.of(actual);
    }

    protected void hasStringAnnotationProperty(Annotation annotation, String propertyName, Supplier<String> propertyValue, String expected) {
        var actual = propertyValue.get();
        var annotationName = annotation.getClass().getCanonicalName();
        if (expected == null) {
            if (!StringUtils.hasText(actual)) {
                failWithMessage("Expected %s-annotation to have a %s", annotationName, propertyName);
            }
        } else if (!expected.equals(actual)) {
            failWithMessage("Expected %s-annotation to have a %s of '%s'", annotationName, propertyName, expected);
        }
    }
}
