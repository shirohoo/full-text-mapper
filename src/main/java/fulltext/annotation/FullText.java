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
 * {@link FullTextMapper} refers to this annotation to mapping the object to the full text.
 * set here are used globally only in class scope and do not take precedence over field level annotations.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FullText {

    /**
     * Means the total length of one line in full text.
     * @return a total length of full text
     */
    int length();

    /**
     * Set how to encode full text. The default is UTF-8.
     * @return {@link Charset}
     */
    Charset encoding() default Charset.UTF_8;

    /**
     * Sets the padding character to be used in full text. The default is a space character(" ").
     * @return {@link PadCharacter}
     */
    PadCharacter padChar() default PadCharacter.SPACE;

    /**
     * Determines whether the pad is padded on the left or on the right. default is left.
     * @return {@link PadPosition}
     */
    PadPosition padPosition() default PadPosition.LEFT;

}
