package fulltext;

import static org.assertj.core.api.Assertions.assertThat;
import fulltext.model.ValidModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class LineFullTextMapperTest {

    private FullTextMapper mapper = FullTextMapperFactory.getLineFullTextMapper();

    @Test
    void readValue() throws Exception {
        Optional<ValidModel> testModel = mapper.readValue(mockData(), ValidModel.class);
        assertThat(testModel.get()).isEqualTo(expectedModel());
    }

    @Test
    void write() throws Exception {
        String actual = mapper.write(expectedModel());
        assertThat(actual).isEqualTo(
            "120211011                                                                                           "
                + "2      siro 28                                                                                "
                + "      3                                                                                       "
                + "            "
        );
    }

    private String mockData() {
        return "120211011                                                                                           " +
            "2      siro 28                                                                                      " +
            "3                                                                                                   ";
    }

    private ValidModel expectedModel() {
        return ValidModel.builder()
            .headerType("1")
            .createAt(LocalDate.parse("20211011", DateTimeFormatter.BASIC_ISO_DATE))
            .headerPadding("")
            .dataType("2")
            .name("siro")
            .age(28)
            .dataPadding("")
            .trailerType("3")
            .trailerPadding("")
            .build();
    }

}
