package model;

import annotation.FullText;
import type.PaddingSymbol;
import type.RecordType;

public class TestFullText extends AbstractFullText {

    @FullText(recordType = RecordType.HEADER, name = "HEADER_TYPE", size = 1)
    private String headerType;

    @FullText(recordType = RecordType.HEADER, name = "CREATE_AT", size = 8)
    private String createAt; // yyyyMMdd

    @FullText(recordType = RecordType.DATA, name = "DATA_TYPE", size = 1)
    private String dataType;

    @FullText(recordType = RecordType.DATA, name = "NAME", size = 10)
    private String name;

    @FullText(recordType = RecordType.DATA, name = "AGE", size = 3)
    private String age;

    @FullText(recordType = RecordType.TRAILER, name = "TRAILER_TYPE", size = 1)
    private String trailerType;

    private TestFullText(final PaddingSymbol paddingSymbol, final int rowSize, final String headerType, final String createAt,
        final String dataType, final String name, final String age, final String trailerType) {
        super(paddingSymbol, rowSize);
        this.headerType = headerType;
        this.createAt = createAt;
        this.dataType = dataType;
        this.name = name;
        this.age = age;
        this.trailerType = trailerType;
    }

    public static FullTextModelBuilder builder() {
        return new FullTextModelBuilder();
    }

    public static class FullTextModelBuilder {

        private PaddingSymbol paddingSymbol;
        private int rowSize;
        private String headerType;
        private String createAt;
        private String dataType;
        private String name;
        private String age;
        private String trailerType;

        public FullTextModelBuilder paddingSymbol(final PaddingSymbol paddingSymbol) {
            this.paddingSymbol = paddingSymbol;
            return this;
        }

        public FullTextModelBuilder rowSize(final int rowSize) {
            this.rowSize = rowSize;
            return this;
        }

        public FullTextModelBuilder headerType(final String headerType) {
            this.headerType = headerType;
            return this;
        }

        public FullTextModelBuilder createAt(final String createAt) {
            this.createAt = createAt;
            return this;
        }

        public FullTextModelBuilder dataType(final String dataType) {
            this.dataType = dataType;
            return this;
        }

        public FullTextModelBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public FullTextModelBuilder age(final String age) {
            this.age = age;
            return this;
        }

        public FullTextModelBuilder trailerType(final String trailerType) {
            this.trailerType = trailerType;
            return this;
        }

        public TestFullText build() {
            return new TestFullText(paddingSymbol, rowSize, headerType, createAt, dataType, name, age, trailerType);
        }

    }

}
