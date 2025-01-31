package io.github.dmitrymiyuzov.validator.base;

import io.restassured.response.Response;

/**
 * @author "Dmitry Miyuzov"
 */
public class ResponsibleImpl implements Responsible {
    private final Response response;

    public ResponsibleImpl(Response response) {
        this.response = response;
    }

    @Override
    public Response getResponse() {
        return response;
    }
}
