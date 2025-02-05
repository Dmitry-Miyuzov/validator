package io.github.dmitrymiyuzov.validator.complexValidator.main;

import io.github.dmitrymiyuzov.validator.allure.AllureConfig;
import io.github.dmitrymiyuzov.validator.allure.AllureConfigurable;
import io.github.dmitrymiyuzov.validator.allure.AllureStepValidator;
import io.github.dmitrymiyuzov.validator.implementation.assertion.AssertValidatable;
import io.github.dmitrymiyuzov.validator.implementation.assertion.AssertValidatableSoft;
import io.github.dmitrymiyuzov.validator.implementation.assertion.ThrowingCallable;
import io.github.dmitrymiyuzov.validator.implementation.base.Allurable;
import io.github.dmitrymiyuzov.validator.implementation.base.GroupValidatable;
import io.github.dmitrymiyuzov.validator.implementation.base.Validatable;
import io.github.dmitrymiyuzov.validator.implementation.response.ResponseValidatable;
import io.github.dmitrymiyuzov.validator.implementation.response.ResponseValidatableSoft;
import io.qameta.allure.model.Status;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import io.github.dmitrymiyuzov.validator.assertionValidator.main.AssertionValidator;
import io.github.dmitrymiyuzov.validator.base.Validator;
import io.github.dmitrymiyuzov.validator.responseValidator.main.ResponseValidator;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author "Dmitry Miyuzov"
 */
public class ComplexValidator implements
        Validatable,
        GroupValidatable<ComplexValidator, ComplexNestedValidator>,
        AssertValidatable<ComplexValidator>,
        AssertValidatableSoft<ComplexValidator>,
        ResponseValidatable<ComplexValidator>,
        ResponseValidatableSoft<ComplexValidator>,
        Allurable<ComplexValidator> {
    private final Validator validator;
    private final AllureConfig allureConfig;
    private final AssertionValidator assertionValidator;
    private final ResponseValidator responseValidator;

    public ComplexValidator(AssertionValidator assertionValidator, ResponseValidator responseValidator, Validator validator) {
        this.assertionValidator = assertionValidator;
        this.responseValidator = responseValidator;
        this.validator = validator;
        this.allureConfig = this.validator.getAllureConfig();
    }

    /******************************************ASSERT THAT CODE DOES NOT EXCEPTION*************************************/
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertThatCodeDoesNotException(String allureStepName, ThrowingCallable call) {
        this.assertionValidator.assertThatCodeDoesNotException(allureStepName, call);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertThatCodeDoesNotException(String allureStepName, ThrowingCallable call, String errorMessageAllure) {
        this.assertionValidator.assertThatCodeDoesNotException(allureStepName, call, errorMessageAllure);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertThatCodeDoesNotExceptionSoft(String allureStepName, ThrowingCallable call) {
        this.assertionValidator.assertThatCodeDoesNotExceptionSoft(allureStepName, call);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertThatCodeDoesNotExceptionSoft(String allureStepName, ThrowingCallable call, String errorMessage) {
        this.assertionValidator.assertThatCodeDoesNotExceptionSoft(allureStepName, call, errorMessage);
        return this;
    }

    /******************************************EQUALS*************************************/
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertEquals(String allureStepName, T actual, T expected) {
        this.assertionValidator.assertEquals(allureStepName, actual, expected);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertEquals(String allureStepName, T actual, T expected, String errorMessage) {
        this.assertionValidator.assertEquals(allureStepName, actual, expected, errorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertEquals(String allureStepName, Supplier<T> actual, T expected) {
        this.assertionValidator.assertEquals(allureStepName, actual, expected);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertEquals(String allureStepName, Supplier<T> actual, T expected, String errorMessage) {
        this.assertionValidator.assertEquals(allureStepName, actual, expected, errorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertEquals(String allureStepName, Supplier<T> actual, Supplier<T> expected) {
        this.assertionValidator.assertEqualsSoft(allureStepName, actual, expected);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertEquals(String allureStepName, Supplier<T> actual, Supplier<T> expected, String errorMessage) {
        this.assertionValidator.assertEquals(allureStepName, actual, expected, errorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertEquals(String allureStepName, T actual, Supplier<T> expected) {
        this.assertionValidator.assertEquals(allureStepName, actual, expected);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertEquals(String allureStepName, T actual, Supplier<T> expected, String errorMessage) {
        this.assertionValidator.assertEquals(allureStepName, actual, expected, errorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertEqualsSoft(String allureStepName, T actual, T expected) {
        this.assertionValidator.assertEqualsSoft(allureStepName, actual, expected);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertEqualsSoft(String allureStepName, T actual, T expected, String errorMessage) {
        this.assertionValidator.assertEqualsSoft(allureStepName, actual, expected, errorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertEqualsSoft(String allureStepName, Supplier<T> actual, T expected) {
        this.assertionValidator.assertEqualsSoft(allureStepName, actual, expected);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertEqualsSoft(String allureStepName, Supplier<T> actual, T expected, String errorMessage) {
        this.assertionValidator.assertEqualsSoft(allureStepName, actual, expected, errorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertEqualsSoft(String allureStepName, Supplier<T> actual, Supplier<T> expected) {
        this.assertionValidator.assertEqualsSoft(allureStepName, actual, expected);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertEqualsSoft(String allureStepName, Supplier<T> actual, Supplier<T> expected, String errorMessage) {
        this.assertionValidator.assertEqualsSoft(allureStepName, actual, expected, errorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertEqualsSoft(String allureStepName, T actual, Supplier<T> expected) {
        this.assertionValidator.assertEqualsSoft(allureStepName, actual, expected);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertEqualsSoft(String allureStepName, T actual, Supplier<T> expected, String errorMessage) {
        this.assertionValidator.assertEqualsSoft(allureStepName, actual, expected, errorMessage);
        return this;
    }

    /******************************************NOT EQUALS*************************************/
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertNotEquals(String allureStepName, T actual, T expected) {
        this.assertionValidator.assertNotEquals(allureStepName, actual, expected);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertNotEquals(String allureStepName, T actual, T expected, String errorMessage) {
        this.assertionValidator.assertNotEquals(allureStepName, actual, expected, errorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertNotEquals(String allureStepName, Supplier<T> actual, T expected) {
        this.assertionValidator.assertNotEquals(allureStepName, actual, expected);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertNotEquals(String allureStepName, Supplier<T> actual, T expected, String errorMessage) {
        this.assertionValidator.assertNotEquals(allureStepName, actual, expected, errorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertNotEquals(String allureStepName, Supplier<T> actual, Supplier<T> expected) {
        this.assertionValidator.assertNotEquals(allureStepName, actual, expected);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertNotEquals(String allureStepName, Supplier<T> actual, Supplier<T> expected, String errorMessage) {
        this.assertionValidator.assertNotEquals(allureStepName, actual, expected, errorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertNotEquals(String allureStepName, T actual, Supplier<T> expected) {
        this.assertionValidator.assertNotEquals(allureStepName, actual, expected);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertNotEquals(String allureStepName, T actual, Supplier<T> expected, String errorMessage) {
        this.assertionValidator.assertNotEquals(allureStepName, actual, expected, errorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertNotEqualsSoft(String allureStepName, T actual, T expected) {
        this.assertionValidator.assertNotEqualsSoft(allureStepName, actual, expected);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertNotEqualsSoft(String allureStepName, T actual, T expected, String errorMessage) {
        this.assertionValidator.assertNotEqualsSoft(allureStepName, actual, expected, errorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertNotEqualsSoft(String allureStepName, Supplier<T> actual, T expected) {
        this.assertionValidator.assertNotEqualsSoft(allureStepName, actual, expected);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertNotEqualsSoft(String allureStepName, Supplier<T> actual, T expected, String errorMessage) {
        this.assertionValidator.assertNotEqualsSoft(allureStepName, actual, expected, errorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertNotEqualsSoft(String allureStepName, Supplier<T> actual, Supplier<T> expected) {
        this.assertionValidator.assertNotEqualsSoft(allureStepName, actual, expected);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertNotEqualsSoft(String allureStepName, Supplier<T> actual, Supplier<T> expected, String errorMessage) {
        this.assertionValidator.assertNotEqualsSoft(allureStepName, actual, expected, errorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertNotEqualsSoft(String allureStepName, T actual, Supplier<T> expected) {
        this.assertionValidator.assertNotEqualsSoft(allureStepName, actual, expected);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator assertNotEqualsSoft(String allureStepName, T actual, Supplier<T> expected, String errorMessage) {
        this.assertionValidator.assertNotEqualsSoft(allureStepName, actual, expected, errorMessage);
        return this;
    }

    /******************************************TRUE*************************************/
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertTrue(String allureStepName, boolean condition) {
        this.assertionValidator.assertTrue(allureStepName, condition);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertTrue(String allureStepName, boolean condition, String errorMessage) {
        this.assertionValidator.assertTrue(allureStepName, condition, errorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertTrue(String allureStepName, Supplier<Boolean> condition) {
        this.assertionValidator.assertTrue(allureStepName, condition);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertTrue(String allureStepName, Supplier<Boolean> condition, String errorMessage) {
        this.assertionValidator.assertTrue(allureStepName, condition, errorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertTrueSoft(String allureStepName, boolean condition) {
        this.assertionValidator.assertTrueSoft(allureStepName, condition);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertTrueSoft(String allureStepName, boolean condition, String errorMessage) {
        this.assertionValidator.assertTrueSoft(allureStepName, condition, errorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertTrueSoft(String allureStepName, Supplier<Boolean> condition) {
        this.assertionValidator.assertTrueSoft(allureStepName, condition);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertTrueSoft(String allureStepName, Supplier<Boolean> condition, String errorMessage) {
        this.assertionValidator.assertTrueSoft(allureStepName, condition, errorMessage);
        return this;
    }

    /******************************************FALSE*************************************/
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertFalse(String allureStepName, boolean condition) {
        this.assertionValidator.assertFalse(allureStepName, condition);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertFalse(String allureStepName, boolean condition, String errorMessage) {
        this.assertionValidator.assertFalse(allureStepName, condition, errorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertFalse(String allureStepName, Supplier<Boolean> condition) {
        this.assertionValidator.assertFalse(allureStepName, condition);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertFalse(String allureStepName, Supplier<Boolean> condition, String errorMessage) {
        this.assertionValidator.assertFalse(allureStepName, condition, errorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertFalseSoft(String allureStepName, boolean condition) {
        this.assertionValidator.assertFalseSoft(allureStepName, condition);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertFalseSoft(String allureStepName, boolean condition, String errorMessage) {
        this.assertionValidator.assertFalseSoft(allureStepName, condition, errorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertFalseSoft(String allureStepName, Supplier<Boolean> condition) {
        this.assertionValidator.assertFalseSoft(allureStepName, condition);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertFalseSoft(String allureStepName, Supplier<Boolean> condition, String errorMessage) {
        this.assertionValidator.assertFalseSoft(allureStepName, condition, errorMessage);
        return this;
    }

    /******************************************NULL*************************************/
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertNull(String allureStepName, Object object) {
        this.assertionValidator.assertNull(allureStepName, object);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertNull(String allureStepName, Object object, String errorMessage) {
        this.assertionValidator.assertNull(allureStepName, object, errorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertNull(String allureStepName, Supplier<?> object) {
        this.assertionValidator.assertNull(allureStepName, object);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertNull(String allureStepName, Supplier<?> object, String errorMessage) {
        this.assertionValidator.assertNull(allureStepName, object, errorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertNullSoft(String allureStepName, Object object) {
        this.assertionValidator.assertNullSoft(allureStepName, object);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertNullSoft(String allureStepName, Object object, String errorMessage) {
        this.assertionValidator.assertNullSoft(allureStepName, object, errorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertNullSoft(String allureStepName, Supplier<?> object) {
        this.assertionValidator.assertNullSoft(allureStepName, object);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertNullSoft(String allureStepName, Supplier<?> object, String errorMessage) {
        this.assertionValidator.assertNullSoft(allureStepName, object, errorMessage);
        return this;
    }

    /******************************************NOT NULL*************************************/
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertNotNull(String allureStepName, Object object) {
        this.assertionValidator.assertNotNull(allureStepName, object);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertNotNull(String allureStepName, Object object, String errorMessage) {
        this.assertionValidator.assertNotNull(allureStepName, object, errorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertNotNull(String allureStepName, Supplier<?> object) {
        this.assertionValidator.assertNotNull(allureStepName, object);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertNotNull(String allureStepName, Supplier<?> object, String errorMessage) {
        this.assertionValidator.assertNotNull(allureStepName, object, errorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertNotNullSoft(String allureStepName, Object object) {
        this.assertionValidator.assertNotNullSoft(allureStepName, object);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertNotNullSoft(String allureStepName, Object object, String errorMessage) {
        this.assertionValidator.assertNotNullSoft(allureStepName, object, errorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertNotNullSoft(String allureStepName, Supplier<?> object) {
        this.assertionValidator.assertNotNullSoft(allureStepName, object);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertNotNullSoft(String allureStepName, Supplier<?> object, String errorMessage) {
        this.assertionValidator.assertNotNullSoft(allureStepName, object, errorMessage);
        return this;
    }

    /******************************************LOCAL DATE TIME*************************************/
    @Override
    public ComplexValidator assertLocalDateTime(String nameActualDate, String nameExpectedDate,
                                                LocalDateTime actual, LocalDateTime expected, DateTimeFormatter format) {
        this.assertionValidator.assertLocalDateTime(nameActualDate, nameExpectedDate, actual, expected, format);
        return this;
    }
    @Override
    public ComplexValidator assertLocalDateTimeSoft(String nameActualDate, String nameExpectedDate,
                                                    LocalDateTime actual, LocalDateTime expected, DateTimeFormatter format) {
        this.assertionValidator.assertLocalDateTimeSoft(nameActualDate, nameExpectedDate, actual, expected, format);
        return this;
    }

    /******************************************ZONED DATE TIME*************************************/
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertZonedDateTimeWithToleranceHours(String allureStepName, ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceHours) {
        this.assertionValidator.assertZonedDateTimeWithToleranceHours(allureStepName, z1, z2, allowDifferenceHours);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertZonedDateTimeWithToleranceHours(String allureStepName, Supplier<ZonedDateTime> z1, ZonedDateTime z2, long allowDifferenceHours) {
        this.assertionValidator.assertZonedDateTimeWithToleranceHours(allureStepName, z1, z2, allowDifferenceHours);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertZonedDateTimeWithToleranceHours(String allureStepName, ZonedDateTime z1, Supplier<ZonedDateTime> z2, long allowDifferenceHours) {
        this.assertionValidator.assertZonedDateTimeWithToleranceHours(allureStepName, z1, z2, allowDifferenceHours);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertZonedDateTimeWithToleranceHours(String allureStepName, Supplier<ZonedDateTime> z1, Supplier<ZonedDateTime> z2, long allowDifferenceHours) {
        this.assertionValidator.assertZonedDateTimeWithToleranceHours(allureStepName, z1, z2, allowDifferenceHours);
        return this;
    }

    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertZonedDateTimeWithToleranceMinutes(String allureStepName, ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceMinutes) {
        this.assertionValidator.assertZonedDateTimeWithToleranceMinutes(allureStepName, z1, z2, allowDifferenceMinutes);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertZonedDateTimeWithToleranceMinutes(String allureStepName, Supplier<ZonedDateTime> z1, ZonedDateTime z2, long allowDifferenceMinutes) {
        this.assertionValidator.assertZonedDateTimeWithToleranceMinutes(allureStepName, z1, z2, allowDifferenceMinutes);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertZonedDateTimeWithToleranceMinutes(String allureStepName, ZonedDateTime z1, Supplier<ZonedDateTime> z2, long allowDifferenceMinutes) {
        this.assertionValidator.assertZonedDateTimeWithToleranceMinutes(allureStepName, z1, z2, allowDifferenceMinutes);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertZonedDateTimeWithToleranceMinutes(String allureStepName, Supplier<ZonedDateTime> z1, Supplier<ZonedDateTime> z2, long allowDifferenceMinutes) {
        this.assertionValidator.assertZonedDateTimeWithToleranceMinutes(allureStepName, z1, z2, allowDifferenceMinutes);
        return this;
    }

    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertZonedDateTimeWithToleranceSeconds(String allureStepName, ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceSeconds) {
        this.assertionValidator.assertZonedDateTimeWithToleranceSeconds(allureStepName, z1, z2, allowDifferenceSeconds);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertZonedDateTimeWithToleranceSeconds(String allureStepName, Supplier<ZonedDateTime> z1, ZonedDateTime z2, long allowDifferenceSeconds) {
        this.assertionValidator.assertZonedDateTimeWithToleranceSeconds(allureStepName, z1, z2, allowDifferenceSeconds);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertZonedDateTimeWithToleranceSeconds(String allureStepName, ZonedDateTime z1, Supplier<ZonedDateTime> z2, long allowDifferenceSeconds) {
        this.assertionValidator.assertZonedDateTimeWithToleranceSeconds(allureStepName, z1, z2, allowDifferenceSeconds);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertZonedDateTimeWithToleranceSeconds(String allureStepName, Supplier<ZonedDateTime> z1, Supplier<ZonedDateTime> z2, long allowDifferenceSeconds) {
        this.assertionValidator.assertZonedDateTimeWithToleranceSeconds(allureStepName, z1, z2, allowDifferenceSeconds);
        return this;
    }

    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertZonedDateTimeWithToleranceHoursSoft(String allureStepName, ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceHours) {
        this.assertionValidator.assertZonedDateTimeWithToleranceHoursSoft(allureStepName, z1, z2, allowDifferenceHours);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertZonedDateTimeWithToleranceHoursSoft(String allureStepName, Supplier<ZonedDateTime> z1, ZonedDateTime z2, long allowDifferenceHours) {
        this.assertionValidator.assertZonedDateTimeWithToleranceHoursSoft(allureStepName, z1, z2, allowDifferenceHours);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertZonedDateTimeWithToleranceHoursSoft(String allureStepName, ZonedDateTime z1, Supplier<ZonedDateTime> z2, long allowDifferenceHours) {
        this.assertionValidator.assertZonedDateTimeWithToleranceHoursSoft(allureStepName, z1, z2, allowDifferenceHours);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertZonedDateTimeWithToleranceHoursSoft(String allureStepName, Supplier<ZonedDateTime> z1, Supplier<ZonedDateTime> z2, long allowDifferenceHours) {
        this.assertionValidator.assertZonedDateTimeWithToleranceHoursSoft(allureStepName, z1, z2, allowDifferenceHours);
        return this;
    }

    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertZonedDateTimeWithToleranceMinutesSoft(String allureStepName, ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceMinutes) {
        this.assertionValidator.assertZonedDateTimeWithToleranceMinutesSoft(allureStepName, z1, z2, allowDifferenceMinutes);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertZonedDateTimeWithToleranceMinutesSoft(String allureStepName, Supplier<ZonedDateTime> z1, ZonedDateTime z2, long allowDifferenceMinutes) {
        this.assertionValidator.assertZonedDateTimeWithToleranceMinutesSoft(allureStepName, z1, z2, allowDifferenceMinutes);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertZonedDateTimeWithToleranceMinutesSoft(String allureStepName, ZonedDateTime z1, Supplier<ZonedDateTime> z2, long allowDifferenceMinutes) {
        this.assertionValidator.assertZonedDateTimeWithToleranceMinutesSoft(allureStepName, z1, z2, allowDifferenceMinutes);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertZonedDateTimeWithToleranceMinutesSoft(String allureStepName, Supplier<ZonedDateTime> z1, Supplier<ZonedDateTime> z2, long allowDifferenceMinutes) {
        this.assertionValidator.assertZonedDateTimeWithToleranceMinutesSoft(allureStepName, z1, z2, allowDifferenceMinutes);
        return this;
    }

    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertZonedDateTimeWithToleranceSecondsSoft(String allureStepName, ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceSeconds) {
        this.assertionValidator.assertZonedDateTimeWithToleranceSecondsSoft(allureStepName, z1, z2, allowDifferenceSeconds);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertZonedDateTimeWithToleranceSecondsSoft(String allureStepName, Supplier<ZonedDateTime> z1, ZonedDateTime z2, long allowDifferenceSeconds) {
        this.assertionValidator.assertZonedDateTimeWithToleranceSecondsSoft(allureStepName, z1, z2, allowDifferenceSeconds);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertZonedDateTimeWithToleranceSecondsSoft(String allureStepName, ZonedDateTime z1, Supplier<ZonedDateTime> z2, long allowDifferenceSeconds) {
        this.assertionValidator.assertZonedDateTimeWithToleranceSecondsSoft(allureStepName, z1, z2, allowDifferenceSeconds);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator assertZonedDateTimeWithToleranceSecondsSoft(String allureStepName, Supplier<ZonedDateTime> z1, Supplier<ZonedDateTime> z2, long allowDifferenceSeconds) {
        this.assertionValidator.assertZonedDateTimeWithToleranceSecondsSoft(allureStepName, z1, z2, allowDifferenceSeconds);
        return this;
    }

    /******************************************body*************************************/
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator body(String jsonPath, Matcher<T> matcher) {
        this.responseValidator.body(jsonPath, matcher);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator body(String jsonPath, Matcher<T> matcher, String allureErrorMessage) {
        this.responseValidator.body(jsonPath, matcher, allureErrorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator body(String allureStepName, String jsonPath, Matcher<T> matcher) {
        this.responseValidator.body(allureStepName, jsonPath, matcher);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator body(String allureStepName, String jsonPath, Matcher<T> matcher, String allureErrorMessage) {
        this.responseValidator.body(allureStepName, jsonPath, matcher, allureErrorMessage);
        return this;
    }

    /******************************************bodySoft*************************************/
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator bodySoft(String jsonPath, Matcher<T> matcher) {
        this.responseValidator.bodySoft(jsonPath, matcher);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator bodySoft(String jsonPath, Matcher<T> matcher, String allureErrorMessage) {
        this.responseValidator.bodySoft(jsonPath, matcher, allureErrorMessage);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator bodySoft(String allureStepName, String jsonPath, Matcher<T> matcher) {
        this.responseValidator.bodySoft(allureStepName, jsonPath, matcher);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator bodySoft(String allureStepName, String jsonPath, Matcher<T> matcher, String allureErrorMessage) {
        this.responseValidator.bodySoft(allureStepName, jsonPath, matcher, allureErrorMessage);
        return this;
    }

    /******************************************body equals file*************************************/
    @Override
    @SuppressWarnings("all")
    public final ComplexValidator bodyEqualsFile(String pathFile) {
        this.responseValidator.bodyEqualsFile(pathFile);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public final ComplexValidator bodyEqualsFileSoft(String pathFile) {
        this.responseValidator.bodyEqualsFileSoft(pathFile);
        return this;
    }

    /******************************************body has/does not has attribute*************************************/
    @Override
    @SuppressWarnings("all")
    public final ComplexValidator bodyHasAttribute(String parentJsonPath, String attribute) {
        this.responseValidator.bodyHasAttribute(parentJsonPath, attribute);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public final ComplexValidator bodyHasAttributeSoft(String parentJsonPath, String attribute) {
        this.responseValidator.bodyHasAttributeSoft(parentJsonPath, attribute);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public final ComplexValidator bodyNotHasAttribute(String parentJsonPath, String attribute) {
        this.responseValidator.bodyNotHasAttribute(parentJsonPath, attribute);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public final ComplexValidator bodyNotHasAttributeSoft(String parentJsonPath, String attribute) {
        this.responseValidator.bodyNotHasAttributeSoft(parentJsonPath, attribute);
        return this;
    }

    /******************************************body array*************************************/
    @Override
    @SuppressWarnings("all")
    public final ComplexValidator bodyArrayEmpty(String jsonPath) {
        this.responseValidator.bodyArrayEmpty(jsonPath);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public final ComplexValidator bodyArrayEmptySoft(String jsonPath) {
        this.responseValidator.bodyArrayEmptySoft(jsonPath);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public final ComplexValidator bodyArraySizeEquals(String jsonPath, int expectedSize) {
        this.responseValidator.bodyArraySizeEquals(jsonPath, expectedSize);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public final ComplexValidator bodyArraySizeEqualsSoft(String jsonPath, int expectedSize) {
        this.responseValidator.bodyArraySizeEqualsSoft(jsonPath, expectedSize);
        return this;
    }

    /******************************************body Equals*************************************/
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator bodyEquals(String jsonPath, T expectedValue) {
        this.responseValidator.bodyEquals(jsonPath, expectedValue);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public <T> ComplexValidator bodyEqualsSoft(String jsonPath, T expectedValue) {
        this.responseValidator.bodyEqualsSoft(jsonPath, expectedValue);
        return this;
    }

    /******************************************body Contains*************************************/
    @Override
    @SuppressWarnings("all")
    public ComplexValidator bodyContains(String jsonPath, String containsValue) {
        this.responseValidator.bodyContains(jsonPath, containsValue);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator bodyContainsSoft(String jsonPath, String containsValue) {
        this.responseValidator.bodyContainsSoft(jsonPath, containsValue);
        return this;
    }

    /******************************************body not null*************************************/
    @Override
    @SuppressWarnings("all")
    public ComplexValidator bodyNotNull(String jsonPath) {
        this.responseValidator.bodyNotNull(jsonPath);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator bodyNotNullSoft(String jsonPath) {
        this.responseValidator.bodyNotNullSoft(jsonPath);
        return this;
    }

    /******************************************statusCode*************************************/
    @Override
    @SuppressWarnings("all")
    public ComplexValidator statusCode(int expectedStatus) {
        this.responseValidator.statusCode(expectedStatus);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator statusCodeOrElse(int expectedStatus, Consumer<Response> orElse) {
        this.responseValidator.statusCodeOrElse(expectedStatus, orElse);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator statusCodeAndElse(int expectedStatus, Consumer<Response> andElse) {
        this.responseValidator.statusCodeOrElse(expectedStatus, andElse);
        return this;
    }

    @Override
    @SuppressWarnings("all")
    public ComplexValidator statusCodeSoft(int expectedStatus) {
        this.responseValidator.statusCodeSoft(expectedStatus);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator statusCodeOrElseSoft(int expectedStatus, Consumer<Response> orElse) {
        this.responseValidator.statusCodeOrElseSoft(expectedStatus, orElse);
        return this;
    }

    @Override
    @SuppressWarnings("all")
    public ComplexValidator statusCodeAndElseSoft(int expectedStatus, Consumer<Response> andElse) {
        this.responseValidator.statusCodeAndElseSoft(expectedStatus, andElse);
        return this;
    }

    /******************************************HeaderEquals*************************************/
    @Override
    @SuppressWarnings("all")
    public ComplexValidator headerEquals(String header, String expectedValue) {
        this.responseValidator.headerEquals(header, expectedValue);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator headerEqualsSoft(String header, String expectedValue) {
        this.responseValidator.headerEqualsSoft(header, expectedValue);
        return this;
    }

    /******************************************HeaderContains*************************************/
    @Override
    @SuppressWarnings("all")
    public ComplexValidator headerContains(String header, String containsValue) {
        this.responseValidator.headerContains(header, containsValue);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator headerContainsSoft(String header, String containsValue) {
        this.responseValidator.headerContainsSoft(header, containsValue);
        return this;
    }

    /******************************************JSON SCHEMA*************************************/
    @Override
    @SuppressWarnings("all")
    public ComplexValidator matchesJsonSchema(String schema) {
        this.responseValidator.matchesJsonSchema(schema);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexValidator matchesJsonSchemaSoft(String schema) {
        this.responseValidator.matchesJsonSchemaSoft(schema);
        return this;
    }

    /******************************************group*************************************/
    @Override
    public ComplexValidator groupSoft(String allureStepName, Consumer<ComplexNestedValidator> consumer) {
        AllureStepValidator allureStepValidator =
                AllureStepValidator.beginStep(allureStepName, Status.FAILED, allureConfig.isDisableSoftNameAllure());
        int oldCountError = this.validator.getErrorManager().getErrors().size();
        consumer.accept(new ComplexNestedValidator(this));
        int currentCountError = this.validator.getErrorManager().getErrors().size();
        if (oldCountError == currentCountError) allureStepValidator.setStatus(Status.PASSED);
        allureStepValidator.stopStep();
        return this;
    }

    @Override
    public void validate() {
        this.validator.validate();
    }

    /******************************************Setters*************************************/
    public ComplexValidator allureConfigure(Consumer<AllureConfigurable> config) {
        config.accept(this.validator.getAllureConfig());
        return this;
    }
}
