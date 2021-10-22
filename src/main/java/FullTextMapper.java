import static java.lang.String.format;
import static java.util.Arrays.stream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.logging.Logger;

public final class FullTextMapper {

    private final static Logger log = Logger.getGlobal();

    private final Charset encoder;

    private FullTextMapper() {
        this(Charset.UTF_8);
    }

    private FullTextMapper(final Charset encoder) {
        this.encoder = encoder;
    }

    public static FullTextMapper create() {
        return new FullTextMapper();
    }

    public static FullTextMapper create(final Charset encode) {
        return new FullTextMapper(encode);
    }

    public <E> Optional<E> readValue(final byte[] bytes, final Class<E> clazz) {
        final String line = convertStr(bytes);
        verify(line, clazz);
        return Optional.ofNullable(parse(line, clazz));
    }

    public <E> Optional<E> readValue(final String line, final Class<E> clazz) {
        verify(line, clazz);
        return Optional.ofNullable(parse(line, clazz));
    }

    private String convertStr(final byte[] bytes) {
        String line = "";
        try {
            line = new String(bytes, Charset.findBy(encoder));
        } catch (UnsupportedEncodingException e) {
            // I'm sure it will never happen. so didn't do anything.
        }
        return line;
    }

    private <E> void verify(final String line, final Class<E> clazz) {
        final int lineLength = line.length();
        final int definedTotalLength = getDefinedTotalLength(clazz);
        if (lineLength != definedTotalLength) {
            throw new IllegalArgumentException(format(
                "The full text specification and the information in the argument object do not match. full text length: %d, length defined in object: %d",
                lineLength, definedTotalLength
            ));
        }
    }

    private <E> int getDefinedTotalLength(final Class<E> clazz) {
        return stream(clazz.getDeclaredFields())
            .map(field -> field.getAnnotation(FullText.class))
            .map(FullText::length)
            .reduce(0, Integer::sum);
    }

    private <E> E parse(String line, final Class<E> clazz) {
        try {
            final E ele = createInstance(clazz);
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                int length = field.getAnnotation(FullText.class).length();
                Class<?> classType = field.getType();

                if (classType.equals(String.class)) {
                    field.set(ele, slice(line, length));
                    line = line.substring(length);
                } else if (classType.equals(int.class) || classType.equals(Integer.class)) {
                    field.set(ele, Integer.valueOf(slice(line, length)));
                    line = line.substring(length);
                } else if (classType.equals(long.class) || classType.equals(Long.class)) {
                    field.set(ele, Long.valueOf(slice(line, length)));
                    line = line.substring(length);
                } else if (classType.equals(double.class) || classType.equals(Double.class)) {
                    field.set(ele, Double.valueOf(slice(line, length)));
                    line = line.substring(length);
                } else if (classType.equals(LocalDate.class)) {
                    field.set(ele, LocalDate.parse(slice(line, length), DateTimeFormatter.BASIC_ISO_DATE));
                    line = line.substring(length);
                } else if (classType.equals(LocalDateTime.class)) {
                    field.set(ele, LocalDate.parse(slice(line, length), DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
                    line = line.substring(length);
                } else if (classType.equals(BigDecimal.class)) {
                    field.set(ele, new BigDecimal(slice(line, length)));
                    line = line.substring(length);
                }
            }
            if (line.length() != 0) {
                throw new IllegalArgumentException("Parsing has been completed. but remaining data exists. current data: " + line);
            }
            return ele;
        } catch (IllegalAccessException e) {
            log.severe(e.getMessage() + ". returned null.");
            return null;
        }
    }

    private <E> E createInstance(final Class<E> clazz) {
        Constructor<E> defaultConstructor = (Constructor<E>) clazz.getDeclaredConstructors()[0]; // Make it work even if the default constructor's access modifier is private
        defaultConstructor.setAccessible(true);
        try {
            return defaultConstructor.newInstance();
        } catch (InstantiationException e) {
            log.severe(e.getMessage() + ". returned null.");
            return null;
        } catch (IllegalAccessException e) {
            log.severe(e.getMessage() + ". returned null.");
            return null;
        } catch (InvocationTargetException e) {
            log.severe(e.getMessage() + ". returned null.");
            return null;
        }
    }

    private String slice(final String line, final int length) {
        return line.substring(0, length).trim();
    }

}
