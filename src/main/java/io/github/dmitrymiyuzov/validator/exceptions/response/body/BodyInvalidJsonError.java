package io.github.dmitrymiyuzov.validator.exceptions.response.body;

import java.util.List;

/**
 * @author "Dmitry Miyuzov"
 */
public class BodyInvalidJsonError extends IllegalArgumentException {
    private final String jsonPath;
    private final int numberError;

    public BodyInvalidJsonError(String jsonPath, List<Throwable> listError) {
        this.jsonPath = jsonPath;
        this.numberError = listError.size() + 1;
    }

    @Override
    public String toString() {
        return "------------------------------------------".concat("\n")
                .concat("\t").concat(String.valueOf(numberError)).concat(".Ошибка валидации тела ответа.\n")
                .concat("\tПодробности: Не валидный jsonPath: \"").concat(jsonPath).concat("\"\n")
                .concat("\tСтектрейс: ↓↓↓");
    }
}
