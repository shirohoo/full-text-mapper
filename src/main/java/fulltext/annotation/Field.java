package fulltext.annotation;

import fulltext.FullTextMapper;
import fulltext.enums.Charset;
import fulltext.enums.PadCharacter;
import fulltext.enums.PadPosition;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link FullTextMapper} refers to this annotation to mapping the fields of the object to the full text.
 * <p>
 * If the property declared in {@link Field} and the annotation declared in {@link FullText} are different,
 * the property declared in Field takes precedence.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {

    /**
     * length of this field
     * @return a length of one field in full text
     */
    int length() default 0;

    /**
     * padChar of this field. default is PadCharacter.SPACE
     * @return {@link PadCharacter}
     */
    PadCharacter padChar() default PadCharacter.SPACE;

    /**
     * padPosition of this field. default is PadPosition.LEFT_PAD
     * @return {@link PadPosition}
     */
    PadPosition padPosition() default PadPosition.LEFT_PAD;

}
