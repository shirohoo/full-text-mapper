package fulltext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import fulltext.fixture.FullTextCreator;
import fulltext.fixture.ModelCreator;
import fulltext.fixture.model.InvalidClassAnnotationModel;
import fulltext.fixture.model.UnsupportedAnnotationModel;
import fulltext.fixture.model.ValidModel;
import fulltext.fixture.model.ValidOptionModel;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class LineFullTextMapperTest {

    private FullTextMapper mapper = FullTextMapperFactory.lineFullTextMapper();

    @Test
    void readValue() throws Exception {
        Optional<ValidModel> actual = mapper.readValue(FullTextCreator.VALID_DATA, ValidModel.class);
        assertThat(actual.get()).isEqualTo(ModelCreator.VALID_MODEL);
    }

    @Test
    void readValue_option() throws Exception {
        Optional<ValidOptionModel> actual = mapper.readValue(FullTextCreator.VALID_OPTION_DATA, ValidOptionModel.class);
        assertThat(actual.get()).isEqualTo(ModelCreator.VALID_OPTION_MODEL);
    }

    @Test
    void readValue_exception_1() throws Exception {
        assertThatThrownBy(() -> mapper.readValue(FullTextCreator.VALID_DATA, null))
            .hasMessage("Class is must not be null.")
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    void readValue_exception_2() throws Exception {
        assertThatThrownBy(() -> mapper.readValue(FullTextCreator.VALID_DATA, InvalidClassAnnotationModel.class))
            .hasMessage("There is a problem with setting the full text object. @FullText: 301, @Field total length: 300")
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void readValue_exception_3() throws Exception {
        assertThatThrownBy(() -> mapper.readValue(FullTextCreator.VALID_DATA, UnsupportedAnnotationModel.class))
            .hasMessageMatching("Both @FullText and @Field can't be [a-zA-Z]*.NONE")
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void write() throws Exception {
        String actual = mapper.write(ModelCreator.VALID_MODEL);
        assertThat(actual).isEqualTo(FullTextCreator.VALID_DATA);
    }

    @Test
    void write_option() throws Exception {
        String actual = mapper.write(ModelCreator.VALID_OPTION_MODEL);
        assertThat(actual).isEqualTo(FullTextCreator.VALID_OPTION_DATA);
    }

}
