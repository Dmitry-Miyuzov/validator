package io.github.dmitrymiyuzov.validator.exceptions.response.body;

import java.util.List;

/**
 * @author "Dmitry Miyuzov"
 */
public class BodyUnknownError extends Throwable {
    private final int numberError;

    public BodyUnknownError(String message, List<Throwable> listError) {
        super(message);
        this.numberError = listError.size() + 1;
    }

    public String toString() {
        String message = getMessage().trim();
        return "------------------------------------------".concat("\n")
                .concat("\t").concat(String.valueOf(numberError)).concat(".Ошибка валидации ответа.\n")
                .concat("\tПодробности: ").concat(message).concat("\n")
                .concat("\tСтектрейс: ↓↓↓");
    }
}
