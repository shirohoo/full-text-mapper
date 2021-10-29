package fulltext.annotation;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import fulltext.enums.Charset;
import fulltext.enums.PadCharacter;
import fulltext.enums.PadPosition;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class FullTextReflector {

    private FullTextReflector() {
    }

    public static FullText findClassAnnotation(final Class<?> clazz) {
        Objects.requireNonNull(clazz, "Class must not be null.");
        final FullText annotation = clazz.getAnnotation(FullText.class);
        if (isNull(annotation)) {
            throw new NoSuchElementException("Could not find @FullText in argument object. please add @FullText at class level.");
        }
        return annotation;
    }

    public static Field findFieldAnnotation(final java.lang.reflect.Field field) {
        Objects.requireNonNull(field, "Field must not be null.");
        Field annotation = field.getAnnotation(Field.class);
        if (isNull(annotation)) {
            throw new NoSuchElementException("Could not find @Field in argument object. please add @Field at field level.");
        }
        return annotation;
    }

    public static Charset getEncoding(final Class<?> clazz) {
        return findClassAnnotation(clazz).encoding();
    }

    public static int getLength(final Class<?> clazz) {
        return findClassAnnotation(clazz).length();
    }

    public static int getLength(final java.lang.reflect.Field field) {
        return findFieldAnnotation(field).length();
    }

    public static PadCharacter getPadCharacter(final FullText classAnnotation, final Field fieldAnnotation) {
        final PadCharacter cpc = classAnnotation.padChar();
        final PadCharacter fpc = fieldAnnotation.padChar();
        return cpc.equals(fpc) ? cpc : fpc;
    }

    public static PadPosition getPadPosition(final FullText classAnnotation, final Field fieldAnnotation) {
        final PadPosition cpp = classAnnotation.padPosition();
        final PadPosition fpp = fieldAnnotation.padPosition();
        return cpp.equals(fpp) ? cpp : fpp;
    }

    public static void verifyAnnotation(final Class<?> clazz) {
        Objects.requireNonNull(clazz, "Class is must not be null.");

        final int fullTextLen = getLength(clazz);
        final int fieldsLen = fieldsLen(clazz);

        if (fullTextLen != fieldsLen) {
            throw new IllegalArgumentException(format(
                "There is a problem with setting the full text object. @FullText: %d, @Field total length: %d",
                fullTextLen, fieldsLen
            ));
        }
    }

    private static int fieldsLen(final Class<?> clazz) {
        return stream(clazz.getDeclaredFields())
            .map(FullTextReflector::findFieldAnnotation)
            .filter(Objects::nonNull)
            .map(Field::length)
            .reduce(0, Integer::sum);
    }

    public static boolean isSupported(final Class<?> clazz) {
        return nonNull(findClassAnnotation(clazz));
    }

    public static boolean isSupported(final java.lang.reflect.Field field) {
        return nonNull(findFieldAnnotation(field));
    }

}
