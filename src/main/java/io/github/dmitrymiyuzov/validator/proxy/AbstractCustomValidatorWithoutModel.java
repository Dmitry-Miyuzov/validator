package io.github.dmitrymiyuzov.validator.proxy;

import io.restassured.response.Response;

public abstract class AbstractCustomValidatorWithoutModel extends AbstractValidator {
    public AbstractCustomValidatorWithoutModel(Response responseProxy) {
        this.response = responseProxy;
    }
}
