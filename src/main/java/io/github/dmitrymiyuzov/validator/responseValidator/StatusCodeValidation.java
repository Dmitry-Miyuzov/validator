package io.github.dmitrymiyuzov.validator.responseValidator;

import io.github.dmitrymiyuzov.validator.allure.AllureConfig;
import io.github.dmitrymiyuzov.validator.allure.AllureStepValidator;
import io.github.dmitrymiyuzov.validator.base.Validator;
import io.github.dmitrymiyuzov.validator.exceptions.ErrorManager;
import io.qameta.allure.model.Status;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.github.dmitrymiyuzov.validator.exceptions.response.statusCode.StatusCodeValidationError;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author "Dmitry Miyuzov"
 */
public class StatusCodeValidation {
    private final Validator validator;
    private final ErrorManager errorManager;
    private final AllureConfig allureConfig;

    public StatusCodeValidation(Validator validator) {
        this.validator = validator;
        this.errorManager = validator.getErrorManager();
        this.allureConfig = this.validator.getAllureConfig();
    }

    public void validationSoft(Supplier<ValidatableResponse> response, int expectedStatus) {
        AllureStepValidator currentValidation = AllureStepValidator.beginStep(getStepName(expectedStatus),
                allureConfig.isDisableValidationNameAllure());
        try {
            tryValidation(response);
            currentValidation.exitStepWithStopTime(Status.PASSED);
        } catch (AssertionError e) {
            createAllureStepWhenError(expectedStatus);
            currentValidation.exitStepWithStopTime(Status.FAILED);
            addErrorToPool(e);
            errorManager.setSoftAssert(true);
            validator.setMainAllureStepStatusWhenSoft(Status.FAILED);
        }
    }

    public void validationSoft(Supplier<ValidatableResponse> response, int expectedStatus, Consumer<Response> orElse) {
        AllureStepValidator currentValidation = AllureStepValidator.beginStep(getStepName(expectedStatus),
                allureConfig.isDisableValidationNameAllure());
        try {
            tryValidation(response);
            currentValidation.exitStepWithStopTime(Status.PASSED);
        } catch (AssertionError e) {
            orElse.accept(validator.getExchange().getResponse());
            createAllureStepWhenError(expectedStatus);
            currentValidation.exitStepWithStopTime(Status.FAILED);
            addErrorToPool(e);
            errorManager.setSoftAssert(true);
            validator.setMainAllureStepStatusWhenSoft(Status.FAILED);
        }
    }

    public void validationAndElseSoft(Supplier<ValidatableResponse> response, int expectedStatus, Consumer<Response> andElse) {
        AllureStepValidator currentValidation = AllureStepValidator.beginStep(getStepName(expectedStatus),
                allureConfig.isDisableValidationNameAllure());
        try {
            tryValidation(response);
            currentValidation.exitStepWithStopTime(Status.PASSED);
            andElse.accept(validator.getExchange().getResponse());
        } catch (AssertionError e) {
            andElse.accept(validator.getExchange().getResponse());
            createAllureStepWhenError(expectedStatus);
            currentValidation.exitStepWithStopTime(Status.FAILED);
            addErrorToPool(e);
            errorManager.setSoftAssert(true);
            validator.setMainAllureStepStatusWhenSoft(Status.FAILED);
        }
    }

    public void validationAndElse(Supplier<ValidatableResponse> response, int expectedStatus, Consumer<Response> andElse) {
        AllureStepValidator currentValidation = AllureStepValidator.beginStep(getStepName(expectedStatus),
                allureConfig.isDisableValidationNameAllure());
        try {
            tryValidation(response);
            currentValidation.exitStepWithStopTime(Status.PASSED);
            andElse.accept(validator.getExchange().getResponse());
        } catch (AssertionError e) {
            andElse.accept(validator.getExchange().getResponse());
            createAllureStepWhenError(expectedStatus);
            currentValidation.exitStepWithStopTime(Status.FAILED);
            validator.exitMainAllureStep(Status.FAILED);
            addErrorToPool(e);
            errorManager.throwExceptionBroken();
        }
    }

    public void validation(Supplier<ValidatableResponse> response, int expectedStatus, Consumer<Response> orElse) {
        AllureStepValidator currentValidation = AllureStepValidator.beginStep(getStepName(expectedStatus),
                allureConfig.isDisableValidationNameAllure());
        try {
            tryValidation(response);
            currentValidation.exitStepWithStopTime(Status.PASSED);
        } catch (AssertionError e) {
            orElse.accept(validator.getExchange().getResponse());
            createAllureStepWhenError(expectedStatus);
            currentValidation.exitStepWithStopTime(Status.FAILED);
            validator.exitMainAllureStep(Status.FAILED);
            addErrorToPool(e);
            errorManager.throwExceptionBroken();
        }
    }
    public void validation(Supplier<ValidatableResponse> response, int expectedStatus) {
        validation(response, expectedStatus, orElse -> {});
    }

    private String getStepName(int expectedStatus) {
        return "Проверка того что статус код = " + expectedStatus + ".";
    }

    private void tryValidation(Supplier<ValidatableResponse> response) {
        errorManager.increaseCountValidation();
        response.get();
    }

    private void createAllureStepWhenError(int expectedStatus) {
        int actual = validator.getExchange()
                .getResponse()
                .statusCode();

        AllureStepValidator.beginStep(
                        "Ожидаемый: %d, актуальный: %d"
                                .formatted(expectedStatus, actual),
                        allureConfig.isDisableMessageErrorAllure()
                )
                .exitStepWithStopTime(Status.FAILED);
    }

    private void addErrorToPool(AssertionError assertionError) {
        List<Throwable> errors = errorManager.getErrors();
        StatusCodeValidationError statusCodeValidationError = new StatusCodeValidationError(assertionError.getMessage(), errors);
        errors.add(statusCodeValidationError);
    }
}
