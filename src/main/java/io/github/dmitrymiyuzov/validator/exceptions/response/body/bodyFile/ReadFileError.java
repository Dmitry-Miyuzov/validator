package io.github.dmitrymiyuzov.validator.exceptions.response.body.bodyFile;

import java.util.List;

public class ReadFileError extends IllegalStateException {
    private final int numberError;

    public ReadFileError(List<Throwable> listError) {
        this.numberError = listError.size() + 1;
    }

    @Override
    public String toString() {
        return "------------------------------------------".concat("\n")
                .concat("\t").concat(String.valueOf(numberError)).concat(".Ошибка валидации тела ответа.\n")
                .concat("\tПодробности: Попытка сравнить тело ответа с файлом, но при чтении файла произошла ошибка").concat("\n")
                .concat("\tСтектрейс: ↓↓↓");
    }
}
