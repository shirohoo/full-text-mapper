package fulltext.fixture.model;

import fulltext.annotation.Field;
import fulltext.annotation.FullText;
import java.time.LocalDate;
import java.time.LocalDateTime;

@FullText(length = 14)
public class LocalDateTime1Model {
    @Field(length = 14)
    private LocalDateTime localDateTime;

    public LocalDateTime1Model() {
    }

    public LocalDateTime1Model(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
