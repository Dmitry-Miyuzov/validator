package io.github.dmitrymiyuzov.validator.xlsx.poiObjectMapper;

import java.io.Serial;

public class UnsupportedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 3103542175797043236L;


    // Constructors
    // ------------------------------------------------------------------------

    public UnsupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedException(String message) {
        super(message);
    }


}
