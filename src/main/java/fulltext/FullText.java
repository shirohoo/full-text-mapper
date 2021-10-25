package fulltext;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FullText {

    int totalLength() default 0;

    Charset encoding() default Charset.UTF_8;

    PaddingCharacter paddingChar() default PaddingCharacter.SPACE;

}
