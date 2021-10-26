package fulltext;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class PadCharacterTest {

    @Test
    void pad_space() throws Exception {
        String pad = PadCharacter.SPACE.pad(10);
        assertThat(pad).isEqualTo("          ");
    }

    @Test
    void pad_zero() throws Exception {
        String pad = PadCharacter.ZERO.pad(10);
        assertThat(pad).isEqualTo("0000000000");
    }

    @Test
    void leftPad_space() throws Exception {
        String data = "siro";
        String actual = PadCharacter.SPACE.leftPad(data, 5);
        assertThat(actual).isEqualTo("     siro");
    }

    @Test
    void leftPad_zero() throws Exception {
        String data = "siro";
        String actual = PadCharacter.ZERO.leftPad(data, 5);
        assertThat(actual).isEqualTo("00000siro");
    }

    @Test
    void remove_space() throws Exception {
        String actual = PadCharacter.SPACE.removeLeftPad("     siro");
        assertThat(actual).isEqualTo("siro");
    }

    @Test
    void remove_zero() throws Exception {
        String actual = PadCharacter.ZERO.removeLeftPad("00000siro");
        assertThat(actual).isEqualTo("siro");
    }

}