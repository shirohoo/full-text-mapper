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
     */
    int length() default 0;

    /**
     * encoding of this field. default is Charset.UTF_8
     */
    Charset encoding() default Charset.UTF_8;

    /**
     * padChar of this field. default is PadCharacter.SPACE
     */
    PadCharacter padChar() default PadCharacter.SPACE;

    /**
     * padPosition of this field. default is PadPosition.LEFT_PAD
     */
    PadPosition padPosition() default PadPosition.LEFT_PAD;

}