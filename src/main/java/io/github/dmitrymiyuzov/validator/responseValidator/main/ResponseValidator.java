package io.github.dmitrymiyuzov.validator.responseValidator.main;

import io.github.dmitrymiyuzov.validator.allure.AllureConfig;
import io.github.dmitrymiyuzov.validator.allure.AllureConfigurable;
import io.github.dmitrymiyuzov.validator.allure.AllureStepValidator;
import io.github.dmitrymiyuzov.validator.base.Validator;
import io.github.dmitrymiyuzov.validator.exceptions.ErrorManager;
import io.github.dmitrymiyuzov.validator.implementation.base.Allurable;
import io.github.dmitrymiyuzov.validator.implementation.base.GroupValidatable;
import io.github.dmitrymiyuzov.validator.implementation.base.Validatable;
import io.github.dmitrymiyuzov.validator.implementation.response.ResponseValidatable;
import io.github.dmitrymiyuzov.validator.implementation.response.ResponseValidatableSoft;
import io.qameta.allure.model.Status;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.util.function.Consumer;

/**
 * @author "Dmitry Miyuzov"
 */
public class ResponseValidator implements
        Validatable,
        GroupValidatable<ResponseValidator, ResponseNestedValidator>,
        ResponseValidatable<ResponseValidator>,
        ResponseValidatableSoft<ResponseValidator>,
        Allurable<ResponseValidator> {
    protected final Validator validator;
    private final ErrorManager errorManager;
    private final AllureConfig allureConfig;

    public ResponseValidator(Validator validator) {
        this.validator = validator;
        this.errorManager = validator.getErrorManager();
        this.allureConfig = this.validator.getAllureConfig();
    }

    /******************************************body*************************************/
    @Override
    public final <T> ResponseValidator body(String allureStepName, String jsonPath, Matcher<T> matcher) {
        this.validator.getBodyValidation().body(
                allureStepName,
                () -> this.validator.getExchange()
                        .getResponse()
                        .then()
                        .body(jsonPath, matcher),
                jsonPath,
                matcher
        );
        return this;
    }
    @Override
    public <T> ResponseValidator body(String allureStepName, String jsonPath, Matcher<T> matcher, String allureErrorMessage) {
        this.validator.getBodyValidation().body(
                allureStepName,
                () -> this.validator.getExchange()
                        .getResponse()
                        .then()
                        .body(jsonPath, matcher),
                jsonPath,
                matcher,
                allureErrorMessage
        );
        return this;
    }
    @Override
    public final <T> ResponseValidator body(String jsonPath, Matcher<T> matcher) {
        return body(getStepNameValidateJsonPath(jsonPath), jsonPath, matcher);
    }
    @Override
    public final <T> ResponseValidator body(String jsonPath, Matcher<T> matcher, String allureErrorMessage) {
        return body(getStepNameValidateJsonPath(jsonPath), jsonPath, matcher, allureErrorMessage);
    }

    /******************************************bodySoft*************************************/
    @Override
    public final <T> ResponseValidator bodySoft(String allureStepName, String jsonPath, Matcher<T> matcher) {
        this.validator.getBodyValidation().bodySoft(
                allureStepName,
                () -> this.validator.getExchange()
                        .getResponse()
                        .then()
                        .body(jsonPath, matcher),
                jsonPath,
                matcher
        );
        return this;
    }
    @Override
    public final <T> ResponseValidator bodySoft(String allureStepName, String jsonPath, Matcher<T> matcher, String allureErrorMessage) {
        this.validator.getBodyValidation().bodySoft(
                allureStepName,
                () -> this.validator.getExchange()
                        .getResponse()
                        .then()
                        .body(jsonPath, matcher),
                jsonPath,
                matcher,
                allureErrorMessage
        );
        return this;
    }
    @Override
    public final <T> ResponseValidator bodySoft(String jsonPath, Matcher<T> matcher) {
        return bodySoft(getStepNameValidateJsonPath(jsonPath), jsonPath, matcher);
    }
    @Override
    public final <T> ResponseValidator bodySoft(String jsonPath, Matcher<T> matcher, String allureErrorMessage) {
        return bodySoft(getStepNameValidateJsonPath(jsonPath), jsonPath, matcher, allureErrorMessage);
    }

    /******************************************body equals file*************************************/
    @Override
    public final ResponseValidator bodyEqualsFile(String pathFile) {
        validator.getBodyValidation().bodyEqualsFile(pathFile);
        return this;
    }
    @Override
    public final ResponseValidator bodyEqualsFileSoft(String pathFile) {
        validator.getBodyValidation().bodyEqualsFileSoft(pathFile);
        return this;
    }

    /******************************************body has/does not has attribute*************************************/
    @Override
    public final ResponseValidator bodyHasAttribute(String parentJsonPath, String attribute) {
        String message = getStepNameValidateHasAttribute(parentJsonPath, attribute);
        String errorMessage = getAllureErrorMessageValidateHasAttribute(attribute);

        return body(message, parentJsonPath, Matchers.hasKey(attribute), errorMessage);
    }
    @Override
    public final ResponseValidator bodyHasAttributeSoft(String parentJsonPath, String attribute) {
        String message = getStepNameValidateHasAttribute(parentJsonPath, attribute);
        String errorMessage = getAllureErrorMessageValidateHasAttribute(attribute);

        return bodySoft(message, parentJsonPath, Matchers.hasKey(attribute), errorMessage);
    }
    @Override
    public final ResponseValidator bodyNotHasAttribute(String parentJsonPath, String attribute) {
        String message = getStepNameValidateNotHasAttribute(parentJsonPath, attribute);
        String errorMessage = getAllureErrorMessageValidateNotHasAttribute(attribute);

        return body(message, parentJsonPath, Matchers.not(Matchers.hasKey(attribute)), errorMessage);
    }
    @Override
    public final ResponseValidator bodyNotHasAttributeSoft(String parentJsonPath, String attribute) {
        String message = getStepNameValidateNotHasAttribute(parentJsonPath, attribute);
        String errorMessage = getAllureErrorMessageValidateNotHasAttribute(attribute);

        return bodySoft(message, parentJsonPath, Matchers.not(Matchers.hasKey(attribute)), errorMessage);
    }

    /******************************************body array*************************************/
    @Override
    public final ResponseValidator bodyArrayEmpty(String jsonPath) {
        String message = getStepNameValidateArrayEmpty(jsonPath);
        String errorMessage = getAllureErrorMessageValidateArrayEmpty(jsonPath);

        return body(message, jsonPath, Matchers.empty(), errorMessage);
    }
    @Override
    public final ResponseValidator bodyArrayEmptySoft(String jsonPath) {
        String message = getStepNameValidateArrayEmpty(jsonPath);
        String errorMessage = getAllureErrorMessageValidateArrayEmpty(jsonPath);

        return bodySoft(message, jsonPath, Matchers.empty(), errorMessage);
    }
    @Override
    public final ResponseValidator bodyArraySizeEquals(String jsonPath, int expectedSize) {
        String message = getStepNameValidateArraySizeEquals(jsonPath, expectedSize);
        String errorMessage = getAllureErrorMessageValidateArraySizeEquals(jsonPath, expectedSize);

        return body(message, jsonPath, Matchers.hasSize(expectedSize), errorMessage);
    }
    @Override
    public final ResponseValidator bodyArraySizeEqualsSoft(String jsonPath, int expectedSize) {
        String message = getStepNameValidateArraySizeEquals(jsonPath, expectedSize);
        String errorMessage = getAllureErrorMessageValidateArraySizeEquals(jsonPath, expectedSize);

        return bodySoft(message, jsonPath, Matchers.hasSize(expectedSize), errorMessage);
    }

    /******************************************body Equals*************************************/
    @Override
    public final <T> ResponseValidator bodyEquals(String jsonPath, T expectedValue) {
        Matcher<T> tMatcher = Matchers.equalTo(expectedValue);
        this.validator.getBodyValidation().body(
                getStepNameValidateJsonPathEquals(jsonPath, expectedValue),
                () -> this.validator.getExchange()
                        .getResponse()
                        .then()
                        .body(jsonPath, tMatcher),
                jsonPath,
                tMatcher,
                getActualValueJsonPath(jsonPath)
        );
        return this;
    }
    @Override
    public final <T> ResponseValidator bodyEqualsSoft(String jsonPath, T expectedValue) {
        Matcher<T> tMatcher = Matchers.equalTo(expectedValue);
        this.validator.getBodyValidation().bodySoft(
                getStepNameValidateJsonPathEquals(jsonPath, expectedValue),
                () -> this.validator.getExchange()
                        .getResponse()
                        .then()
                        .body(jsonPath, tMatcher),
                jsonPath,
                tMatcher,
                getActualValueJsonPath(jsonPath)
        );
        return this;
    }

    /******************************************body Contains*************************************/
    @Override
    public final ResponseValidator bodyContains(String jsonPath, String containsValue) {
        Matcher<String> tMatcher = Matchers.containsString(containsValue);
        this.validator.getBodyValidation().body(
                getStepNameValidateJsonPathContains(jsonPath, containsValue),
                () -> this.validator.getExchange()
                        .getResponse()
                        .then()
                        .body(jsonPath, tMatcher),
                jsonPath,
                tMatcher,
                getActualValueJsonPath(jsonPath)
        );
        return this;
    }
    @Override
    public final ResponseValidator bodyContainsSoft(String jsonPath, String containsValue) {
        Matcher<String> tMatcher = Matchers.containsString(containsValue);
        this.validator.getBodyValidation().bodySoft(
                getStepNameValidateJsonPathContains(jsonPath, containsValue),
                () -> this.validator.getExchange()
                        .getResponse()
                        .then()
                        .body(jsonPath, tMatcher),
                jsonPath,
                tMatcher,
                getActualValueJsonPath(jsonPath)
        );
        return this;
    }

    /******************************************body not null*************************************/
    @Override
    public final ResponseValidator bodyNotNull(String jsonPath) {
        Matcher<Object> tMatcher = Matchers.notNullValue();
        this.validator.getBodyValidation().body(
                getStepNameValidateJsonPathNotNull(jsonPath),
                () -> this.validator.getExchange()
                        .getResponse()
                        .then()
                        .body(jsonPath, tMatcher),
                jsonPath,
                tMatcher
        );
        return this;
    }
    @Override
    public final ResponseValidator bodyNotNullSoft(String jsonPath) {
        Matcher<Object> tMatcher = Matchers.notNullValue();
        this.validator.getBodyValidation().bodySoft(
                getStepNameValidateJsonPathNotNull(jsonPath),
                () -> this.validator.getExchange()
                        .getResponse()
                        .then()
                        .body(jsonPath, tMatcher),
                jsonPath,
                tMatcher
        );
        return this;
    }

    /******************************************statusCode*************************************/
    @Override
    public final ResponseValidator statusCode(int expectedStatus) {
        this.validator.getStatusCodeValidation()
                .validation(
                        () -> this.validator.getExchange()
                                .getResponse()
                                .then()
                                .statusCode(expectedStatus),
                        expectedStatus
                );
        return this;
    }
    @Override
    public final ResponseValidator statusCodeOrElse(int expectedStatus, Consumer<Response> orElse) {
        this.validator.getStatusCodeValidation()
                .validation(
                        () -> this.validator.getExchange()
                                .getResponse()
                                .then()
                                .statusCode(expectedStatus),
                        expectedStatus,
                        orElse
                );
        return this;
    }

    @Override
    public ResponseValidator statusCodeAndElse(int expectedStatus, Consumer<Response> andElse) {
        this.validator.getStatusCodeValidation()
                .validationAndElse(
                        () -> this.validator.getExchange()
                                .getResponse()
                                .then()
                                .statusCode(expectedStatus),
                        expectedStatus,
                        andElse
                );
        return this;
    }

    @Override
    public final ResponseValidator statusCodeSoft(int expectedStatus) {
        this.validator.getStatusCodeValidation()
                .validationSoft(
                        () -> this.validator.getExchange()
                                .getResponse()
                                .then()
                                .statusCode(expectedStatus),
                        expectedStatus
                );
        return this;
    }
    @Override
    public final ResponseValidator statusCodeOrElseSoft(int expectedStatus, Consumer<Response> orElse) {
        this.validator.getStatusCodeValidation()
                .validationSoft(
                        () -> this.validator.getExchange()
                                .getResponse()
                                .then()
                                .statusCode(expectedStatus),
                        expectedStatus,
                        orElse
                );
        return this;
    }
    @Override
    public final ResponseValidator statusCodeAndElseSoft(int expectedStatus, Consumer<Response> andElse) {
        this.validator.getStatusCodeValidation()
                .validationAndElseSoft(
                        () -> this.validator.getExchange()
                                .getResponse()
                                .then()
                                .statusCode(expectedStatus),
                        expectedStatus,
                        andElse
                );
        return this;
    }

    /******************************************HeaderEquals*************************************/
    @Override
    public final ResponseValidator headerEquals(String header, String expectedValue) {
        this.validator.getHeaderValidation().headerEquals(header, expectedValue);
        return this;
    }
    @Override
    public final ResponseValidator headerEqualsSoft(String header, String expectedValue) {
        this.validator.getHeaderValidation().headerEqualsSoft(header, expectedValue);
        return this;
    }

    /******************************************HeaderContains*************************************/
    @Override
    public final ResponseValidator headerContains(String header, String containsValue) {
        this.validator.getHeaderValidation().headerContains(header, containsValue);
        return this;
    }
    @Override
    public final ResponseValidator headerContainsSoft(String header, String containsValue) {
        this.validator.getHeaderValidation().headerContainsSoft(header, containsValue);
        return this;
    }

    /******************************************JSON SCHEMA*************************************/
    @Override
    public ResponseValidator matchesJsonSchema(String schema) {
        this.validator.getBodyValidation().matchesJsonSchema(schema);
        return this;
    }

    @Override
    public ResponseValidator matchesJsonSchemaSoft(String schema) {
        this.validator.getBodyValidation().matchesJsonSchemaSoft(schema);
        return this;
    }

    /******************************************group*************************************/
    @Override
    public final ResponseValidator groupSoft(String allureStepName, Consumer<ResponseNestedValidator> consumer) {
        AllureStepValidator allureStepValidator
                = AllureStepValidator.beginStep(allureStepName, Status.FAILED, allureConfig.isDisableSoftNameAllure());
        int oldCountError = errorManager.getErrors().size();
        consumer.accept(new ResponseNestedValidator(this));
        int currentCountError = errorManager.getErrors().size();
        if (oldCountError == currentCountError) allureStepValidator.setStatus(Status.PASSED);
        allureStepValidator.stopStep();
        return this;
    }

    @Override
    public void validate() {
        this.validator.validate();
    }

    private String getStepNameValidateArraySizeEquals(String jsonPath, int expectedSize) {
        return "Проверка того что массив по пути = \"%s\" имеет размер \"%d\""
                .formatted(jsonPath, expectedSize);
    }
    private String getAllureErrorMessageValidateArraySizeEquals(String jsonPath, int expectedSize) {
        return "Массив по пути \"%s\" не имеет размер = \"%d\""
                .formatted(jsonPath, expectedSize);
    }
    private String getStepNameValidateArrayEmpty(String jsonPath) {
        return "Проверка того что массив по пути = \"%s\" пустой"
                .formatted(jsonPath);
    }
    private String getAllureErrorMessageValidateArrayEmpty(String jsonPath) {
        return "Массив по пути \"%s\" не пустой."
                .formatted(jsonPath);
    }
    private String getStepNameValidateHasAttribute(String parentJsonPath, String attribute) {
        return "Проверка того что по пути = \"%s\" существует атрибут = \"%s\""
                .formatted(parentJsonPath, attribute);
    }
    private String getAllureErrorMessageValidateHasAttribute(String attribute) {
        return "Атрибут \"%s\" отсутствует"
                .formatted(attribute);
    }

    private String getStepNameValidateNotHasAttribute(String parentJsonPath, String attribute) {
        return "Проверка того что по пути = \"%s\" не существует атрибут = \"%s\""
                .formatted(parentJsonPath, attribute);
    }
    private String getAllureErrorMessageValidateNotHasAttribute(String attribute) {
        return "Атрибут \"%s\" существует"
                .formatted(attribute);
    }

    private <T> String getStepNameValidateJsonPathEquals(String jsonPath, T expectedValue) {
        return "Проверка того что элемент по пути jsonPath \"%s\" равен \"%s\"."
                .formatted(jsonPath, expectedValue);
    }
    private String getStepNameValidateJsonPathContains(String jsonPath, String containsValue) {
        return "Проверка того что элемент по пути jsonPath - \"%s\" содержит строку: \"%s\""
                .formatted(jsonPath, containsValue);
    }
    private String getStepNameValidateJsonPathNotNull(String jsonPath) {
        return "Проверка того что элемент по пути jsonPath - \"%s\" не null"
                .formatted(jsonPath);
    }
    private String getActualValueJsonPath(String jsonPath) {
        String valueJsonPath;
        try {
            valueJsonPath = this.validator.getExchange()
                    .getResponse()
                    .jsonPath()
                    .getString(jsonPath);
            valueJsonPath = "Актуальное значение: \"%s\"."
                    .formatted(valueJsonPath);
        } catch (IllegalArgumentException e) {
            valueJsonPath = null;
        }
        return valueJsonPath;
    }
    private String getStepNameValidateJsonPath(String jsonPath) {
        return "Проверка элемента по jsonPath = \"%s\"."
                .formatted(jsonPath);
    }

    /******************************************Setters*************************************/
    public ResponseValidator allureConfigure(Consumer<AllureConfigurable> config) {
        config.accept(this.validator.getAllureConfig());
        return this;
    }
}
