package io.github.dmitrymiyuzov.validator.assertionValidator.main;

import io.github.dmitrymiyuzov.validator.implementation.assertion.AssertValidatableSoft;
import io.github.dmitrymiyuzov.validator.implementation.assertion.ThrowingCallable;
import io.github.dmitrymiyuzov.validator.implementation.base.Allurable;
import io.github.dmitrymiyuzov.validator.implementation.base.GroupValidatable;
import org.assertj.core.util.CanIgnoreReturnValue;
import io.github.dmitrymiyuzov.validator.allure.AllureConfigurable;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author "Dmitry Miyuzov"
 */
@CanIgnoreReturnValue
@SuppressWarnings("all")
public class AssertionNestedValidator implements
        AssertValidatableSoft<AssertionNestedValidator>,
        GroupValidatable<AssertionNestedValidator, AssertionNestedValidator>,
        Allurable<AssertionNestedValidator> {
    private final AssertionValidator assertionValidator;

    public AssertionNestedValidator(AssertionValidator assertionValidator) {
        this.assertionValidator = assertionValidator;
    }

    /******************************************ASSERT THAT CODE DOES NOT EXCEPTION*************************************/
    @Override
    public AssertionNestedValidator assertThatCodeDoesNotExceptionSoft(String allureStepName, ThrowingCallable call) {
        assertionValidator.assertThatCodeDoesNotExceptionSoft(allureStepName, call);
        return this;
    }
    @Override
    public AssertionNestedValidator assertThatCodeDoesNotExceptionSoft(String allureStepName, ThrowingCallable call, String errorMessageAllure) {
        assertionValidator.assertThatCodeDoesNotExceptionSoft(allureStepName, call, errorMessageAllure);
        return this;
    }

    /******************************************EQUALS SOFT*************************************/
    @Override
    public <T> AssertionNestedValidator assertEqualsSoft(String allureStepName, T actual, T expected) {
        assertionValidator.assertEqualsSoft(allureStepName, actual, expected);
        return this;
    }
    @Override
    public <T> AssertionNestedValidator assertEqualsSoft(String allureStepName, T actual, T expected, String errorMessage) {
        assertionValidator.assertEqualsSoft(allureStepName, actual, expected, errorMessage);
        return this;
    }
    @Override
    public <T> AssertionNestedValidator assertEqualsSoft(String allureStepName, Supplier<T> actual, T expected) {
        assertionValidator.assertEqualsSoft(allureStepName, actual, expected);
        return this;
    }
    @Override
    public <T> AssertionNestedValidator assertEqualsSoft(String allureStepName, Supplier<T> actual, T expected, String errorMessage) {
        assertionValidator.assertEqualsSoft(allureStepName, actual, expected, errorMessage);
        return this;
    }
    @Override
    public <T> AssertionNestedValidator assertEqualsSoft(String allureStepName, Supplier<T> actual, Supplier<T> expected) {
        assertionValidator.assertEqualsSoft(allureStepName, actual, expected);
        return this;
    }
    @Override
    public <T> AssertionNestedValidator assertEqualsSoft(String allureStepName, Supplier<T> actual, Supplier<T> expected, String errorMessage) {
        assertionValidator.assertEqualsSoft(allureStepName, actual, expected, errorMessage);
        return this;
    }
    @Override
    public <T> AssertionNestedValidator assertEqualsSoft(String allureStepName, T actual, Supplier<T> expected) {
        assertionValidator.assertEqualsSoft(allureStepName, actual, expected);
        return this;
    }
    @Override
    public <T> AssertionNestedValidator assertEqualsSoft(String allureStepName, T actual, Supplier<T> expected, String errorMessage) {
        assertionValidator.assertEqualsSoft(allureStepName, actual, expected, errorMessage);
        return this;
    }

    /******************************************NOT EQUALS SOFT*************************************/
    @Override
    public <T> AssertionNestedValidator assertNotEqualsSoft(String allureStepName, T actual, T expected) {
        assertionValidator.assertNotEqualsSoft(allureStepName, actual, expected);
        return this;
    }
    @Override
    public <T> AssertionNestedValidator assertNotEqualsSoft(String allureStepName, T actual, T expected, String errorMessage) {
        assertionValidator.assertNotEqualsSoft(allureStepName, actual, expected, errorMessage);
        return this;
    }
    @Override
    public <T> AssertionNestedValidator assertNotEqualsSoft(String allureStepName, Supplier<T> actual, T expected) {
        assertionValidator.assertNotEqualsSoft(allureStepName, actual, expected);
        return this;
    }
    @Override
    public <T> AssertionNestedValidator assertNotEqualsSoft(String allureStepName, Supplier<T> actual, T expected, String errorMessage) {
        assertionValidator.assertNotEqualsSoft(allureStepName, actual, expected, errorMessage);
        return this;
    }
    @Override
    public <T> AssertionNestedValidator assertNotEqualsSoft(String allureStepName, Supplier<T> actual, Supplier<T> expected) {
        assertionValidator.assertNotEqualsSoft(allureStepName, actual, expected);
        return this;
    }
    @Override
    public <T> AssertionNestedValidator assertNotEqualsSoft(String allureStepName, Supplier<T> actual, Supplier<T> expected, String errorMessage) {
        assertionValidator.assertNotEqualsSoft(allureStepName, actual, expected, errorMessage);
        return this;
    }
    @Override
    public <T> AssertionNestedValidator assertNotEqualsSoft(String allureStepName, T actual, Supplier<T> expected) {
        assertionValidator.assertNotEqualsSoft(allureStepName, actual, expected);
        return this;
    }
    @Override
    public <T> AssertionNestedValidator assertNotEqualsSoft(String allureStepName, T actual, Supplier<T> expected, String errorMessage) {
        assertionValidator.assertNotEqualsSoft(allureStepName, actual, expected, errorMessage);
        return this;
    }

    /******************************************TRUE SOFT*************************************/
    @Override
    public AssertionNestedValidator assertTrueSoft(String allureStepName, boolean condition) {
        assertionValidator.assertTrueSoft(allureStepName, condition);
        return this;
    }
    @Override
    public AssertionNestedValidator assertTrueSoft(String allureStepName, boolean condition, String errorMessage) {
        assertionValidator.assertTrueSoft(allureStepName, condition, errorMessage);
        return this;
    }
    @Override
    public AssertionNestedValidator assertTrueSoft(String allureStepName, Supplier<Boolean> condition) {
        assertionValidator.assertTrueSoft(allureStepName, condition);
        return this;
    }
    @Override
    public AssertionNestedValidator assertTrueSoft(String allureStepName, Supplier<Boolean> condition, String errorMessage) {
        assertionValidator.assertTrueSoft(allureStepName, condition, errorMessage);
        return this;
    }

    /******************************************FALSE SOFT*************************************/
    @Override
    public AssertionNestedValidator assertFalseSoft(String allureStepName, boolean condition) {
        assertionValidator.assertFalseSoft(allureStepName, condition);
        return this;
    }
    @Override
    public AssertionNestedValidator assertFalseSoft(String allureStepName, boolean condition, String errorMessage) {
        assertionValidator.assertFalseSoft(allureStepName, condition, errorMessage);
        return this;
    }
    @Override
    public AssertionNestedValidator assertFalseSoft(String allureStepName, Supplier<Boolean> condition) {
        assertionValidator.assertFalseSoft(allureStepName, condition);
        return this;
    }
    @Override
    public AssertionNestedValidator assertFalseSoft(String allureStepName, Supplier<Boolean> condition, String errorMessage) {
        assertionValidator.assertFalseSoft(allureStepName, condition, errorMessage);
        return this;
    }

    /******************************************NULL SOFT*************************************/
    @Override
    public AssertionNestedValidator assertNullSoft(String allureStepName, Object object) {
        assertionValidator.assertNullSoft(allureStepName, object);
        return this;
    }
    @Override
    public AssertionNestedValidator assertNullSoft(String allureStepName, Object object, String errorMessage) {
        assertionValidator.assertNullSoft(allureStepName, object, errorMessage);
        return this;
    }
    @Override
    public AssertionNestedValidator assertNullSoft(String allureStepName, Supplier<Object> object) {
        assertionValidator.assertNullSoft(allureStepName, object);
        return this;
    }
    @Override
    public AssertionNestedValidator assertNullSoft(String allureStepName, Supplier<Object> object, String errorMessage) {
        assertionValidator.assertNullSoft(allureStepName, object, errorMessage);
        return this;
    }

    /******************************************NOT NULL SOFT*************************************/
    @Override
    public AssertionNestedValidator assertNotNullSoft(String allureStepName, Object object) {
        assertionValidator.assertNotNullSoft(allureStepName, object);
        return this;
    }
    @Override
    public AssertionNestedValidator assertNotNullSoft(String allureStepName, Object object, String errorMessage) {
        assertionValidator.assertNotNullSoft(allureStepName, object, errorMessage);
        return this;
    }
    @Override
    public AssertionNestedValidator assertNotNullSoft(String allureStepName, Supplier<Object> object) {
        assertionValidator.assertNotNullSoft(allureStepName, object);
        return this;
    }
    @Override
    public AssertionNestedValidator assertNotNullSoft(String allureStepName, Supplier<Object> object, String errorMessage) {
        assertionValidator.assertNotNullSoft(allureStepName, object, errorMessage);
        return this;
    }

    @Override
    /******************************************LOCAL DATE TIME*************************************/
    public AssertionNestedValidator assertLocalDateTimeSoft(String nameActualDate, String nameExpectedDate,
                                                            LocalDateTime actual, LocalDateTime expected, DateTimeFormatter format) {
        assertionValidator.assertLocalDateTimeSoft(nameActualDate, nameExpectedDate, actual, expected, format);
        return this;
    }

    /******************************************ZONED DATE TIME*************************************/
    @Override
    public AssertionNestedValidator assertZonedDateTimeWithToleranceHoursSoft(String allureStepName, ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceHours) {
        assertionValidator.assertZonedDateTimeWithToleranceHoursSoft(allureStepName, z1, z2, allowDifferenceHours);
        return this;
    }
    @Override
    public AssertionNestedValidator assertZonedDateTimeWithToleranceHoursSoft(String allureStepName, Supplier<ZonedDateTime> z1, ZonedDateTime z2, long allowDifferenceHours) {
        assertionValidator.assertZonedDateTimeWithToleranceHoursSoft(allureStepName, z1, z2, allowDifferenceHours);
        return this;
    }
    @Override
    public AssertionNestedValidator assertZonedDateTimeWithToleranceHoursSoft(String allureStepName, ZonedDateTime z1, Supplier<ZonedDateTime> z2, long allowDifferenceHours) {
        assertionValidator.assertZonedDateTimeWithToleranceHoursSoft(allureStepName, z1, z2, allowDifferenceHours);
        return this;
    }
    @Override
    public AssertionNestedValidator assertZonedDateTimeWithToleranceHoursSoft(String allureStepName, Supplier<ZonedDateTime> z1, Supplier<ZonedDateTime> z2, long allowDifferenceHours) {
        assertionValidator.assertZonedDateTimeWithToleranceHoursSoft(allureStepName, z1, z2, allowDifferenceHours);
        return this;
    }

    @Override
    public AssertionNestedValidator assertZonedDateTimeWithToleranceMinutesSoft(String allureStepName, ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceMinutes) {
        assertionValidator.assertZonedDateTimeWithToleranceMinutesSoft(allureStepName, z1, z2, allowDifferenceMinutes);
        return this;
    }
    @Override
    public AssertionNestedValidator assertZonedDateTimeWithToleranceMinutesSoft(String allureStepName, Supplier<ZonedDateTime> z1, ZonedDateTime z2, long allowDifferenceMinutes) {
        assertionValidator.assertZonedDateTimeWithToleranceMinutesSoft(allureStepName, z1, z2, allowDifferenceMinutes);
        return this;
    }
    @Override
    public AssertionNestedValidator assertZonedDateTimeWithToleranceMinutesSoft(String allureStepName, ZonedDateTime z1, Supplier<ZonedDateTime> z2, long allowDifferenceMinutes) {
        assertionValidator.assertZonedDateTimeWithToleranceMinutesSoft(allureStepName, z1, z2, allowDifferenceMinutes);
        return this;
    }
    @Override
    public AssertionNestedValidator assertZonedDateTimeWithToleranceMinutesSoft(String allureStepName, Supplier<ZonedDateTime> z1, Supplier<ZonedDateTime> z2, long allowDifferenceMinutes) {
        assertionValidator.assertZonedDateTimeWithToleranceMinutesSoft(allureStepName, z1, z2, allowDifferenceMinutes);
        return this;
    }

    @Override
    public AssertionNestedValidator assertZonedDateTimeWithToleranceSecondsSoft(String allureStepName, ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceSeconds) {
        assertionValidator.assertZonedDateTimeWithToleranceSecondsSoft(allureStepName, z1, z2, allowDifferenceSeconds);
        return this;
    }
    @Override
    public AssertionNestedValidator assertZonedDateTimeWithToleranceSecondsSoft(String allureStepName, Supplier<ZonedDateTime> z1, ZonedDateTime z2, long allowDifferenceSeconds) {
        assertionValidator.assertZonedDateTimeWithToleranceSecondsSoft(allureStepName, z1, z2, allowDifferenceSeconds);
        return this;
    }
    @Override
    public AssertionNestedValidator assertZonedDateTimeWithToleranceSecondsSoft(String allureStepName, ZonedDateTime z1, Supplier<ZonedDateTime> z2, long allowDifferenceSeconds) {
        assertionValidator.assertZonedDateTimeWithToleranceSecondsSoft(allureStepName, z1, z2, allowDifferenceSeconds);
        return this;
    }
    @Override
    public AssertionNestedValidator assertZonedDateTimeWithToleranceSecondsSoft(String allureStepName, Supplier<ZonedDateTime> z1, Supplier<ZonedDateTime> z2, long allowDifferenceSeconds) {
        assertionValidator.assertZonedDateTimeWithToleranceSecondsSoft(allureStepName, z1, z2, allowDifferenceSeconds);
        return this;
    }

    /******************************************group soft*************************************/
    @Override
    public AssertionNestedValidator groupSoft(String allureStepName, Consumer<AssertionNestedValidator> response) {
        assertionValidator.groupSoft(allureStepName, response);
        return this;
    }

    /******************************************Setters*************************************/
    public AssertionNestedValidator allureConfigure(Consumer<AllureConfigurable> config) {
        assertionValidator.allureConfigure(config);
        return this;
    }
}
