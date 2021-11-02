package fulltext.enums;

public enum PadPosition {

    NONE,
    LEFT_PAD,
    RIGHT_PAD,
    ;

    public boolean isLeft() {
        return this == LEFT_PAD;
    }

}
