package model;

import static java.util.Arrays.stream;
import annotation.Data;
import annotation.Header;
import annotation.Trailer;
import java.util.Objects;

public abstract class AbstractFullText<T extends AbstractFullText> {

    protected final Class<T> clazz;

    protected final int rowSize;

    public AbstractFullText(final Class<T> clazz, final int rowSize) {
        this.clazz = clazz;
        this.rowSize = rowSize;
    }

    public int remainingSize(final int size) {
        final int remainingSize = this.rowSize - size;
        if (remainingSize < 0) {
            throw new IllegalStateException(String.format(
                "Unable to get remaining size. The entered size exceeds the total size. total size: %d. but, entered size: %d", rowSize, remainingSize
            ));
        }
        return remainingSize;
    }

    public int headerSize() {
        return stream(clazz.getDeclaredFields())
            .map(declaredField -> declaredField.getAnnotation(Header.class))
            .filter(Objects::nonNull)
            .map(Header::size)
            .reduce(0, Integer::sum);
    }

    public int dataSize() {
        return stream(clazz.getDeclaredFields())
            .map(declaredField -> declaredField.getAnnotation(Data.class))
            .filter(Objects::nonNull)
            .map(Data::size)
            .reduce(0, Integer::sum);
    }


    public int trailersSize() {
        return stream(clazz.getDeclaredFields())
            .map(declaredField -> declaredField.getAnnotation(Trailer.class))
            .filter(Objects::nonNull)
            .map(Trailer::size)
            .reduce(0, Integer::sum);
    }

}
