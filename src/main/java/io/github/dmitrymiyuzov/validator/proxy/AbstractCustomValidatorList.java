package io.github.dmitrymiyuzov.validator.proxy;

import io.restassured.response.Response;

import java.util.List;

@SuppressWarnings("all")
public abstract class AbstractCustomValidatorList<Model> extends AbstractValidator{;
    protected List<Model> modelArray;

    public AbstractCustomValidatorList(List<Model> modelArray, Response responseProxy) {
        this.modelArray = modelArray;
        this.response = responseProxy;
    }

    public List<Model> validateAndReturn() {
        getInstance().validate();
        return modelArray;
    }
}
