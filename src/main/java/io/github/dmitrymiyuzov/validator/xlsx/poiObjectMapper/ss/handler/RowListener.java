package io.github.dmitrymiyuzov.validator.xlsx.poiObjectMapper.ss.handler;

/**
 * An abstract representation of Row level Callback for {@link io.sfera.at.tasks.extension.java.xlsx.poiObjectMapper.ss.reader.SpreadsheetReader} implementations.
 */
public interface RowListener<T> {

    /**
     * This method will be called after every row by the {@link io.sfera.at.tasks.extension.java.xlsx.poiObjectMapper.ss.reader.SpreadsheetReader} implementation.
     *
     * @param rowNum the Row Number in the sheet. (indexed from 0)
     * @param rowObj the java bean constructed using the Row data.
     */
    void row(int rowNum, T rowObj);

}
