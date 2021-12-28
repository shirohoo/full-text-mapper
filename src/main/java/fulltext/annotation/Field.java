package fulltext.annotation;

import fulltext.FullTextMapper;
import fulltext.enums.PadCharacter;
import fulltext.enums.PadPosition;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.format.DateTimeFormatter;

/**
 * {@link FullTextMapper} refers to this annotation to mapping the fields of the object to the full text.
 * <p>
 * If the property declared in {@link Field} and the annotation declared in {@link FullText} are different, the property declared in Field takes precedence.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {
    /**
     * length of this field
     *
     * @return a length of one field in full text
     */
    int length() default 0;

    /**
     * padChar of this field. default is PadCharacter.SPACE
     *
     * @return {@link PadCharacter}
     * @throws UnsupportedOperationException If @FullText.PadCharacter and @Field.PadCharacter are both PadCharacter.NONE
     */
    PadCharacter padChar() default PadCharacter.NONE;

    /**
     * padPosition of this field. default is PadPosition.LEFT_PAD
     *
     * @return {@link PadPosition}
     * @throws UnsupportedOperationException If @FullText.PadPosition and @Field.PadPosition are both PadPosition.NONE
     */
    PadPosition padPosition() default PadPosition.NONE;

    /**
     * This is the default format for LocalDate. Observe the format of DateTimeFormatter.
     * @return LocalDate format
     */
    String localDateFormat() default "yyyyMMdd";

    /**
     * This is the default format for LocalDateTime. Observe the format of DateTimeFormatter.
     * @return LocalDateTime format
     */
    String localDateTimeFormat() default "yyyyMMddHHmmss";
}
