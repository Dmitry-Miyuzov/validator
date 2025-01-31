package io.github.dmitrymiyuzov.validator.exceptions.assertion;

import java.util.List;

/**
 * @author "Dmitry Miyuzov"
 */
public class AssertValidationError extends AssertionError {
    private final int numberError;

    public AssertValidationError(String message, List<Throwable> listError) {
        super(message);
        this.numberError = listError.size() + 1;
    }

    public String toString() {
        String message = getMessage().trim();
        return "------------------------------------------".concat("\n")
                .concat("\t").concat(String.valueOf(numberError)).concat(".Ошибка валидации утверждения.\n")
                .concat("\tПодробности: ").concat(message).concat("\n")
                .concat("\tСтектрейс: ↓↓↓");
    }
}
