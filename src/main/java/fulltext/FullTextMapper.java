package fulltext;

import java.util.Optional;

public interface FullTextMapper {

    <T> Optional<T> readLine(final String data, final Class<T> clazz);

    <T> Optional<T> readLine(final byte[] data, final Class<T> clazz);

}
