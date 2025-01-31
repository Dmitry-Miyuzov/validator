package io.github.dmitrymiyuzov.validator.exceptions.response.header;

import java.util.List;

/**
 * @author "Dmitry Miyuzov"
 */
public class HeaderMissingError extends Error{
    private final String header;
    private final int numberError;

    public HeaderMissingError(String header, List<Throwable> listError) {
        this.header = header;
        this.numberError = listError.size() + 1;
    }
    @Override
    public String toString() {
        return "------------------------------------------".concat("\n")
                .concat("\t").concat(String.valueOf(numberError)).concat(".Ошибка валидации заголовка.\n")
                .concat("\tПодробности: ↓↓↓").concat("\n")
                .concat("\tЗаголовок \"").concat(header).concat("\" отсутствует.\n")
                .concat("\tСтектрейс: ↓↓↓");
    }
}
