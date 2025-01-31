package io.github.dmitrymiyuzov.validator.implementation.response;

import io.restassured.response.Response;
import org.assertj.core.util.CheckReturnValue;
import org.hamcrest.Matcher;

import java.util.function.Consumer;

public interface ResponseValidatableSoft<R> {
    /******************************************bodySoft*************************************/
    @CheckReturnValue
    <T> R bodySoft(String jsonPath, Matcher<T> matcher);
    @CheckReturnValue
    <T> R bodySoft(String jsonPath, Matcher<T> matcher, String allureErrorMessage);
    @CheckReturnValue
    <T> R bodySoft(String allureStepName, String jsonPath, Matcher<T> matcher);
    @CheckReturnValue
    <T> R bodySoft(String allureStepName, String jsonPath, Matcher<T> matcher, String allureErrorMessage);

    /******************************************body equals file Soft*************************************/
    @CheckReturnValue
    R bodyEqualsFileSoft(String pathFile);

    /******************************************body has/does not has attribute*************************************/
    @CheckReturnValue
    R bodyHasAttributeSoft(String parentJsonPath, String attribute);
    @CheckReturnValue
    R bodyNotHasAttributeSoft(String parentJsonPath, String attribute);

    /******************************************body array*************************************/
    @CheckReturnValue
    R bodyArrayEmptySoft(String jsonPath);
    @CheckReturnValue
    R bodyArraySizeEqualsSoft(String jsonPath, int expectedSize);

    /******************************************body equals soft*************************************/
    @CheckReturnValue
    <T> R bodyEqualsSoft(String jsonPath, T expectedValue);

    /******************************************body contains soft*************************************/
    @CheckReturnValue
    R bodyContainsSoft(String jsonPath, String containsValue);

    /******************************************body not null soft*************************************/
    @CheckReturnValue
    R bodyNotNullSoft(String jsonPath);

    /******************************************status code soft*************************************/
    @CheckReturnValue
    R statusCodeSoft(int expectedStatus);
    @CheckReturnValue
    R statusCodeOrElseSoft(int expectedStatus, Consumer<Response> orElse);
    @CheckReturnValue
    R statusCodeAndElseSoft(int expectedStatus, Consumer<Response> andElse);

    /******************************************header equals soft*************************************/
    @CheckReturnValue
    R headerEqualsSoft(String header, String expectedValue);

    /******************************************header contains soft*************************************/
    @CheckReturnValue
    R headerContainsSoft(String header, String containsValue);

    /******************************************JsonSchema*************************************/
    @CheckReturnValue
    R matchesJsonSchemaSoft(String schema);
}
