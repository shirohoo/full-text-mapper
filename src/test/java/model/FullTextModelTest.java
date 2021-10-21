package model;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.FullTextWriter;
import parser.SimpleFullTextWriter;
import type.PaddingSymbol;
import type.RecordType;

public class FullTextModelTest {

    private FullTextModel fullTextModel =
        FullTextModel.builder()
            .paddingSymbol(PaddingSymbol.SPACE)
            .rowSize(100)
            .headerType("1")
            .createAt("20211011")
            .dataType("2")
            .name("siro")
            .age("28")
            .trailerType("3")
            .build();

    private FullTextWriter<FullTextModel> writer;

    @BeforeEach
    void setUp() {
        List<FullTextModel> fullTextList = List.of(this.fullTextModel, fullTextModel);
        writer = SimpleFullTextWriter.from(fullTextList);
    }

    @Test
    void parseHeaderInList() throws Exception {
        assertThat(writer.writeAll(RecordType.HEADER)).isEqualTo(
            "120211011                                                                                           "
                + SimpleFullTextWriter.NEW_LINE
                + "120211011                                                                                           "
        );
    }

    @Test
    void parseDataInList() throws Exception {
        assertThat(writer.writeAll(RecordType.DATA)).isEqualTo(
            "2      siro 28                                                                                      "
                + SimpleFullTextWriter.NEW_LINE
                + "2      siro 28                                                                                      "
        );
    }

    @Test
    void parseTrailerInList() throws Exception {
        assertThat(writer.writeAll(RecordType.TRAILER)).isEqualTo(
            "3                                                                                                   "
                + SimpleFullTextWriter.NEW_LINE
                + "3                                                                                                   "
        );
    }

    @Test
    void writeOf() throws Exception {
        assertThat(writer.writeOf(fullTextModel, RecordType.DATA)).isEqualTo(
            "2      siro 28                                                                                      "
        );
    }

}
