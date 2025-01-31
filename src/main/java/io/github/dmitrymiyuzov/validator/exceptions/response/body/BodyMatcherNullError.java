package io.github.dmitrymiyuzov.validator.exceptions.response.body;

import java.util.List;

/**
 * @author "Dmitry Miyuzov"
 */
public class BodyMatcherNullError extends IllegalArgumentException {
    private final int numberError;

    public BodyMatcherNullError(List<Throwable> listError) {
        this.numberError = listError.size() + 1;
    }

    @Override
    public String toString() {
        return "------------------------------------------".concat("\n")
                .concat("\t").concat(String.valueOf(numberError)).concat(".Ошибка валидации ответа.\n")
                .concat("\tПодробности: Matcher is null").concat("\n")
                .concat("\tСтектрейс: ↓↓↓");
    }
}
