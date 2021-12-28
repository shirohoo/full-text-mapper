package fulltext.fixture.model;

import fulltext.annotation.Field;
import fulltext.annotation.FullText;
import fulltext.enums.Charset;
import fulltext.enums.PadCharacter;
import fulltext.enums.PadPosition;
import java.time.LocalDate;
import java.util.Objects;

@FullText(
    length = 300,
    encoding = Charset.UTF_8, // 명시하지 않을 경우 기본값은 UTF-8입니다.
    padChar = PadCharacter.SPACE, // 명시하지 않을 경우 기본값은 공백문자(" ")입니다.
    padPosition = PadPosition.LEFT // 명시하지 않을 경우 기본적으로 왼쪽에 패딩문자를 채워넣습니다.
)
public class ValidOptionModel {
    @Field(length = 1)
    private String headerType;

    @Field(length = 8)
    private LocalDate createAt; // yyyyMMdd

    @Field(length = 91)
    private String headerPadding;

    @Field(length = 1)
    private String dataType;

    @Field(length = 10, padPosition = PadPosition.RIGHT) // @Field의 속성이 @FullText보다 우선됩니다.
    private String name;

    @Field(length = 3, padChar = PadCharacter.ZERO) // @Field의 속성이 @FullText보다 우선됩니다.
    private int age;

    @Field(length = 86)
    private String dataPadding;

    @Field(length = 1)
    private String trailerType;

    @Field(length = 99)
    private String trailerPadding;

    private ValidOptionModel() {
    }

    private ValidOptionModel(final String headerType, final LocalDate createAt, final String headerPadding, final String dataType,
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

    public static ValidOptionModelBuilder builder() {
        return new ValidOptionModelBuilder();
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
        final ValidOptionModel validModel = (ValidOptionModel) o;
        return age == validModel.age && Objects.equals(headerType, validModel.headerType) && Objects.equals(createAt, validModel.createAt) && Objects.equals(headerPadding, validModel.headerPadding) && Objects.equals(
            dataType, validModel.dataType) && Objects.equals(name, validModel.name) && Objects.equals(dataPadding, validModel.dataPadding) && Objects.equals(trailerType, validModel.trailerType) && Objects.equals(
            trailerPadding, validModel.trailerPadding);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headerType, createAt, headerPadding, dataType, name, age, dataPadding, trailerType, trailerPadding);
    }

    public static class ValidOptionModelBuilder {
        private String headerType;
        private LocalDate createAt;
        private String headerPadding;
        private String dataType;
        private String name;
        private int age;
        private String dataPadding;
        private String trailerType;
        private String trailerPadding;

        public ValidOptionModelBuilder headerType(final String headerType) {
            this.headerType = headerType;
            return this;
        }

        public ValidOptionModelBuilder createAt(final LocalDate createAt) {
            this.createAt = createAt;
            return this;
        }

        public ValidOptionModelBuilder headerPadding(final String headerPadding) {
            this.headerPadding = headerPadding;
            return this;
        }

        public ValidOptionModelBuilder dataType(final String dataType) {
            this.dataType = dataType;
            return this;
        }

        public ValidOptionModelBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public ValidOptionModelBuilder age(final int age) {
            this.age = age;
            return this;
        }

        public ValidOptionModelBuilder dataPadding(final String dataPadding) {
            this.dataPadding = dataPadding;
            return this;
        }

        public ValidOptionModelBuilder trailerType(final String trailerType) {
            this.trailerType = trailerType;
            return this;
        }

        public ValidOptionModelBuilder trailerPadding(final String trailerPadding) {
            this.trailerPadding = trailerPadding;
            return this;
        }

        public ValidOptionModel build() {
            return new ValidOptionModel(headerType, createAt, headerPadding, dataType, name, age, dataPadding, trailerType, trailerPadding);
        }
    }
}
