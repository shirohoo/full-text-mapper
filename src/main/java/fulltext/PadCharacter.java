package fulltext;

import static java.util.Arrays.stream;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum PadCharacter {
    SPACE(" ");

    private static final Map<PadCharacter, String> MAP = stream(values())
        .collect(Collectors.toUnmodifiableMap(Function.identity(), PadCharacter::getCharacter));

    private final String character;

    PadCharacter(final String character) {
        this.character = character;
    }

    private String getCharacter() {
        return character;
    }

    public static String findBy(final PadCharacter charset) {
        if (!MAP.containsKey(charset)) {
            throw new NoSuchElementException();
        }
        return MAP.get(charset);
    }
}
