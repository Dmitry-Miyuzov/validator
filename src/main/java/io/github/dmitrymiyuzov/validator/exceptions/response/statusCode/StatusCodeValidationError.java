package io.github.dmitrymiyuzov.validator.exceptions.response.statusCode;

import java.util.List;

/**
 * @author "Dmitry Miyuzov"
 */
public class StatusCodeValidationError extends Error {
    private final int numberError;

    public StatusCodeValidationError(String message, List<Throwable> listError) {
        super(message);
        numberError = listError.size() + 1;
    }

    @Override
    public void setStackTrace(StackTraceElement[] stackTrace) {
        super.setStackTrace(stackTrace);
    }

    @Override
    public String toString() {
        String message = getMessage().replaceFirst("1 expectation failed.\n", "").trim();
        return "------------------------------------------".concat("\n")
                .concat("\t").concat(String.valueOf(numberError)).concat(".Ошибка валидации статус кода ответа.\n")
                .concat("\tПодробности: ↓↓↓").concat("\n")
                .concat("\t").concat(message)
                .concat("\tСтектрейс: ↓↓↓");
    }


}
