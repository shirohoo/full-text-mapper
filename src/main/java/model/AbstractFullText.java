package model;

import static java.util.Arrays.stream;
import annotation.FullText;
import java.util.Objects;
import java.util.function.Predicate;
import type.RecordType;

public abstract class AbstractFullText<T extends AbstractFullText> {

    protected final Class<T> clazz;

    protected final int rowSize;

    public AbstractFullText(final Class<T> clazz, final int rowSize) {
        this.clazz = clazz;
        this.rowSize = rowSize;
    }

    public int remainingSize(final int currentDataSize) {
        final int remainingSize = this.rowSize - currentDataSize;
        if (remainingSize < 0) {
            throw new IllegalStateException(String.format(
                "Unable to get remaining size. The entered size exceeds the total size. total size: %d. but, entered size: %d", rowSize, remainingSize
            ));
        }
        return remainingSize;
    }

    public int sizeOf(final RecordType type) {
        return stream(clazz.getDeclaredFields())
            .map(declaredField -> declaredField.getAnnotation(FullText.class))
            .filter(Objects::nonNull)
            .filter(equals(type))
            .map(FullText::size)
            .reduce(0, Integer::sum);
    }

    private Predicate<FullText> equals(final RecordType type) {
        return annotation -> Objects.equals(annotation.recordType(), type);
    }

}
