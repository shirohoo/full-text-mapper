package fulltext.enums;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PadPositionTest {
    @MethodSource
    @ParameterizedTest
    void isNone(final PadPosition PadPosition, final boolean expected) throws Exception {
        assertThat(PadPosition.isNone()).isEqualTo(expected);
    }

    private static Stream<Arguments> isNone() {
        return Stream.of(
            Arguments.of(PadPosition.NONE, true),
            Arguments.of(PadPosition.LEFT, false),
            Arguments.of(PadPosition.RIGHT, false)
        );
    }

    @MethodSource
    @ParameterizedTest
    void isLeft(final PadPosition PadPosition, final boolean expected) throws Exception {
        assertThat(PadPosition.isLeft()).isEqualTo(expected);
    }

    private static Stream<Arguments> isLeft() {
        return Stream.of(
            Arguments.of(PadPosition.NONE, false),
            Arguments.of(PadPosition.LEFT, true),
            Arguments.of(PadPosition.RIGHT, false)
        );
    }
}
