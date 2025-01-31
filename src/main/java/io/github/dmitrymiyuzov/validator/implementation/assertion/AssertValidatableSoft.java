package io.github.dmitrymiyuzov.validator.implementation.assertion;

import org.assertj.core.util.CheckReturnValue;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

/**
 * @author "Dmitry Miyuzov"
 */
public interface AssertValidatableSoft<R> {

    /******************************************ASSERT THAT CODE DOES NOT EXCEPTION*************************************/
    @CheckReturnValue
    R assertThatCodeDoesNotExceptionSoft(String allureStepName, ThrowingCallable call);
    @CheckReturnValue
    R assertThatCodeDoesNotExceptionSoft(String allureStepName, ThrowingCallable call, String errorMessageAllure);

    /******************************************EQUALS SOFT*************************************/
    @CheckReturnValue
    <T> R assertEqualsSoft(String allureStepName, T actual, T expected);
    @CheckReturnValue
    <T> R assertEqualsSoft(String allureStepName, T actual, T expected, String errorMessage);
    @CheckReturnValue
    <T> R assertEqualsSoft(String allureStepName, Supplier<T> actual, T expected);
    @CheckReturnValue
    <T> R assertEqualsSoft(String allureStepName, Supplier<T> actual, T expected, String errorMessage);
    @CheckReturnValue
    <T> R assertEqualsSoft(String allureStepName, Supplier<T> actual, Supplier<T> expected);
    @CheckReturnValue
    <T> R assertEqualsSoft(String allureStepName, Supplier<T> actual, Supplier<T> expected, String errorMessage);
    @CheckReturnValue
    <T> R assertEqualsSoft(String allureStepName, T actual, Supplier<T> expected);
    @CheckReturnValue
    <T> R assertEqualsSoft(String allureStepName, T actual, Supplier<T> expected, String errorMessage);


    /******************************************NOT EQUALS SOFT*************************************/
    @CheckReturnValue
    <T> R assertNotEqualsSoft(String allureStepName, T actual, T expected);
    @CheckReturnValue
    <T> R assertNotEqualsSoft(String allureStepName, T actual, T expected, String errorMessage);
    @CheckReturnValue
    <T> R assertNotEqualsSoft(String allureStepName, Supplier<T> actual, T expected);
    @CheckReturnValue
    <T> R assertNotEqualsSoft(String allureStepName, Supplier<T> actual, T expected, String errorMessage);
    @CheckReturnValue
    <T> R assertNotEqualsSoft(String allureStepName, Supplier<T> actual, Supplier<T> expected);
    @CheckReturnValue
    <T> R assertNotEqualsSoft(String allureStepName, Supplier<T> actual, Supplier<T> expected, String errorMessage);
    @CheckReturnValue
    <T> R assertNotEqualsSoft(String allureStepName, T actual, Supplier<T> expected);
    @CheckReturnValue
    <T> R assertNotEqualsSoft(String allureStepName, T actual, Supplier<T> expected, String errorMessage);

    /******************************************TRUE SOFT*************************************/
    @CheckReturnValue
    R assertTrueSoft(String allureStepName, boolean condition);
    @CheckReturnValue
    R assertTrueSoft(String allureStepName, boolean condition, String errorMessage);
    @CheckReturnValue
    R assertTrueSoft(String allureStepName, Supplier<Boolean> condition);
    @CheckReturnValue
    R assertTrueSoft(String allureStepName, Supplier<Boolean> condition, String errorMessage);

    /******************************************FALSE SOFT*************************************/
    @CheckReturnValue
    R assertFalseSoft(String allureStepName, boolean condition);
    @CheckReturnValue
    R assertFalseSoft(String allureStepName, boolean condition, String errorMessage);
    @CheckReturnValue
    R assertFalseSoft(String allureStepName, Supplier<Boolean> condition);
    @CheckReturnValue
    R assertFalseSoft(String allureStepName, Supplier<Boolean> condition, String errorMessage);

    /******************************************NULL SOFT*************************************/
    @CheckReturnValue
    R assertNullSoft(String allureStepName, Object object);
    @CheckReturnValue
    R assertNullSoft(String allureStepName, Object object, String errorMessage);
    @CheckReturnValue
    R assertNullSoft(String allureStepName, Supplier<Object> object);
    @CheckReturnValue
    R assertNullSoft(String allureStepName, Supplier<Object> object, String errorMessage);

    /******************************************NOT NULL SOFT*************************************/
    @CheckReturnValue
    R assertNotNullSoft(String allureStepName, Object object);
    @CheckReturnValue
    R assertNotNullSoft(String allureStepName, Object object, String errorMessage);
    @CheckReturnValue
    R assertNotNullSoft(String allureStepName, Supplier<Object> object);
    @CheckReturnValue
    R assertNotNullSoft(String allureStepName, Supplier<Object> object, String errorMessage);

    /******************************************LOCAL DATE TIME*************************************/
    R assertLocalDateTimeSoft(String nameActualDate, String nameExpectedDate,
                              LocalDateTime actual, LocalDateTime expected, DateTimeFormatter format);

    /******************************************ZONED DATE TIME*************************************/
    /*
    Данный метод сравнивает даты, учитывая погрешность в часах.
    Не учитывается все что меньше в ед.измерениях (например, минуты, секунды, милисекунды)
    Например:
    Есть дата: 08.09.2024 10:00
    Есть дата: 08.09 2024 12:03

    Если мы укажем погрешность в часах 2.
    То даты будут равны, хоть и разница 2 часа и 3 минуты.
     */
    @CheckReturnValue
    R assertZonedDateTimeWithToleranceHoursSoft(String allureStepName, ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceHours);
    @CheckReturnValue
    R assertZonedDateTimeWithToleranceHoursSoft(String allureStepName, Supplier<ZonedDateTime> z1, ZonedDateTime z2, long allowDifferenceHours);
    @CheckReturnValue
    R assertZonedDateTimeWithToleranceHoursSoft(String allureStepName, ZonedDateTime z1, Supplier<ZonedDateTime> z2, long allowDifferenceHours);
    @CheckReturnValue
    R assertZonedDateTimeWithToleranceHoursSoft(String allureStepName, Supplier<ZonedDateTime> z1, Supplier<ZonedDateTime> z2, long allowDifferenceHours);

    /*
    Данный метод сравнивает даты, учитывая погрешность в минутах.
    Не учитывается все что меньше в ед.измерениях (например, секунды, милисекунды)
    Например:
    Есть дата: 08.09.2024 10:00:00
    Есть дата: 08.09 2024 10:03:03

    Если мы укажем погрешность в минутах 3.
    То даты будут равны, хоть и разница 3 минуты и 3 секунды.
     */
    @CheckReturnValue
    R assertZonedDateTimeWithToleranceMinutesSoft(String allureStepName, ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceMinutes);
    @CheckReturnValue
    R assertZonedDateTimeWithToleranceMinutesSoft(String allureStepName, Supplier<ZonedDateTime> z1, ZonedDateTime z2, long allowDifferenceMinutes);
    @CheckReturnValue
    R assertZonedDateTimeWithToleranceMinutesSoft(String allureStepName, ZonedDateTime z1, Supplier<ZonedDateTime> z2, long allowDifferenceMinutes);
    @CheckReturnValue
    R assertZonedDateTimeWithToleranceMinutesSoft(String allureStepName, Supplier<ZonedDateTime> z1, Supplier<ZonedDateTime> z2, long allowDifferenceMinutes);

    /*
    Данный метод сравнивает даты, учитывая погрешность в секундах.
    Не учитывается все что меньше в ед.измерениях (например, милисекунды)
    Например:
    Есть дата: 08.09.2024 10:00:03.000
    Есть дата: 08.09 2024 10:00:06.300

    Если мы укажем погрешность в секундах 3.
    То даты будут равны, хоть и разница 3 секунды и 300 мс.
     */
    @CheckReturnValue
    R assertZonedDateTimeWithToleranceSecondsSoft(String allureStepName, ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceSeconds);
    @CheckReturnValue
    R assertZonedDateTimeWithToleranceSecondsSoft(String allureStepName, Supplier<ZonedDateTime> z1, ZonedDateTime z2, long allowDifferenceSeconds);
    @CheckReturnValue
    R assertZonedDateTimeWithToleranceSecondsSoft(String allureStepName, ZonedDateTime z1, Supplier<ZonedDateTime> z2, long allowDifferenceSeconds);
    @CheckReturnValue
    R assertZonedDateTimeWithToleranceSecondsSoft(String allureStepName, Supplier<ZonedDateTime> z1, Supplier<ZonedDateTime> z2, long allowDifferenceSeconds);
}
