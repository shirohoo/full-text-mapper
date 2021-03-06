package fulltext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import fulltext.exception.RuleViolationException;
import fulltext.fixture.FullTextCreator;
import fulltext.fixture.ModelCreator;
import fulltext.fixture.model.InvalidClassAnnotationModel;
import fulltext.fixture.model.LocalDate1Model;
import fulltext.fixture.model.LocalDate2Model;
import fulltext.fixture.model.LocalDateTime1Model;
import fulltext.fixture.model.LocalDateTime2Model;
import fulltext.fixture.model.NoConstructorModel;
import fulltext.fixture.model.NumbersBinding;
import fulltext.fixture.model.UnsupportedAnnotationModel;
import fulltext.fixture.model.ValidModel;
import fulltext.fixture.model.ValidOptionModel;
import org.junit.jupiter.api.Test;

class LineFullTextMapperTest {
    FullTextMapper mapper = FullTextMapperFactory.lineFullTextMapper();

    @Test
    void readValue() throws Exception {
        ValidModel actual = mapper.readValue(FullTextCreator.VALID_DATA, ValidModel.class);
        assertThat(actual).isEqualTo(ModelCreator.VALID_MODEL);
    }

    @Test
    void readValue_option() throws Exception {
        ValidOptionModel actual = mapper.readValue(FullTextCreator.VALID_OPTION_DATA, ValidOptionModel.class);
        assertThat(actual).isEqualTo(ModelCreator.VALID_OPTION_MODEL);
    }

    @Test
    void readValue_numbers() throws Exception {
        NumbersBinding actual = mapper.readValue(FullTextCreator.NUMBERS_DATA, NumbersBinding.class);
        assertAll(
            () -> assertThat(actual.getIntValue()).isEqualTo(0),
            () -> assertThat(actual.getLongValue()).isEqualTo(0),
            () -> assertThat(actual.getDoubleValue()).isEqualTo(0)
        );
    }

    @Test
    void readValue_localDate1() throws Exception {
        LocalDate1Model actual = mapper.readValue(FullTextCreator.LOCAL_DATE1, LocalDate1Model.class);
        assertThat(actual.getLocalDate()).isEqualTo("2020-12-31");
    }

    @Test
    void readValue_localDate2() throws Exception {
        LocalDate2Model actual = mapper.readValue(FullTextCreator.LOCAL_DATE2, LocalDate2Model.class);
        assertThat(actual.getLocalDate()).isEqualTo("2020-12-31");
    }

    @Test
    void readValue_localDateTime1() throws Exception {
        LocalDateTime1Model actual = mapper.readValue(FullTextCreator.LOCAL_DATE_TIME1, LocalDateTime1Model.class);
        assertThat(actual.getLocalDateTime()).isEqualTo("2020-12-31T12:00");
    }

    @Test
    void readValue_localDateTime2() throws Exception {
        LocalDateTime2Model actual = mapper.readValue(FullTextCreator.LOCAL_DATE_TIME2, LocalDateTime2Model.class);
        assertThat(actual.getLocalDateTime()).isEqualTo("2020-12-31T12:00");
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
    void readValue_exception_4() throws Exception {
        assertThatThrownBy(() -> mapper.readValue(FullTextCreator.VALID_DATA, NoConstructorModel.class))
            .isInstanceOf(RuleViolationException.class)
            .hasMessage("No suitable constructor. Make sure fulltext.fixture.model.NoConstructorModel.<init>() has a default constructor");
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
