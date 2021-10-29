package fulltext.annotation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import fulltext.enums.Charset;
import fulltext.enums.PadCharacter;
import fulltext.enums.PadPosition;
import fulltext.model.InvalidClassAnnotationModel;
import fulltext.model.NoClassAnnotationModel;
import fulltext.model.ValidModel;
import fulltext.model.ValidOptionModel;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class FullTextReflectorTest {

    @Test
    void findClassAnnotation() throws Exception {
        assertThat(FullTextReflector.findClassAnnotation(ValidModel.class)).isNotNull();
    }

    @Test
    void findClassAnnotation_NullPointerException() throws Exception {
        assertThatThrownBy(() -> FullTextReflector.findClassAnnotation(null))
            .hasMessage("Class must not be null.")
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    void findClassAnnotation_NoSuchElementException() throws Exception {
        assertThatThrownBy(() -> FullTextReflector.findClassAnnotation(NoClassAnnotationModel.class))
            .hasMessage("Could not find @FullText in argument object. please add @FullText at class level.")
            .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void findFieldAnnotation() throws Exception {
        final java.lang.reflect.Field field = validField();
        assertThat(FullTextReflector.findFieldAnnotation(field)).isNotNull();
    }

    @Test
    void findFieldAnnotation_NullPointerException() throws Exception {
        assertThatThrownBy(() -> FullTextReflector.findFieldAnnotation(null))
            .hasMessage("Field must not be null.")
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    void findFieldAnnotation_NoSuchElementException() throws Exception {
        final java.lang.reflect.Field invalidField = invalidField();
        assertThatThrownBy(() -> FullTextReflector.findFieldAnnotation(invalidField))
            .hasMessage("Could not find @Field in argument object. please add @Field at field level.")
            .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void getEncoding() throws Exception {
        final Charset encoding = FullTextReflector.getEncoding(ValidModel.class);
        assertThat(encoding).isEqualTo(Charset.UTF_8);
    }

    @Test
    void getLength_Class() throws Exception {
        assertThat(FullTextReflector.getLength(ValidModel.class)).isEqualTo(300);
    }

    @Test
    void getLength_Field() throws Exception {
        assertThat(FullTextReflector.getLength(validField())).isEqualTo(1);
    }

    @Test
    void getPadCharacter() throws Exception {
        // given
        final FullText classAnnotation = FullTextReflector.findClassAnnotation(ValidOptionModel.class);
        final Field fieldAnnotation = FullTextReflector.findFieldAnnotation(validOptionAgeField());

        // when
        final PadCharacter padCharacter = FullTextReflector.getPadCharacter(classAnnotation, fieldAnnotation);

        // then
        assertThat(padCharacter).isEqualTo(PadCharacter.ZERO);
    }

    @Test
    void getPadPosition() throws Exception {
        // given
        final FullText classAnnotation = FullTextReflector.findClassAnnotation(ValidOptionModel.class);
        final Field fieldAnnotation = FullTextReflector.findFieldAnnotation(validOptionNameField());

        // when
        final PadPosition padPosition = FullTextReflector.getPadPosition(classAnnotation, fieldAnnotation);

        // then
        assertThat(padPosition).isEqualTo(PadPosition.RIGHT_PAD);
    }

    @Test
    void verifyAnnotation_NullPointerException() throws Exception {
        assertThatThrownBy(() -> FullTextReflector.verifyAnnotation(null))
            .hasMessage("Class is must not be null.")
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    void verifyAnnotation_IllegalArgumentException() throws Exception {
        assertThatThrownBy(() -> FullTextReflector.verifyAnnotation(InvalidClassAnnotationModel.class))
            .hasMessageMatching("There is a problem with setting the full text object. @FullText: [0-9]*, @Field total length: [0-9]*")
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static java.lang.reflect.Field validField() throws Exception {
        final Class<?> clazz = Class.forName("fulltext.model.ValidModel");
        return clazz.getDeclaredField("headerType");
    }

    private static java.lang.reflect.Field validOptionAgeField() throws Exception {
        final Class<?> clazz = Class.forName("fulltext.model.ValidOptionModel");
        return clazz.getDeclaredField("age");
    }

    private static java.lang.reflect.Field validOptionNameField() throws Exception {
        final Class<?> clazz = Class.forName("fulltext.model.ValidOptionModel");
        return clazz.getDeclaredField("name");
    }

    private static java.lang.reflect.Field invalidField() throws Exception {
        final Class<?> clazz = Class.forName("fulltext.model.NoFieldAnnotationModel");
        return clazz.getDeclaredField("headerType");
    }

}