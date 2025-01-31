package io.github.dmitrymiyuzov.validator.exceptions.response.body;

import java.util.List;

public class BodyNotDefineContentType extends IllegalStateException {
    private final int numberError;

    public BodyNotDefineContentType(List<Throwable> listError) {
        this.numberError = listError.size() + 1;
    }

    @Override
    public String toString() {
        return "------------------------------------------".concat("\n")
                .concat("\t").concat(String.valueOf(numberError)).concat(".Ошибка тела ответа.\n")
                .concat("\tПодробности: Попытка валидации тела ответа по jsonPath, но у ответа не указан header - content-type").concat("\n")
                .concat("\tСтектрейс: ↓↓↓");
    }
}
