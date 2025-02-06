package io.github.dmitrymiyuzov.validator.exceptions.base;

/**
 * @author "Dmitry Miyuzov"
 * Завершающая ошибка валидатора - она будет выброшена, если во всей цепочке возникла хотя бы одна ошибка (не по вине валидации)
 * а например не валидный jsonPath ввели
 */
public class ChainValidationException extends RuntimeException {
    private final Integer countValidation;
    private final Integer countErrorValidation;

    public ChainValidationException(String message, Integer countValidation, Integer countErrorValidation) {
        super(message);
        this.countValidation = countValidation;
        this.countErrorValidation = countErrorValidation;
    }

    @Override
    public String toString() {
        return "\n\tChainValidationException: Ошибки произошедшие в цепочке валидаций.\n"
                .concat("\t").concat(getMessage()).concat("\n")
                .concat("\t----------------------------------------------------------------------------------------------------------");
    }

    public Integer getCountErrorValidation() {
        return countErrorValidation;
    }

    public Integer getCountValidation() {
        return countValidation;
    }
}
