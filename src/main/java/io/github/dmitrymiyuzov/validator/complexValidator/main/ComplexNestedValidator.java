package io.github.dmitrymiyuzov.validator.complexValidator.main;

import io.github.dmitrymiyuzov.validator.allure.AllureConfigurable;
import io.github.dmitrymiyuzov.validator.implementation.assertion.AssertValidatableSoft;
import io.github.dmitrymiyuzov.validator.implementation.assertion.ThrowingCallable;
import io.github.dmitrymiyuzov.validator.implementation.base.Allurable;
import io.github.dmitrymiyuzov.validator.implementation.base.GroupValidatable;
import io.github.dmitrymiyuzov.validator.implementation.response.ResponseValidatableSoft;
import io.restassured.response.Response;
import org.assertj.core.util.CanIgnoreReturnValue;
import org.hamcrest.Matcher;

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
public class ComplexNestedValidator implements
        AssertValidatableSoft<ComplexNestedValidator>,
        ResponseValidatableSoft<ComplexNestedValidator>,
        GroupValidatable<ComplexNestedValidator, ComplexNestedValidator>,
        Allurable<ComplexNestedValidator> {
    private final ComplexValidator complexValidator;

    public ComplexNestedValidator(ComplexValidator complexValidator) {
        this.complexValidator = complexValidator;
    }

    /******************************************ASSERT THAT CODE DOES NOT EXCEPTION*************************************/
    @Override
    @SuppressWarnings("all")
    public ComplexNestedValidator assertThatCodeDoesNotExceptionSoft(String allureStepName, ThrowingCallable call) {
        complexValidator.assertThatCodeDoesNotExceptionSoft(allureStepName, call);
        return this;
    }
    @Override
    @SuppressWarnings("all")
    public ComplexNestedValidator assertThatCodeDoesNotExceptionSoft(String allureStepName, ThrowingCallable call, String errorMessageAllure) {
        complexValidator.assertThatCodeDoesNotExceptionSoft(allureStepName, call, errorMessageAllure);
        return this;
    }

    /******************************************EQUALS SOFT*************************************/
    @Override
    public <T> ComplexNestedValidator assertEqualsSoft(String allureStepName, T actual, T expected) {
        complexValidator.assertEqualsSoft(allureStepName, actual, expected);
        return this;
    }
    @Override
    public <T> ComplexNestedValidator assertEqualsSoft(String allureStepName, T actual, T expected, String errorMessage) {
        complexValidator.assertEqualsSoft(allureStepName, actual, expected, errorMessage);
        return this;
    }
    @Override
    public <T> ComplexNestedValidator assertEqualsSoft(String allureStepName, Supplier<T> actual, T expected) {
        complexValidator.assertEqualsSoft(allureStepName, actual, expected);
        return this;
    }
    @Override
    public <T> ComplexNestedValidator assertEqualsSoft(String allureStepName, Supplier<T> actual, T expected, String errorMessage) {
        complexValidator.assertEqualsSoft(allureStepName, actual, expected, errorMessage);
        return this;
    }
    @Override
    public <T> ComplexNestedValidator assertEqualsSoft(String allureStepName, Supplier<T> actual, Supplier<T> expected) {
        complexValidator.assertEqualsSoft(allureStepName, actual, expected);
        return this;
    }
    @Override
    public <T> ComplexNestedValidator assertEqualsSoft(String allureStepName, Supplier<T> actual, Supplier<T> expected, String errorMessage) {
        complexValidator.assertEqualsSoft(allureStepName, actual, expected, errorMessage);
        return this;
    }
    @Override
    public <T> ComplexNestedValidator assertEqualsSoft(String allureStepName, T actual, Supplier<T> expected) {
        complexValidator.assertEqualsSoft(allureStepName, actual, expected);
        return this;
    }
    @Override
    public <T> ComplexNestedValidator assertEqualsSoft(String allureStepName, T actual, Supplier<T> expected, String errorMessage) {
        complexValidator.assertEqualsSoft(allureStepName, actual, expected, errorMessage);
        return this;
    }

    /******************************************NOT EQUALS SOFT*************************************/
    @Override
    public <T> ComplexNestedValidator assertNotEqualsSoft(String allureStepName, T actual, T expected) {
        complexValidator.assertNotEqualsSoft(allureStepName, actual, expected);
        return this;
    }
    @Override
    public <T> ComplexNestedValidator assertNotEqualsSoft(String allureStepName, T actual, T expected, String errorMessage) {
        complexValidator.assertNotEqualsSoft(allureStepName, actual, expected, errorMessage);
        return this;
    }
    @Override
    public <T> ComplexNestedValidator assertNotEqualsSoft(String allureStepName, Supplier<T> actual, T expected) {
        complexValidator.assertNotEqualsSoft(allureStepName, actual, expected);
        return this;
    }
    @Override
    public <T> ComplexNestedValidator assertNotEqualsSoft(String allureStepName, Supplier<T> actual, T expected, String errorMessage) {
        complexValidator.assertNotEqualsSoft(allureStepName, actual, expected, errorMessage);
        return this;
    }
    @Override
    public <T> ComplexNestedValidator assertNotEqualsSoft(String allureStepName, Supplier<T> actual, Supplier<T> expected) {
        complexValidator.assertNotEqualsSoft(allureStepName, actual, expected);
        return this;
    }
    @Override
    public <T> ComplexNestedValidator assertNotEqualsSoft(String allureStepName, Supplier<T> actual, Supplier<T> expected, String errorMessage) {
        complexValidator.assertNotEqualsSoft(allureStepName, actual, expected, errorMessage);
        return this;
    }
    @Override
    public <T> ComplexNestedValidator assertNotEqualsSoft(String allureStepName, T actual, Supplier<T> expected) {
        complexValidator.assertNotEqualsSoft(allureStepName, actual, expected);
        return this;
    }
    @Override
    public <T> ComplexNestedValidator assertNotEqualsSoft(String allureStepName, T actual, Supplier<T> expected, String errorMessage) {
        complexValidator.assertNotEqualsSoft(allureStepName, actual, expected, errorMessage);
        return this;
    }

    /******************************************TRUE SOFT*************************************/
    @Override
    public ComplexNestedValidator assertTrueSoft(String allureStepName, boolean condition) {
        complexValidator.assertTrueSoft(allureStepName, condition);
        return this;
    }
    @Override
    public ComplexNestedValidator assertTrueSoft(String allureStepName, boolean condition, String errorMessage) {
        complexValidator.assertTrueSoft(allureStepName, condition, errorMessage);
        return this;
    }
    @Override
    public ComplexNestedValidator assertTrueSoft(String allureStepName, Supplier<Boolean> condition) {
        complexValidator.assertTrueSoft(allureStepName, condition);
        return this;
    }
    @Override
    public ComplexNestedValidator assertTrueSoft(String allureStepName, Supplier<Boolean> condition, String errorMessage) {
        complexValidator.assertTrueSoft(allureStepName, condition, errorMessage);
        return this;
    }

    /******************************************FALSE SOFT*************************************/
    @Override
    public ComplexNestedValidator assertFalseSoft(String allureStepName, boolean condition) {
        complexValidator.assertFalseSoft(allureStepName, condition);
        return this;
    }
    @Override
    public ComplexNestedValidator assertFalseSoft(String allureStepName, boolean condition, String errorMessage) {
        complexValidator.assertFalseSoft(allureStepName, condition, errorMessage);
        return this;
    }
    @Override
    public ComplexNestedValidator assertFalseSoft(String allureStepName, Supplier<Boolean> condition) {
        complexValidator.assertFalseSoft(allureStepName, condition);
        return this;
    }
    @Override
    public ComplexNestedValidator assertFalseSoft(String allureStepName, Supplier<Boolean> condition, String errorMessage) {
        complexValidator.assertFalseSoft(allureStepName, condition, errorMessage);
        return this;
    }

    /******************************************NULL SOFT*************************************/
    @Override
    public ComplexNestedValidator assertNullSoft(String allureStepName, Object object) {
        complexValidator.assertNullSoft(allureStepName, object);
        return this;
    }
    @Override
    public ComplexNestedValidator assertNullSoft(String allureStepName, Object object, String errorMessage) {
        complexValidator.assertNullSoft(allureStepName, object, errorMessage);
        return this;
    }
    @Override
    public ComplexNestedValidator assertNullSoft(String allureStepName, Supplier<?> object) {
        complexValidator.assertNullSoft(allureStepName, object);
        return this;
    }
    @Override
    public ComplexNestedValidator assertNullSoft(String allureStepName, Supplier<?> object, String errorMessage) {
        complexValidator.assertNullSoft(allureStepName, object, errorMessage);
        return this;
    }

    /******************************************NOT NULL SOFT*************************************/
    @Override
    public ComplexNestedValidator assertNotNullSoft(String allureStepName, Object object) {
        complexValidator.assertNotNullSoft(allureStepName, object);
        return this;
    }
    @Override
    public ComplexNestedValidator assertNotNullSoft(String allureStepName, Object object, String errorMessage) {
        complexValidator.assertNotNullSoft(allureStepName, object, errorMessage);
        return this;
    }
    @Override
    public ComplexNestedValidator assertNotNullSoft(String allureStepName, Supplier<?> object) {
        complexValidator.assertNotNullSoft(allureStepName, object);
        return this;
    }
    @Override
    public ComplexNestedValidator assertNotNullSoft(String allureStepName, Supplier<?> object, String errorMessage) {
        complexValidator.assertNotNullSoft(allureStepName, object, errorMessage);
        return this;
    }

    @Override
    /******************************************LOCAL DATE TIME*************************************/
    public ComplexNestedValidator assertLocalDateTimeSoft(String nameActualDate, String nameExpectedDate,
                                                          LocalDateTime actual, LocalDateTime expected, DateTimeFormatter format) {
        complexValidator.assertLocalDateTimeSoft(nameActualDate, nameExpectedDate, actual, expected, format);
        return this;
    }

    /******************************************ZONED DATE TIME*************************************/
    @Override
    public ComplexNestedValidator assertZonedDateTimeWithToleranceHoursSoft(String allureStepName, ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceHours) {
        this.complexValidator.assertZonedDateTimeWithToleranceHoursSoft(allureStepName, z1, z2, allowDifferenceHours);
        return this;
    }
    @Override
    public ComplexNestedValidator assertZonedDateTimeWithToleranceHoursSoft(String allureStepName, Supplier<ZonedDateTime> z1, ZonedDateTime z2, long allowDifferenceHours) {
        this.complexValidator.assertZonedDateTimeWithToleranceHoursSoft(allureStepName, z1, z2, allowDifferenceHours);
        return this;
    }
    @Override
    public ComplexNestedValidator assertZonedDateTimeWithToleranceHoursSoft(String allureStepName, ZonedDateTime z1, Supplier<ZonedDateTime> z2, long allowDifferenceHours) {
        this.complexValidator.assertZonedDateTimeWithToleranceHoursSoft(allureStepName, z1, z2, allowDifferenceHours);
        return this;
    }
    @Override
    public ComplexNestedValidator assertZonedDateTimeWithToleranceHoursSoft(String allureStepName, Supplier<ZonedDateTime> z1, Supplier<ZonedDateTime> z2, long allowDifferenceHours) {
        this.complexValidator.assertZonedDateTimeWithToleranceHoursSoft(allureStepName, z1, z2, allowDifferenceHours);
        return this;
    }

    @Override
    public ComplexNestedValidator assertZonedDateTimeWithToleranceMinutesSoft(String allureStepName, ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceMinutes) {
        this.complexValidator.assertZonedDateTimeWithToleranceMinutesSoft(allureStepName, z1, z2, allowDifferenceMinutes);
        return this;
    }
    @Override
    public ComplexNestedValidator assertZonedDateTimeWithToleranceMinutesSoft(String allureStepName, Supplier<ZonedDateTime> z1, ZonedDateTime z2, long allowDifferenceMinutes) {
        this.complexValidator.assertZonedDateTimeWithToleranceMinutesSoft(allureStepName, z1, z2, allowDifferenceMinutes);
        return this;
    }
    @Override
    public ComplexNestedValidator assertZonedDateTimeWithToleranceMinutesSoft(String allureStepName, ZonedDateTime z1, Supplier<ZonedDateTime> z2, long allowDifferenceMinutes) {
        this.complexValidator.assertZonedDateTimeWithToleranceMinutesSoft(allureStepName, z1, z2, allowDifferenceMinutes);
        return this;
    }
    @Override
    public ComplexNestedValidator assertZonedDateTimeWithToleranceMinutesSoft(String allureStepName, Supplier<ZonedDateTime> z1, Supplier<ZonedDateTime> z2, long allowDifferenceMinutes) {
        this.complexValidator.assertZonedDateTimeWithToleranceMinutesSoft(allureStepName, z1, z2, allowDifferenceMinutes);
        return this;
    }

    @Override
    public ComplexNestedValidator assertZonedDateTimeWithToleranceSecondsSoft(String allureStepName, ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceSeconds) {
        this.complexValidator.assertZonedDateTimeWithToleranceSecondsSoft(allureStepName, z1, z2, allowDifferenceSeconds);
        return this;
    }
    @Override
    public ComplexNestedValidator assertZonedDateTimeWithToleranceSecondsSoft(String allureStepName, Supplier<ZonedDateTime> z1, ZonedDateTime z2, long allowDifferenceSeconds) {
        this.complexValidator.assertZonedDateTimeWithToleranceSecondsSoft(allureStepName, z1, z2, allowDifferenceSeconds);
        return this;
    }
    @Override
    public ComplexNestedValidator assertZonedDateTimeWithToleranceSecondsSoft(String allureStepName, ZonedDateTime z1, Supplier<ZonedDateTime> z2, long allowDifferenceSeconds) {
        this.complexValidator.assertZonedDateTimeWithToleranceSecondsSoft(allureStepName, z1, z2, allowDifferenceSeconds);
        return this;
    }
    @Override
    public ComplexNestedValidator assertZonedDateTimeWithToleranceSecondsSoft(String allureStepName, Supplier<ZonedDateTime> z1, Supplier<ZonedDateTime> z2, long allowDifferenceSeconds) {
        this.complexValidator.assertZonedDateTimeWithToleranceSecondsSoft(allureStepName, z1, z2, allowDifferenceSeconds);
        return this;
    }

    /******************************************bodySoft*************************************/
    @Override
    public <T> ComplexNestedValidator bodySoft(String jsonPath, Matcher<T> matcher) {
        complexValidator.bodySoft(jsonPath, matcher);
        return this;
    }
    @Override
    public <T> ComplexNestedValidator bodySoft(String jsonPath, Matcher<T> matcher, String allureErrorMessage) {
        complexValidator.bodySoft(jsonPath, matcher, allureErrorMessage);
        return this;
    }
    @Override
    public <T> ComplexNestedValidator bodySoft(String allureStepName, String jsonPath, Matcher<T> matcher) {
        complexValidator.bodySoft(allureStepName, jsonPath, matcher);
        return this;
    }
    @Override
    public <T> ComplexNestedValidator bodySoft(String allureStepName, String jsonPath, Matcher<T> matcher, String allureErrorMessage) {
        complexValidator.bodySoft(allureStepName, jsonPath, matcher, allureErrorMessage);
        return this;
    }

    /******************************************body equals file soft*************************************/
    @Override
    public ComplexNestedValidator bodyEqualsFileSoft(String pathFile) {
        complexValidator.bodyEqualsFileSoft(pathFile);
        return this;
    }

    /******************************************body has/does not has attribute*************************************/
    @Override
    public final ComplexNestedValidator bodyHasAttributeSoft(String parentJsonPath, String attribute) {
        complexValidator.bodyHasAttributeSoft(parentJsonPath, attribute);
        return this;
    }
    @Override
    public final ComplexNestedValidator bodyNotHasAttributeSoft(String parentJsonPath, String attribute) {
        complexValidator.bodyNotHasAttributeSoft(parentJsonPath, attribute);
        return this;
    }

    /******************************************body array*************************************/
    @Override
    public final ComplexNestedValidator bodyArrayEmptySoft(String jsonPath) {
        complexValidator.bodyArrayEmptySoft(jsonPath);
        return this;
    }
    @Override
    public final ComplexNestedValidator bodyArraySizeEqualsSoft(String jsonPath, int expectedSize) {
        complexValidator.bodyArraySizeEqualsSoft(jsonPath, expectedSize);
        return this;
    }

    /******************************************body equals soft*************************************/
    @Override
    public <T> ComplexNestedValidator bodyEqualsSoft(String jsonPath, T expectedValue) {
        complexValidator.bodyEqualsSoft(jsonPath, expectedValue);
        return this;
    }

    /******************************************body contains soft*************************************/
    @Override
    public ComplexNestedValidator bodyContainsSoft(String jsonPath, String containsValue) {
        complexValidator.bodyContainsSoft(jsonPath, containsValue);
        return this;
    }

    /******************************************body not null soft*************************************/
    @Override
    public ComplexNestedValidator bodyNotNullSoft(String jsonPath) {
        complexValidator.bodyNotNullSoft(jsonPath);
        return this;
    }

    /******************************************status code soft*************************************/
    @Override
    public ComplexNestedValidator statusCodeSoft(int expectedStatus) {
        complexValidator.statusCodeSoft(expectedStatus);
        return this;
    }
    @Override
    public ComplexNestedValidator statusCodeOrElseSoft(int expectedStatus, Consumer<Response> orElse) {
        complexValidator.statusCodeOrElseSoft(expectedStatus, orElse);
        return this;
    }

    @Override
    public ComplexNestedValidator statusCodeAndElseSoft(int expectedStatus, Consumer<Response> andElse) {
        complexValidator.statusCodeAndElseSoft(expectedStatus, andElse);
        return this;
    }

    /******************************************header equals soft*************************************/
    @Override
    public ComplexNestedValidator headerEqualsSoft(String header, String expectedValue) {
        complexValidator.headerEqualsSoft(header, expectedValue);
        return this;
    }

    /******************************************header contains soft*************************************/
    @Override
    public ComplexNestedValidator headerContainsSoft(String header, String containsValue) {
        complexValidator.headerContainsSoft(header, containsValue);
        return this;
    }

    /******************************************matches schema soft*************************************/
    @Override
    public ComplexNestedValidator matchesJsonSchemaSoft(String schema) {
        complexValidator.matchesJsonSchemaSoft(schema);
        return this;
    }

    /******************************************group soft*************************************/
    @Override
    public ComplexNestedValidator groupSoft(String allureStepName, Consumer<ComplexNestedValidator> consumer) {
        complexValidator.groupSoft(allureStepName, consumer);
        return this;
    }

    /******************************************Setters*************************************/
    public ComplexNestedValidator allureConfigure(Consumer<AllureConfigurable> config) {
        complexValidator.allureConfigure(config);
        return this;
    }
}
