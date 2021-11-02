package fulltext.fixture.model;

import fulltext.annotation.Field;
import java.time.LocalDate;
import java.util.Objects;

public class NoClassAnnotationModel {

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

    private NoClassAnnotationModel() {
    }

    private NoClassAnnotationModel(final String headerType, final LocalDate createAt, final String headerPadding, final String dataType,
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

    public static NoClassAnnotationModelBuilder builder() {
        return new NoClassAnnotationModelBuilder();
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
        final NoClassAnnotationModel validModel = (NoClassAnnotationModel) o;
        return age == validModel.age && Objects.equals(headerType, validModel.headerType) && Objects.equals(createAt, validModel.createAt) && Objects.equals(headerPadding, validModel.headerPadding) && Objects.equals(
            dataType, validModel.dataType) && Objects.equals(name, validModel.name) && Objects.equals(dataPadding, validModel.dataPadding) && Objects.equals(trailerType, validModel.trailerType) && Objects.equals(
            trailerPadding, validModel.trailerPadding);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headerType, createAt, headerPadding, dataType, name, age, dataPadding, trailerType, trailerPadding);
    }

    public static class NoClassAnnotationModelBuilder {

        private String headerType;
        private LocalDate createAt;
        private String headerPadding;
        private String dataType;
        private String name;
        private int age;
        private String dataPadding;
        private String trailerType;
        private String trailerPadding;

        public NoClassAnnotationModelBuilder headerType(final String headerType) {
            this.headerType = headerType;
            return this;
        }

        public NoClassAnnotationModelBuilder createAt(final LocalDate createAt) {
            this.createAt = createAt;
            return this;
        }

        public NoClassAnnotationModelBuilder headerPadding(final String headerPadding) {
            this.headerPadding = headerPadding;
            return this;
        }

        public NoClassAnnotationModelBuilder dataType(final String dataType) {
            this.dataType = dataType;
            return this;
        }

        public NoClassAnnotationModelBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public NoClassAnnotationModelBuilder age(final int age) {
            this.age = age;
            return this;
        }

        public NoClassAnnotationModelBuilder dataPadding(final String dataPadding) {
            this.dataPadding = dataPadding;
            return this;
        }

        public NoClassAnnotationModelBuilder trailerType(final String trailerType) {
            this.trailerType = trailerType;
            return this;
        }

        public NoClassAnnotationModelBuilder trailerPadding(final String trailerPadding) {
            this.trailerPadding = trailerPadding;
            return this;
        }

        public NoClassAnnotationModel build() {
            return new NoClassAnnotationModel(headerType, createAt, headerPadding, dataType, name, age, dataPadding, trailerType, trailerPadding);
        }

    }

}
