package fulltext;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PadCharacterTest {

    @MethodSource
    @ParameterizedTest
    void pad(final PadCharacter padCharacter, final int padLen, final String expected) throws Exception {
        String actual = padCharacter.pad(padLen);
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> pad() {
        return Stream.of(
            Arguments.of(PadCharacter.SPACE, 10, "          "),
            Arguments.of(PadCharacter.ZERO, 10, "0000000000")
        );
    }

    @MethodSource
    @ParameterizedTest
    void leftPad(final PadCharacter padCharacter, String charSequence, final int padLen, final String expected) throws Exception {
        String actual = padCharacter.leftPad(charSequence, padLen);
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> leftPad() {
        return Stream.of(
            Arguments.of(PadCharacter.SPACE, "siro", 5, "     siro"),
            Arguments.of(PadCharacter.ZERO, "siro", 5, "00000siro")
        );
    }

    @MethodSource
    @ParameterizedTest
    void rightPad(final PadCharacter padCharacter, String charSequence, final int padLen, final String expected) throws Exception {
        String actual = padCharacter.rightPad(charSequence, padLen);
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> rightPad() {
        return Stream.of(
            Arguments.of(PadCharacter.SPACE, "siro", 5, "siro     "),
            Arguments.of(PadCharacter.ZERO, "siro", 5, "siro00000")
        );
    }

    @MethodSource
    @ParameterizedTest
    void removeLeft(final PadCharacter padCharacter, final String charSequence, final String expected) throws Exception {
        String actual = padCharacter.removeLeftPad(charSequence);
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> removeLeft() {
        return Stream.of(
            Arguments.of(PadCharacter.SPACE, "    siro", "siro"),
            Arguments.of(PadCharacter.SPACE, " ", ""),
            Arguments.of(PadCharacter.SPACE, "", ""),
            Arguments.of(PadCharacter.ZERO, "00000siro", "siro"),
            Arguments.of(PadCharacter.ZERO, "0", ""),
            Arguments.of(PadCharacter.ZERO, "", "")
        );
    }

    @MethodSource
    @ParameterizedTest
    void removeRightPad(final PadCharacter padCharacter, final String charSequence, final String expected) throws Exception {
        String actual = padCharacter.removeRightPad(charSequence);
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> removeRightPad() {
        return Stream.of(
            Arguments.of(PadCharacter.SPACE, "siro    ", "siro"),
            Arguments.of(PadCharacter.SPACE, " ", ""),
            Arguments.of(PadCharacter.SPACE, "", ""),
            Arguments.of(PadCharacter.ZERO, "siro00000", "siro"),
            Arguments.of(PadCharacter.ZERO, "0", ""),
            Arguments.of(PadCharacter.ZERO, "", "")
        );
    }

}
