package fulltext;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link FullTextMapper}는 이 어노테이션을 참조하여 전문과 객체를 매핑합니다.
 * <p>
 * {@link FullTextMapper} refers to this annotation to mapping the object to the full text.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FullText {

    /**
     * 전문 한줄의 총 길이를 의미합니다.
     * <p>
     * Means the total length of one line in full text.
     */
    int totalLength();

    /**
     * 전문을 어떤 방식으로 인코딩할지 설정합니다. 기본값은 UTF-8입니다.
     * <p>
     * Set how to encode full text. The default is UTF-8.
     */
    Charset encoding() default Charset.UTF_8;

    /**
     * 전문에서 사용 할 패딩 문자를 설정합니다. 기본값은 공백(" ")입니다.
     * <p>
     * Sets the padding character to be used in full text. The default is a space character(" ").
     */
    PaddingCharacter paddingChar() default PaddingCharacter.SPACE;

}
