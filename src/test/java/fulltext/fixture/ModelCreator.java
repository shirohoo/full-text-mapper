package fulltext.fixture;

import fulltext.fixture.model.InvalidClassAnnotationModel;
import fulltext.fixture.model.NoClassAnnotationModel;
import fulltext.fixture.model.ValidModel;
import fulltext.fixture.model.ValidOptionModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class ModelCreator {

    public static ValidModel validModel() {
        return ValidModel.builder()
            .headerType("1")
            .createAt(LocalDate.parse("20211011", DateTimeFormatter.BASIC_ISO_DATE))
            .headerPadding("")
            .dataType("2")
            .name("siro")
            .age(28)
            .dataPadding("")
            .trailerType("3")
            .trailerPadding("")
            .build();
    }

    public static ValidOptionModel validOptionModel() {
        return ValidOptionModel.builder()
            .headerType("1")
            .createAt(LocalDate.parse("20211011", DateTimeFormatter.BASIC_ISO_DATE))
            .headerPadding("")
            .dataType("2")
            .name("siro")
            .age(28)
            .dataPadding("")
            .trailerType("3")
            .trailerPadding("")
            .build();
    }

    public static InvalidClassAnnotationModel invalidClassAnnotationModel() {
        return InvalidClassAnnotationModel.builder()
            .headerType("1")
            .createAt(LocalDate.parse("20211011", DateTimeFormatter.BASIC_ISO_DATE))
            .headerPadding("")
            .dataType("2")
            .name("siro")
            .age(28)
            .dataPadding("")
            .trailerType("3")
            .trailerPadding("")
            .build();
    }

    public static NoClassAnnotationModel noClassAnnotationModel() {
        return NoClassAnnotationModel.builder()
            .headerType("1")
            .createAt(LocalDate.parse("20211011", DateTimeFormatter.BASIC_ISO_DATE))
            .headerPadding("")
            .dataType("2")
            .name("siro")
            .age(28)
            .dataPadding("")
            .trailerType("3")
            .trailerPadding("")
            .build();
    }

}
