package fulltext;

import fulltext.annotation.Field;
import fulltext.annotation.FullText;

/**
 * <p>
 * FullTextMapper mapping full text and Object to each other. In order to use it,{@link FullText} and, {@link Field} must be properly declared in the object to be mapped.
 * <p>
 * implSpec -  a class implementing this interface should be thread-safe and be able to handle the properties of {@link FullText} and {@link Field}.
 * <p>
 * In this case, Field property takes precedence over FullText.
 */
public interface FullTextMapper {
    /**
     * After inputting the string data and class together, it maps the data to the instance of the input class and returns it.
     *
     * @param data  full text of string type
     * @param clazz map to full text
     * @param <T>   class in which {@link FullText} and {@link Field} are declared
     * @return instance of T
     */
    <T> T readValue(final String data, final Class<T> clazz);

    /**
     * After inputting the byte array data and class together, it maps the data to the instance of the input class and returns it.
     *
     * @param <T>   class in which {@link FullText} and {@link Field} are declared
     * @param data  full text of byte array type
     * @param clazz map to full text
     * @return instance of T
     */
    <T> T readValue(final byte[] data, final Class<T> clazz);

    /**
     * It takes an object as input, refers to {@link FullText} and {@link Field} declared, and creates full text and returns it.
     *
     * @param object want to output in full text
     * @return full text
     */
    String write(final Object object);
}
