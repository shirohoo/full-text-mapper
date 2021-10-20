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

    public int getHeadersSize() {
        return stream(clazz.getDeclaredFields())
            .map(declaredField -> declaredField.getAnnotation(Header.class))
            .filter(Objects::nonNull)
            .map(Header::size)
            .reduce(0, Integer::sum);
    }

    public int getHeadersPadding() {
        final int size = this.rowSize - getHeadersSize();
        if (size < 0) {
            throw new IllegalStateException(String.format("Unable to get padding size. Total desired size: %d, Total size of header: %d", rowSize, size));
        }
        return size;
    }

    public int getDataSize() {
        return stream(clazz.getDeclaredFields())
            .map(declaredField -> declaredField.getAnnotation(Data.class))
            .filter(Objects::nonNull)
            .map(Data::size)
            .reduce(0, Integer::sum);
    }

    public int getDataPadding() {
        final int size = this.rowSize - getDataSize();
        if (size < 0) {
            throw new IllegalStateException(String.format("Unable to get padding size. Total desired size: %d, Total size of data: %d", rowSize, size));
        }
        return size;
    }


    public int geTrailersSize() {
        return stream(clazz.getDeclaredFields())
            .map(declaredField -> declaredField.getAnnotation(Trailer.class))
            .filter(Objects::nonNull)
            .map(Trailer::size)
            .reduce(0, Integer::sum);
    }

    public int getTrailersPadding() {
        final int size = this.rowSize - geTrailersSize();
        if (size < 0) {
            throw new IllegalStateException(String.format("Unable to get padding size. Total desired size: %d, Total size of trailer: %d", rowSize, size));
        }
        return size;
    }

}
