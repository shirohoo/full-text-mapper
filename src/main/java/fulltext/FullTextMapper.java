package fulltext;

import java.util.Optional;

public interface FullTextMapper {

    <T> Optional<T> readValue(final String data, final Class<T> clazz);

    <T> Optional<T> readValue(final byte[] data, final Class<T> clazz);

}
