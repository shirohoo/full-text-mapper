package model;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class FullTextTest {

    private FullText fullText = FullText.createFullText(20);

    @Test
    void getHeadersSize() throws Exception {
        int size = fullText.getHeadersSize();
        assertThat(size).isEqualTo(9);
    }

    @Test
    void getHeadersPadding() throws Exception {
        int size = fullText.getHeadersPadding();
        assertThat(size).isEqualTo(11);
    }

    @Test
    void getDataSize() throws Exception {
        int size = fullText.getDataSize();
        assertThat(size).isEqualTo(14);
    }

    @Test
    void getDataPadding() throws Exception {
        int size = fullText.getDataPadding();
        assertThat(size).isEqualTo(6);
    }

    @Test
    void geTrailersSize() throws Exception {
        int size = fullText.geTrailersSize();
        assertThat(size).isEqualTo(1);
    }

    @Test
    void getTrailersPadding() throws Exception {
        int size = fullText.getTrailersPadding();
        assertThat(size).isEqualTo(19);
    }

}
