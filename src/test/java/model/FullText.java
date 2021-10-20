package model;

import annotation.Data;
import annotation.Header;
import annotation.Trailer;

public class FullText extends AbstractFullText {

    private FullText(final int totalLength) {
        super(FullText.class, totalLength);
    }

    public static FullText createFullText(final int totalLength) {
        return new FullText(totalLength);
    }

    @Header(name = "HEADER_TYPE", size = 1)
    private String headerType;

    @Header(name = "CREATE_AT", size = 8)
    private String createAt; // yyyyMMdd

    @Data(name = "DATA_TYPE", size = 1)
    private String dataType;

    @Data(name = "NAME", size = 10)
    private String name;

    @Data(name = "AGE", size = 3)
    private String age;

    @Trailer(name = "TRAILER_TYPE", size = 1)
    private String trailerType;

}
