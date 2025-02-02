package io.github.dmitrymiyuzov.validator.base;


import io.github.dmitrymiyuzov.validator._config.impl.ValidatorFabricConfig;
import io.github.dmitrymiyuzov.validator.allure.AllureConfig;
import io.github.dmitrymiyuzov.validator.allure.AllureStepValidator;
import io.github.dmitrymiyuzov.validator.exceptions.ErrorManager;
import io.qameta.allure.model.Status;
import io.github.dmitrymiyuzov.validator.assertionValidator.AssertValidation;
import io.github.dmitrymiyuzov.validator.responseValidator.BodyValidation;
import io.github.dmitrymiyuzov.validator.responseValidator.HeaderValidation;
import io.github.dmitrymiyuzov.validator.responseValidator.StatusCodeValidation;

/**
 * @author "Dmitry Miyuzov"
 */
public class Validator {
    private static final String TEXT_CHAIN_END_SUCCESS = "Цепочка проверок завершилась успешно";
    private final ValidatorFabricConfig config;
    private final StatusCodeValidation statusCodeValidation;
    private final HeaderValidation headerValidation;
    private final BodyValidation bodyValidation;
    private final AssertValidation assertValidation;
    private final ErrorManager errorManager;
    private final AllureStepValidator mainAllureStep;
    private final AllureConfig allureConfig;
    private Status mainAllureStepStatus;
    private Responsible responsible;

    Validator(String allureStepName, ValidatorFabricConfig config) {
        this.config = config;
        this.allureConfig = new AllureConfig();
        this.errorManager = new ErrorManager(config);
        this.statusCodeValidation = new StatusCodeValidation(this);
        this.headerValidation = new HeaderValidation(this);
        this.bodyValidation = new BodyValidation(this);
        this.assertValidation = new AssertValidation(this);
        this.mainAllureStep = AllureStepValidator.beginStep(allureStepName, false);
    }

    Validator(String allureStepName, Responsible responsible, ValidatorFabricConfig config) {
        this.config = config;
        this.allureConfig = new AllureConfig();
        this.errorManager = new ErrorManager(config);
        this.statusCodeValidation = new StatusCodeValidation(this);
        this.headerValidation = new HeaderValidation(this);
        this.bodyValidation = new BodyValidation(this);
        this.assertValidation = new AssertValidation(this);
        this.responsible = responsible;
        this.mainAllureStep = AllureStepValidator.beginStep(allureStepName, false);
    }

    public void exitMainAllureStep(Status status) {
        if (mainAllureStep != null) {
            mainAllureStep.exitStepWithStopTime(status);
        }
    }

    /**
     * Завершающие метод в цепочке проверок. Вызов обязателен.
     */
    public void validate() {
        if (errorManager.isSoftAssert()) {
            failedExit(mainAllureStepStatus);
        } else {
            passedExit();
        }
    }

    private void failedExit(Status status) {
        AllureStepValidator.doStep(errorManager.getFailedTextChainEnd(), Status.FAILED, false);
        exitMainAllureStep(status);

        errorManager.throwException(errorManager.getFailedTextChainEnd());
    }

    private void passedExit() {
        AllureStepValidator.doStep(TEXT_CHAIN_END_SUCCESS, Status.PASSED, false);
        mainAllureStep.exitStepWithStopTime(Status.PASSED);

    }

    ////////////////////////////getters/////////////////////////////

    public ValidatorFabricConfig getConfig() {
        return config;
    }

    public AllureConfig getAllureConfig() {
        return allureConfig;
    }

    public StatusCodeValidation getStatusCodeValidation() {
        return statusCodeValidation;
    }

    public HeaderValidation getHeaderValidation() {
        return headerValidation;
    }

    public BodyValidation getBodyValidation() {
        return bodyValidation;
    }

    public AssertValidation getAssertValidation() {
        return assertValidation;
    }

    public ErrorManager getErrorManager() {
        return errorManager;
    }

    public AllureStepValidator getMainAllureStep() {
        return mainAllureStep;
    }

    public void setMainAllureStepStatusWhenSoft(Status status) {
        if (mainAllureStepStatus == null) {
            mainAllureStepStatus = status;
        } else if (mainAllureStepStatus.equals(Status.FAILED)) {
            mainAllureStepStatus = status;
        }

    }

    public Responsible getExchange() {
        return responsible;
    }
}
