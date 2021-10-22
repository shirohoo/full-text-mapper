package fulltext;

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

    /**
     * 이 정적 팩토리 메소드는 기본 생성자를 호출합니다.
     * This static factory method is invoked default constructor.
     * <p>
     * 반환된 인스턴스의 인코딩 유형은 기본적으로 UTF-8로 설정되어 있습니다.
     * returned instance has encoding type set to UTF-8 by default.
     *
     * @return instance of fulltext.FullTextMapper
     */
    public static FullTextMapper create() {
        return new FullTextMapper();
    }

    private FullTextMapper() {
        this(Charset.UTF_8);
    }

    /**
     * 이 팩토리 메서드는 fulltext.Charset을 전달받는 생성자를 호출합니다.
     * This factory method calls the constructor taking fulltext.Charset as an argument.
     * <p>
     * 기본 인코딩은 UTF-8이지만 다른 인코딩을 사용하려면 이 메서드를 호출해야 합니다.
     * The default encoding is UTF-8, but you should invoke this method if you want to use a different encoding.
     *
     * @return instance of fulltext.FullTextMapper
     */
    public static FullTextMapper create(final Charset encode) {
        return new FullTextMapper(encode);
    }

    private FullTextMapper(final Charset encoder) {
        this.encoder = encoder;
    }

    /**
     * 바이트 배열로 구성된 전문을 지정된 인코더로 인코딩 한 후, 전달받은 객체의 각 필드에 바인딩 합니다.
     * After encoding the full text composed of a byte array with the specified encoder, it is binding to each field of the received object.
     * <p>
     * 이 때 각 필드의 선언 타입에 따라 전문의 데이터를 형변환하여 바인딩합니다.
     * At this time, the data of the full text is converted and binding to the declaration type of each field.
     * <p>
     * 데이터 바인딩이 문제없이 완료되면 생성된 인스턴스를 반환합니다.
     * If data binding completes without any problems, return the created instance.
     * <p>
     * 몇가지 예외가 발생할 수 있지만, 예외가 발생 할 경우 로그를 남기고 항상 Optional을 반환합니다.
     * Several exceptions may occur, but when an exception occurs, it logs and always returns Optional.
     *
     * @param bytes 전문에서 읽어온 바이트 배열 타입의 데이터입니다.
     *              Byte array type data read from the full text.
     * @param clazz 전문에서 읽어온 데이터를 바인딩하여 인스턴스화할 클래스입니다.
     *              A class to instantiate by binding data read from the full text.
     * @return
     */
    public <E> Optional<E> readValue(final byte[] bytes, final Class<E> clazz) {
        final String line = convertStr(bytes);
        verify(line, clazz);
        return Optional.ofNullable(parse(line, clazz));
    }

    /**
     * 전달받은 객체의 인스턴스를 생성하고, 전문을 읽어들여 생성한 인스턴스의 각 필드에 바인딩합니다.
     * Creates an instance of the received object, reads the full text, and binds it to each field of the created instance.
     * <p>
     * 이 때 각 필드의 선언 타입에 따라 전문의 데이터를 형변환하여 바인딩합니다.
     * At this time, the data of the full text is converted and binding to the declaration type of each field.
     * <p>
     * 데이터 바인딩이 문제없이 완료되면 생성된 인스턴스를 반환합니다.
     * If data binding completes without any problems, return the created instance.
     * <p>
     * 몇가지 예외가 발생할 수 있지만, 예외가 발생 할 경우 로그를 남기고 항상 Optional을 반환합니다.
     * Several exceptions may occur, but when an exception occurs, it logs and always returns Optional.
     *
     * @param line 전문에서 읽어온 문자열 타입의 데이터입니다.
     *              String type data read from the full text.
     * @param clazz 전문에서 읽어온 데이터를 바인딩하여 인스턴스화할 클래스입니다.
     *              A class to instantiate by binding data read from the full text.
     * @return
     */
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
            errorLogging(e.getMessage());
            return null;
        }
    }

    private <E> E createInstance(final Class<E> clazz) {
        Constructor<E> defaultConstructor = (Constructor<E>) clazz.getDeclaredConstructors()[0]; // Make it work even if the default constructor's access modifier is private
        defaultConstructor.setAccessible(true);
        try {
            return defaultConstructor.newInstance();
        } catch (InstantiationException e) {
            errorLogging(e.getMessage());
            return null;
        } catch (IllegalAccessException e) {
            errorLogging(e.getMessage());
            return null;
        } catch (InvocationTargetException e) {
            errorLogging(e.getMessage());
            return null;
        }
    }

    private String slice(final String line, final int length) {
        return line.substring(0, length).trim();
    }

    private void errorLogging(final String message) {
        log.severe(message + ". returned null.");
    }

}
