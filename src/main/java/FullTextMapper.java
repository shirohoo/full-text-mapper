import static java.lang.String.format;
import static java.util.Arrays.stream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class FullTextMapper {

    private final Charset encode;

    private FullTextMapper() {
        this(Charset.UTF_8);
    }

    private FullTextMapper(final Charset encode) {
        this.encode = encode;
    }

    public static FullTextMapper create() {
        return new FullTextMapper();
    }

    public static FullTextMapper create(final Charset encode) {
        return new FullTextMapper(encode);
    }

    public <E> E readValue(String line, final Class<E> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        verify(line, clazz);
        return parse(line, clazz);
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

    private <E> E parse(String line, final Class<E> clazz) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        final E ele = createInstance(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            int length = field.getAnnotation(FullText.class).length();
            Class<?> classType = field.getType();
            if (classType.equals(String.class)) {
                String data = line.substring(0, length).trim();
                line = line.substring(length);
                field.set(ele, data);
            } else if (classType.equals(int.class) || classType.equals(Integer.class)) {
                String data = line.substring(0, length).trim();
                line = line.substring(length);
                field.set(ele, Integer.valueOf(data));
            } else if (classType.equals(long.class) || classType.equals(Long.class)) {
                String data = line.substring(0, length).trim();
                line = line.substring(length);
                field.set(ele, Long.valueOf(data));
            } else if (classType.equals(double.class) || classType.equals(Double.class)) {
                String data = line.substring(0, length).trim();
                line = line.substring(length);
                field.set(ele, Double.valueOf(data));
            } else if (classType.equals(LocalDate.class)) {
                String data = line.substring(0, length).trim();
                line = line.substring(length);
                field.set(ele, LocalDate.parse(data, DateTimeFormatter.BASIC_ISO_DATE));
            } else if (classType.equals(LocalDateTime.class)) {
                String data = line.substring(0, length).trim();
                line = line.substring(length);
                field.set(ele, LocalDate.parse(data, DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
            } else if (classType.equals(BigDecimal.class)) {
                String data = line.substring(0, length).trim();
                line = line.substring(length);
                field.set(ele, new BigDecimal(data));
            }
        }
        if (line.length() != 0) {
            throw new IllegalStateException("Parsing exception. remaining data exists. current data: " + line);
        }
        return ele;
    }

    private <E> E createInstance(final Class<E> clazz) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<E> defaultConstructor = (Constructor<E>) clazz.getDeclaredConstructors()[0]; // Make it work even if the default constructor's access modifier is private
        defaultConstructor.setAccessible(true);
        return defaultConstructor.newInstance();
    }

}
