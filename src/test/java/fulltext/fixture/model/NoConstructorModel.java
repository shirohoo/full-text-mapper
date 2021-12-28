package fulltext.fixture.model;

import fulltext.annotation.Field;
import fulltext.annotation.FullText;
import fulltext.enums.Charset;
import fulltext.enums.PadCharacter;
import java.time.LocalDate;
import java.util.Objects;

@FullText(
    length = 300,
    encoding = Charset.UTF_8, // 명시하지 않을 경우 기본값은 UTF-8입니다.
    padChar = PadCharacter.SPACE // 명시하지 않을 경우 기본값은 공백문자(" ")입니다.
)
public class NoConstructorModel {

    @Field(length = 1)
    private String headerType;

    @Field(length = 8)
    private LocalDate createAt; // yyyyMMdd

    @Field(length = 91)
    private String headerPadding;

    @Field(length = 1)
    private String dataType;

    @Field(length = 10)
    private String name;

    @Field(length = 3)
    private int age;

    @Field(length = 86)
    private String dataPadding;

    @Field(length = 1)
    private String trailerType;

    @Field(length = 99)
    private String trailerPadding;

    private NoConstructorModel(final String headerType, final LocalDate createAt, final String headerPadding, final String dataType,
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

    public static NoConstructorModelBuilder builder() {
        return new NoConstructorModelBuilder();
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
        final NoConstructorModel model = (NoConstructorModel) o;
        return age == model.age && Objects.equals(headerType, model.headerType) && Objects.equals(createAt, model.createAt) && Objects.equals(headerPadding, model.headerPadding) && Objects.equals(
            dataType, model.dataType) && Objects.equals(name, model.name) && Objects.equals(dataPadding, model.dataPadding) && Objects.equals(trailerType, model.trailerType) && Objects.equals(
            trailerPadding, model.trailerPadding);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headerType, createAt, headerPadding, dataType, name, age, dataPadding, trailerType, trailerPadding);
    }

    public static class NoConstructorModelBuilder {

        private String headerType;
        private LocalDate createAt;
        private String headerPadding;
        private String dataType;
        private String name;
        private int age;
        private String dataPadding;
        private String trailerType;
        private String trailerPadding;

        public NoConstructorModelBuilder headerType(final String headerType) {
            this.headerType = headerType;
            return this;
        }

        public NoConstructorModelBuilder createAt(final LocalDate createAt) {
            this.createAt = createAt;
            return this;
        }

        public NoConstructorModelBuilder headerPadding(final String headerPadding) {
            this.headerPadding = headerPadding;
            return this;
        }

        public NoConstructorModelBuilder dataType(final String dataType) {
            this.dataType = dataType;
            return this;
        }

        public NoConstructorModelBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public NoConstructorModelBuilder age(final int age) {
            this.age = age;
            return this;
        }

        public NoConstructorModelBuilder dataPadding(final String dataPadding) {
            this.dataPadding = dataPadding;
            return this;
        }

        public NoConstructorModelBuilder trailerType(final String trailerType) {
            this.trailerType = trailerType;
            return this;
        }

        public NoConstructorModelBuilder trailerPadding(final String trailerPadding) {
            this.trailerPadding = trailerPadding;
            return this;
        }

        public NoConstructorModel build() {
            return new NoConstructorModel(headerType, createAt, headerPadding, dataType, name, age, dataPadding, trailerType, trailerPadding);
        }

    }

}
