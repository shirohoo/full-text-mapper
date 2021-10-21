package annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import type.Encoding;
import type.RecordType;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FullText {

    RecordType recordType() default RecordType.NONE;

    String name() default "";

    int size() default 0;

    Encoding encoding() default Encoding.UTF_8;

}
