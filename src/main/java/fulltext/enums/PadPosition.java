package fulltext.enums;

public enum PadPosition {

    NONE,
    LEFT,
    RIGHT,
    ;

    public boolean isNone() {
        return this == NONE;
    }

    public boolean isLeft() {
        return this == LEFT;
    }

}
