package fulltext.fixture;

import fulltext.fixture.model.InvalidClassAnnotationModel;
import fulltext.fixture.model.NoClassAnnotationModel;
import fulltext.fixture.model.ValidModel;
import fulltext.fixture.model.ValidOptionModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class ModelCreator {

    public static final ValidModel VALID_MODEL =
        ValidModel.builder()
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

    public static final ValidOptionModel VALID_OPTION_MODEL =
        ValidOptionModel.builder()
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

    public static final InvalidClassAnnotationModel INVALID_CLASS_ANNOTATION_MODEL =
        InvalidClassAnnotationModel.builder()
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

    public static final NoClassAnnotationModel NO_CLASS_ANNOTATION_MODEL =
        NoClassAnnotationModel.builder()
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
