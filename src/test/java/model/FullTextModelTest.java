package model;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import type.RecordType;

public class FullTextModelTest {

    private FullTextModel fullTextModel = FullTextModel.createFullText(20);

    @Test
    void headersSize() throws Exception {
        int size = fullTextModel.sizeOf(RecordType.HEADER);
        assertThat(size).isEqualTo(9);
    }

    @Test
    void dataSize() throws Exception {
        int size = fullTextModel.sizeOf(RecordType.DATA);
        assertThat(size).isEqualTo(14);
    }

    @Test
    void trailersSize() throws Exception {
        int size = fullTextModel.sizeOf(RecordType.TRAILER);
        assertThat(size).isEqualTo(1);
    }

    @Test
    void remainingSize_1() throws Exception {
        int currentDataSize = fullTextModel.sizeOf(RecordType.HEADER);
        int remainingSize = fullTextModel.remainingSize(currentDataSize);
        assertThat(remainingSize).isEqualTo(11);
    }

    @Test
    void remainingSize_2() throws Exception {
        int currentDataSize = fullTextModel.sizeOf(RecordType.DATA);
        int remainingSize = fullTextModel.remainingSize(currentDataSize);
        assertThat(remainingSize).isEqualTo(6);
    }

    @Test
    void remainingSize_3() throws Exception {
        int currentDataSize = fullTextModel.sizeOf(RecordType.TRAILER);
        int remainingSize = fullTextModel.remainingSize(currentDataSize);
        assertThat(remainingSize).isEqualTo(19);
    }

}
