package fulltext;

public class FullTextMapperFactory {

    private FullTextMapperFactory() {
    }

    public static FullTextMapper getLineFullTextMapper() {
        return LineFullTextMapperHolder.instance;
    }

    private static class LineFullTextMapperHolder {

        private static final FullTextMapper instance = LineFullTextMapper.newInstance();

    }

}
