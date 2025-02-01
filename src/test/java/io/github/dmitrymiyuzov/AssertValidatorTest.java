package io.github.dmitrymiyuzov;

import io.github.dmitrymiyuzov.validator._config.ThreadLocalValidatorFabricConfig;
import io.github.dmitrymiyuzov.validator._config.impl.ValidatorFabricConfig;
import io.github.dmitrymiyuzov.validator.base.ValidatorFabric;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;

import java.util.function.Supplier;

public class AssertValidatorTest {

    @BeforeAll
    public static void initValidator() {
        ThreadLocalValidatorFabricConfig.setConfig(
                new ValidatorFabricConfig() {
                    @Override
                    public Boolean isEnabledFilter() {
                        return true;
                    }

                    @Override
                    public String includeByContainsPackage() {
                        return "io.github.dmitrymiyuzov";
                    }

                    @Override
                    public String[] excludeByContainsClasses() {
                        return new String[] {"io.github.dmitrymiyuzov.validator"};
                    }
                }
        );
    }

    @Nested
    class Soft {
        @Nested
        @Epic("Assert")
        @Feature("Soft")
        @Story("Equals")
        class Equals {
            private Supplier<Integer> throwException(String allureMessage) {
                return () -> Allure.step(
                        allureMessage,
                        () -> Integer.parseInt("df")
                );
            }

            private Supplier<Integer> supplierInteger(String allureMessage, Integer integer) {
                return () -> Allure.step(
                        allureMessage,
                        () -> integer
                );
            }

            @Test
            @DisplayName("Успешный")
            public void assertEqualsSoft1() {
                ValidatorFabric.beginAssertValidation()
                        .assertEqualsSoft("Успешный", 1, 1)
                        .validate();
            }

            @Test
            @DisplayName("Проваленный с ошибкой от AssertJ")
            public void assertEqualsSoft2() {
                ValidatorFabric.beginAssertValidation()
                        .assertEqualsSoft("Проваленный с ошибкой от AssertJ", 1, 2)
                        .validate();
            }

            @Test
            @DisplayName("Проваленный с кастомной ошибкой.")
            public void assertEqualsSoft3() {
                ValidatorFabric.beginAssertValidation()
                        .assertEqualsSoft("Проваленный с кастомной ошибкой", 1, 2, "Моя ошибка")
                        .validate();
            }

            @Test
            @DisplayName("Проваленный, с передачей функции в актуальный результат. Ошибка в функции.")
            public void assertEqualsSoft4() {
                ValidatorFabric.beginAssertValidation()
                        .assertEqualsSoft(
                                "Проваленный, актуальный результат функцией с аллюром. Функция с ошибкой.",
                                throwException("Actual Function"),
                                1
                        )
                        .validate();
            }

            @Test
            @DisplayName("Проваленный, с передачей функции в ожидаемый результат. Ошибка в функции.")
            public void assertEqualsSoft5() {
                ValidatorFabric.beginAssertValidation()
                        .assertEqualsSoft(
                                "Проваленный, ожидаемый результат функцией с аллюром. Функция с ошибкой.",
                                1,
                                throwException("Expected Function")
                        )
                        .validate();
            }

            @Test
            @DisplayName("Проваленный, с передачей функции в актуальный и ожидаемый результат. Ошибка в функции. Ожидаемый даже не вызовется.")
            public void assertEqualsSoft6() {
                ValidatorFabric.beginAssertValidation()
                        .assertEqualsSoft(
                                "Проваленный, актуальный и ожидаемый результат функцией с аллюром. Функция с ошибкой.",
                                throwException("Actual Function"),
                                throwException("Expected Function")
                        )
                        .validate();
            }

            @Test
            @DisplayName("Проваленный, с передачей функции в актуальный результат. Без ошибки в функции.")
            public void assertEqualsSoft7() {
                ValidatorFabric.beginAssertValidation()
                        .assertEqualsSoft(
                                "Проваленный, актуальный результат функцией с аллюром. Функция успешна.",
                                supplierInteger("Actual Function", 2),
                                1
                        )
                        .validate();
            }

            @Test
            @DisplayName("Проваленный, с передачей функции в актуальный результат. Без ошибки в функции.")
            public void assertEqualsSoft8() {
                ValidatorFabric.beginAssertValidation()
                        .assertEqualsSoft(
                                "Проваленный, ожидаемый результат функцией с аллюром. Функция успешна.",
                                1,
                                supplierInteger("Expected Function", 2)
                        )
                        .validate();
            }

            @Test
            @DisplayName("Проваленный, с передачей функции в актуальный результат. Без ошибки в функции.")
            public void assertEqualsSoft9() {
                ValidatorFabric.beginAssertValidation()
                        .assertEqualsSoft(
                                "Проваленный, актуальный и ожидаемый результат функцией с аллюром. Функция успешна.",
                                supplierInteger("Actual Function", 1),
                                supplierInteger("Expected Function", 2)
                        )
                        .validate();
            }
        }
    }
}
