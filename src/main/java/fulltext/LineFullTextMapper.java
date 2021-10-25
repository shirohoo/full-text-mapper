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
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * {@link LineFullTextMapper} 는 전문과 객체를 서로 매핑합니다.
 * <p>
 * 사용하기 위해서는 매핑하려는 Object에 @FullText와 @Protocol이 알맞게 선언돼있어야 합니다.
 * <p>
 * FullTextMapper mapping full text and Object to each other. In order to use it, {@link FullText} and, {@link Protocol} must be properly declared in the object to be mapped.
 */
public final class LineFullTextMapper implements FullTextMapper {

    private final static Logger log = Logger.getGlobal();

    /**
     * 이 정적 팩토리 메소드는 기본 생성자를 호출합니다.
     * <p>
     * This static factory method is invoked default constructor.
     * <p>
     *
     * @return instance of fulltext.FullTextMapper
     */
    public static LineFullTextMapper newInstance() {
        return new LineFullTextMapper();
    }

    private LineFullTextMapper() {
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
     * 몇가지 예외가 발생할 수 있지만, 예외가 발생 할 경우 항상 에러 로그와 함께 Optional을 반환합니다.
     * <p>
     * Several exceptions may occur, but when an exception occurs, an Optional is always returned with an error log.
     * <p>
     *
     * @param bytes 전문에서 읽어온 바이트 배열 타입의 데이터입니다. Byte array type data read from the full text.
     * @param clazz 전문에서 읽어온 데이터를 바인딩하여 인스턴스화할 클래스입니다. A class to instantiate by binding data read from the full text.
     * @return instance of Optional{@literal <}T>
     */
    public <T> Optional<T> readValue(final byte[] bytes, final Class<T> clazz) {
        final String line = convertStr(bytes, getFullText(clazz).encoding());
        verify(line, clazz);
        return Optional.ofNullable(parse(line, clazz));
    }

    private String convertStr(final byte[] bytes, Charset encoder) {
        try {
            return new String(bytes, Charset.findBy(encoder));
        } catch (UnsupportedEncodingException e) {
            // I'm sure it will never happen. so didn't do anything.
            return null;
        }
    }

    private <T> FullText getFullText(final Class<T> clazz) {
        final FullText annotation = clazz.getAnnotation(FullText.class);
        if (isNull(annotation)) {
            throw new NoSuchElementException("Could not find @FullText in argument object. please add @FullText at class level.");
        }
        return annotation;
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
     * @return instance of Optional{@literal <}T>
     */
    public <T> Optional<T> readValue(final String line, final Class<T> clazz) {
        verify(line, clazz);
        return Optional.ofNullable(parse(line, clazz));
    }

    private <T> void verify(final String line, final Class<T> clazz) {
        Objects.requireNonNull(line, "line is must not be null.");
        Objects.requireNonNull(clazz, "clazz is must not be null.");

        final int lineLength = line.length();
        final int fullTextTotalLength = getFullText(clazz).totalLength();
        final int protocolTotalLength = getProtocolTotalLength(clazz);

        if (fullTextTotalLength != protocolTotalLength) {
            throw new IllegalArgumentException(format(
                "There is a problem with setting the full text object. @FullText: %d, @Protocol total length: %d",
                fullTextTotalLength, protocolTotalLength
            ));
        }

        if (lineLength != fullTextTotalLength) {
            throw new IllegalArgumentException(format(
                "The full text specification and the information in the argument object do not match. full text length: %d, @FullText length: %d",
                lineLength, fullTextTotalLength
            ));
        }

        if (lineLength != protocolTotalLength) {
            throw new IllegalArgumentException(format(
                "The full text specification and the information in the argument object do not match. full text length: %d, @Protocol total length: %d",
                lineLength, protocolTotalLength
            ));
        }
    }

    private <T> int getProtocolTotalLength(final Class<T> clazz) {
        return stream(clazz.getDeclaredFields())
            .map(field -> field.getAnnotation(Protocol.class))
            .map(Protocol::length)
            .reduce(0, Integer::sum);
    }

    private <T> T parse(String line, final Class<T> clazz) {
        final T type = newInstance(clazz);
        return isNull(type) ? null : dataBind(line, clazz, type);
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

    private <T> T dataBind(String line, final Class<T> clazz, final T type) {
        try {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                int length = field.getAnnotation(Protocol.class).length();
                Class<?> classType = field.getType();

                if (classType.equals(String.class)) {
                    String sliceData = slice(line, length);
                    field.set(type, sliceData);
                    line = line.substring(length);
                } else if (classType.equals(int.class) || classType.equals(Integer.class)) {
                    String sliceData = slice(line, length);
                    field.set(type, Integer.valueOf(sliceData));
                    line = line.substring(length);
                } else if (classType.equals(long.class) || classType.equals(Long.class)) {
                    String sliceData = slice(line, length);
                    field.set(type, Long.valueOf(sliceData));
                    line = line.substring(length);
                } else if (classType.equals(double.class) || classType.equals(Double.class)) {
                    String sliceData = slice(line, length);
                    field.set(type, Double.valueOf(sliceData));
                    line = line.substring(length);
                } else if (classType.equals(LocalDate.class)) {
                    String sliceData = slice(line, length);
                    field.set(type, LocalDate.parse(sliceData, DateTimeFormatter.BASIC_ISO_DATE));
                    line = line.substring(length);
                } else if (classType.equals(LocalDateTime.class)) {
                    String sliceData = slice(line, length);
                    field.set(type, LocalDate.parse(sliceData, DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
                    line = line.substring(length);
                } else if (classType.equals(BigDecimal.class)) {
                    String sliceData = slice(line, length);
                    field.set(type, new BigDecimal(sliceData));
                    line = line.substring(length);
                }
            }
            if (line.length() != 0) {
                errorLogging("Parsing has been completed. but remaining data exists. current data: " + line);
                return null;
            }
            return type;
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
