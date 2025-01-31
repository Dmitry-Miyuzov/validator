package io.github.dmitrymiyuzov.validator.exceptions.response.body.bodyFile;

import java.util.List;

public class BodyNotEqualsFileError extends AssertionError {
    private final int numberError;

    public BodyNotEqualsFileError(List<Throwable> listError) {
        this.numberError = listError.size() + 1;
    }

    @Override
    public String toString() {
        return "------------------------------------------".concat("\n")
                .concat("\t").concat(String.valueOf(numberError)).concat(". Ошибка валидации тела ответа.\n")
                .concat("\tПодробности: Тела ответа и файл не равны").concat("\n")
                .concat("\tСтектрейс: ↓↓↓");
    }
}
