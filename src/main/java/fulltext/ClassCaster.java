package fulltext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public enum ClassCaster {

    STRING(String.class, data -> data),
    INT(int.class, Integer::valueOf),
    INT_WRAPPER(Integer.class, Integer::valueOf),
    LONG(long.class, Long::valueOf),
    LONG_WRAPPER(Long.class, Long::valueOf),
    DOUBLE(double.class, Double::valueOf),
    DOUBLE_WRAPPER(Double.class, Double::valueOf),
    LOCAL_DATE(LocalDate.class, data -> LocalDate.parse(data, DateTimeFormatter.BASIC_ISO_DATE)),
    LOCAL_DATE_TIME(LocalDateTime.class, data -> LocalDateTime.parse(data, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))),
    BIG_DECIMAL(BigDecimal.class, BigDecimal::new);

    private final Class<?> clazz;
    private final Function<String, ?> castFunction;

    ClassCaster(final Class<?> clazz, final Function<String, ?> castFunction) {
        this.clazz = clazz;
        this.castFunction = castFunction;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Function<String, ?> castFunction() {
        return castFunction;
    }

}
