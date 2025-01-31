package io.github.dmitrymiyuzov.validator.exceptions.response.header;

import java.util.List;

/**
 * @author "Dmitry Miyuzov"
 */
public class HeaderEqualsError extends AssertionError{
    private final String header;
    private final String headerValue;
    private final String expectedValue;
    private final int numberError;

    public HeaderEqualsError(String header, String headerValue, String expectedValue, List<Throwable> listError) {
        this.header = header;
        this.headerValue = headerValue;
        this.expectedValue = expectedValue;
        this.numberError = listError.size() + 1;

    }
    @Override
    public String toString() {
        return "------------------------------------------".concat("\n")
                .concat("\t").concat(String.valueOf(numberError)).concat(".Ошибка валидации заголовка.\n")
                .concat("\tПодробности: ↓↓↓").concat("\n")
                .concat("\tЗначение заголовка \"").concat(header).concat("\" не равно: \"").concat(expectedValue).concat("\"\n")
                .concat("\tАктуальное значение: \"").concat(headerValue).concat("\"\n")
                .concat("\tСтектрейс: ↓↓↓");
    }
}
