package fulltext;

import static fulltext.annotation.FullTextReflector.findClassAnnotation;
import static fulltext.annotation.FullTextReflector.findFieldAnnotation;
import static fulltext.annotation.FullTextReflector.getEncoding;
import static fulltext.annotation.FullTextReflector.getLength;
import static fulltext.annotation.FullTextReflector.getPadCharacter;
import static fulltext.annotation.FullTextReflector.getPadPosition;
import static fulltext.annotation.FullTextReflector.verifyAnnotation;
import static java.util.Objects.isNull;
import fulltext.annotation.Field;
import fulltext.annotation.FullText;
import fulltext.enums.Charset;
import fulltext.enums.ClassCaster;
import fulltext.enums.PadCharacter;
import fulltext.enums.PadPosition;
import fulltext.exception.RuleViolationException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
     *
     * @param bytes Byte array type data read from the full text.
     * @param clazz A class to instantiate by binding data read from the full text.
     * @return instance of T
     * @throws RuleViolationException Occurs when the code convention of this module is violated. Read the documentation.
     */
    public <T> T readValue(final byte[] bytes, final Class<T> clazz) {
        verifyAnnotation(clazz);
        final String line = byteArrayToString(bytes, getEncoding(clazz));
        return parse(line, clazz);
    }

    private String byteArrayToString(final byte[] bytes, Charset encoder) {
        try {
            return new String(bytes, Charset.findBy(encoder));
        } catch (UnsupportedEncodingException e) {
            // I'm sure it will never happen. so didn't do anything.
            return null;
        }
    }

    /**
     * Creates an instance of the received object, reads the full text, and binds it to each field of the created instance.
     * <p>
     * At this time, the data of the full text is converted and binding to the declaration type of each field.
     * <p>
     * If data binding completes without any problems, return the created instance.
     * <p>
     *
     * @param line  String type data read from the full text.
     * @param clazz A class to instantiate by binding data read from the full text.
     * @return instance of T
     * @throws RuleViolationException Occurs when the code convention of this module is violated. Read the documentation.
     */
    public <T> T readValue(final String line, final Class<T> clazz) {
        verifyAnnotation(clazz);
        return parse(line, clazz);
    }

    private <T> T parse(String line, final Class<T> clazz) {
        final T instance = newInstance(clazz);
        return isNull(instance) ? null : setInstance(line, clazz, instance);
    }

    private <T> T newInstance(final Class<T> clazz) {
        try {
            return invokeConstructor(clazz);
        } catch (Exception e) {
            throw new RuleViolationException("No suitable constructor. Make sure " + e.getLocalizedMessage() + " has a default constructor");
        }
    }

    private <T> T invokeConstructor(final Class<T> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Constructor<T> defaultConstructor = clazz.getDeclaredConstructor();
        defaultConstructor.setAccessible(true); // Make it work even if the default constructor's access modifier is private
        return defaultConstructor.newInstance();
    }

    private <T> T setInstance(String line, final Class<T> clazz, final T instance) {
        for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
            final FullText classAnnotation = findClassAnnotation(clazz);
            final Field fieldAnnotation = findFieldAnnotation(field);
            final PadCharacter padChar = getPadCharacter(classAnnotation, fieldAnnotation);
            final PadPosition padPosition = getPadPosition(classAnnotation, fieldAnnotation);
            line = dataBind(line, instance, field, padChar, padPosition);
        }
        if (line.length() != 0) {
            throw new RuleViolationException("Parsing has been completed. but remaining data exists. current data: " + line);
        }
        return instance;
    }

    private String dataBind(String data, final Object instance, final java.lang.reflect.Field field, final PadCharacter padCharacter, final PadPosition padPosition) {
        field.setAccessible(true);
        for (ClassCaster classCaster : ClassCaster.values()) {
            Class<?> fieldType = field.getType();
            if (fieldType.equals(classCaster.getClazz())) {
                String value = removePad(data, padCharacter, padPosition, getLength(field));
                if (
                    (classCaster == ClassCaster.INT_WRAPPER ||
                        classCaster == ClassCaster.LONG_WRAPPER ||
                        classCaster == ClassCaster.DOUBLE_WRAPPER) &&
                        ("".equals(value)) || value == null) {
                    value = "0";
                }
                try {
                    field.set(instance, classCaster.getFunction().apply(value));
                } catch (IllegalAccessException e) {
                    throw new RuleViolationException(field.getName() + " field is either inaccessible or final");
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Exception while assigning " + value + " to " + field.getName());
                }
                data = data.substring(getLength(field));
            }
        }
        return data;
    }

    private String removePad(final String data, final PadCharacter padCharacter, final PadPosition padPosition, final int length) {
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
            final FullText classAnnotation = findClassAnnotation(clazz);
            final Field fieldAnnotation = findFieldAnnotation(declaredField);
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
        final Object field = fieldValue(object, declaredField);

        // never exists if field is null
        // because setAccessible(true).
        return isNull(field) ? "" : field;
    }

    private Object fieldValue(final Object object, final java.lang.reflect.Field field) {
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            // I'm sure it will never happen. so didn't do anything.
            // because setAccessible(true).
            return null;
        }
    }

    private void appendData(final StringBuilder builder, final Field fieldAnnotation, final PadCharacter padCharacter, final PadPosition padPosition, final String data) {
        builder.append(appendPad(padPosition, padCharacter, data, padLen(fieldAnnotation, data)));
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

}
