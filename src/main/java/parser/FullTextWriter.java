package parser;

import model.AbstractFullText;
import type.RecordType;

public interface FullTextWriter<T extends AbstractFullText> {

    String write();

    String writeOf(final T fullText, final RecordType recordType);

    String writeAll(final RecordType recordType);

}
