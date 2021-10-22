package writer;

import model.FullText;
import type.RecordType;

public interface FullTextWriter<T extends FullText> {

    String write();

    String writeOf(final T fullText, final RecordType recordType);

    String writeAll(final RecordType recordType);

}
