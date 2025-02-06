package io.github.dmitrymiyuzov.validator.responseValidator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dmitrymiyuzov.validator.allure.AllureConfig;
import io.github.dmitrymiyuzov.validator.allure.AllureStepValidator;
import io.github.dmitrymiyuzov.validator.base.Validator;
import io.github.dmitrymiyuzov.validator.exceptions.ErrorManager;
import io.github.dmitrymiyuzov.validator.exceptions.response.body.*;
import io.github.dmitrymiyuzov.validator.exceptions.response.body.bodyFile.BodyNotEqualsFileError;
import io.github.dmitrymiyuzov.validator.exceptions.response.body.bodyFile.ExpectedFileNotFoundError;
import io.github.dmitrymiyuzov.validator.exceptions.response.body.bodyFile.ReadFileError;
import io.github.dmitrymiyuzov.validator.exceptions.response.body.bodyFile.ReadTreeError;
import io.github.dmitrymiyuzov.validator.exceptions.response.jsonSchema.JsonSchemaError;
import io.github.dmitrymiyuzov.validator.helpers.FileReaderHelper;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.restassured.response.ValidatableResponse;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.hamcrest.Matcher;
import org.json.JSONObject;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author "Dmitry Miyuzov"
 */
public class BodyValidation {
    private final Validator validator;
    private final ErrorManager errorManager;
    private final AllureConfig allureConfig;

    public BodyValidation(Validator validator) {
        this.validator = validator;
        this.errorManager = validator.getErrorManager();
        this.allureConfig = this.validator.getAllureConfig();
    }

    public <T> void body(String allureStepName, Supplier<ValidatableResponse> response, String jsonPath, Matcher<T> matcher, String allureErrorMessage) {
        AllureStepValidator currentValidation = AllureStepValidator.beginStep(allureStepName, allureConfig.isDisableValidationNameAllure());
        try {
            tryValidation(response);
            currentValidation.exitStepWithStopTime(Status.PASSED);
        } catch (AssertionError e) {
            createAllureStepErrorMessage(allureErrorMessage);
            currentValidation.exitStepWithStopTime(Status.FAILED);
            validator.exitMainAllureStep(Status.FAILED);
            addErrorWhenNotAssertMatcherToPool(e.getMessage(), jsonPath);
            errorManager.throwExceptionBroken();
        } catch (IllegalArgumentException e) {
            if (matcher == null) {
                createAllureStepMatcherNull();
                addErrorMatcherNullToPool();
            } else {
                createAllureStepInvalidJsonPath(jsonPath);
                addErrorInvalidJsonPathToPool(jsonPath);
            }
            createAllureStepErrorMessage(allureErrorMessage);
            currentValidation.exitStepWithStopTime(Status.FAILED);
            validator.exitMainAllureStep(Status.FAILED);
            errorManager.throwExceptionBroken();
        } catch (Exception e) {
            if (e instanceof IllegalStateException && e.getMessage().contains("but no content-type was defined")) {
                createAllureStepWhenNotDefineContentType();
                addErrorResponseNotDefineContentType();
            } else {
                createAllureStepErrorMessage(allureErrorMessage);
                addUnknownErrorToPool(e.getMessage());
            }

            currentValidation.exitStepWithStopTime(Status.FAILED);
            validator.exitMainAllureStep(Status.FAILED);
            errorManager.throwExceptionBroken();
        }
    }

    public <T> void matchesJsonSchema(String jsonSchema) {
        AllureStepValidator currentValidation = AllureStepValidator.beginStep(
                "Проверка на соответствие json-схемы",
                allureConfig.isDisableValidationNameAllure()
        );
        String allureErrorMessage = "Ответ не соответствует json-схеме.";
        try {
            tryValidationJsonScheme(jsonSchema);
            currentValidation.exitStepWithStopTime(Status.PASSED);
        } catch (ValidationException e) {
            createAllureStepErrorMessage(allureErrorMessage);
            Allure.addAttachment("errorSchema", "application/json", e.toJSON().toString(4));
            currentValidation.exitStepWithStopTime(Status.FAILED);
            validator.exitMainAllureStep(Status.FAILED);
            addErrorWhenNotAssertJsonSchema();
            errorManager.throwExceptionBroken();
        }
    }

    public <T> void body(String allureStepName, Supplier<ValidatableResponse> response, String jsonPath, Matcher<T> matcher) {
        body(allureStepName, response, jsonPath, matcher, null);
    }

    public void bodyEqualsFile(String pathFile) {
        AllureStepValidator currentValidation = AllureStepValidator.beginStep(
                "Проверка того что файл и ответ совпадают (идет сравнение по дереву)",
                allureConfig.isDisableValidationNameAllure());
        try {
            tryValidationFile(pathFile);
            currentValidation.exitStepWithStopTime(Status.PASSED);
        } catch (ExpectedFileNotFoundError e) {
            AllureStepValidator.doStep("Файл не найден", Status.FAILED, allureConfig.isDisableMessageErrorAllure());
            currentValidation.exitStepWithStopTime(Status.FAILED);
            validator.exitMainAllureStep(Status.FAILED);
            errorManager.getErrors().add(e);
            errorManager.throwExceptionBroken();
        } catch (ReadFileError e) {
            AllureStepValidator.doStep("Ошибка чтения файла", Status.FAILED, allureConfig.isDisableMessageErrorAllure());
            currentValidation.exitStepWithStopTime(Status.FAILED);
            validator.exitMainAllureStep(Status.FAILED);
            errorManager.getErrors().add(e);
            errorManager.throwExceptionBroken();
        } catch (ReadTreeError e) {
            AllureStepValidator.doStep("Ошибка при попытке парсинга ответа или файла", Status.FAILED,
                    allureConfig.isDisableMessageErrorAllure());
            currentValidation.exitStepWithStopTime(Status.FAILED);
            validator.exitMainAllureStep(Status.FAILED);
            errorManager.getErrors().add(e);
            errorManager.throwExceptionBroken();
        } catch (AssertionError e) {
            AllureStepValidator.doStep("Файл и ответ не равны", Status.FAILED, allureConfig.isDisableMessageErrorAllure());
            currentValidation.exitStepWithStopTime(Status.FAILED);
            validator.exitMainAllureStep(Status.FAILED);
            errorManager.getErrors().add(e);
            errorManager.throwExceptionBroken();
        }
    }

    public <T> void bodySoft(String allureStepName, Supplier<ValidatableResponse> response,
                             String jsonPath, Matcher<T> matcher, String allureErrorMessage) {
        AllureStepValidator currentValidation = AllureStepValidator.beginStep(allureStepName, allureConfig.isDisableValidationNameAllure());
        try {
            tryValidation(response);
            currentValidation.exitStepWithStopTime(Status.PASSED);
        } catch (AssertionError e) {
            createAllureStepErrorMessage(allureErrorMessage);
            currentValidation.exitStepWithStopTime(Status.FAILED);
            addErrorWhenNotAssertMatcherToPool(e.getMessage(), jsonPath);
            errorManager.setSoftAssert(true);
            validator.setMainAllureStepStatusWhenSoft(Status.FAILED);
        } catch (IllegalArgumentException e) {
            if (matcher == null) {
                createAllureStepMatcherNull();
                addErrorMatcherNullToPool();
            } else {
                createAllureStepInvalidJsonPath(jsonPath);
                addErrorInvalidJsonPathToPool(jsonPath);
            }
            createAllureStepErrorMessage(allureErrorMessage);
            currentValidation.exitStepWithStopTime(Status.FAILED);
            errorManager.setSoftAssert(true);
            validator.setMainAllureStepStatusWhenSoft(Status.FAILED);
        } catch (Exception e) {
            if (e instanceof IllegalStateException && e.getMessage().contains("but no content-type was defined")) {
                createAllureStepWhenNotDefineContentType();
                addErrorResponseNotDefineContentType();
            } else {
                createAllureStepErrorMessage(allureErrorMessage);
                addUnknownErrorToPool(e.getMessage());
            }
            currentValidation.exitStepWithStopTime(Status.FAILED);
            errorManager.setSoftAssert(true);
            validator.setMainAllureStepStatusWhenSoft(Status.FAILED);
        }
    }

    public <T> void matchesJsonSchemaSoft(String jsonSchema) {
        AllureStepValidator currentValidation = AllureStepValidator.beginStep(
                "Проверка на соответствие json-схемы",
                allureConfig.isDisableValidationNameAllure()
        );
        String allureErrorMessage = "Ответ не соответствует json-схеме.";
        try {
            tryValidationJsonScheme(jsonSchema);
            currentValidation.exitStepWithStopTime(Status.PASSED);
        } catch (ValidationException e) {
            createAllureStepErrorMessage(allureErrorMessage);
            Allure.addAttachment("errorSchema", "application/json", e.toJSON().toString(4));
            currentValidation.exitStepWithStopTime(Status.FAILED);
            addErrorWhenNotAssertJsonSchema();
            errorManager.setSoftAssert(true);
            validator.setMainAllureStepStatusWhenSoft(Status.FAILED);
        }
    }

    public <T> void bodySoft(String allureStepName, Supplier<ValidatableResponse> response, String jsonPath, Matcher<T> matcher) {
        bodySoft(allureStepName, response, jsonPath, matcher, null);
    }

    public void bodyEqualsFileSoft(String pathFile) {
        AllureStepValidator currentValidation = AllureStepValidator.beginStep(
                "Проверка того что файл и ответ совпадают (идет сравнение по дереву)",
                allureConfig.isDisableValidationNameAllure());
        try {
            tryValidationFile(pathFile);
            currentValidation.exitStepWithStopTime(Status.PASSED);
        } catch (ExpectedFileNotFoundError e) {
            AllureStepValidator.doStep("Файл не найден", Status.FAILED, allureConfig.isDisableMessageErrorAllure());
            currentValidation.exitStepWithStopTime(Status.FAILED);
            errorManager.getErrors().add(e);
            errorManager.setSoftAssert(true);
            validator.setMainAllureStepStatusWhenSoft(Status.FAILED);
        } catch (ReadFileError e) {
            AllureStepValidator.doStep("Ошибка чтения файла", Status.FAILED, allureConfig.isDisableMessageErrorAllure());
            currentValidation.exitStepWithStopTime(Status.FAILED);
            errorManager.getErrors().add(e);
            errorManager.setSoftAssert(true);
            validator.setMainAllureStepStatusWhenSoft(Status.FAILED);
        } catch (ReadTreeError e) {
            AllureStepValidator.doStep("Ошибка при попытке парсинга ответа или файла", Status.FAILED,
                    allureConfig.isDisableMessageErrorAllure());
            currentValidation.exitStepWithStopTime(Status.FAILED);
            errorManager.getErrors().add(e);
            errorManager.setSoftAssert(true);
            validator.setMainAllureStepStatusWhenSoft(Status.FAILED);
        } catch (AssertionError e) {
            AllureStepValidator.doStep("Файл и ответ не равны", Status.FAILED, allureConfig.isDisableMessageErrorAllure());
            currentValidation.exitStepWithStopTime(Status.FAILED);
            errorManager.getErrors().add(e);
            errorManager.setSoftAssert(true);
            validator.setMainAllureStepStatusWhenSoft(Status.FAILED);
        }
    }

    private void tryValidation(Supplier<ValidatableResponse> response) {
        errorManager.increaseCountValidation();
        response.get();
    }

    private void tryValidationJsonScheme(String jsonSchema) {
        errorManager.increaseCountValidation();

        JSONObject rawSchema = new JSONObject(jsonSchema);
        Schema schema = SchemaLoader.load(rawSchema);
        String body = validator.getExchange()
                .getResponse()
                .getBody()
                .asString();


        Allure.addAttachment("schema", "application/json", jsonSchema);

        schema.validate(new JSONObject(body));
    }

    private void tryValidationFile(String pathFile) {
        errorManager.increaseCountValidation();

        String bodyString = this.validator.getExchange().getResponse().getBody().asPrettyString();
        String expectedString = FileReaderHelper.readFileAsString(pathFile, errorManager);
        Allure.addAttachment("Ответ", "application/json", bodyString);
        Allure.addAttachment("Файл", "application/json", expectedString);


        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode actualNode;
        JsonNode expectedNode;
        try {
            actualNode = objectMapper.readTree(bodyString);
            expectedNode = objectMapper.readTree(expectedString);
        } catch (JsonProcessingException e) {
            throw new ReadTreeError(errorManager.getErrors());
        }

        if (!actualNode.equals(expectedNode)) {
            throw new BodyNotEqualsFileError(errorManager.getErrors());
        }
    }

    private void createAllureStepErrorMessage(String allureErrorMessage) {
        if (allureErrorMessage != null) {
            AllureStepValidator.doStep(allureErrorMessage, Status.FAILED, allureConfig.isDisableMessageErrorAllure());
        }
    }

    private void createAllureStepMatcherNull() {
        AllureStepValidator.doStep("Matcher is null", Status.FAILED, allureConfig.isDisableMessageErrorAllure());
    }

    private void createAllureStepInvalidJsonPath(String jsonPath) {
        AllureStepValidator.doStep("Попытка валидации не валидного json: \"%s\".".formatted(jsonPath),
                Status.FAILED, allureConfig.isDisableMessageErrorAllure());
    }

    private void createAllureStepWhenNotDefineContentType() {
        AllureStepValidator.doStep("Попытка валидации тела ответа по jsonPath, но у ответа не указан header - content-type.",
                Status.FAILED, allureConfig.isDisableMessageErrorAllure());
    }

    private void addUnknownErrorToPool(String message) {
        List<Throwable> errors = errorManager.getErrors();
        BodyUnknownError bodyUnknownError = new BodyUnknownError(message, errors);
        errors.add(bodyUnknownError);
    }

    private void addErrorWhenNotAssertMatcherToPool(String message, String jsonPath) {
        List<Throwable> errors = errorManager.getErrors();
        BodyMatcherError bodyMatcherError = new BodyMatcherError(message, jsonPath, errors);
        errors.add(bodyMatcherError);
    }

    private void addErrorWhenNotAssertJsonSchema() {
        List<Throwable> errors = errorManager.getErrors();
        JsonSchemaError jsonSchemaError = new JsonSchemaError(errors);
        errors.add(jsonSchemaError);
    }

    private void addErrorMatcherNullToPool() {
        List<Throwable> errors = errorManager.getErrors();
        BodyMatcherNullError bodyMatcherNullError = new BodyMatcherNullError(errors);
        errors.add(bodyMatcherNullError);
    }

    private void addErrorInvalidJsonPathToPool(String jsonPath) {
        List<Throwable> errors = errorManager.getErrors();
        BodyInvalidJsonError bodyError = new BodyInvalidJsonError(jsonPath, errors);
        errors.add(bodyError);
    }

    private void addErrorResponseNotDefineContentType() {
        List<Throwable> errors = errorManager.getErrors();
        BodyNotDefineContentType bodyNotDefineContentType = new BodyNotDefineContentType(errors);
        errors.add(bodyNotDefineContentType);
    }
}