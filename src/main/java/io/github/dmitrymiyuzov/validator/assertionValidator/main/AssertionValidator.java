package io.github.dmitrymiyuzov.validator.assertionValidator.main;

import io.github.dmitrymiyuzov.validator.allure.AllureConfig;
import io.github.dmitrymiyuzov.validator.allure.AllureConfigurable;
import io.github.dmitrymiyuzov.validator.allure.AllureStepValidator;
import io.github.dmitrymiyuzov.validator.base.Validator;
import io.github.dmitrymiyuzov.validator.exceptions.ErrorManager;
import io.github.dmitrymiyuzov.validator.implementation.assertion.AssertValidatable;
import io.github.dmitrymiyuzov.validator.implementation.assertion.AssertValidatableSoft;
import io.github.dmitrymiyuzov.validator.implementation.assertion.ThrowingCallable;
import io.github.dmitrymiyuzov.validator.implementation.base.Allurable;
import io.github.dmitrymiyuzov.validator.implementation.base.GroupValidatable;
import io.github.dmitrymiyuzov.validator.implementation.base.Validatable;
import io.qameta.allure.model.Status;
import org.assertj.core.api.Assertions;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author "Dmitry Miyuzov"
 */
public class AssertionValidator implements
        Validatable,
        GroupValidatable<AssertionValidator, AssertionNestedValidator>,
        AssertValidatable<AssertionValidator>,
        AssertValidatableSoft<AssertionValidator>,
        Allurable<AssertionValidator> {
    private final Validator validator;
    private final AllureConfig allureConfig;

    public AssertionValidator(Validator validator) {
        this.validator = validator;
        this.allureConfig = this.validator.getAllureConfig();
    }


    /******************************************ASSERT THAT CODE DOES NOT EXCEPTION*************************************/
    @Override
    public AssertionValidator assertThatCodeDoesNotException(String allureStepName, ThrowingCallable call) {
        this.validator.getAssertValidation().validationThatCodeDoesNotException(
                allureStepName,
                call,
                null
        );
        return this;
    }
    @Override
    public AssertionValidator assertThatCodeDoesNotException(String allureStepName, ThrowingCallable call, String errorMessageAllure) {
        this.validator.getAssertValidation().validationThatCodeDoesNotException(
                allureStepName,
                call,
                errorMessageAllure
        );
        return this;
    }
    @Override
    public AssertionValidator assertThatCodeDoesNotExceptionSoft(String allureStepName, ThrowingCallable call) {
        this.validator.getAssertValidation().validationThatCodeDoesNotExceptionSoft(
                allureStepName,
                call,
                null
        );
        return this;
    }
    @Override
    public AssertionValidator assertThatCodeDoesNotExceptionSoft(String allureStepName, ThrowingCallable call, String errorMessageAllure) {
        this.validator.getAssertValidation().validationThatCodeDoesNotExceptionSoft(
                allureStepName,
                call,
                errorMessageAllure
        );
        return this;
    }

    /******************************************EQUALS*************************************/
    @Override
    public <T> AssertionValidator assertEquals(String allureStepName, T actual, T expected) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(actual).isEqualTo(expected)
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertEquals(String allureStepName, T actual, T expected, String errorMessage) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(actual).withFailMessage(errorMessage).isEqualTo(expected),
                errorMessage
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertEquals(String allureStepName, Supplier<T> actual, T expected) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(actual.get()).isEqualTo(expected)
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertEquals(String allureStepName, Supplier<T> actual, T expected, String errorMessage) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(actual.get()).withFailMessage(errorMessage).isEqualTo(expected),
                errorMessage
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertEquals(String allureStepName, Supplier<T> actual, Supplier<T> expected) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(actual.get()).isEqualTo(expected.get())
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertEquals(String allureStepName, Supplier<T> actual, Supplier<T> expected, String errorMessage) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(actual.get()).withFailMessage(errorMessage).isEqualTo(expected.get()),
                errorMessage
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertEquals(String allureStepName, T actual, Supplier<T> expected) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(actual).isEqualTo(expected.get())
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertEquals(String allureStepName, T actual, Supplier<T> expected, String errorMessage) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(actual).withFailMessage(errorMessage).isEqualTo(expected.get()),
                errorMessage
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertEqualsSoft(String allureStepName, T actual, T expected) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(actual).isEqualTo(expected)
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertEqualsSoft(String allureStepName, T actual, T expected, String errorMessage) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(actual).withFailMessage(errorMessage).isEqualTo(expected),
                errorMessage
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertEqualsSoft(String allureStepName, Supplier<T> actual, T expected) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(actual.get()).isEqualTo(expected)
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertEqualsSoft(String allureStepName, Supplier<T> actual, T expected, String errorMessage) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(actual.get()).withFailMessage(errorMessage).isEqualTo(expected),
                errorMessage
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertEqualsSoft(String allureStepName, Supplier<T> actual, Supplier<T> expected) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(actual.get()).isEqualTo(expected.get())
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertEqualsSoft(String allureStepName, Supplier<T> actual, Supplier<T> expected, String errorMessage) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(actual.get()).withFailMessage(errorMessage).isEqualTo(expected.get()),
                errorMessage
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertEqualsSoft(String allureStepName, T actual, Supplier<T> expected) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(actual).isEqualTo(expected.get())
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertEqualsSoft(String allureStepName, T actual, Supplier<T> expected, String errorMessage) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(actual).withFailMessage(errorMessage).isEqualTo(expected.get()),
                errorMessage
        );
        return this;
    }

    /******************************************NOT EQUALS*************************************/
    @Override
    public <T> AssertionValidator assertNotEquals(String allureStepName, T actual, T expected) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(actual).isNotEqualTo(expected)
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertNotEquals(String allureStepName, T actual, T expected, String errorMessage) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(actual).withFailMessage(errorMessage).isNotEqualTo(expected),
                errorMessage
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertNotEquals(String allureStepName, Supplier<T> actual, T expected) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(actual.get()).isNotEqualTo(expected)
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertNotEquals(String allureStepName, Supplier<T> actual, T expected, String errorMessage) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(actual.get()).withFailMessage(errorMessage).isNotEqualTo(expected),
                errorMessage
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertNotEquals(String allureStepName, Supplier<T> actual, Supplier<T> expected) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(actual.get()).isNotEqualTo(expected.get())
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertNotEquals(String allureStepName, Supplier<T> actual, Supplier<T> expected, String errorMessage) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(actual.get()).withFailMessage(errorMessage).isNotEqualTo(expected.get()),
                errorMessage
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertNotEquals(String allureStepName, T actual, Supplier<T> expected) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(actual).isNotEqualTo(expected.get())
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertNotEquals(String allureStepName, T actual, Supplier<T> expected, String errorMessage) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(actual).withFailMessage(errorMessage).isNotEqualTo(expected.get()),
                errorMessage
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertNotEqualsSoft(String allureStepName, T actual, T expected) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(actual).isNotEqualTo(expected)
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertNotEqualsSoft(String allureStepName, T actual, T expected, String errorMessage) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(actual).withFailMessage(errorMessage).isNotEqualTo(expected),
                errorMessage
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertNotEqualsSoft(String allureStepName, Supplier<T> actual, T expected) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(actual.get()).isNotEqualTo(expected)
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertNotEqualsSoft(String allureStepName, Supplier<T> actual, T expected, String errorMessage) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(actual.get()).withFailMessage(errorMessage).isNotEqualTo(expected),
                errorMessage
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertNotEqualsSoft(String allureStepName, Supplier<T> actual, Supplier<T> expected) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(actual.get()).isNotEqualTo(expected.get())
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertNotEqualsSoft(String allureStepName, Supplier<T> actual, Supplier<T> expected, String errorMessage) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(actual.get()).withFailMessage(errorMessage).isNotEqualTo(expected.get()),
                errorMessage
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertNotEqualsSoft(String allureStepName, T actual, Supplier<T> expected) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(actual).isNotEqualTo(expected.get())
        );
        return this;
    }
    @Override
    public <T> AssertionValidator assertNotEqualsSoft(String allureStepName, T actual, Supplier<T> expected, String errorMessage) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(actual).withFailMessage(errorMessage).isNotEqualTo(expected.get()),
                errorMessage
        );
        return this;
    }

    /******************************************TRUE*************************************/
    @Override
    public AssertionValidator assertTrue(String allureStepName, boolean condition) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(condition).isTrue()
        );
        return this;
    }
    @Override
    public AssertionValidator assertTrue(String allureStepName, boolean condition, String errorMessage) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(condition).withFailMessage(errorMessage).isTrue(),
                errorMessage
        );
        return this;
    }
    @Override
    public AssertionValidator assertTrue(String allureStepName, Supplier<Boolean> condition) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(condition.get()).isTrue()
        );
        return this;
    }
    @Override
    public AssertionValidator assertTrue(String allureStepName, Supplier<Boolean> condition, String errorMessage) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(condition.get()).withFailMessage(errorMessage).isTrue()
        );
        return this;
    }
    @Override
    public AssertionValidator assertTrueSoft(String allureStepName, boolean condition) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(condition).isTrue()
        );
        return this;
    }
    @Override
    public AssertionValidator assertTrueSoft(String allureStepName, boolean condition, String errorMessage) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(condition).withFailMessage(errorMessage).isTrue(),
                errorMessage
        );
        return this;
    }
    @Override
    public AssertionValidator assertTrueSoft(String allureStepName, Supplier<Boolean> condition) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(condition.get()).isTrue()
        );
        return this;
    }
    @Override
    public AssertionValidator assertTrueSoft(String allureStepName, Supplier<Boolean> condition, String errorMessage) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(condition.get()).withFailMessage(errorMessage).isTrue(),
                errorMessage
        );
        return this;
    }

    /******************************************FALSE*************************************/
    @Override
    public AssertionValidator assertFalse(String allureStepName, boolean condition) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(condition).isFalse()
        );
        return this;
    }
    @Override
    public AssertionValidator assertFalse(String allureStepName, boolean condition, String errorMessage) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(condition).withFailMessage(errorMessage).isFalse(),
                errorMessage
        );
        return this;
    }
    @Override
    public AssertionValidator assertFalse(String allureStepName, Supplier<Boolean> condition) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(condition.get()).isFalse()
        );
        return this;
    }
    @Override
    public AssertionValidator assertFalse(String allureStepName, Supplier<Boolean> condition, String errorMessage) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(condition.get()).withFailMessage(errorMessage).isFalse(),
                errorMessage
        );
        return this;
    }
    @Override
    public AssertionValidator assertFalseSoft(String allureStepName, boolean condition) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(condition).isFalse()
        );
        return this;
    }
    @Override
    public AssertionValidator assertFalseSoft(String allureStepName, boolean condition, String errorMessage) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(condition).withFailMessage(errorMessage).isFalse(),
                errorMessage
        );
        return this;
    }
    @Override
    public AssertionValidator assertFalseSoft(String allureStepName, Supplier<Boolean> condition) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(condition.get()).isFalse()
        );
        return this;
    }
    @Override
    public AssertionValidator assertFalseSoft(String allureStepName, Supplier<Boolean> condition, String errorMessage) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(condition.get()).withFailMessage(errorMessage).isFalse(),
                errorMessage
        );
        return this;
    }

    /******************************************NULL*************************************/
    @Override
    public AssertionValidator assertNull(String allureStepName, Object object) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(object).isNull()
        );
        return this;
    }
    @Override
    public AssertionValidator assertNull(String allureStepName, Object object, String errorMessage) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(object).withFailMessage(errorMessage).isNull(),
                errorMessage
        );
        return this;
    }
    @Override
    public AssertionValidator assertNull(String allureStepName, Supplier<Object> object) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(object.get()).isNull()
        );
        return this;
    }
    @Override
    public AssertionValidator assertNull(String allureStepName, Supplier<Object> object, String errorMessage) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(object.get()).withFailMessage(errorMessage).isNull(),
                errorMessage
        );
        return this;
    }
    @Override
    public AssertionValidator assertNullSoft(String allureStepName, Object object) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(object).isNull()
        );
        return this;
    }
    @Override
    public AssertionValidator assertNullSoft(String allureStepName, Object object, String errorMessage) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(object).withFailMessage(errorMessage).isNull(),
                errorMessage
        );
        return this;
    }
    @Override
    public AssertionValidator assertNullSoft(String allureStepName, Supplier<Object> object) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(object.get()).isNull()
        );
        return this;
    }
    @Override
    public AssertionValidator assertNullSoft(String allureStepName, Supplier<Object> object, String errorMessage) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(object.get()).withFailMessage(errorMessage).isNull(),
                errorMessage
        );
        return this;
    }

    /******************************************NOT NULL*************************************/
    @Override
    public AssertionValidator assertNotNull(String allureStepName, Object object) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(object).isNotNull()
        );
        return this;
    }
    @Override
    public AssertionValidator assertNotNull(String allureStepName, Object object, String errorMessage) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(object).withFailMessage(errorMessage).isNotNull(),
                errorMessage
        );
        return this;
    }
    @Override
    public AssertionValidator assertNotNull(String allureStepName, Supplier<Object> object) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(object.get()).isNotNull()
        );
        return this;
    }
    @Override
    public AssertionValidator assertNotNull(String allureStepName, Supplier<Object> object, String errorMessage) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(object.get()).withFailMessage(errorMessage).isNotNull(),
                errorMessage
        );
        return this;
    }
    @Override
    public AssertionValidator assertNotNullSoft(String allureStepName, Object object) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(object).isNotNull()
        );
        return this;
    }
    @Override
    public AssertionValidator assertNotNullSoft(String allureStepName, Object object, String errorMessage) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(object).withFailMessage(errorMessage).isNotNull(),
                errorMessage
        );
        return this;
    }
    @Override
    public AssertionValidator assertNotNullSoft(String allureStepName, Supplier<Object> object) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(object.get()).isNotNull()
        );
        return this;
    }
    @Override
    public AssertionValidator assertNotNullSoft(String allureStepName, Supplier<Object> object, String errorMessage) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(object.get()).withFailMessage(errorMessage).isNotNull(),
                errorMessage
        );
        return this;
    }

    /******************************************LOCAL DATE TIME*************************************/
    private Runnable assertLocalDateTimeFunction(String nameActualDate, String nameExpectedDate,
                                                 LocalDateTime actual, LocalDateTime expected, DateTimeFormatter format) {
        return () -> {
            String actualDate = actual.format(format);
            String expectedDate = expected.format(format);

            AllureStepValidator.doStep(
                    "Актуальная дата - %s, в формате - %s".formatted(nameActualDate, actualDate),
                    Status.PASSED,
                    allureConfig.isDisableValidationNameAllure()
            );
            AllureStepValidator.doStep(
                    "Ожидаемая дата - %s, в формате - %s".formatted(nameExpectedDate, expectedDate),
                    Status.PASSED,
                    allureConfig.isDisableValidationNameAllure()
            );

            Assertions.assertThat(actualDate).withFailMessage("Актуальная дата - %s, Ожидаемая дата - %s".formatted(actualDate, expectedDate))
                    .isEqualTo(expected);
        };
    }
    @Override
    public AssertionValidator assertLocalDateTime(String nameActualDate, String nameExpectedDate,
                                                  LocalDateTime actual, LocalDateTime expected, DateTimeFormatter format) {
        this.validator.getAssertValidation().validationDate(
                "Сравнить даты в указанном формате",
                assertLocalDateTimeFunction(nameActualDate, nameExpectedDate, actual, expected, format)
        );
        return this;
    }
    @Override
    public AssertionValidator assertLocalDateTimeSoft(String nameActualDate, String nameExpectedDate,
                                                      LocalDateTime actual, LocalDateTime expected, DateTimeFormatter format) {
        this.validator.getAssertValidation().validationDateSoft(
                "Сравнить даты в указанном формате",
                assertLocalDateTimeFunction(nameActualDate, nameExpectedDate, actual, expected, format)
        );
        return this;
    }

    /******************************************ZONED DATE TIME*************************************/
    @Override
    public AssertionValidator assertZonedDateTimeWithToleranceHours(String allureStepName, ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceHours) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(assertZonedDateTimeWithToleranceHoursSoft(z1, z2, allowDifferenceHours))
                        .withFailMessage(
                                "Разница в часах - %s, Допустимая разница в часах - %s.".formatted(
                                        getDifferenceHours(z1, z2),
                                        allowDifferenceHours
                                )
                        )
                        .isTrue()
        );
        return this;
    }
    @Override
    public AssertionValidator assertZonedDateTimeWithToleranceHours(String allureStepName, Supplier<ZonedDateTime> z1, ZonedDateTime z2, long allowDifferenceHours) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(assertZonedDateTimeWithToleranceHoursSoft(z1.get(), z2, allowDifferenceHours))
                        .withFailMessage(
                                "Допустимая разница в часах - %s.".formatted(allowDifferenceHours)
                        )
                        .isTrue()
        );
        return this;
    }
    @Override
    public AssertionValidator assertZonedDateTimeWithToleranceHours(String allureStepName, ZonedDateTime z1, Supplier<ZonedDateTime> z2, long allowDifferenceHours) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(assertZonedDateTimeWithToleranceHoursSoft(z1, z2.get(), allowDifferenceHours))
                        .withFailMessage(
                                "Допустимая разница в часах - %s.".formatted(allowDifferenceHours)
                        )
                        .isTrue()
        );
        return this;
    }
    @Override
    public AssertionValidator assertZonedDateTimeWithToleranceHours(String allureStepName, Supplier<ZonedDateTime> z1, Supplier<ZonedDateTime> z2, long allowDifferenceHours) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(assertZonedDateTimeWithToleranceHoursSoft(z1.get(), z2.get(), allowDifferenceHours))
                        .withFailMessage(
                                "Допустимая разница в часах - %s.".formatted(allowDifferenceHours)
                        )
                        .isTrue()
        );
        return this;
    }
    @Override
    public AssertionValidator assertZonedDateTimeWithToleranceHoursSoft(String allureStepName, ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceHours) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(assertZonedDateTimeWithToleranceHoursSoft(z1, z2, allowDifferenceHours))
                        .withFailMessage(
                                "Разница в часах - %s, Допустимая разница в часах - %s.".formatted(
                                        getDifferenceHours(z1, z2),
                                        allowDifferenceHours
                                )
                        )
                        .isTrue()
        );
        return this;
    }
    @Override
    public AssertionValidator assertZonedDateTimeWithToleranceHoursSoft(String allureStepName, Supplier<ZonedDateTime> z1, ZonedDateTime z2, long allowDifferenceHours) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(assertZonedDateTimeWithToleranceHoursSoft(z1.get(), z2, allowDifferenceHours))
                        .withFailMessage(
                                "Допустимая разница в часах - %s.".formatted(allowDifferenceHours)
                        )
                        .isTrue()
        );
        return this;
    }
    @Override
    public AssertionValidator assertZonedDateTimeWithToleranceHoursSoft(String allureStepName, ZonedDateTime z1, Supplier<ZonedDateTime> z2, long allowDifferenceHours) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(assertZonedDateTimeWithToleranceHoursSoft(z1, z2.get(), allowDifferenceHours))
                        .withFailMessage(
                                "Допустимая разница в часах - %s.".formatted(allowDifferenceHours)
                        )
                        .isTrue()
        );
        return this;
    }
    @Override
    public AssertionValidator assertZonedDateTimeWithToleranceHoursSoft(String allureStepName, Supplier<ZonedDateTime> z1, Supplier<ZonedDateTime> z2, long allowDifferenceHours) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(assertZonedDateTimeWithToleranceHoursSoft(z1.get(), z2.get(), allowDifferenceHours))
                        .withFailMessage(
                                "Допустимая разница в часах - %s.".formatted(allowDifferenceHours)
                        )
                        .isTrue()
        );
        return this;
    }
    private boolean assertZonedDateTimeWithToleranceHoursSoft(ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceHours) {
        Duration duration = Duration.between(z1, z2);
        long differenceInHours = Math.abs(duration.toHours());

        return differenceInHours <= allowDifferenceHours;
    }
    private long getDifferenceHours(ZonedDateTime z1, ZonedDateTime z2) {
        Duration duration = Duration.between(z1, z2);
        return Math.abs(duration.toHours());
    }

    @Override
    public AssertionValidator assertZonedDateTimeWithToleranceMinutes(String allureStepName, ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceMinutes) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(assertZonedDateTimeWithToleranceMinutesSoft(z1, z2, allowDifferenceMinutes))
                        .withFailMessage(
                                "Разница в минутах - %s, Допустимая разница в минутах - %s.".formatted(
                                        getDifferenceMinutes(z1, z2),
                                        allowDifferenceMinutes
                                )
                        )
                        .isTrue()
        );
        return this;
    }
    @Override
    public AssertionValidator assertZonedDateTimeWithToleranceMinutes(String allureStepName, Supplier<ZonedDateTime> z1, ZonedDateTime z2, long allowDifferenceMinutes) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(assertZonedDateTimeWithToleranceMinutesSoft(z1.get(), z2, allowDifferenceMinutes))
                        .withFailMessage(
                                "Допустимая разница в минутах - %s.".formatted(allowDifferenceMinutes)
                        )
                        .isTrue()
        );
        return this;
    }
    @Override
    public AssertionValidator assertZonedDateTimeWithToleranceMinutes(String allureStepName, ZonedDateTime z1, Supplier<ZonedDateTime> z2, long allowDifferenceMinutes) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(assertZonedDateTimeWithToleranceMinutesSoft(z1, z2.get(), allowDifferenceMinutes))
                        .withFailMessage(
                                "Допустимая разница в минутах - %s.".formatted(allowDifferenceMinutes)
                        )
                        .isTrue()
        );
        return this;
    }
    @Override
    public AssertionValidator assertZonedDateTimeWithToleranceMinutes(String allureStepName, Supplier<ZonedDateTime> z1, Supplier<ZonedDateTime> z2, long allowDifferenceMinutes) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(assertZonedDateTimeWithToleranceMinutesSoft(z1.get(), z2.get(), allowDifferenceMinutes))
                        .withFailMessage(
                                "Допустимая разница в минутах - %s.".formatted(allowDifferenceMinutes)
                        )
                        .isTrue()
        );
        return this;
    }
    @Override
    public AssertionValidator assertZonedDateTimeWithToleranceMinutesSoft(String allureStepName, ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceMinutes) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(assertZonedDateTimeWithToleranceMinutesSoft(z1, z2, allowDifferenceMinutes))
                        .withFailMessage(
                                "Разница в минутах - %s, Допустимая разница в минутах - %s.".formatted(
                                        getDifferenceMinutes(z1, z2),
                                        allowDifferenceMinutes
                                )
                        )
                        .isTrue()
        );
        return this;
    }
    @Override
    public AssertionValidator assertZonedDateTimeWithToleranceMinutesSoft(String allureStepName, Supplier<ZonedDateTime> z1, ZonedDateTime z2, long allowDifferenceMinutes) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(assertZonedDateTimeWithToleranceMinutesSoft(z1.get(), z2, allowDifferenceMinutes))
                        .withFailMessage(
                                "Допустимая разница в минутах - %s.".formatted(allowDifferenceMinutes)
                        )
                        .isTrue()
        );
        return this;
    }
    @Override
    public AssertionValidator assertZonedDateTimeWithToleranceMinutesSoft(String allureStepName, ZonedDateTime z1, Supplier<ZonedDateTime> z2, long allowDifferenceMinutes) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(assertZonedDateTimeWithToleranceMinutesSoft(z1, z2.get(), allowDifferenceMinutes))
                        .withFailMessage(
                                "Допустимая разница в минутах - %s.".formatted(allowDifferenceMinutes)
                        )
                        .isTrue()
        );
        return this;
    }
    @Override
    public AssertionValidator assertZonedDateTimeWithToleranceMinutesSoft(String allureStepName, Supplier<ZonedDateTime> z1, Supplier<ZonedDateTime> z2, long allowDifferenceMinutes) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(assertZonedDateTimeWithToleranceMinutesSoft(z1.get(), z2.get(), allowDifferenceMinutes))
                        .withFailMessage(
                                "Допустимая разница в минутах - %s.".formatted(allowDifferenceMinutes)
                        )
                        .isTrue()
        );
        return this;
    }
    private boolean assertZonedDateTimeWithToleranceMinutesSoft(ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceMinutes) {
        Duration duration = Duration.between(z1, z2);
        long differenceInMinutes = Math.abs(duration.toMinutes());

        return differenceInMinutes <= allowDifferenceMinutes;
    }
    private long getDifferenceMinutes(ZonedDateTime z1, ZonedDateTime z2) {
        Duration duration = Duration.between(z1, z2);
        return Math.abs(duration.toMinutes());
    }

    @Override
    public AssertionValidator assertZonedDateTimeWithToleranceSeconds(String allureStepName, ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceSeconds) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(assertZonedDateTimeWithToleranceSecondsSoft(z1, z2, allowDifferenceSeconds))
                        .withFailMessage(
                                "Разница в секундах - %s, Допустимая разница в секундах - %s.".formatted(
                                        getDifferenceSeconds(z1, z2),
                                        allowDifferenceSeconds
                                )
                        )
                        .isTrue()
        );
        return this;
    }
    @Override
    public AssertionValidator assertZonedDateTimeWithToleranceSeconds(String allureStepName, Supplier<ZonedDateTime> z1, ZonedDateTime z2, long allowDifferenceSeconds) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(assertZonedDateTimeWithToleranceSecondsSoft(z1.get(), z2, allowDifferenceSeconds))
                        .withFailMessage(
                                "Допустимая разница в секундах - %s.".formatted(allowDifferenceSeconds)
                        )
                        .isTrue()
        );
        return this;
    }
    @Override
    public AssertionValidator assertZonedDateTimeWithToleranceSeconds(String allureStepName, ZonedDateTime z1, Supplier<ZonedDateTime> z2, long allowDifferenceSeconds) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(assertZonedDateTimeWithToleranceSecondsSoft(z1, z2.get(), allowDifferenceSeconds))
                        .withFailMessage(
                                "Допустимая разница в секундах - %s.".formatted(allowDifferenceSeconds)
                        )
                        .isTrue()
        );
        return this;
    }
    @Override
    public AssertionValidator assertZonedDateTimeWithToleranceSeconds(String allureStepName, Supplier<ZonedDateTime> z1, Supplier<ZonedDateTime> z2, long allowDifferenceSeconds) {
        this.validator.getAssertValidation().validation(
                allureStepName,
                () -> Assertions.assertThat(assertZonedDateTimeWithToleranceSecondsSoft(z1.get(), z2.get(), allowDifferenceSeconds))
                        .withFailMessage(
                                "Допустимая разница в секундах - %s.".formatted(allowDifferenceSeconds)
                        )
                        .isTrue()
        );
        return this;
    }
    @Override
    public AssertionValidator assertZonedDateTimeWithToleranceSecondsSoft(String allureStepName, ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceSeconds) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(assertZonedDateTimeWithToleranceSecondsSoft(z1, z2, allowDifferenceSeconds))
                        .withFailMessage(
                                "Разница в секундах - %s, Допустимая разница в секундах - %s.".formatted(
                                        getDifferenceSeconds(z1, z2),
                                        allowDifferenceSeconds
                                )
                        )
                        .isTrue()
        );
        return this;
    }
    @Override
    public AssertionValidator assertZonedDateTimeWithToleranceSecondsSoft(String allureStepName, Supplier<ZonedDateTime> z1, ZonedDateTime z2, long allowDifferenceSeconds) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(assertZonedDateTimeWithToleranceSecondsSoft(z1.get(), z2, allowDifferenceSeconds))
                        .withFailMessage(
                                "Допустимая разница в секундах - %s.".formatted(allowDifferenceSeconds)
                        )
                        .isTrue()
        );
        return this;
    }
    @Override
    public AssertionValidator assertZonedDateTimeWithToleranceSecondsSoft(String allureStepName, ZonedDateTime z1, Supplier<ZonedDateTime> z2, long allowDifferenceSeconds) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(assertZonedDateTimeWithToleranceSecondsSoft(z1, z2.get(), allowDifferenceSeconds))
                        .withFailMessage(
                                "Допустимая разница в секундах - %s.".formatted(allowDifferenceSeconds)
                        )
                        .isTrue()
        );
        return this;
    }
    @Override
    public AssertionValidator assertZonedDateTimeWithToleranceSecondsSoft(String allureStepName, Supplier<ZonedDateTime> z1, Supplier<ZonedDateTime> z2, long allowDifferenceSeconds) {
        this.validator.getAssertValidation().validationSoft(
                allureStepName,
                () -> Assertions.assertThat(assertZonedDateTimeWithToleranceSecondsSoft(z1.get(), z2.get(), allowDifferenceSeconds))
                        .withFailMessage(
                                "Допустимая разница в секундах - %s.".formatted(allowDifferenceSeconds)
                        )
                        .isTrue()
        );
        return this;
    }
    private boolean assertZonedDateTimeWithToleranceSecondsSoft(ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceSeconds) {
        Duration duration = Duration.between(z1, z2);
        long differenceInSeconds = Math.abs(duration.toSeconds());

        return differenceInSeconds <= allowDifferenceSeconds;
    }
    private long getDifferenceSeconds(ZonedDateTime z1, ZonedDateTime z2) {
        Duration duration = Duration.between(z1, z2);
        return Math.abs(duration.toSeconds());
    }

    /******************************************GROUP SOFT*************************************/
    @Override
    public AssertionValidator groupSoft(String allureStepName, Consumer<AssertionNestedValidator> consumer) {
        AllureStepValidator allureStepValidator =
                AllureStepValidator.beginStep(allureStepName, Status.FAILED, allureConfig.isDisableSoftNameAllure());
        ErrorManager errorManager = this.validator.getErrorManager();
        int oldCountError = errorManager.getErrors().size();
        consumer.accept(new AssertionNestedValidator(this));
        int currentCountError = errorManager.getErrors().size();
        if (oldCountError == currentCountError) allureStepValidator.setStatus(Status.PASSED);
        allureStepValidator.stopStep();
        return this;
    }

    @Override
    public void validate() {
        this.validator.validate();
    }

    /******************************************Setters*************************************/
    public AssertionValidator allureConfigure(Consumer<AllureConfigurable> config) {
        config.accept(this.validator.getAllureConfig());
        return this;
    }
}
