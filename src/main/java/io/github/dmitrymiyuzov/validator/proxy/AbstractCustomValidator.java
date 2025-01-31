package io.github.dmitrymiyuzov.validator.proxy;

import io.restassured.response.Response;

@SuppressWarnings("all")
public abstract class AbstractCustomValidator<Model> extends AbstractValidator{
    protected Model model;

    public AbstractCustomValidator(Model model, Response responseProxy) {
        this.model = model;
        this.response = responseProxy;
    }

    protected AbstractCustomValidator<Model> validateSchemaSoft(String schema) {
        getInstance().matchesJsonSchemaSoft(schema);
        return this;
    }

    public Model validateAndReturn() {
        getInstance().validate();
        return model;
    }
}
