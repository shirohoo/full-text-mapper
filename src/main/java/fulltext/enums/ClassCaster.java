package fulltext.enums;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.BiFunction;

public enum ClassCaster {
    STRING(String.class, (data, nonArgs) -> data),
    INT(int.class, (data, nonArgs) -> Integer.valueOf(data)),
    INT_WRAPPER(Integer.class, (data, nonArgs) -> Integer.valueOf(data)),
    LONG(long.class, (data, nonArgs) -> Long.valueOf(data)),
    LONG_WRAPPER(Long.class, (data, nonArgs) -> Long.valueOf(data)),
    DOUBLE(double.class, (data, nonArgs) -> Double.valueOf(data)),
    DOUBLE_WRAPPER(Double.class, (data, nonArgs) -> Double.valueOf(data)),
    LOCAL_DATE(LocalDate.class, (data, format) -> LocalDate.parse(data, DateTimeFormatter.ofPattern(format))),
    LOCAL_DATE_TIME(LocalDateTime.class, (data, format) -> LocalDateTime.parse(data, DateTimeFormatter.ofPattern(format))),
    BIG_DECIMAL(BigDecimal.class, (data, nonArgs) -> new BigDecimal(data)),
    ;

    private final Class<?> clazz;
    private final BiFunction<String, String, ?> function;

    ClassCaster(final Class<?> clazz, final BiFunction<String, String, ?> function) {
        this.clazz = clazz;
        this.function = function;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public BiFunction<String, String, ?> getFunction() {
        return function;
    }
}
