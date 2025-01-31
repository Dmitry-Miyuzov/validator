package io.github.dmitrymiyuzov.validator.implementation.response;

import io.restassured.response.Response;
import org.assertj.core.util.CheckReturnValue;
import org.hamcrest.Matcher;

import java.util.function.Consumer;

/**
 * @author "Dmitry Miyuzov"
 */
public interface ResponseValidatable<R> {
    /******************************************body*************************************/
    @CheckReturnValue
    <T> R body(String jsonPath, Matcher<T> matcher);
    @CheckReturnValue
    <T> R body(String jsonPath, Matcher<T> matcher, String allureErrorMessage);
    @CheckReturnValue
    <T> R body(String allureStepName, String jsonPath, Matcher<T> matcher);
    @CheckReturnValue
    <T> R body(String allureStepName, String jsonPath, Matcher<T> matcher, String allureErrorMessage);

    /******************************************body equals file*************************************/
    @CheckReturnValue
    R bodyEqualsFile(String pathFile);

    /******************************************body has/does not has attribute*************************************/
    @CheckReturnValue
    R bodyHasAttribute(String parentJsonPath, String attribute);
    @CheckReturnValue
    R bodyNotHasAttribute(String parentJsonPath, String attribute);

    /******************************************body array*************************************/
    @CheckReturnValue
    R bodyArrayEmpty(String jsonPath);
    @CheckReturnValue
    R bodyArraySizeEquals(String jsonPath, int expectedSize);

    /******************************************body Equals*************************************/
    @CheckReturnValue
    <T> R bodyEquals(String jsonPath, T expectedValue);

    /******************************************body Contains*************************************/
    @CheckReturnValue
    R bodyContains(String jsonPath, String containsValue);

    /******************************************body not null*************************************/
    @CheckReturnValue
    R bodyNotNull(String jsonPath);

    /******************************************statusCode*************************************/
    @CheckReturnValue
    R statusCode(int expectedStatus);

    @CheckReturnValue
    R statusCodeOrElse(int expectedStatus, Consumer<Response> orElse);

    @CheckReturnValue
    R statusCodeAndElse(int expectedStatus, Consumer<Response> andElse);

    /******************************************HeaderEquals*************************************/
    @CheckReturnValue
    R headerEquals(String header, String expectedValue);

    /******************************************HeaderContains*************************************/
    @CheckReturnValue
    R headerContains(String header, String containsValue);

    /******************************************JsonSchema*************************************/
    @CheckReturnValue
    R matchesJsonSchema(String schema);
}
