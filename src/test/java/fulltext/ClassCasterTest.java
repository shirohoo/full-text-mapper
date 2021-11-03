package fulltext;

import static fulltext.enums.ClassCaster.BIG_DECIMAL;
import static fulltext.enums.ClassCaster.DOUBLE;
import static fulltext.enums.ClassCaster.DOUBLE_WRAPPER;
import static fulltext.enums.ClassCaster.INT;
import static fulltext.enums.ClassCaster.INT_WRAPPER;
import static fulltext.enums.ClassCaster.LOCAL_DATE;
import static fulltext.enums.ClassCaster.LOCAL_DATE_TIME;
import static fulltext.enums.ClassCaster.LONG;
import static fulltext.enums.ClassCaster.LONG_WRAPPER;
import static fulltext.enums.ClassCaster.STRING;
import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ClassCasterTest {

    @MethodSource
    @ParameterizedTest
    void function(final String data, final Function<String, ?> function, final Object expected) throws Exception {
        assertThat(function.apply(data)).isEqualTo(expected);
    }

    private static Stream<Arguments> function() {
        return Stream.of(
            Arguments.of("1000", STRING.getFunction(), "1000"),
            Arguments.of("1000", INT.getFunction(), 1000),
            Arguments.of("1000", INT_WRAPPER.getFunction(), 1000),
            Arguments.of("1000", LONG.getFunction(), 1000L),
            Arguments.of("1000", LONG_WRAPPER.getFunction(), 1000L),
            Arguments.of("1000.0", DOUBLE.getFunction(), 1000.0D),
            Arguments.of("1000.0", DOUBLE_WRAPPER.getFunction(), 1000.0D),
            Arguments.of("20201010", LOCAL_DATE.getFunction(), LocalDate.of(2020, 10, 10)),
            Arguments.of("20201010000000", LOCAL_DATE_TIME.getFunction(), LocalDateTime.parse("20201010000000", DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))),
            Arguments.of("1000", BIG_DECIMAL.getFunction(), new BigDecimal("1000"))
        );
    }

}
