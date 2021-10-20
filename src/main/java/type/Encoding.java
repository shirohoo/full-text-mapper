package type;

public enum Encoding {
    EUC_KR("EUC-KR"),
    UTF_8("UTF-8"),
    ;

    private final String value;

    Encoding(final String value) {
        this.value = value;
    }

    public static String valueOf(final Encoding encoding) {
        return encoding.value;
    }
}
