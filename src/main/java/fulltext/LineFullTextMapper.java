package fulltext;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Objects.isNull;
import fulltext.annotation.Field;
import fulltext.annotation.FullText;
import fulltext.enums.Charset;
import fulltext.enums.ClassCaster;
import fulltext.enums.PadCharacter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * <p>
 * FullTextMapper mapping full text and Object to each other. In order to use it, {@link FullText} and, {@link Field} must be properly declared in the object to be mapped.
 */
public final class LineFullTextMapper implements FullTextMapper {

    private final static Logger log = Logger.getGlobal();

    /**
     * This static factory method is invoked default constructor.
     * <p>
     *
     * @return instance of fulltext.FullTextMapper
     */
    public static LineFullTextMapper newInstance() {
        return new LineFullTextMapper();
    }

    private LineFullTextMapper() {
    }

    /**
     * After encoding the full text composed of a byte array with the specified encoder, it is binding to each field of the received object.
     * <p>
     * At this time, the data of the full text is converted and binding to the declaration type of each field.
     * <p>
     * If data binding completes without any problems, return the created instance.
     * <p>
     * Several exceptions may occur, but when an exception occurs, an Optional is always returned with an error log.
     * <p>
     *
     * @param bytes Byte array type data read from the full text.
     * @param clazz A class to instantiate by binding data read from the full text.
     * @return instance of Optional{@literal <}T {@literal >}
     */
    public <T> Optional<T> readValue(final byte[] bytes, final Class<T> clazz) {
        final String line = convertStr(bytes, getAnnotation(clazz).encoding());
        verify(line, clazz);
        return Optional.ofNullable(parse(line, clazz));
    }

    private String convertStr(final byte[] bytes, Charset encoder) {
        try {
            return new String(bytes, Charset.findBy(encoder));
        } catch (UnsupportedEncodingException e) {
            // I'm sure it will never happen. so didn't do anything.
            return null;
        }
    }

    private <T> FullText getAnnotation(final Class<T> clazz) {
        final FullText annotation = clazz.getAnnotation(FullText.class);
        if (isNull(annotation)) {
            throw new NoSuchElementException("Could not find @FullText in argument object. please add @FullText at class level.");
        }
        return annotation;
    }

    /**
     * Creates an instance of the received object, reads the full text, and binds it to each field of the created instance.
     * <p>
     * At this time, the data of the full text is converted and binding to the declaration type of each field.
     * <p>
     * If data binding completes without any problems, return the created instance.
     * <p>
     * Several exceptions may occur, but when an exception occurs, it logs and always returns Optional.
     * <p>
     *
     * @param line  String type data read from the full text.
     * @param clazz A class to instantiate by binding data read from the full text.
     * @return instance of Optional{@literal <}T {@literal >}
     */
    public <T> Optional<T> readValue(final String line, final Class<T> clazz) {
        verify(line, clazz);
        return Optional.ofNullable(parse(line, clazz));
    }

    private <T> void verify(final String line, final Class<T> clazz) {
        verifyAnnotation(clazz);
    }

    private <T> void verifyAnnotation(final Class<T> clazz) {
        Objects.requireNonNull(clazz, "clazz is must not be null.");

        final int fullTextLen = getAnnotation(clazz).length();
        final int fieldsLen = fieldsLen(clazz);

        if (fullTextLen != fieldsLen) {
            throw new IllegalArgumentException(format(
                "There is a problem with setting the full text object. @FullText: %d, @Field total length: %d",
                fullTextLen, fieldsLen
            ));
        }
    }

    private <T> int fieldsLen(final Class<T> clazz) {
        return stream(clazz.getDeclaredFields())
            .map(this::getAnnotation)
            .filter(Objects::nonNull)
            .map(Field::length)
            .reduce(0, Integer::sum);
    }

    private Field getAnnotation(final java.lang.reflect.Field field) {
        Field annotation = field.getAnnotation(Field.class);
        if (isNull(annotation)) {
            throw new NoSuchElementException("Could not find @Field in argument object. please add @Field at field level.");
        }
        return annotation;
    }

    private <T> T parse(String line, final Class<T> clazz) {
        final T instance = newInstance(clazz);
        return isNull(instance) ? null : setInstance(line, clazz, instance);
    }

    private <T> T newInstance(final Class<T> clazz) {
        try {
            return invokeConstructor(clazz);
        } catch (Exception e) {
            errorLogging(e);
            return null;
        }
    }

    private <T> T invokeConstructor(final Class<T> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Constructor<T> defaultConstructor = clazz.getDeclaredConstructor();
        defaultConstructor.setAccessible(true); // Make it work even if the default constructor's access modifier is private
        return defaultConstructor.newInstance();
    }

    private <T> T setInstance(String line, final Class<T> clazz, final T instance) {
        try {
            for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
                line = dataBind(line, instance, field, getAnnotation(clazz).padChar());
            }
            if (line.length() != 0) {
                throw new IllegalArgumentException("Parsing has been completed. but remaining data exists. current data: " + line);
            }
            return instance;
        } catch (IllegalAccessException e) {
            errorLogging(e);
            return null;
        }
    }


    private <T> String dataBind(String data, final T instance, final java.lang.reflect.Field field, final PadCharacter padCharacter) throws IllegalAccessException {
        final int length = getAnnotation(field).length();

        field.setAccessible(true);

        for (ClassCaster classCaster : ClassCaster.values()) {
            if (field.getType().equals(classCaster.getClazz())) {
                Function<String, ?> typeCasting = classCaster.castFunction();
                field.set(instance, typeCasting.apply(slice(data, length, padCharacter)));
                data = data.substring(length);
            }
        }
        return data;
    }

    private String slice(final String data, final int len, final PadCharacter padCharacter) {
        return padCharacter.removeLeftPad(data.substring(0, len));
    }

    /**
     * It takes an object as input, refers to {@link FullText} and {@link Field} declared, and creates full text and returns it.
     *
     * @param object want to output in full text
     * @return full text
     */
    @Override
    public String write(final Object object) {
        final Class<?> clazz = object.getClass();
        PadCharacter padCharacter = getAnnotation(clazz).padChar();
        verifyAnnotation(clazz);

        StringBuilder builder = new StringBuilder();
        for (java.lang.reflect.Field declaredField : clazz.getDeclaredFields()) {
            declaredField.setAccessible(true);
            Object field = getField(object, declaredField);
            if (isNull(field)) {
                field = "";
            }

            Class<?> fieldClass = field.getClass();
            if (fieldClass.equals(LocalDate.class)) {
                String data = ((LocalDate) field).format(DateTimeFormatter.BASIC_ISO_DATE);
                int padLen = padLen(getAnnotation(declaredField), data);
                builder.append(padCharacter.leftPad(data, padLen));
            } else if (fieldClass.equals(LocalDateTime.class)) {
                String data = ((LocalDateTime) field).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                int padLen = padLen(getAnnotation(declaredField), data);
                builder.append(padCharacter.leftPad(data, padLen));
            } else {
                String data = field.toString();
                int padLen = padLen(getAnnotation(declaredField), data);
                builder.append(padCharacter.leftPad(data, padLen));
            }
        }
        return builder.toString();
    }

    private Object getField(final Object object, final java.lang.reflect.Field declaredField) {
        Object field = null;
        try {
            field = declaredField.get(object);
        } catch (IllegalAccessException e) {
            // I'm sure it will never happen. so didn't do anything.
            // because setAccessible(true).
        }
        return field;
    }

    private int padLen(final Field field, final Object data) {
        return field.length() - data.toString().length();
    }

    private void errorLogging(final Exception e) {
        log.severe(e.getClass() + ": " + e.getMessage() + ". returned null.");
    }

}
