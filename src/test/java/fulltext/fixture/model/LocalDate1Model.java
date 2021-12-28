package fulltext.fixture.model;

import fulltext.annotation.Field;
import fulltext.annotation.FullText;
import java.time.LocalDate;

@FullText(length = 8)
public class LocalDate1Model {
    @Field(length = 8)
    private LocalDate localDate;

    public LocalDate1Model() {
    }

    public LocalDate1Model(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }
}
