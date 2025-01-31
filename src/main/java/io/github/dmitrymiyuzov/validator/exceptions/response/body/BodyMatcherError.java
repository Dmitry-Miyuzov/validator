package io.github.dmitrymiyuzov.validator.exceptions.response.body;

import java.util.Arrays;
import java.util.List;

/**
 * @author "Dmitry Miyuzov"
 */
public class BodyMatcherError extends AssertionError {
    private final String jsonPath;
    private final int numberError;

    public BodyMatcherError(String message, String jsonPath, List<Throwable> listError) {
        super(message);
        this.jsonPath = jsonPath;
        this.numberError = listError.size() + 1;
    }

    @Override
    public String toString() {
        if (jsonPath != null) {
            String message = getMessage().trim();
            List<String> list = Arrays.asList(message.split("\n"));
            return "------------------------------------------".concat("\n")
                    .concat("\t").concat(String.valueOf(numberError)).concat(".Ошибка валидации тела ответа.\n")
                    .concat("\tПодробности: Проверяемый jsonPath: \"").concat(jsonPath).concat("\"\n")
                    .concat("\t").concat(list.get(2)).concat("\n")
                    .concat("\t").concat(list.get(3)).concat("\n")
                    .concat("\tСтектрейс: ↓↓↓");
        } else {
            return "------------------------------------------".concat("\n")
                    .concat("\t").concat(String.valueOf(numberError)).concat(".Ошибка валидации ответа.\n")
                    .concat("\tПодробности: Не пройдена валидация json-схемы").concat("\n")
                    .concat("\tСтектрейс: ↓↓↓");
        }
    }
}
