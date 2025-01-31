package io.github.dmitrymiyuzov.validator.base;

import io.restassured.response.Response;

/**
 * @author "Dmitry Miyuzov"
 */
public interface Responsible {
    Response getResponse();
}
