package io.github.dmitrymiyuzov.validator.proxy;

import io.github.dmitrymiyuzov.validator.proxy.markerInterface.ResponseModel;
import io.github.dmitrymiyuzov.validator.proxy.markerInterface.ResponseModelDefaultCustomValidator;
import io.github.dmitrymiyuzov.validator.proxy.markerInterface.ResponseModelDefaultCustomValidatorList;
import io.restassured.response.Response;
import io.github.dmitrymiyuzov.validator.complexValidator.main.ComplexValidator;

@SuppressWarnings("all")
public interface ResponseProxy extends Response {
    <Validator extends AbstractCustomValidatorWithoutModel>
    Validator customValidationWithoutModel(int expectedStatusCode, Class<Validator> customValidator);

    <Validator extends AbstractCustomValidator<Model>, Model extends ResponseModelDefaultCustomValidator<Validator>>
    Validator customValidation(int expectedStatusCode, Class<Model> model);

    <Model extends ResponseModel, Validator extends AbstractCustomValidator<Model>>
    Validator customValidation(int expectedStatusCode, Class<Model> model, Class<Validator> customValidator);

    <Model extends ResponseModel, Validator extends AbstractCustomValidator<Model>>
    Validator customValidation(int expectedStatusCode, String jsonPath, Class<Model> model, Class<Validator> customValidator);

    <Validator extends AbstractCustomValidator<String>>
    Validator customValidationString(int expectedStatusCode, Class<Validator> customValidator);

    <Validator extends AbstractCustomValidatorList<Model>, Model extends ResponseModelDefaultCustomValidatorList<Validator>>
    Validator customValidationList(int expectedStatusCode, Class<Model> model);

    <Model extends ResponseModel, Validator extends AbstractCustomValidatorList<Model>>
    Validator customValidationList(int expectedStatusCode, String jsonPath, Class<Model> model, Class<Validator> customValidator);

    /*
    Предполагается что в ответе приходит массив байтов - который можно спамить в лист моделей.
     */
    <Model extends ResponseModel, Validator extends AbstractCustomValidatorList<Model>>
    Validator customValidationXLSXList(int expectedStatusCode, Class<Model> model, Class<Validator> customValidator);

    <Model extends ResponseModel, Validator extends AbstractCustomValidatorList<Model>>
    Validator customValidationCSVList(int expectedStatusCode, char separator, Class<Model> model, Class<Validator> customValidator);

    ComplexValidator validation();
}
