package fulltext;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Objects.isNull;
import fulltext.annotation.Field;
import fulltext.annotation.FullText;
import fulltext.enums.Charset;
import fulltext.enums.ClassCaster;
import fulltext.enums.PadCharacter;
import fulltext.enums.PadPosition;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * <p>
 * FullTextMapper mapping full text and Object to each other. In order to use it, {@link FullText} and, {@link Field} must be properly declared in the object to be mapped. This class is thread safe.
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
        final String line = convertStr(bytes, getEncoding(clazz));
        verifyAnnotation(clazz);
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

    private Charset getEncoding(final Class<?> clazz) {
        return getAnnotation(clazz).encoding();
    }

    private <T> FullText getAnnotation(final Class<T> clazz) {
        final FullText annotation = clazz.getAnnotation(FullText.class);
        if (isNull(annotation)) {
            throw new NoSuchElementException("Could not find @FullText in argument object. please add @FullText at class level.");
        }
        return annotation;
    }

    private Field getAnnotation(final java.lang.reflect.Field field) {
        Field annotation = field.getAnnotation(Field.class);
        if (isNull(annotation)) {
            throw new NoSuchElementException("Could not find @Field in argument object. please add @Field at field level.");
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
        verifyAnnotation(clazz);
        return Optional.ofNullable(parse(line, clazz));
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
                FullText classAnnotation = getAnnotation(clazz);
                Field fieldAnnotation = getAnnotation(field);
                PadCharacter padChar = getPadCharacter(classAnnotation, fieldAnnotation);
                PadPosition padPosition = getPadPosition(classAnnotation, fieldAnnotation);
                line = dataBind(line, instance, field, padChar, padPosition);
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


    private <T> String dataBind(String data, final T instance, final java.lang.reflect.Field field, final PadCharacter padCharacter, final PadPosition padPosition) throws IllegalAccessException {
        final int length = getAnnotation(field).length();

        field.setAccessible(true);

        for (ClassCaster classCaster : ClassCaster.values()) {
            if (field.getType().equals(classCaster.getClazz())) {
                String sliceData = slice(data, padCharacter, padPosition, length);
                field.set(instance, classCaster.classCast().apply(sliceData));
                data = data.substring(length);
            }
        }
        return data;
    }

    private String slice(final String data, final PadCharacter padCharacter, final PadPosition padPosition, final int length) {
        if (padPosition.isLeft()) {
            return padCharacter.removeLeftPad(data.substring(0, length));
        }
        return padCharacter.removeRightPad(data.substring(0, length));
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
        verifyAnnotation(clazz);

        StringBuilder sb = new StringBuilder();
        for (java.lang.reflect.Field declaredField : clazz.getDeclaredFields()) {
            final FullText classAnnotation = getAnnotation(clazz);
            final Field fieldAnnotation = getAnnotation(declaredField);
            final PadCharacter padCharacter = getPadCharacter(classAnnotation, fieldAnnotation);
            final PadPosition padPosition = getPadPosition(classAnnotation, fieldAnnotation);

            declaredField.setAccessible(true);

            final Object field = correctNull(object, declaredField);
            final Class<?> fieldClass = field.getClass();
            if (fieldClass.equals(LocalDate.class)) {
                String data = ((LocalDate) field).format(DateTimeFormatter.BASIC_ISO_DATE);
                appendData(sb, fieldAnnotation, padCharacter, padPosition, data);
            } else if (fieldClass.equals(LocalDateTime.class)) {
                String data = ((LocalDateTime) field).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                appendData(sb, fieldAnnotation, padCharacter, padPosition, data);
            } else {
                String data = field.toString();
                appendData(sb, fieldAnnotation, padCharacter, padPosition, data);
            }
        }
        return sb.toString();
    }

    private Object correctNull(final Object object, final java.lang.reflect.Field declaredField) {
        final Object field = getField(object, declaredField);
        if (isNull(field)) {
            return "";
        }
        return field;
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

    private void appendData(final StringBuilder builder, final Field fieldAnnotation, final PadCharacter padCharacter, final PadPosition padPosition, final String data) {
        final int padLen = padLen(fieldAnnotation, data);
        builder.append(appendPad(padPosition, padCharacter, data, padLen));
    }

    private String appendPad(final PadPosition padPosition, final PadCharacter padCharacter, final String data, final int padLen) {
        if (padPosition.isLeft()) {
            return padCharacter.leftPad(data, padLen);
        }
        return padCharacter.rightPad(data, padLen);
    }

    private int padLen(final Field field, final Object data) {
        return field.length() - data.toString().length();
    }

    private PadCharacter getPadCharacter(final FullText classAnnotation, final Field fieldAnnotation) {
        final PadCharacter cpc = classAnnotation.padChar();
        final PadCharacter fpc = fieldAnnotation.padChar();
        return cpc.equals(fpc) ? cpc : fpc;
    }

    private PadPosition getPadPosition(final FullText classAnnotation, final Field fieldAnnotation) {
        final PadPosition cpp = classAnnotation.padPosition();
        final PadPosition fpp = fieldAnnotation.padPosition();
        return cpp.equals(fpp) ? cpp : fpp;
    }

    private void errorLogging(final Exception e) {
        log.severe(e.getClass() + ": " + e.getMessage() + ". returned null.");
    }

}
