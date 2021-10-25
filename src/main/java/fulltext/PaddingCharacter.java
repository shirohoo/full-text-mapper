package fulltext;

import static java.util.Arrays.stream;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum PaddingCharacter {
    SPACE(" ");

    private static final Map<PaddingCharacter, String> MAP = stream(values())
        .collect(Collectors.toUnmodifiableMap(Function.identity(), PaddingCharacter::getCharacter));

    private final String character;

    PaddingCharacter(final String character) {
        this.character = character;
    }

    private String getCharacter() {
        return character;
    }

    public static String findBy(final PaddingCharacter charset) {
        if (!MAP.containsKey(charset)) {
            throw new NoSuchElementException();
        }
        return MAP.get(charset);
    }
}
