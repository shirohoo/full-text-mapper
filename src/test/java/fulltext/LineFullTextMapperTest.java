package fulltext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import fulltext.fixture.FullTextCreator;
import fulltext.fixture.ModelCreator;
import fulltext.fixture.model.InvalidClassAnnotationModel;
import fulltext.fixture.model.ValidModel;
import fulltext.fixture.model.ValidOptionModel;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class LineFullTextMapperTest {

    private FullTextMapper mapper = FullTextMapperFactory.lineFullTextMapper();

    @Test
    void readValue() throws Exception {
        Optional<ValidModel> actual = mapper.readValue(FullTextCreator.validData(), ValidModel.class);
        assertThat(actual.get()).isEqualTo(ModelCreator.validModel());
    }

    @Test
    void readValue_option() throws Exception {
        Optional<ValidOptionModel> actual = mapper.readValue(FullTextCreator.validOptionData(), ValidOptionModel.class);
        assertThat(actual.get()).isEqualTo(ModelCreator.validOptionModel());
    }

    @Test
    void readValue_exception_1() throws Exception {
        assertThatThrownBy(() -> mapper.readValue(FullTextCreator.validData(), null))
            .hasMessage("Class is must not be null.")
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    void readValue_exception_2() throws Exception {
        assertThatThrownBy(() -> mapper.readValue(FullTextCreator.validData(), InvalidClassAnnotationModel.class))
            .hasMessage("There is a problem with setting the full text object. @FullText: 301, @Field total length: 300")
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void write() throws Exception {
        String actual = mapper.write(ModelCreator.validModel());
        assertThat(actual).isEqualTo(FullTextCreator.validModel());
    }

    @Test
    void write_option() throws Exception {
        String actual = mapper.write(ModelCreator.validOptionModel());
        assertThat(actual).isEqualTo(FullTextCreator.validOptionData());
    }

}
