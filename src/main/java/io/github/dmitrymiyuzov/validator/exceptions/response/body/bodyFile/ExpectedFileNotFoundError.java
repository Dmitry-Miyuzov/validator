package io.github.dmitrymiyuzov.validator.exceptions.response.body.bodyFile;

import java.util.List;

public class ExpectedFileNotFoundError extends IllegalStateException {
    private final int numberError;

    public ExpectedFileNotFoundError(List<Throwable> listError) {
        this.numberError = listError.size() + 1;
    }

    @Override
    public String toString() {
        return "------------------------------------------".concat("\n")
                .concat("\t").concat(String.valueOf(numberError)).concat(".Ошибка тела ответа.\n")
                .concat("\tПодробности: Попытка сравнить тело ответа с файлом, но файл пустой или не найден").concat("\n")
                .concat("\tСтектрейс: ↓↓↓");
    }
}
