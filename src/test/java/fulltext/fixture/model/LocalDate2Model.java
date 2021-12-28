package fulltext.fixture.model;

import fulltext.annotation.Field;
import fulltext.annotation.FullText;
import java.time.LocalDate;

@FullText(length = 10)
public class LocalDate2Model {
    @Field(length = 10, localDateFormat = "yyyy-MM-dd")
    private LocalDate localDate;

    public LocalDate2Model() {
    }

    public LocalDate2Model(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }
}
