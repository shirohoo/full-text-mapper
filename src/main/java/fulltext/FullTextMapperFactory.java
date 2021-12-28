package fulltext;

public class FullTextMapperFactory {
    private FullTextMapperFactory() {
    }

    /**
     * Returns a singleton instance of LineFullTextMapper.
     *
     * @return a instance of LineFullTextMapper. this is thread-safe.
     */
    public static FullTextMapper lineFullTextMapper() {
        return LineFullTextMapperHolder.instance;
    }

    private static class LineFullTextMapperHolder {
        private static final FullTextMapper instance = LineFullTextMapper.newInstance();

    }
}
