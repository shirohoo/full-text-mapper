package fulltext;

import static java.lang.Math.max;
import static java.util.Arrays.stream;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum PadCharacter {
    SPACE(" "),
    ZERO("0");

    private static final Map<PadCharacter, String> MAP = stream(values())
        .collect(Collectors.toUnmodifiableMap(Function.identity(), PadCharacter::getCharacter));

    private final String character;

    PadCharacter(final String character) {
        this.character = character;
    }

    public String getCharacter() {
        return character;
    }

    public String pad(final int len) {
        return new StringBuilder()
            .append(character.repeat(max(0, len)))
            .toString();
    }

    public String leftPad(final String data, final int len) {
        return pad(len) + data;
    }

    public String removeLeftPad(final String data) {
        return data.replaceFirst("^" + character + "*", "");
    }
}
