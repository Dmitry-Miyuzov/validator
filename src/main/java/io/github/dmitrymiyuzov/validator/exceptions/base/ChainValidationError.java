package io.github.dmitrymiyuzov.validator.exceptions.base;

/**
 * @author "Dmitry Miyuzov"
 * Завершающая ошибка валидатора - она будет выброшена, если во всей цепочке - возникали только ошибки валидации
 */
public class ChainValidationError extends AssertionError {
    private Integer countValidation;
    private Integer countErrorValidation;

    public ChainValidationError(String message, Integer countValidation, Integer countErrorValidation) {
        super(message);
        this.countValidation = countValidation;
        this.countErrorValidation = countErrorValidation;
    }

    @Override
    public String toString() {
        return "\n\tChainValidationError: Ошибки произошедшие в цепочке валидаций.\n"
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
