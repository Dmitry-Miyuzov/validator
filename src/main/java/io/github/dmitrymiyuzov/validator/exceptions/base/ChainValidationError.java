package io.github.dmitrymiyuzov.validator.exceptions.base;

/**
 * @author "Dmitry Miyuzov"
 * Завершающая ошибка валидатора - она будет выброшена, если во всей цепочке - возникали только ошибки валидации
 */
public class ChainValidationError extends AssertionError {

    public ChainValidationError(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "\n\tChainValidationError: Ошибки произошедшие в цепочке валидаций.\n"
                .concat("\t").concat(getMessage()).concat("\n")
                .concat("\t----------------------------------------------------------------------------------------------------------");
    }



}
