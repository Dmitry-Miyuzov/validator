package io.github.dmitrymiyuzov.validator.xlsx.poiObjectMapper;

import java.io.Serial;

public class SpreadsheetReadException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;


    // Constructors
    // ------------------------------------------------------------------------

    public SpreadsheetReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpreadsheetReadException(String message) {
        super(message);
    }

}
