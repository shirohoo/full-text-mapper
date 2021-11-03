package fulltext.enums;

public enum PadCharacter {

    NONE(""),
    SPACE(" "),
    ZERO("0"),
    ;

    private final String character;

    PadCharacter(final String character) {
        this.character = character;
    }

    public boolean isNone() {
        return this == NONE;
    }

    public String leftPad(final String data, final int len) {
        return pad(len) + data;
    }

    public String rightPad(final String data, final int len) {
        return data + pad(len);
    }

    public String pad(final int len) {
        return new StringBuilder()
            .append(new String(new char[len]).replace("\0", character))
            .toString();
    }

    public String removeLeftPad(final String data) {
        return data.replaceFirst("^" + character + "*", "");
    }

    public String removeRightPad(String data) {
        while (data.endsWith(character)) {
            data = data.substring(0, data.lastIndexOf(character));
        }
        return data;
    }

}
