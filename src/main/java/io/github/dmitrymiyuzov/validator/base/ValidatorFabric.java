package io.github.dmitrymiyuzov.validator.base;

import io.github.dmitrymiyuzov.validator._config.ThreadLocalValidatorFabricConfig;
import io.github.dmitrymiyuzov.validator.complexValidator.main.ComplexValidator;
import io.github.dmitrymiyuzov.validator.proxy.ResponseProxy;
import io.github.dmitrymiyuzov.validator.proxy.ResponseProxyHandler;
import io.restassured.response.Response;
import org.assertj.core.util.CheckReturnValue;
import io.github.dmitrymiyuzov.validator.assertionValidator.main.AssertionValidator;
import io.github.dmitrymiyuzov.validator.responseValidator.main.ResponseValidator;

import java.lang.reflect.Proxy;

/**
 * @author "Dmitry Miyuzov"
 * Designed for the team - frsof at.
 *
 */
public class ValidatorFabric {
    private static final String MAIN_DEFAULT_ASSERT_ALLURE = "Проверки (Assert)";
    private static final String MAIN_DEFAULT_RESPONSE_ALLURE = "Проверки ответа";
    private static final String MAIN_DEFAULT_COMPLEX_ALLURE = "Проверки ответа и общие проверки";
    /**
     * Начало цепочки c валидацией ТОЛЬКО ответа
     *
     * @param allureStepName Названия шага в аллюр отчете.
     */
    @CheckReturnValue
    public static ResponseValidator beginResponseValidation(String allureStepName, Responsible responsible) {
        ThreadLocalValidatorFabricConfig config = new ThreadLocalValidatorFabricConfig();
        Validator validator = new Validator(allureStepName, responsible, config);
        return new ResponseValidator(validator);
    }

    @CheckReturnValue
    public static ResponseValidator beginResponseValidation(Responsible responsible) {
        return beginResponseValidation(MAIN_DEFAULT_RESPONSE_ALLURE, responsible);
    }

    @CheckReturnValue
    public static ResponseValidator beginResponseValidation(String allureStepName, Response response) {
        return beginResponseValidation(allureStepName, new ResponsibleImpl(response));
    }

    @CheckReturnValue
    public static ResponseValidator beginResponseValidation(Response response) {
        return beginResponseValidation(new ResponsibleImpl(response));
    }

    /**
     * Начало цепочки только с валидацией ТОЛЬКО общих (ассерты)
     *
     * @param allureStepName Названия шага в аллюр отчете.
     */
    @CheckReturnValue
    public static AssertionValidator beginAssertValidation(String allureStepName) {
        ThreadLocalValidatorFabricConfig config = new ThreadLocalValidatorFabricConfig();
        Validator validator = new Validator(allureStepName, config);
        return new AssertionValidator(validator);
    }

    @CheckReturnValue
    public static AssertionValidator beginAssertValidation() {
        return beginAssertValidation(MAIN_DEFAULT_ASSERT_ALLURE);
    }

    /**
     * Начало цепочки только с валидацией ответа и общих (ассерты)
     *
     * @param allureStepName Названия шага в аллюр отчете.
     */

    @CheckReturnValue
    public static ComplexValidator beginComplexValidation(String allureStepName, Responsible responsible) {
        ThreadLocalValidatorFabricConfig config = new ThreadLocalValidatorFabricConfig();
        Validator validator = new Validator(allureStepName, responsible, config);
        AssertionValidator assertionValidator = new AssertionValidator(validator);
        ResponseValidator responseValidator = new ResponseValidator(validator);
        return new ComplexValidator(assertionValidator, responseValidator, validator);
    }

    @CheckReturnValue
    public static ComplexValidator beginComplexValidation(Responsible responsible) {
        return beginComplexValidation(MAIN_DEFAULT_COMPLEX_ALLURE, responsible);
    }

    @CheckReturnValue
    public static ComplexValidator beginComplexValidation(String allureStepName, Response response) {
        return beginComplexValidation(allureStepName, new ResponsibleImpl(response));
    }

    @CheckReturnValue
    public static ComplexValidator beginComplexValidation(Response response) {
        return beginComplexValidation(new ResponsibleImpl(response));
    }

    public static ResponseProxy proxy(Response response) {
        return (ResponseProxy) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{ResponseProxy.class},
                new ResponseProxyHandler(response)
        );
    }
}
