package io.github.dmitrymiyuzov.validator.exceptions.response.jsonSchema;

import java.util.List;

public class JsonSchemaError extends AssertionError {
    private final int numberError;

    public JsonSchemaError(List<Throwable> listError) {
        this.numberError = listError.size() + 1;
    }

    @Override
    public String toString() {
        return "------------------------------------------".concat("\n")
                .concat("\t").concat(String.valueOf(numberError)).concat(".Ошибка валидации json-схемы.\n")
                .concat("\tПодробности: Смотрите Allure-отчет.").concat("\n")
                .concat("\tСтектрейс: ↓↓↓");
    }
}
