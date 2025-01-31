package io.github.dmitrymiyuzov.validator.responseValidator.main;

import io.github.dmitrymiyuzov.validator.allure.AllureConfigurable;
import io.github.dmitrymiyuzov.validator.implementation.base.Allurable;
import io.github.dmitrymiyuzov.validator.implementation.base.GroupValidatable;
import io.github.dmitrymiyuzov.validator.implementation.response.ResponseValidatableSoft;
import io.restassured.response.Response;
import org.assertj.core.util.CanIgnoreReturnValue;
import org.hamcrest.Matcher;

import java.util.function.Consumer;

/**
 * @author "Dmitry Miyuzov"
 */
@SuppressWarnings("all")
@CanIgnoreReturnValue
public class ResponseNestedValidator implements
        ResponseValidatableSoft<ResponseNestedValidator>,
        GroupValidatable<ResponseNestedValidator, ResponseNestedValidator>,
        Allurable<ResponseNestedValidator> {
    private final ResponseValidator responseValidator;

    public ResponseNestedValidator(ResponseValidator responseValidator) {
        this.responseValidator = responseValidator;
    }

    /******************************************bodySoft*************************************/
    @Override
    public <T> ResponseNestedValidator bodySoft(String jsonPath, Matcher<T> matcher) {
        responseValidator.bodySoft(jsonPath, matcher);
        return this;
    }
    @Override
    public <T> ResponseNestedValidator bodySoft(String jsonPath, Matcher<T> matcher, String allureErrorMessage) {
        responseValidator.bodySoft(jsonPath, matcher, allureErrorMessage);
        return this;
    }
    @Override
    public <T> ResponseNestedValidator bodySoft(String allureStepName, String jsonPath, Matcher<T> matcher) {
        responseValidator.bodySoft(allureStepName, jsonPath, matcher);
        return this;
    }
    @Override
    public <T> ResponseNestedValidator bodySoft(String allureStepName, String jsonPath, Matcher<T> matcher, String allureErrorMessage) {
        responseValidator.bodySoft(allureStepName, jsonPath, matcher, allureErrorMessage);
        return this;
    }

    /******************************************body equals file soft*************************************/
    @Override
    public ResponseNestedValidator bodyEqualsFileSoft(String pathFile) {
        responseValidator.bodyEqualsFileSoft(pathFile);
        return this;
    }

    /******************************************body has/does not has attribute*************************************/
    @Override
    public final ResponseNestedValidator bodyHasAttributeSoft(String parentJsonPath, String attribute) {
        responseValidator.bodyHasAttributeSoft(parentJsonPath, attribute);
        return this;
    }
    @Override
    public final ResponseNestedValidator bodyNotHasAttributeSoft(String parentJsonPath, String attribute) {
        responseValidator.bodyNotHasAttributeSoft(parentJsonPath, attribute);
        return this;
    }

    /******************************************body array*************************************/
    @Override
    public final ResponseNestedValidator bodyArrayEmptySoft(String jsonPath) {
        responseValidator.bodyArrayEmptySoft(jsonPath);
        return this;
    }
    @Override
    public final ResponseNestedValidator bodyArraySizeEqualsSoft(String jsonPath, int expectedSize) {
        responseValidator.bodyArraySizeEqualsSoft(jsonPath, expectedSize);
        return this;
    }

    /******************************************body equals soft*************************************/
    @Override
    public <T> ResponseNestedValidator bodyEqualsSoft(String jsonPath, T expectedValue) {
        responseValidator.bodyEqualsSoft(jsonPath, expectedValue);
        return this;
    }

    /******************************************body contains soft*************************************/
    @Override
    public ResponseNestedValidator bodyContainsSoft(String jsonPath, String containsValue) {
        responseValidator.bodyContainsSoft(jsonPath, containsValue);
        return this;
    }

    /******************************************body not null soft*************************************/
    @Override
    public ResponseNestedValidator bodyNotNullSoft(String jsonPath) {
        responseValidator.bodyNotNullSoft(jsonPath);
        return this;
    }

    /******************************************status code soft*************************************/
    @Override
    public ResponseNestedValidator statusCodeSoft(int expectedStatus) {
        responseValidator.statusCodeSoft(expectedStatus);
        return this;
    }
    @Override
    public ResponseNestedValidator statusCodeOrElseSoft(int expectedStatus, Consumer<Response> orElse) {
        responseValidator.statusCodeOrElseSoft(expectedStatus, orElse);
        return this;
    }
    @Override
    public ResponseNestedValidator statusCodeAndElseSoft(int expectedStatus, Consumer<Response> andElse) {
        responseValidator.statusCodeAndElseSoft(expectedStatus, andElse);
        return this;
    }

    /******************************************header equals soft*************************************/
    @Override
    public ResponseNestedValidator headerEqualsSoft(String header, String expectedValue) {
        responseValidator.headerEqualsSoft(header, expectedValue);
        return this;
    }

    /******************************************header contains soft*************************************/
    @Override
    public ResponseNestedValidator headerContainsSoft(String header, String containsValue) {
        responseValidator.headerContainsSoft(header, containsValue);
        return this;
    }

    @Override
    public ResponseNestedValidator matchesJsonSchemaSoft(String schema) {
        responseValidator.matchesJsonSchemaSoft(schema);
        return this;
    }

    /******************************************group soft*************************************/
    @Override
    public ResponseNestedValidator groupSoft(String allureStepName, Consumer<ResponseNestedValidator> response) {
        responseValidator.groupSoft(allureStepName, response);
        return this;
    }

    /******************************************Setters*************************************/
    public ResponseNestedValidator allureConfigure(Consumer<AllureConfigurable> config) {
        responseValidator.allureConfigure(config);
        return this;
    }
}
