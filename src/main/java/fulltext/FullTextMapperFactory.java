package fulltext;

public class FullTextMapperFactory {

    private FullTextMapperFactory() {
    }

    /**
     *
     * @return
     */
    public static FullTextMapper lineFullTextMapper() {
        return LineFullTextMapperHolder.instance;
    }

    private static class LineFullTextMapperHolder {

        private static final FullTextMapper instance = LineFullTextMapper.newInstance();

    }

}
