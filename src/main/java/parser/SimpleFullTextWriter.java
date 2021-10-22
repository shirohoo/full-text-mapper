package parser;

import static java.lang.String.format;
import static java.util.Objects.nonNull;
import annotation.DataByte;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import model.FullText;
import type.RecordType;

public class SimpleFullTextWriter<T extends FullText> implements FullTextWriter<T> {

    public static final String NEW_LINE = System.getProperty("line.separator");

    private final List<T> fullTextList;

    private final String paddingSymbol;

    private final int rowSize;

    public SimpleFullTextWriter(final List<T> fullTextList) {
        if (fullTextList.size() < 1) {
            throw new IllegalArgumentException("List size must be grater then 1.");
        }
        T forInitialize = fullTextList.get(0);
        this.fullTextList = fullTextList;
        this.paddingSymbol = forInitialize.getPaddingSymbol();
        this.rowSize = forInitialize.getRowSize();
    }

    public static <T extends FullText> SimpleFullTextWriter<T> from(final List<T> fullText) {
        return new SimpleFullTextWriter<T>(fullText);
    }

    @Override
    public String write() {
        return null;
    }

    @Override
    public String writeOf(final T fullText, final RecordType recordType) {
        return parse(fullText, recordType);
    }

    @Override
    public String writeAll(final RecordType recordType) {
        return fullTextList.stream()
            .map(fullText -> parse(fullText, recordType))
            .collect(Collectors.joining(NEW_LINE));
    }

    private String parse(final T fullText, final RecordType recordType) {
        String data = getData(fullText, recordType);
        return verify(data + getPadding(data));
    }

    private String getData(final T fullText, final RecordType recordType) {
        String data = "";
        try {
            for (Field field : fullText.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                DataByte annotation = field.getAnnotation(DataByte.class);
                if (annotation.recordType().equals(recordType) && nonNull(annotation)) {
                    String fieldValue = (String) field.get(fullText);
                    field.set(fullText, (getPadding(annotation.size(), fieldValue) + fieldValue));
                    data += (String) field.get(fullText);
                }
            }
        } catch (IllegalAccessException e) {
            // No problem. do nothing.
        }
        return data;
    }

    private String getPadding(final String data) {
        return getPadding(rowSize, data);
    }

    private String getPadding(final int maxSize, final String fieldValue) {
        String padding = "";
        for (int i = 0; i < maxSize - fieldValue.length(); i++) {
            padding += paddingSymbol;
        }
        return padding;
    }

    private String verify(final String data) {
        if (data.length() > rowSize) {
            throw new IllegalStateException(
                format(
                    "Unable to return data. current row size exceeds the defined row size. defined row size: %d. but, current row size: %d",
                    rowSize, data.length()
                )
            );
        }
        return data;
    }

}
