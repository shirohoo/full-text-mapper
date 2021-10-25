package fulltext;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Objects.isNull;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * {@link LineFullTextMapper} 는 전문과 객체를 서로 매핑합니다.
 * <p>
 * 사용하기 위해서는 매핑하려는 Object에 @FullText가 알맞게 선언돼있어야 합니다.
 * <p>
 * FullTextMapper mapping full text and Object to each other. In order to use it, {@link FullText} must be properly declared in the object to be mapped.
 */
public final class LineFullTextMapper implements FullTextMapper{

    private final static Logger log = Logger.getGlobal();

    private final Charset encoder;

    /**
     * 이 정적 팩토리 메소드는 기본 생성자를 호출합니다.
     * <p>
     * This static factory method is invoked default constructor.
     * <p>
     * <p>
     * 반환된 인스턴스의 인코딩 유형은 기본적으로 UTF-8로 설정되어 있습니다.
     * <p>
     * returned instance has encoding type set to UTF-8 by default.
     * <p>
     *
     * @return instance of fulltext.FullTextMapper
     */
    public static LineFullTextMapper create() {
        return new LineFullTextMapper();
    }

    private LineFullTextMapper() {
        this(Charset.UTF_8);
    }

    /**
     * 이 팩토리 메서드는 fulltext.Charset을 전달받는 생성자를 호출합니다.
     * <p>
     * This factory method calls the constructor taking fulltext.Charset as an argument.
     * <p>
     * <p>
     * 기본 인코딩은 UTF-8이지만 다른 인코딩을 사용하려면 이 메서드를 호출해야 합니다.
     * <p>
     * The default encoding is UTF-8, but you should invoke this method if you want to use a different encoding.
     * <p>
     *
     * @return instance of fulltext.FullTextMapper
     */
    public static LineFullTextMapper create(final Charset encode) {
        return new LineFullTextMapper(encode);
    }

    private LineFullTextMapper(final Charset encoder) {
        this.encoder = encoder;
    }

    /**
     * 바이트 배열로 구성된 전문을 지정된 인코더로 인코딩 한 후, 전달받은 객체의 각 필드에 바인딩 합니다.
     * <p>
     * After encoding the full text composed of a byte array with the specified encoder, it is binding to each field of the received object.
     * <p>
     * <p>
     * 이 때 각 필드의 선언 타입에 따라 전문의 데이터를 형변환하여 바인딩합니다.
     * <p>
     * At this time, the data of the full text is converted and binding to the declaration type of each field.
     * <p>
     * <p>
     * 데이터 바인딩이 문제없이 완료되면 생성된 인스턴스를 반환합니다.
     * <p>
     * If data binding completes without any problems, return the created instance.
     * <p>
     * <p>
     * 몇가지 예외가 발생할 수 있지만, 예외가 발생 할 경우 로그를 남기고 항상 Optional을 반환합니다.
     * <p>
     * Several exceptions may occur, but when an exception occurs, it logs and always returns Optional.
     * <p>
     *
     * @param bytes 전문에서 읽어온 바이트 배열 타입의 데이터입니다. Byte array type data read from the full text.
     * @param clazz 전문에서 읽어온 데이터를 바인딩하여 인스턴스화할 클래스입니다. A class to instantiate by binding data read from the full text.
     * @return
     */
    public <T> Optional<T> readLine(final byte[] bytes, final Class<T> clazz) {
        final String line = convertStr(bytes);
        verify(line, clazz);
        return Optional.ofNullable(parse(line, clazz));
    }

    /**
     * 전달받은 객체의 인스턴스를 생성하고, 전문을 읽어들여 생성한 인스턴스의 각 필드에 바인딩합니다.
     * <p>
     * Creates an instance of the received object, reads the full text, and binds it to each field of the created instance.
     * <p>
     * <p>
     * 이 때 각 필드의 선언 타입에 따라 전문의 데이터를 형변환하여 바인딩합니다.
     * <p>
     * At this time, the data of the full text is converted and binding to the declaration type of each field.
     * <p>
     * <p>
     * 데이터 바인딩이 문제없이 완료되면 생성된 인스턴스를 반환합니다.
     * <p>
     * If data binding completes without any problems, return the created instance.
     * <p>
     * <p>
     * 몇가지 예외가 발생할 수 있지만, 예외가 발생 할 경우 로그를 남기고 항상 Optional을 반환합니다.
     * <p>
     * Several exceptions may occur, but when an exception occurs, it logs and always returns Optional.
     * <p>
     *
     * @param line  전문에서 읽어온 문자열 타입의 데이터입니다. String type data read from the full text.
     * @param clazz 전문에서 읽어온 데이터를 바인딩하여 인스턴스화할 클래스입니다. A class to instantiate by binding data read from the full text.
     * @return
     */
    public <T> Optional<T> readLine(final String line, final Class<T> clazz) {
        verify(line, clazz);
        return Optional.ofNullable(parse(line, clazz));
    }

    private String convertStr(final byte[] bytes) {
        try {
            return new String(bytes, Charset.findBy(encoder));
        } catch (UnsupportedEncodingException e) {
            // I'm sure it will never happen. so didn't do anything.
            return null;
        }
    }

    private <T> void verify(final String line, final Class<T> clazz) {
        Objects.requireNonNull(line, "line is must not be null.");
        Objects.requireNonNull(clazz, "clazz is must not be null.");
        final int lineLength = line.length();
        final int definedTotalLength = getDefinedTotalLength(clazz);
        if (lineLength != definedTotalLength) {
            throw new IllegalArgumentException(format(
                "The full text specification and the information in the argument object do not match. full text length: %d, length defined in object: %d",
                lineLength, definedTotalLength
            ));
        }
    }

    private <T> int getDefinedTotalLength(final Class<T> clazz) {
        return stream(clazz.getDeclaredFields())
            .map(field -> field.getAnnotation(FullText.class))
            .map(FullText::length)
            .reduce(0, Integer::sum);
    }

    private <T> T parse(String line, final Class<T> clazz) {
        final T ele = newInstance(clazz);
        return isNull(ele) ? null : dataBind(line, clazz, ele);
    }

    private <T> T newInstance(final Class<T> clazz) {
        try {
            return invokeConstructor(clazz);
        } catch (Exception e) {
            errorLogging(e.getMessage());
            return null;
        }
    }

    private <T> T invokeConstructor(final Class<T> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<T> defaultConstructor = (Constructor<T>) clazz.getDeclaredConstructors()[0]; // Make it work even if the default constructor's access modifier is private
        defaultConstructor.setAccessible(true);
        return defaultConstructor.newInstance();
    }

    private <T> T dataBind(String line, final Class<T> clazz, final T ele) {
        try {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                int length = field.getAnnotation(FullText.class).length();
                Class<?> classType = field.getType();

                if (classType.equals(String.class)) {
                    String sliceData = slice(line, length);
                    field.set(ele, sliceData);
                    line = line.substring(length);
                } else if (classType.equals(int.class) || classType.equals(Integer.class)) {
                    String sliceData = slice(line, length);
                    field.set(ele, Integer.valueOf(sliceData));
                    line = line.substring(length);
                } else if (classType.equals(long.class) || classType.equals(Long.class)) {
                    String sliceData = slice(line, length);
                    field.set(ele, Long.valueOf(sliceData));
                    line = line.substring(length);
                } else if (classType.equals(double.class) || classType.equals(Double.class)) {
                    String sliceData = slice(line, length);
                    field.set(ele, Double.valueOf(sliceData));
                    line = line.substring(length);
                } else if (classType.equals(LocalDate.class)) {
                    String sliceData = slice(line, length);
                    field.set(ele, LocalDate.parse(sliceData, DateTimeFormatter.BASIC_ISO_DATE));
                    line = line.substring(length);
                } else if (classType.equals(LocalDateTime.class)) {
                    String sliceData = slice(line, length);
                    field.set(ele, LocalDate.parse(sliceData, DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
                    line = line.substring(length);
                } else if (classType.equals(BigDecimal.class)) {
                    String sliceData = slice(line, length);
                    field.set(ele, new BigDecimal(sliceData));
                    line = line.substring(length);
                }
            }
            if (line.length() != 0) {
                errorLogging("Parsing has been completed. but remaining data exists. current data: " + line);
                return null;
            }
            return ele;
        } catch (IllegalAccessException e) {
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
