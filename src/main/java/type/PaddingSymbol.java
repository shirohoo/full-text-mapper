package type;

public enum PaddingSymbol {
    SPACE(" "),
    ZERO("0"),
    ;

    private final String symbol;

    PaddingSymbol(final String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
