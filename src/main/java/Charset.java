import static java.util.Arrays.stream;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Charset {

    UTF_8("UTF-8"),
    EUC_KR("EUC-KR"),
    ;

    private static final Map<Charset, String> MAP = stream(values())
        .collect(Collectors.toUnmodifiableMap(Function.identity(), Charset::getEncoding));

    private final String encoding;

    Charset(final String encoding) {
        this.encoding = encoding;
    }

    private String getEncoding() {
        return encoding;
    }

    public static String findBy(final Charset charset) {
        if (!MAP.containsKey(charset)) {
            throw new NoSuchElementException();
        }
        return MAP.get(charset);
    }
}
