package fulltext;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class LineProtocolMapperTest {

    private FullTextMapper mapper = FullTextMapperFactory.getLineFullTextMapper();

    @Test
    void readValue() throws Exception {
        Optional<TestModel> testModel = mapper.readValue(mockData(), TestModel.class);
        assertThat(testModel.get()).isEqualTo(expectedModel());
    }

    private String mockData() {
        return "120211011                                                                                           " +
            "2      siro 28                                                                                      " +
            "3                                                                                                   ";
    }

    private TestModel expectedModel() {
        return TestModel.builder()
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
