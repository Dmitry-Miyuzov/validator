package io.github.dmitrymiyuzov.validator.implementation.base;

import org.assertj.core.util.CheckReturnValue;

import java.util.function.Consumer;

/**
 * @author "Dmitry Miyuzov"
 */
public interface GroupValidatable<Validator, NestedValidator> {
    /**
     * Метод предназачен для вызова в цепочке валидаций. Позволяет в цепочке валидаций - создать поднезависимую цепочку (подшаг).
     * Она является софтовой - тоесть даже если она не пройдет - цепочка все равно продолжится.
     *
     * @param allureStepName Имя шага.
     */
    @CheckReturnValue
    Validator groupSoft(String allureStepName, Consumer<NestedValidator> consumer);
}
