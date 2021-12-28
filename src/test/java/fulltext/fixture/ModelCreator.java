package fulltext.fixture;

import fulltext.fixture.model.InvalidClassAnnotationModel;
import fulltext.fixture.model.LocalDate1Model;
import fulltext.fixture.model.LocalDate2Model;
import fulltext.fixture.model.LocalDateTime1Model;
import fulltext.fixture.model.LocalDateTime2Model;
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

    public static final LocalDate1Model LOCAL_DATE1_MODEL = new LocalDate1Model(LocalDate.of(2000, 12, 31));
    public static final LocalDate2Model LOCAL_DATE2_MODEL = new LocalDate2Model(LocalDate.of(2000, 12, 31));
    public static final LocalDateTime1Model LOCAL_DATE_TIME1_MODEL = new LocalDateTime1Model(LocalDate.of(2000, 12, 31).atStartOfDay());
    public static final LocalDateTime2Model LOCAL_DATE_TIME2_MODEL = new LocalDateTime2Model(LocalDate.of(2000, 12, 31).atStartOfDay());

}
