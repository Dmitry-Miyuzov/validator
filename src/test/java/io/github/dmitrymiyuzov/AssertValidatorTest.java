package io.github.dmitrymiyuzov;

import io.github.dmitrymiyuzov.validator._config.ThreadLocalValidatorFabricConfig;
import io.github.dmitrymiyuzov.validator._config.impl.ValidatorFabricConfig;
import io.github.dmitrymiyuzov.validator.base.ValidatorFabric;
import io.github.dmitrymiyuzov.validator.exceptions.base.ChainValidationError;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;

import java.util.function.Supplier;

public class AssertValidatorTest {

    private Supplier<Integer> throwExceptionInteger(String allureMessage) {
        return () -> Allure.step(
                allureMessage,
                () -> Integer.parseInt("df")
        );
    }

    private Supplier<Boolean> throwExceptionBoolean(String allureMessage) {
        return () -> Allure.step(
                allureMessage,
                () -> {
                    Integer.parseInt("df");
                    return false;
                }
        );
    }

    private Supplier<Integer> supplierInteger(String allureMessage, Integer integer) {
        return () -> Allure.step(
                allureMessage,
                () -> integer
        );
    }

    private Supplier<Boolean> supplierBoolean(String allureMessage, Boolean bool) {
        return () -> Allure.step(
                allureMessage,
                () -> bool
        );
    }

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
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertEqualsSoft("Проваленный с ошибкой от AssertJ", 1, 2)
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный с кастомной ошибкой.")
            public void assertEqualsSoft3() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertEqualsSoft("Проваленный с кастомной ошибкой", 1, 2, "Моя ошибка")
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }

            }

            @Test
            @DisplayName("Проваленный, с передачей функции в актуальный результат. Ошибка в функции.")
            public void assertEqualsSoft4() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertEqualsSoft(
                                    "Проваленный, актуальный результат c функцией с аллюром. Функция с ошибкой.",
                                    throwExceptionInteger("Actual Function"),
                                    1
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }

            }

            @Test
            @DisplayName("Проваленный, с передачей функции в ожидаемый результат. Ошибка в функции.")
            public void assertEqualsSoft5() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertEqualsSoft(
                                    "Проваленный, ожидаемый результат c функцией с аллюром. Функция с ошибкой.",
                                    1,
                                    throwExceptionInteger("Expected Function")
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный, с передачей функции в актуальный и ожидаемый результат. Ошибка в функции. Ожидаемый даже не вызовется.")
            public void assertEqualsSoft6() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertEqualsSoft(
                                    "Проваленный, актуальный и ожидаемый результат c функцией с аллюром. Функция с ошибкой.",
                                    throwExceptionInteger("Actual Function"),
                                    throwExceptionInteger("Expected Function")
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный, с передачей функции в актуальный результат. Без ошибки в функции.")
            public void assertEqualsSoft7() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertEqualsSoft(
                                    "Проваленный, актуальный результат c функцией с аллюром. Функция успешна.",
                                    supplierInteger("Actual Function", 2),
                                    1
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный, с передачей функции в ожидаемый результат. Без ошибки в функции.")
            public void assertEqualsSoft8() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertEqualsSoft(
                                    "Проваленный, ожидаемый результат c функцией с аллюром. Функция успешна.",
                                    1,
                                    supplierInteger("Expected Function", 2)
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный, с передачей функции в актуальный и ожидаемый результат. Без ошибки в функции.")
            public void assertEqualsSoft9() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertEqualsSoft(
                                    "Проваленный, актуальный и ожидаемый результат c функцией с аллюром. Функция успешна.",
                                    supplierInteger("Actual Function", 1),
                                    supplierInteger("Expected Function", 2)
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный, две упали, одна прошла..")
            public void assertEqualsSoft10() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertEqualsSoft(
                                    "Проваленная 1",
                                    2,
                                    1
                            )
                            .assertEqualsSoft(
                                    "Успешная",
                                    1,
                                    1
                            )
                            .assertEqualsSoft(
                                    "Проваленная 2",
                                    1,
                                    2
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }
        }

        @Nested
        @Epic("Assert")
        @Feature("Soft")
        @Story("Not Equals")
        class NotEquals {

            @Test
            @DisplayName("Успешный")
            public void assertNotEqualsSoft1() {
                ValidatorFabric.beginAssertValidation()
                        .assertNotEqualsSoft("Успешный", 1, 2)
                        .validate();
            }

            @Test
            @DisplayName("Проваленный с ошибкой от AssertJ")
            public void assertNotEqualsSoft2() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertNotEqualsSoft("Проваленный с ошибкой от AssertJ", 1, 1)
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный с кастомной ошибкой.")
            public void assertNotEqualsSoft3() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertNotEqualsSoft("Проваленный с кастомной ошибкой", 1, 1, "Моя ошибка")
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный, с передачей функции в актуальный результат. Ошибка в функции.")
            public void assertNotEqualsSoft4() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertNotEqualsSoft(
                                    "Проваленный, актуальный результат c функцией с аллюром. Функция с ошибкой.",
                                    throwExceptionInteger("Actual Function"),
                                    1
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный, с передачей функции в ожидаемый результат. Ошибка в функции.")
            public void assertNotEqualsSoft5() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertNotEqualsSoft(
                                    "Проваленный, ожидаемый результат c функцией с аллюром. Функция с ошибкой.",
                                    1,
                                    throwExceptionInteger("Expected Function")
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный, с передачей функции в актуальный и ожидаемый результат. Ошибка в функции. Ожидаемый даже не вызовется.")
            public void assertNotEqualsSoft6() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertNotEqualsSoft(
                                    "Проваленный, актуальный и ожидаемый результат c функцией с аллюром. Функция с ошибкой.",
                                    throwExceptionInteger("Actual Function"),
                                    throwExceptionInteger("Expected Function")
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный, с передачей функции в актуальный результат. Без ошибки в функции.")
            public void assertNotEqualsSoft7() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertNotEqualsSoft(
                                    "Проваленный, актуальный результат c функцией с аллюром. Функция успешна.",
                                    supplierInteger("Actual Function", 1),
                                    1
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный, с передачей функции в ожидаемый результат. Без ошибки в функции.")
            public void assertNotEqualsSoft8() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertNotEqualsSoft(
                                    "Проваленный, ожидаемый результат c функцией с аллюром. Функция успешна.",
                                    1,
                                    supplierInteger("Expected Function", 1)
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный, с передачей функции в актуальный и ожидаемый результат. Без ошибки в функции.")
            public void assertNotEqualsSoft9() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertNotEqualsSoft(
                                    "Проваленный, актуальный и ожидаемый результат c функцией с аллюром. Функция успешна.",
                                    supplierInteger("Actual Function", 1),
                                    supplierInteger("Expected Function", 1)
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный, две упали, одна прошла..")
            public void assertNotEqualsSoft10() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertNotEqualsSoft(
                                    "Проваленная 1",
                                    2,
                                    2
                            )
                            .assertNotEqualsSoft(
                                    "Успешная",
                                    1,
                                    2
                            )
                            .assertNotEqualsSoft(
                                    "Проваленная 2",
                                    2,
                                    2
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }
        }

        @Nested
        @Epic("Assert")
        @Feature("Soft")
        @Story("True")
        class True {
            @Test
            @DisplayName("Успешный")
            public void assertTrueSoft1() {
                ValidatorFabric.beginAssertValidation()
                        .assertTrueSoft("Успешный", true)
                        .validate();
            }

            @Test
            @DisplayName("Проваленный с ошибкой от AssertJ")
            public void assertTrueSoft2() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertTrueSoft("Проваленный с ошибкой от AssertJ", false)
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный с кастомной ошибкой.")
            public void assertTrueSoft3() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertTrueSoft("Проваленный с кастомной ошибкой", false, "Моя ошибка")
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный, с передачей функции. Ошибка в функции.")
            public void assertTrueSoft4() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertTrueSoft(
                                    "Проваленный, функцией с аллюром. Функция с ошибкой.",
                                    throwExceptionBoolean("Actual Function")
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный, с передачей функции. Без ошибки в функции.")
            public void assertTrueSoft5() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertTrueSoft(
                                    "Проваленный, функцией с аллюром. Функция успешна.",
                                    supplierBoolean("Function", false)
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный, две упали, одна прошла..")
            public void assertTrueSoft6() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertTrueSoft(
                                    "Проваленная 1",
                                    false
                            )
                            .assertTrueSoft(
                                    "Успешная",
                                    true
                            )
                            .assertTrueSoft(
                                    "Проваленная 2",
                                    false
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }
        }

        @Nested
        @Epic("Assert")
        @Feature("Soft")
        @Story("False")
        class False {
            @Test
            @DisplayName("Успешный")
            public void assertFalseSoft1() {
                ValidatorFabric.beginAssertValidation()
                        .assertFalseSoft("Успешный", false)
                        .validate();
            }

            @Test
            @DisplayName("Проваленный с ошибкой от AssertJ")
            public void assertFalseSoft2() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertFalseSoft("Проваленный с ошибкой от AssertJ", true)
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный с кастомной ошибкой.")
            public void assertFalseSoft3() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertFalseSoft("Проваленный с кастомной ошибкой", true, "Моя ошибка")
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный, с передачей функции. Ошибка в функции.")
            public void assertFalseSoft4() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertFalseSoft(
                                    "Проваленный, функцией с аллюром. Функция с ошибкой.",
                                    throwExceptionBoolean("Actual Function")
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный, с передачей функции. Без ошибки в функции.")
            public void assertFalseSoft5() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertFalseSoft(
                                    "Проваленный, функцией с аллюром. Функция успешна.",
                                    supplierBoolean("Function", true)
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный, две упали, одна прошла..")
            public void assertFalseSoft6() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertFalseSoft(
                                    "Проваленная 1",
                                    true
                            )
                            .assertFalseSoft(
                                    "Успешная",
                                    false
                            )
                            .assertFalseSoft(
                                    "Проваленная 2",
                                    true
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }
        }

        @Nested
        @Epic("Assert")
        @Feature("Soft")
        @Story("Null")
        class Null {
            @Test
            @DisplayName("Успешный")
            public void assertNullSoft1() {
                ValidatorFabric.beginAssertValidation()
                        .assertNullSoft("Успешный", null)
                        .validate();
            }

            @Test
            @DisplayName("Проваленный с ошибкой от AssertJ")
            public void assertNullSoft2() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertNullSoft("Проваленный с ошибкой от AssertJ", new Object())
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный с кастомной ошибкой.")
            public void assertNullSoft3() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertNullSoft("Проваленный с кастомной ошибкой", new Object(), "Моя ошибка")
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный, с передачей функции. Ошибка в функции.")
            public void assertNullSoft4() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertNullSoft(
                                    "Проваленный, функцией с аллюром. Функция с ошибкой.",
                                    throwExceptionBoolean("Actual Function")
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный, с передачей функции. Без ошибки в функции.")
            public void assertNullSoft5() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertNullSoft(
                                    "Проваленный, функцией с аллюром. Функция успешна.",
                                    supplierBoolean("Function", true)
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный, две упали, одна прошла..")
            public void assertNullSoft6() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertNullSoft(
                                    "Проваленная 1",
                                    true
                            )
                            .assertNullSoft(
                                    "Успешная",
                                    null
                            )
                            .assertNullSoft(
                                    "Проваленная 2",
                                    true
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }
        }

        @Nested
        @Epic("Assert")
        @Feature("Soft")
        @Story("Not null")
        class NotNull {
            @Test
            @DisplayName("Успешный")
            public void assertNotNullSoft1() {
                ValidatorFabric.beginAssertValidation()
                        .assertNotNullSoft("Успешный", new Object())
                        .validate();
            }

            @Test
            @DisplayName("Проваленный с ошибкой от AssertJ")
            public void assertNotNullSoft2() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertNotNullSoft("Проваленный с ошибкой от AssertJ", null)
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный с кастомной ошибкой.")
            public void assertNotNullSoft3() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertNotNullSoft("Проваленный с кастомной ошибкой", null, "Моя ошибка")
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный, с передачей функции. Ошибка в функции.")
            public void assertNotNullSoft4() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertNotNullSoft(
                                    "Проваленный, функцией с аллюром. Функция с ошибкой.",
                                    throwExceptionBoolean("Actual Function")
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный, с передачей функции. Без ошибки в функции.")
            public void assertNotNullSoft5() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertNotNullSoft(
                                    "Проваленный, функцией с аллюром. Функция успешна.",
                                    () -> {
                                        Allure.step("Function");
                                        return null;
                                    }
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("Проваленный, две упали, одна прошла..")
            public void assertNotNullSoft6() {
                try {
                    ValidatorFabric.beginAssertValidation()
                            .assertNotNullSoft(
                                    "Проваленная 1",
                                    null
                            )
                            .assertNotNullSoft(
                                    "Успешная",
                                    new Object()
                            )
                            .assertNotNullSoft(
                                    "Проваленная 2",
                                    null
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
