package model;

import java.util.Objects;
import type.PaddingSymbol;

public abstract class AbstractFullText {

    protected final String paddingSymbol;

    protected final int rowSize;

    public AbstractFullText(final PaddingSymbol paddingSymbol, final int rowSize) {
        Objects.requireNonNull(paddingSymbol, "padding symbol must not be null.");
        if (rowSize < 0) {
            throw new IllegalArgumentException("row size must be greater than 0.");
        }
        this.paddingSymbol = paddingSymbol.getSymbol();
        this.rowSize = rowSize;
    }

    public String getPaddingSymbol() {
        return paddingSymbol;
    }

    public int getRowSize() {
        return rowSize;
    }

}
