package model;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class FullTextTest {

    private FullText fullText = FullText.createFullText(20);

    @Test
    void headersSize() throws Exception {
        int size = fullText.headerSize();
        assertThat(size).isEqualTo(9);
    }

    @Test
    void dataSize() throws Exception {
        int size = fullText.dataSize();
        assertThat(size).isEqualTo(14);
    }

    @Test
    void trailersSize() throws Exception {
        int size = fullText.trailersSize();
        assertThat(size).isEqualTo(1);
    }

    @Test
    void remainingSize_1() throws Exception {
        int remainingSize = fullText.remainingSize(fullText.headerSize());
        assertThat(remainingSize).isEqualTo(11);
    }

    @Test
    void remainingSize_2() throws Exception {
        int remainingSize = fullText.remainingSize(fullText.dataSize());
        assertThat(remainingSize).isEqualTo(6);
    }

    @Test
    void remainingSize_3() throws Exception {
        int remainingSize = fullText.remainingSize(fullText.trailersSize());
        assertThat(remainingSize).isEqualTo(19);
    }

}
