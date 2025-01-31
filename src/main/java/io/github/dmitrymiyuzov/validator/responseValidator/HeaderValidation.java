package io.github.dmitrymiyuzov.validator.responseValidator;

import io.github.dmitrymiyuzov.validator.allure.AllureConfig;
import io.github.dmitrymiyuzov.validator.allure.AllureStepValidator;
import io.github.dmitrymiyuzov.validator.base.Validator;
import io.github.dmitrymiyuzov.validator.exceptions.ErrorManager;
import io.github.dmitrymiyuzov.validator.exceptions.response.header.HeaderContainsError;
import io.github.dmitrymiyuzov.validator.exceptions.response.header.HeaderEqualsError;
import io.github.dmitrymiyuzov.validator.exceptions.response.header.HeaderMissingError;
import io.qameta.allure.model.Status;
import org.assertj.core.api.Assertions;

import java.util.List;

/**
 * @author "Dmitry Miyuzov"
 */
public class HeaderValidation {
    private final Validator validator;
    private final ErrorManager errorManager;
    private final AllureConfig allureConfig;
    private final List<Throwable> errors;


    public HeaderValidation(Validator validator) {
        this.validator = validator;
        this.errorManager = validator.getErrorManager();
        this.allureConfig = this.validator.getAllureConfig();
        this.errors = errorManager.getErrors();
    }

    public void headerEquals(String header, String expectedValue) {
        AllureStepValidator currentValidation
                = AllureStepValidator.beginStep(
                getStepNameHeaderEquals(header, expectedValue),
                allureConfig.isDisableValidationNameAllure()
        );
        try {
            tryHeaderEquals(header, expectedValue);
            currentValidation.exitStepWithStopTime(Status.PASSED);
        } catch (HeaderMissingError e) {
            createAllureStepWhenHeaderMissing();
            currentValidation.exitStepWithStopTime(Status.FAILED);
            validator.exitMainAllureStep(Status.FAILED);
            errors.add(e);
            errorManager.throwExceptionBroken();
        } catch (HeaderEqualsError e) {
            createAllureStepWhenHeaderNotEquals(getValueHeaderOrElseThrowHeaderEquals(header), expectedValue);
            currentValidation.exitStepWithStopTime(Status.FAILED);
            validator.getMainAllureStep().exitStepWithStopTime(Status.FAILED);
            errors.add(e);
            errorManager.throwExceptionBroken();
        }
    }

    public void headerEqualsSoft(String header, String expectedValue) {
        AllureStepValidator currentValidation
                = AllureStepValidator.beginStep(
                getStepNameHeaderEquals(header, expectedValue),
                allureConfig.isDisableValidationNameAllure()
        );
        try {
            tryHeaderEquals(header, expectedValue);
            currentValidation.exitStepWithStopTime(Status.PASSED);
        } catch (HeaderMissingError e) {
            createAllureStepWhenHeaderMissing();
            currentValidation.exitStepWithStopTime(Status.FAILED);
            errors.add(e);
            errorManager.setSoftAssert(true);
        } catch (HeaderEqualsError e) {
            createAllureStepWhenHeaderNotEquals(getValueHeaderOrElseThrowHeaderEquals(header), expectedValue);
            currentValidation.exitStepWithStopTime(Status.FAILED);
            errors.add(e);
            errorManager.setSoftAssert(true);
        }
    }

    public void headerContains(String header, String containsValue) {
        AllureStepValidator currentValidation
                = AllureStepValidator.beginStep(
                getStepNameHeaderContains(header, containsValue),
                allureConfig.isDisableValidationNameAllure()
        );
        try {
            tryHeaderContains(header, containsValue);
            currentValidation.exitStepWithStopTime(Status.PASSED);
        } catch (HeaderMissingError e) {
            createAllureStepWhenHeaderMissing();
            currentValidation.exitStepWithStopTime(Status.FAILED);
            validator.exitMainAllureStep(Status.FAILED);
            errors.add(e);
            errorManager.throwExceptionBroken();
        } catch (HeaderContainsError e) {
            createAllureStepWhenHeaderNotContains(getValueHeaderOrElseThrowHeaderEquals(header), containsValue);
            currentValidation.exitStepWithStopTime(Status.FAILED);
            validator.getMainAllureStep().exitStepWithStopTime(Status.FAILED);
            errors.add(e);
            errorManager.throwExceptionBroken();
        }
    }

    public void headerContainsSoft(String header, String containsValue) {
        AllureStepValidator currentValidation
                = AllureStepValidator.beginStep(
                getStepNameHeaderContains(header, containsValue),
                allureConfig.isDisableValidationNameAllure()
        );
        try {
            tryHeaderContains(header, containsValue);
            currentValidation.exitStepWithStopTime(Status.PASSED);
        } catch (HeaderMissingError e) {
            createAllureStepWhenHeaderMissing();
            currentValidation.exitStepWithStopTime(Status.FAILED);
            errors.add(e);
            errorManager.setSoftAssert(true);
        } catch (HeaderContainsError e) {
            createAllureStepWhenHeaderNotContains(getValueHeaderOrElseThrowHeaderEquals(header), containsValue);
            currentValidation.exitStepWithStopTime(Status.FAILED);
            errors.add(e);
            errorManager.setSoftAssert(true);
        }
    }

    private String getStepNameHeaderEquals(String header, String expectedValue) {
        return "Проверка того что header \"%s\" равен \"%s\"."
                .formatted(header, expectedValue);
    }

    private String getStepNameHeaderContains(String header, String containsValue) {
        return "Проверка того что header \"%s\" содержит строку \"%s\"."
                .formatted(header, containsValue);
    }

    private void tryHeaderEquals(String header, String expectedValue) {
        errorManager.increaseCountValidation();
        String valueHeader = getValueHeaderOrElseThrowHeaderEquals(header);
        assertEquals(header, valueHeader, expectedValue);
    }

    private void tryHeaderContains(String header, String containsValue) {
        errorManager.increaseCountValidation();
        String valueHeader = getValueHeaderOrElseThrowHeaderEquals(header);
        assertContains(header, valueHeader, containsValue);
    }

    private String getValueHeaderOrElseThrowHeaderEquals(String header) {
        String value = this.validator.getExchange()
                .getResponse()
                .header(header);
        if (value == null) {
            throw new HeaderMissingError(header, errors);
        }
        return value;
    }

    private void assertEquals(String header, String valueHeader, String expectedValue) {
        try {
            Assertions.assertThat(valueHeader).isEqualTo(expectedValue);
        } catch (AssertionError e) {
            throw new HeaderEqualsError(header, valueHeader, expectedValue, errors);
        }
    }

    private void assertContains(String header, String valueHeader, String containsValue) {
        try {
            Assertions.assertThat(valueHeader).contains(containsValue);
        } catch (AssertionError e) {
            throw new HeaderContainsError(header, valueHeader, containsValue, errors);
        }
    }

    private void createAllureStepWhenHeaderMissing() {
        AllureStepValidator.doStep("Заголовок не обнаружен.", Status.FAILED, allureConfig.isDisableMessageErrorAllure());
    }

    private void createAllureStepWhenHeaderNotEquals(String valueHeader, String expectedValue) {
        AllureStepValidator.doStep(
                "Ожидаемое значение: \"%s\", актуальное значение: \"%s\""
                        .formatted(expectedValue, valueHeader),
                Status.FAILED,
                allureConfig.isDisableMessageErrorAllure());
    }

    private void createAllureStepWhenHeaderNotContains(String valueHeader, String containsValue) {
        AllureStepValidator.doStep(
                "Заголовок не содержит строку: \"%s\". Актуально значение заголовка: \"%s\"."
                        .formatted(containsValue, valueHeader),
                Status.FAILED,
                allureConfig.isDisableMessageErrorAllure());
    }
}