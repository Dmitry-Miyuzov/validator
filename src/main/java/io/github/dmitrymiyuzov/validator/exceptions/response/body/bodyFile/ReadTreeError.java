package io.github.dmitrymiyuzov.validator.exceptions.response.body.bodyFile;

import java.util.List;

public class ReadTreeError extends IllegalStateException {
    private final int numberError;

    public ReadTreeError(List<Throwable> listError) {
        this.numberError = listError.size() + 1;
    }

    @Override
    public String toString() {
        return "------------------------------------------".concat("\n")
                .concat("\t").concat(String.valueOf(numberError)).concat(".Ошибка валидации тела ответа.\n")
                .concat("\tПодробности: Ошибка при попытке парсинга ответа или файла").concat("\n")
                .concat("\tСтектрейс: ↓↓↓");
    }
}
