package fulltext.model;

import fulltext.enums.Charset;
import fulltext.annotation.FullText;
import fulltext.annotation.Length;
import fulltext.enums.PadCharacter;
import java.time.LocalDate;
import java.util.Objects;

@FullText(
    length = 300,
    encoding = Charset.UTF_8, // 명시하지 않을 경우 기본값은 UTF-8입니다.
    padChar = PadCharacter.SPACE // 명시하지 않을 경우 기본값은 공백문자(" ")입니다.
)
public class ValidModel {

    @Length(1)
    private String headerType;

    @Length(8)
    private LocalDate createAt; // yyyyMMdd

    @Length(91)
    private String headerPadding;

    @Length(1)
    private String dataType;

    @Length(10)
    private String name;

    @Length(3)
    private int age;

    @Length(86)
    private String dataPadding;

    @Length(1)
    private String trailerType;

    @Length(99)
    private String trailerPadding;

    private ValidModel() {
    }

    private ValidModel(final String headerType, final LocalDate createAt, final String headerPadding, final String dataType,
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

    public static TestModelBuilder builder() {
        return new TestModelBuilder();
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
        final ValidModel validModel = (ValidModel) o;
        return age == validModel.age && Objects.equals(headerType, validModel.headerType) && Objects.equals(createAt, validModel.createAt) && Objects.equals(headerPadding, validModel.headerPadding) && Objects.equals(
            dataType, validModel.dataType) && Objects.equals(name, validModel.name) && Objects.equals(dataPadding, validModel.dataPadding) && Objects.equals(trailerType, validModel.trailerType) && Objects.equals(
            trailerPadding, validModel.trailerPadding);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headerType, createAt, headerPadding, dataType, name, age, dataPadding, trailerType, trailerPadding);
    }

    public static class TestModelBuilder {

        private String headerType;
        private LocalDate createAt;
        private String headerPadding;
        private String dataType;
        private String name;
        private int age;
        private String dataPadding;
        private String trailerType;
        private String trailerPadding;

        public TestModelBuilder headerType(final String headerType) {
            this.headerType = headerType;
            return this;
        }

        public TestModelBuilder createAt(final LocalDate createAt) {
            this.createAt = createAt;
            return this;
        }

        public TestModelBuilder headerPadding(final String headerPadding) {
            this.headerPadding = headerPadding;
            return this;
        }

        public TestModelBuilder dataType(final String dataType) {
            this.dataType = dataType;
            return this;
        }

        public TestModelBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public TestModelBuilder age(final int age) {
            this.age = age;
            return this;
        }

        public TestModelBuilder dataPadding(final String dataPadding) {
            this.dataPadding = dataPadding;
            return this;
        }

        public TestModelBuilder trailerType(final String trailerType) {
            this.trailerType = trailerType;
            return this;
        }

        public TestModelBuilder trailerPadding(final String trailerPadding) {
            this.trailerPadding = trailerPadding;
            return this;
        }

        public ValidModel build() {
            return new ValidModel(headerType, createAt, headerPadding, dataType, name, age, dataPadding, trailerType, trailerPadding);
        }

    }

}
