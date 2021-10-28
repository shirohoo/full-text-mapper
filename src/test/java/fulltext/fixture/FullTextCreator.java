package fulltext.fixture;

public final class FullTextCreator {

    public static String validData() {
        return "120211011                                                                                           " +
            "2      siro 28                                                                                      " +
            "3                                                                                                   ";
    }

    public static String validOptionData() {
        return "120211011                                                                                          " +
            " 2siro      028                                                                                     " +
            " 3                                                                                                   ";
    }

    public static String validModel() {
        return "120211011                                                                                           "
            + "2      siro 28                                                                                "
            + "      3                                                                                       "
            + "            ";
    }

}
