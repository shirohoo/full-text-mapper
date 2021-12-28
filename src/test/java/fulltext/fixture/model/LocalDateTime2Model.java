package fulltext.fixture.model;

import fulltext.annotation.Field;
import fulltext.annotation.FullText;
import java.time.LocalDateTime;

@FullText(length = 19)
public class LocalDateTime2Model {
    @Field(length = 19, localDateTimeFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;

    public LocalDateTime2Model() {
    }

    public LocalDateTime2Model(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
