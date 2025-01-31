package io.github.dmitrymiyuzov.validator.implementation.assertion;

import org.assertj.core.util.CheckReturnValue;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

/**
 * @author "Dmitry Miyuzov"
 */
public interface AssertValidatable<R> {

    /******************************************ASSERT THAT CODE DOES NOT EXCEPTION*************************************/
    @CheckReturnValue
    R assertThatCodeDoesNotException(String allureStepName, ThrowingCallable call);
    @CheckReturnValue
    R assertThatCodeDoesNotException(String allureStepName, ThrowingCallable call, String errorMessageAllure);

    /******************************************EQUALS*************************************/
    @CheckReturnValue
    <T> R assertEquals(String allureStepName, T actual, T expected);
    @CheckReturnValue
    <T> R assertEquals(String allureStepName, T actual, T expected, String errorMessage);
    @CheckReturnValue
    <T> R assertEquals(String allureStepName, Supplier<T> actual, T expected);
    @CheckReturnValue
    <T> R assertEquals(String allureStepName, Supplier<T> actual, T expected, String errorMessage);
    @CheckReturnValue
    <T> R assertEquals(String allureStepName, Supplier<T> actual, Supplier<T> expected);
    @CheckReturnValue
    <T> R assertEquals(String allureStepName, Supplier<T> actual, Supplier<T> expected, String errorMessage);
    @CheckReturnValue
    <T> R assertEquals(String allureStepName, T actual, Supplier<T> expected);
    @CheckReturnValue
    <T> R assertEquals(String allureStepName, T actual, Supplier<T> expected, String errorMessage);

    /******************************************NOT EQUALS*************************************/
    @CheckReturnValue
    <T> R assertNotEquals(String allureStepName, T actual, T expected);
    @CheckReturnValue
    <T> R assertNotEquals(String allureStepName, T actual, T expected, String errorMessage);
    @CheckReturnValue
    <T> R assertNotEquals(String allureStepName, Supplier<T> actual, T expected);
    @CheckReturnValue
    <T> R assertNotEquals(String allureStepName, Supplier<T> actual, T expected, String errorMessage);
    @CheckReturnValue
    <T> R assertNotEquals(String allureStepName, Supplier<T> actual, Supplier<T> expected);
    @CheckReturnValue
    <T> R assertNotEquals(String allureStepName, Supplier<T> actual, Supplier<T> expected, String errorMessage);
    @CheckReturnValue
    <T> R assertNotEquals(String allureStepName, T actual, Supplier<T> expected);
    @CheckReturnValue
    <T> R assertNotEquals(String allureStepName, T actual, Supplier<T> expected, String errorMessage);


    /******************************************TRUE*************************************/
    @CheckReturnValue
    R assertTrue(String allureStepName, boolean condition);
    @CheckReturnValue
    R assertTrue(String allureStepName, boolean condition, String errorMessage);
    @CheckReturnValue
    R assertTrue(String allureStepName, Supplier<Boolean> condition);
    @CheckReturnValue
    R assertTrue(String allureStepName, Supplier<Boolean> condition, String errorMessage);

    /******************************************FALSE*************************************/
    @CheckReturnValue
    R assertFalse(String allureStepName, boolean condition);
    @CheckReturnValue
    R assertFalse(String allureStepName, boolean condition, String errorMessage);
    @CheckReturnValue
    R assertFalse(String allureStepName, Supplier<Boolean> condition);
    @CheckReturnValue
    R assertFalse(String allureStepName, Supplier<Boolean> condition, String errorMessage);


    /******************************************NULL*************************************/
    @CheckReturnValue
    R assertNull(String allureStepName, Object object);
    @CheckReturnValue
    R assertNull(String allureStepName, Object object, String errorMessage);
    @CheckReturnValue
    R assertNull(String allureStepName, Supplier<Object> object);
    @CheckReturnValue
    R assertNull(String allureStepName, Supplier<Object> object, String errorMessage);


    /******************************************NOT NULL*************************************/
    @CheckReturnValue
    R assertNotNull(String allureStepName, Object object);
    @CheckReturnValue
    R assertNotNull(String allureStepName, Object object, String errorMessage);
    @CheckReturnValue
    R assertNotNull(String allureStepName, Supplier<Object> object);
    @CheckReturnValue
    R assertNotNull(String allureStepName, Supplier<Object> object, String errorMessage);

    /******************************************LOCAL DATE TIME*************************************/
    R assertLocalDateTime(String nameActualDate, String nameExpectedDate,
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
    R assertZonedDateTimeWithToleranceHours(String allureStepName, ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceHours);
    @CheckReturnValue
    R assertZonedDateTimeWithToleranceHours(String allureStepName, Supplier<ZonedDateTime> z1, ZonedDateTime z2, long allowDifferenceHours);
    @CheckReturnValue
    R assertZonedDateTimeWithToleranceHours(String allureStepName, ZonedDateTime z1, Supplier<ZonedDateTime> z2, long allowDifferenceHours);
    @CheckReturnValue
    R assertZonedDateTimeWithToleranceHours(String allureStepName, Supplier<ZonedDateTime> z1, Supplier<ZonedDateTime> z2, long allowDifferenceHours);

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
    R assertZonedDateTimeWithToleranceMinutes(String allureStepName, ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceMinutes);
    @CheckReturnValue
    R assertZonedDateTimeWithToleranceMinutes(String allureStepName, Supplier<ZonedDateTime> z1, ZonedDateTime z2, long allowDifferenceMinutes);
    @CheckReturnValue
    R assertZonedDateTimeWithToleranceMinutes(String allureStepName, ZonedDateTime z1, Supplier<ZonedDateTime> z2, long allowDifferenceMinutes);
    @CheckReturnValue
    R assertZonedDateTimeWithToleranceMinutes(String allureStepName, Supplier<ZonedDateTime> z1, Supplier<ZonedDateTime> z2, long allowDifferenceMinutes);

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
    R assertZonedDateTimeWithToleranceSeconds(String allureStepName, ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceSeconds);
    @CheckReturnValue
    R assertZonedDateTimeWithToleranceSeconds(String allureStepName, Supplier<ZonedDateTime> z1, ZonedDateTime z2, long allowDifferenceSeconds);
    @CheckReturnValue
    R assertZonedDateTimeWithToleranceSeconds(String allureStepName, ZonedDateTime z1, Supplier<ZonedDateTime> z2, long allowDifferenceSeconds);
    @CheckReturnValue
    R assertZonedDateTimeWithToleranceSeconds(String allureStepName, Supplier<ZonedDateTime> z1, Supplier<ZonedDateTime> z2, long allowDifferenceSeconds);

}
