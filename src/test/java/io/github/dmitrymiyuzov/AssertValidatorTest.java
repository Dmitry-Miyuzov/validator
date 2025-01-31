package io.github.dmitrymiyuzov;

import io.github.dmitrymiyuzov.validator.base.ValidatorFabric;
import io.qameta.allure.Allure;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

public class AssertValidatorTest {
    @Test
    public void assertEqualsSoft() {
        ValidatorFabric.beginAssertValidation()
                .assertEqualsSoft("Успешный", 1, 1)
                .assertEqualsSoft("Проваленный с ошибкой от AssertJ", 1, 2)
                .assertEqualsSoft("Проваленный с кастомной ошибкой", 1, 2, "Моя ошибка")
                .assertEqualsSoft(
                        "Успешный, актуальный результат функцией с аллюром",
                        () -> {
                            Allure.step("Актуальный");
                            return 1;
                        },
                        1
                )
                .assertEqualsSoft(
                        "Успешный, ожидаемый результат функцией с аллюром",
                        1,
                        () -> {
                            Allure.step("Ожидаемый");
                            return 1;
                        }
                )
                .assertEqualsSoft(
                        "Успешный, ожидаемый и актуальный результат функцией с аллюром",
                        (Supplier<Integer>) () -> {
                            Allure.step("Актуальный");
                            return 1;
                        },
                        (Supplier<Integer>) () -> {
                            Allure.step("Ожидаемый");
                            return 1;
                        }
                )
                .validate();
    }
}
