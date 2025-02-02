package io.github.dmitrymiyuzov.validator.assertionValidator;

import io.github.dmitrymiyuzov.validator.allure.AllureConfig;
import io.github.dmitrymiyuzov.validator.allure.AllureStepValidator;
import io.github.dmitrymiyuzov.validator.base.Validator;
import io.github.dmitrymiyuzov.validator.exceptions.ErrorManager;
import io.github.dmitrymiyuzov.validator.exceptions.assertion.AssertValidationError;
import io.github.dmitrymiyuzov.validator.implementation.assertion.ThrowingCallable;
import io.qameta.allure.model.Status;

import java.time.DateTimeException;
import java.util.List;

/**
 * @author "Dmitry Miyuzov"
 */
public class AssertValidation {
    private final Validator validator;
    private final ErrorManager errorManager;
    private final AllureConfig allureConfig;

    public AssertValidation(Validator validator) {
        this.validator = validator;
        this.errorManager = validator.getErrorManager();
        this.allureConfig = validator.getAllureConfig();
    }

    public void validation(String allureStepName, Runnable runnable, String errorMessage) {
        AllureStepValidator currentValidation = AllureStepValidator.beginStep(allureStepName, allureConfig.isDisableValidationNameAllure());
        try {
            errorManager.increaseCountValidation();
            runnable.run();
            currentValidation.exitStepWithStopTime(Status.PASSED);
        } catch (AssertionError e) {
            createAllureStepErrorMessage(errorMessage, e);
            currentValidation.exitStepWithStopTime(Status.FAILED);
            validator.exitMainAllureStep(Status.FAILED);
            addErrorAssertionToPool(e.getMessage());
            errorManager.throwExceptionBroken();
        } catch (Exception e) {
            createAllureStepErrorMessage(errorMessage, e);
            currentValidation.exitStepWithStopTime(Status.BROKEN);
            validator.exitMainAllureStep(Status.BROKEN);
            addErrorAssertionToPool(e.getMessage());
            errorManager.throwExceptionBroken();
        }
    }

    public void validation(String allureStepName, Runnable runnable) {
        validation(allureStepName, runnable, null);
    }

    public void validationSoft(String allureStepName, Runnable runnable, String errorMessage) {
        AllureStepValidator currentValidation = AllureStepValidator.beginStep(allureStepName, allureConfig.isDisableValidationNameAllure());
        try {
            errorManager.increaseCountValidation();
            runnable.run();
            currentValidation.exitStepWithStopTime(Status.PASSED);
        } catch (AssertionError e) {
            createAllureStepErrorMessage(errorMessage, e);
            currentValidation.exitStepWithStopTime(Status.FAILED);
            addErrorAssertionToPool(e.getMessage());
            errorManager.setSoftAssert(true);
            validator.setMainAllureStepStatusWhenSoft(Status.FAILED);
        } catch (Exception e) {
            createAllureStepErrorMessage(errorMessage, e);
            currentValidation.exitStepWithStopTime(Status.BROKEN);
            addErrorAssertionToPool(e.getMessage());
            errorManager.setSoftAssert(true);
            validator.setMainAllureStepStatusWhenSoft(Status.BROKEN);
        }
    }

    public void validationSoft(String allureStepName, Runnable runnable) {
        validationSoft(allureStepName, runnable, null);

    }

    public void validationDate(String allureStepName, Runnable runnable) {
        AllureStepValidator currentValidation = AllureStepValidator.beginStep(allureStepName, allureConfig.isDisableValidationNameAllure());
        try {
            errorManager.increaseCountValidation();
            runnable.run();
            currentValidation.exitStepWithStopTime(Status.PASSED);
        } catch (AssertionError | DateTimeException e) {
            String errorMessage;
            if (e instanceof DateTimeException) {
                errorMessage = "Не получилось распарсить дату в указанный формат";
            } else {
                errorMessage = null;
            }
            createAllureStepErrorMessage(errorMessage, e);
            currentValidation.exitStepWithStopTime(Status.FAILED);
            addErrorAssertionToPool(e.getMessage());
            errorManager.throwExceptionBroken();
        }
    }

    public void validationDateSoft(String allureStepName, Runnable runnable) {
        AllureStepValidator currentValidation = AllureStepValidator.beginStep(allureStepName, allureConfig.isDisableValidationNameAllure());
        try {
            errorManager.increaseCountValidation();
            runnable.run();
            currentValidation.exitStepWithStopTime(Status.PASSED);
        } catch (AssertionError | DateTimeException e) {
            String errorMessage;
            if (e instanceof DateTimeException) {
                errorMessage = "Не получилось распарсить дату в указанный формат";
            } else {
                errorMessage = null;
            }
            createAllureStepErrorMessage(errorMessage, e);
            currentValidation.exitStepWithStopTime(Status.FAILED);
            addErrorAssertionToPool(e.getMessage());
            errorManager.setSoftAssert(true);
            validator.setMainAllureStepStatusWhenSoft(Status.FAILED);
        }
    }

    public void validationThatCodeDoesNotException(String allureStepName, ThrowingCallable call, String errorMessageAllure) {
        AllureStepValidator currentValidation = AllureStepValidator.beginStep(allureStepName, allureConfig.isDisableValidationNameAllure());
        try {
            errorManager.increaseCountValidation();
            call.call();
            currentValidation.exitStepWithStopTime(Status.PASSED);
        } catch (Throwable e) {
            if (errorMessageAllure != null) {
                createAllureStepErrorMessage(errorMessageAllure, e);
            }
            currentValidation.exitStepWithStopTime(Status.FAILED);
            validator.exitMainAllureStep(Status.FAILED);
            addErrorAssertionToPool(e.getMessage());
            errorManager.throwExceptionBroken();
        }
    }

    public void validationThatCodeDoesNotExceptionSoft(String allureStepName, ThrowingCallable call, String errorMessageAllure) {
        AllureStepValidator currentValidation = AllureStepValidator.beginStep(allureStepName, allureConfig.isDisableValidationNameAllure());
        try {
            errorManager.increaseCountValidation();
            call.call();
            currentValidation.exitStepWithStopTime(Status.PASSED);
        } catch (Throwable e) {
            if (errorMessageAllure != null) {
                createAllureStepErrorMessage(errorMessageAllure, e);
            }
            currentValidation.exitStepWithStopTime(Status.FAILED);
            addErrorAssertionToPool(e.getMessage());
            errorManager.setSoftAssert(true);
            validator.setMainAllureStepStatusWhenSoft(Status.FAILED);
        }
    }

    private void createAllureStepErrorMessage(String allureErrorMessage, Throwable e) {
        if (allureErrorMessage != null) {
            AllureStepValidator.doStep(allureErrorMessage, Status.FAILED, allureConfig.isDisableMessageErrorAllure());
        } else {
            AllureStepValidator.doStep(e.getMessage(), Status.FAILED, allureConfig.isDisableMessageErrorAllure());
        }
    }

    private void addErrorAssertionToPool(String message) {
        List<Throwable> errors = errorManager.getErrors();
        AssertValidationError assertValidationError = new AssertValidationError(message, errors);
        errors.add(assertValidationError);
    }
}
