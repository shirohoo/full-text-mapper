package fulltext.annotation;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Objects.isNull;
import fulltext.enums.Charset;
import fulltext.enums.PadCharacter;
import fulltext.enums.PadPosition;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class FullTextReflector {

    private FullTextReflector() {
    }

    /**
     * Finds and returns the {@link FullText} declared in the class level.
     *
     * @param clazz Target class from which you want to extract {@link FullText}
     * @return {@link FullText}
     * @throws NoSuchElementException occurs when {@link FullText} is not found.
     */
    public static FullText findClassAnnotation(final Class<?> clazz) throws NoSuchElementException {
        Objects.requireNonNull(clazz, "Class must not be null.");
        final FullText annotation = clazz.getAnnotation(FullText.class);
        if (isNull(annotation)) {
            throw new NoSuchElementException("Could not find @FullText in argument object. please add @FullText at class level.");
        }
        return annotation;
    }

    /**
     * Finds and returns the {@link Field} declared in the field level.
     *
     * @param field Target field from which you want to extract {@link Field}
     * @return {@link Field}
     * @throws NoSuchElementException occurs when {@link Field} is not found.
     */
    public static Field findFieldAnnotation(final java.lang.reflect.Field field) throws NoSuchElementException {
        Objects.requireNonNull(field, "Field must not be null.");
        Field annotation = field.getAnnotation(Field.class);
        if (isNull(annotation)) {
            throw new NoSuchElementException("Could not find @Field in argument object. please add @Field at field level.");
        }
        return annotation;
    }

    /**
     * Returns the encoding declared in {@link FullText}.
     *
     * @param clazz a declared {@link FullText} annotation at class level.
     * @return {@link Charset}
     */
    public static Charset getEncoding(final Class<?> clazz) {
        return findClassAnnotation(clazz).encoding();
    }

    /**
     * Returns the length declared in {@link FullText}.
     *
     * @param clazz a declared {@link FullText} annotation at class level.
     * @return a total length of full text
     */
    public static int getLength(final Class<?> clazz) {
        return findClassAnnotation(clazz).length();
    }

    /**
     * Returns the length declared in {@link FullText}.
     *
     * @param field a declared {@link Field} annotation at field level.
     * @return a length of one field in full text
     */
    public static int getLength(final java.lang.reflect.Field field) {
        return findFieldAnnotation(field).length();
    }

    /**
     * Returns a {@link PadCharacter} to be used for full text mapping. at this time, if PadCharacter declared in {@link FullText} and PadCharacter declared in {@link Field} are different, PadCharacter declared in Field is returned.
     *
     * @param classAnnotation a declared {@link FullText} annotation at class level.
     * @param fieldAnnotation a declared {@link Field} annotation at field level.
     * @return {@link PadCharacter}
     */
    public static PadCharacter getPadCharacter(final FullText classAnnotation, final Field fieldAnnotation) throws UnsupportedOperationException {
        final PadCharacter cpc = classAnnotation.padChar();
        final PadCharacter fpc = fieldAnnotation.padChar();

        if (cpc.isNone() && fpc.isNone()) {
            throw new UnsupportedOperationException("Both @FullText and @Field can't be PadCharacter.NONE");
        }
        if (fpc.isNone() || cpc == fpc) {
            return cpc;
        }
        return fpc;
    }

    /**
     * Returns a {@link PadPosition} to be used for full text mapping. at this time, if PadPosition declared in {@link FullText} and PadPosition declared in {@link Field} are different, PadPosition declared in Field is returned.
     *
     * @param classAnnotation a declared {@link FullText} annotation at class level.
     * @param fieldAnnotation a declared {@link Field} annotation at field level.
     * @return {@link PadPosition}
     */
    public static PadPosition getPadPosition(final FullText classAnnotation, final Field fieldAnnotation) throws UnsupportedOperationException {
        final PadPosition cpp = classAnnotation.padPosition();
        final PadPosition fpp = fieldAnnotation.padPosition();

        if (cpp.isNone() && fpp.isNone()) {
            throw new UnsupportedOperationException("Both @FullText and @Field can't be PadCharacter.NONE");
        }
        if (fpp.isNone() || cpp == fpp) {
            return cpp;
        }
        return fpp;
    }

    /**
     * Validate that {@link FullText} and {@link Field} are set without any problem in the class.
     *
     * @param clazz the class you want to map to full text.
     * @throws NullPointerException     occurs when the argument is null.
     * @throws IllegalArgumentException occurs when the sum of the length of {@link FullText} and the length of all {@link Field} is different.
     */
    public static void verifyAnnotation(final Class<?> clazz) throws NullPointerException, IllegalArgumentException {
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

}
