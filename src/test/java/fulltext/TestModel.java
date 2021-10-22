package fulltext;

import java.time.LocalDate;
import java.util.Objects;

public class TestModel {

    @FullText(length = 1)
    private String headerType;

    @FullText(length = 8)
    private LocalDate createAt; // yyyyMMdd

    @FullText(length = 91)
    private String headerPadding;

    @FullText(length = 1)
    private String dataType;

    @FullText(length = 10)
    private String name;

    @FullText(length = 3)
    private int age;

    @FullText(length = 86)
    private String dataPadding;

    @FullText(length = 1)
    private String trailerType;

    @FullText(length = 99)
    private String trailerPadding;

    private TestModel() {
    }

    private TestModel(final String headerType, final LocalDate createAt, final String headerPadding, final String dataType,
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
        final TestModel testModel = (TestModel) o;
        return age == testModel.age && Objects.equals(headerType, testModel.headerType) && Objects.equals(createAt, testModel.createAt) && Objects.equals(headerPadding, testModel.headerPadding) && Objects.equals(
            dataType, testModel.dataType) && Objects.equals(name, testModel.name) && Objects.equals(dataPadding, testModel.dataPadding) && Objects.equals(trailerType, testModel.trailerType) && Objects.equals(
            trailerPadding, testModel.trailerPadding);
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

        public TestModel build() {
            return new TestModel(headerType, createAt, headerPadding, dataType, name, age, dataPadding, trailerType, trailerPadding);
        }

    }


}
