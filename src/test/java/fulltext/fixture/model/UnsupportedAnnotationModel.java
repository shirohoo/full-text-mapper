package fulltext.fixture.model;

import fulltext.annotation.Field;
import fulltext.annotation.FullText;
import fulltext.enums.Charset;
import fulltext.enums.PadCharacter;
import java.time.LocalDate;
import java.util.Objects;

@FullText(
    length = 300,
    encoding = Charset.UTF_8,
    padChar = PadCharacter.NONE // @Field도 PadCharacter.NONE 이므로 UnsupportedOperationException 예상
)
public class UnsupportedAnnotationModel {
    @Field(length = 1, padChar = PadCharacter.NONE) // @FullText도 PadCharacter.NONE 이므로 UnsupportedOperationException 예상
    private String headerType;

    @Field(length = 8, padChar = PadCharacter.NONE) // @FullText도 PadCharacter.NONE 이므로 UnsupportedOperationException 예상
    private LocalDate createAt; // yyyyMMdd

    @Field(length = 91, padChar = PadCharacter.NONE) // @FullText도 PadCharacter.NONE 이므로 UnsupportedOperationException 예상
    private String headerPadding;

    @Field(length = 1, padChar = PadCharacter.NONE) // @FullText도 PadCharacter.NONE 이므로 UnsupportedOperationException 예상
    private String dataType;

    @Field(length = 10, padChar = PadCharacter.NONE) // @FullText도 PadCharacter.NONE 이므로 UnsupportedOperationException 예상
    private String name;

    @Field(length = 3, padChar = PadCharacter.NONE) // @FullText도 PadCharacter.NONE 이므로 UnsupportedOperationException 예상
    private int age;

    @Field(length = 86, padChar = PadCharacter.NONE) // @FullText도 PadCharacter.NONE 이므로 UnsupportedOperationException 예상
    private String dataPadding;

    @Field(length = 1, padChar = PadCharacter.NONE) // @FullText도 PadCharacter.NONE 이므로 UnsupportedOperationException 예상
    private String trailerType;

    @Field(length = 99, padChar = PadCharacter.NONE) // @FullText도 PadCharacter.NONE 이므로 UnsupportedOperationException 예상
    private String trailerPadding;

    private UnsupportedAnnotationModel() {
    }

    private UnsupportedAnnotationModel(final String headerType, final LocalDate createAt, final String headerPadding, final String dataType,
        final String name, final int age, final String dataPadding, final String trailerType, final String trailerPadding) {
        this.headerType = headerType;
        this.createAt = createAt;
        this.headerPadding = headerPadding;
        this.dataType = dataType;
        this.name = name;
        this.age = age;
        this.dataPadding = dataPadding;
        this.trailerType = trailerType;
        this.trailerPadding = trailerPadding;
    }

    public static UnsupportedAnnotationModelBuilder builder() {
        return new UnsupportedAnnotationModelBuilder();
    }

    @Override
    public String toString() {
        return "fulltext.TestModel{" +
            "headerType='" + headerType + '\'' +
            ", createAt='" + createAt + '\'' +
            ", headerPadding='" + headerPadding + '\'' +
            ", dataType='" + dataType + '\'' +
            ", name='" + name + '\'' +
            ", age='" + age + '\'' +
            ", dataPadding='" + dataPadding + '\'' +
            ", trailerType='" + trailerType + '\'' +
            ", trailerPadding='" + trailerPadding + '\'' +
            '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UnsupportedAnnotationModel validModel = (UnsupportedAnnotationModel) o;
        return age == validModel.age && Objects.equals(headerType, validModel.headerType) && Objects.equals(createAt, validModel.createAt) && Objects.equals(headerPadding, validModel.headerPadding) && Objects.equals(
            dataType, validModel.dataType) && Objects.equals(name, validModel.name) && Objects.equals(dataPadding, validModel.dataPadding) && Objects.equals(trailerType, validModel.trailerType) && Objects.equals(
            trailerPadding, validModel.trailerPadding);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headerType, createAt, headerPadding, dataType, name, age, dataPadding, trailerType, trailerPadding);
    }

    public static class UnsupportedAnnotationModelBuilder {

        private String headerType;
        private LocalDate createAt;
        private String headerPadding;
        private String dataType;
        private String name;
        private int age;
        private String dataPadding;
        private String trailerType;
        private String trailerPadding;

        public UnsupportedAnnotationModelBuilder headerType(final String headerType) {
            this.headerType = headerType;
            return this;
        }

        public UnsupportedAnnotationModelBuilder createAt(final LocalDate createAt) {
            this.createAt = createAt;
            return this;
        }

        public UnsupportedAnnotationModelBuilder headerPadding(final String headerPadding) {
            this.headerPadding = headerPadding;
            return this;
        }

        public UnsupportedAnnotationModelBuilder dataType(final String dataType) {
            this.dataType = dataType;
            return this;
        }

        public UnsupportedAnnotationModelBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public UnsupportedAnnotationModelBuilder age(final int age) {
            this.age = age;
            return this;
        }

        public UnsupportedAnnotationModelBuilder dataPadding(final String dataPadding) {
            this.dataPadding = dataPadding;
            return this;
        }

        public UnsupportedAnnotationModelBuilder trailerType(final String trailerType) {
            this.trailerType = trailerType;
            return this;
        }

        public UnsupportedAnnotationModelBuilder trailerPadding(final String trailerPadding) {
            this.trailerPadding = trailerPadding;
            return this;
        }

        public UnsupportedAnnotationModel build() {
            return new UnsupportedAnnotationModel(headerType, createAt, headerPadding, dataType, name, age, dataPadding, trailerType, trailerPadding);
        }
    }
}
