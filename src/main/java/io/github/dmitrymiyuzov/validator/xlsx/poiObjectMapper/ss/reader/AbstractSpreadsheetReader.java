package io.github.dmitrymiyuzov.validator.xlsx.poiObjectMapper.ss.reader;

import io.github.dmitrymiyuzov.validator.xlsx.poiObjectMapper.ss.handler.RowListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.dmitrymiyuzov.validator.xlsx.poiObjectMapper.SpreadsheetReadException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * A abstract implementation of {@link SpreadsheetReader}.
 */
abstract class AbstractSpreadsheetReader implements SpreadsheetReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSpreadsheetReader.class);



    // Abstract Methods
    // ------------------------------------------------------------------------



    // Methods
    // ------------------------------------------------------------------------

    @Override
    public <T> void read(Class<T> beanClz, File file, RowListener<T> callback) throws SpreadsheetReadException {
        // Closeble
        try (InputStream fis = new FileInputStream(file)) {

            // chain
            this.read(beanClz, fis, callback);
        } catch (IOException ex) {
            String errMsg = String.format("Failed to read file as Stream : %s", ex.getMessage());
            throw new SpreadsheetReadException(errMsg, ex);
        }
    }


    @Override
    public <T> void read(Class<T> beanClz, File file, int sheetNo, RowListener<T> callback)
            throws SpreadsheetReadException {
        // Sanity checks
        try {
            InputStream fis = new FileInputStream(file);

            // chain
            this.read(beanClz, fis, sheetNo, callback);
        } catch (IOException ex) {
            String errMsg = String.format("Failed to read file as Stream : %s", ex.getMessage());
            throw new SpreadsheetReadException(errMsg, ex);
        }
    }



    @Override
    public <T> List<T> read(Class<T> beanClz, File file) throws SpreadsheetReadException {
        // Closeble
        try (InputStream fis = new FileInputStream(file)) {
            return this.read(beanClz, fis);
        } catch (IOException ex) {
            String errMsg = String.format("Failed to read file as Stream : %s", ex.getMessage());
            throw new SpreadsheetReadException(errMsg, ex);
        }
    }

    @Override
    public <T> List<T> read(Class<T> beanClz, InputStream is) throws SpreadsheetReadException {
        // Result
        final List<T> sheetBeans = new ArrayList<>();

        // Read with callback to fill list
        this.read(beanClz, is, (rowNum, rowObj) -> {
            if (rowObj == null) {
                LOGGER.error("Null object returned for row : {}", rowNum);
                return;
            }

            sheetBeans.add(rowObj);
        });

        return sheetBeans;
    }


    @Override
    public <T> List<T> read(Class<T> beanClz, File file, int sheetNo) throws SpreadsheetReadException {
        // Closeble
        try (InputStream fis = new FileInputStream(file)) {
            return this.read(beanClz, fis, sheetNo);
        } catch (IOException ex) {
            String errMsg = String.format("Failed to read file as Stream : %s", ex.getMessage());
            throw new SpreadsheetReadException(errMsg, ex);
        }
    }

    @Override
    public <T> List<T> read(Class<T> beanClz, InputStream is, int sheetNo) throws SpreadsheetReadException {
        // Result
        final List<T> sheetBeans = new ArrayList<T>();

        // Read with callback to fill list
        this.read(beanClz, is, sheetNo, new RowListener<T>() {

            @Override
            public void row(int rowNum, T rowObj) {
                if (rowObj == null) {
                    LOGGER.error("Null object returned for row : {}", rowNum);
                    return;
                }

                sheetBeans.add(rowObj);
            }

        });

        return sheetBeans;
    }


}
