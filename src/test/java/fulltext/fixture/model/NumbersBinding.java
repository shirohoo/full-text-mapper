package fulltext.fixture.model;

import fulltext.annotation.Field;
import fulltext.annotation.FullText;
import fulltext.enums.PadCharacter;

@FullText(
    length = 15,
    padChar = PadCharacter.ZERO
)
public class NumbersBinding {
    @Field(length = 5)
    private Integer intValue;

    @Field(length = 5)
    private Long longValue;

    @Field(length = 5)
    private Double doubleValue;

    public NumbersBinding() {
    }

    public Integer getIntValue() {
        return intValue;
    }

    public Long getLongValue() {
        return longValue;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }
}
