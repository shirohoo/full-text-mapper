package fulltext;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link FullTextMapper} refers to this annotation to mapping the fields of the object to the full text.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Length {

    /**
     * Defines the length occupied by the field in the full text.
     */
    int value();

}
