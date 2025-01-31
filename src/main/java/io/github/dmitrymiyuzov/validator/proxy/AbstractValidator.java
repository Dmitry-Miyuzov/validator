package io.github.dmitrymiyuzov.validator.proxy;

import io.restassured.response.Response;
import io.github.dmitrymiyuzov.validator.base.ValidatorFabric;
import io.github.dmitrymiyuzov.validator.complexValidator.main.ComplexValidator;

public abstract class AbstractValidator {
    protected Response response;
    private ComplexValidator validator;

    protected ComplexValidator getInstance() {
        if (validator == null) {
            validator = ValidatorFabric.beginComplexValidation(response);
        }
        return validator;
    }

    public void validate() {
        getInstance().validate();
    }
}
