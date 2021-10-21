package model;

import annotation.FullText;
import type.RecordType;

public class FullTextModel extends AbstractFullText {

    private FullTextModel(final int totalLength) {
        super(FullTextModel.class, totalLength);
    }

    public static FullTextModel createFullText(final int totalLength) {
        return new FullTextModel(totalLength);
    }

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

}
