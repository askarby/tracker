package dk.innotech.user.assertions;

import org.assertj.core.api.AbstractAssert;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Random;

import static org.mockito.Mockito.mock;

public class JavaBeanAssert extends AbstractAssert<JavaBeanAssert, Class<?>> {
    public JavaBeanAssert(Class<?> actual) {
        super(actual, JavaBeanAssert.class);
    }

    public JavaBeanAssert hasBeanProperties(String... propertyNames) {
        try {
            for (var name : propertyNames) {
                var field = getField(name);
                if (field == null) {
                    failWithMessage("Expected property with name '%s' to exist", name);
                }

                var getter = getGetter(field);
                if (getter == null) {
                    failWithMessage("Expected getter for property with name '%s' to exist", name);
                }

                var setter = getSetter(field);
                if (setter == null) {
                    failWithMessage("Expected setter for property with name '%s' to exist", name);
                }

                var instance = actual.getConstructor().newInstance();
                var value = createDummyValue(field.getType());
                setter.invoke(instance, value);
                var retrieved = getter.invoke(instance);
                if (!value.equals(retrieved)) {
                    failWithActualExpectedAndMessage(retrieved, value,
                            "Expected setter (%s) and getter (%s) to work according to bean properties",
                            setter.getName(), getter.getName());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    private Field getField(String propertyName) {
        try {
            return actual.getDeclaredField(propertyName);
        } catch (Exception e) {
            return null;
        }
    }

    private Method getGetter(Field field) {
        try {
            var fieldType = field.getType();
            var name = field.getName();
            var methodQualifier = Character.toUpperCase(name.charAt(0)) + name.substring(1);

            var getterName = "get" + methodQualifier;
            if (fieldType.isAssignableFrom(Boolean.TYPE) || fieldType.isAssignableFrom(Boolean.class)) {
                getterName = "is" + methodQualifier;
            }
            return actual.getDeclaredMethod(getterName);
        } catch (Exception e) {
            return null;
        }
    }

    private Method getSetter(Field field) {
        try {
            var name = field.getName();

            var setterName = "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
            return actual.getDeclaredMethod(setterName, field.getType());
        } catch (Exception e) {
            return null;
        }
    }

    private <T> T createDummyValue(Class<T> type) {
        var random = new Random();
        Object value = null;
        if (Integer.TYPE.isAssignableFrom(type) || Integer.class.isAssignableFrom(type)) {
            value = random.nextInt();
        } else if (Long.TYPE.isAssignableFrom(type) || Long.class.isAssignableFrom(type)) {
            value = random.nextLong();
        } else if (Double.TYPE.isAssignableFrom(type) || Double.class.isAssignableFrom(type)) {
            value = random.nextDouble();
        } else if (Float.TYPE.isAssignableFrom(type) || Float.class.isAssignableFrom(type)) {
            value = random.nextFloat();
        } else if (Boolean.TYPE.isAssignableFrom(type) || Boolean.class.isAssignableFrom(type)) {
            value = random.nextBoolean();
        } else if (String.class.isAssignableFrom(type)) {
            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            value =  new String(array, Charset.forName("UTF-8"));
        } else {
            value = mock(type);
        }
        return type.cast(value);
    }
}
