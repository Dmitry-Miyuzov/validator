package io.github.dmitrymiyuzov.validator.proxy;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import io.github.dmitrymiyuzov.validator.proxy.markerInterface.ResponseModelDefaultCustomValidator;
import io.github.dmitrymiyuzov.validator.proxy.markerInterface.ResponseModelDefaultCustomValidatorList;
import io.restassured.response.Response;
import io.github.dmitrymiyuzov.validator.base.ValidatorFabric;
import io.github.dmitrymiyuzov.validator.xlsx.poiObjectMapper.SpreadsheetReadException;
import io.github.dmitrymiyuzov.validator.xlsx.poiObjectMapper.ss.reader.XlsxReader;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SuppressWarnings("all")
public class ResponseProxyHandler implements InvocationHandler {
    private final Response response;

    public ResponseProxyHandler(Response response) {
        this.response = response;
    }

    private Object customValidation(Object[] args) {
        Integer expectedStatusCode = (Integer) args[0];
        ValidatorFabric.beginResponseValidation("Предварительно валидировать ответ", response)
                .statusCode(expectedStatusCode)
                .validate();

        Object concreteModel;
        Object classValidator;
        return switch (args.length) {
            //customValidation(int expectedStatusCode, Class<Model> model)
            case 2 -> {
                Class<?> classModel = (Class<?>) args[1];

                ResponseModelDefaultCustomValidator fakeModel;
                try {
                    fakeModel = (ResponseModelDefaultCustomValidator) classModel.getDeclaredConstructor(new Class<?>[0])
                            .newInstance();
                } catch (NoSuchMethodException e) {
                    throw new IllegalStateException("У модели нет конструктора по умолчанию.");
                } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                    throw new IllegalStateException("Ошибка при создании фейк модели.");
                }

                try {
                    concreteModel = response.getBody().jsonPath().getObject(fakeModel.jsonPathCustomValidator(), classModel);
                } catch (Exception e) {
                    throw new IllegalStateException("Не удалось преобразовать ответ в модель - по пути -> %s".formatted(fakeModel.jsonPathCustomValidator()), e);
                }

                if (concreteModel == null) {
                    throw new IllegalStateException("Не удалось преобразовать ответ в модель - по пути -> %s.".formatted(fakeModel.jsonPathCustomValidator()));
                }

                AbstractCustomValidator customValidator;
                Class defaultValidator = ((ResponseModelDefaultCustomValidator) concreteModel).getDefaultCustomValidator();
                try {
                    customValidator = (AbstractCustomValidator) defaultValidator.getDeclaredConstructors()[0]
                            .newInstance(concreteModel, response);
                } catch (Exception e) {
                    throw new IllegalStateException("Не удалось создать валидатор");
                }

                yield customValidator;
            }
            //customValidation(int expectedStatusCode, Class<Model> model, Class<Validator> customValidator)
            case 3 -> {
                Class<?> model = (Class<?>) args[1];

                try {
                    concreteModel = response.as(model);
                } catch (Exception e) {
                    throw new IllegalStateException("Не удалось преобразовать ответ в модель.", e);
                }

                classValidator = args[2];

                AbstractCustomValidator customValidator;
                try {
                    customValidator = (AbstractCustomValidator) ((Class) classValidator).getDeclaredConstructors()[0]
                            .newInstance(concreteModel, response);
                } catch (Exception e) {
                    throw new IllegalStateException("Не удалось создать валидатор");
                }

                yield customValidator;
            }
            //customValidation(int expectedStatusCode, String jsonPath, Class<Model> model, Class<Validator> customValidator);
            case 4 -> {
                Class<?> model = (Class<?>) args[2];

                try {
                    String jsonPath = (String) args[1];
                    concreteModel = response.getBody().jsonPath().getObject(jsonPath, model);
                } catch (Exception e) {
                    throw new IllegalStateException("Не удалось преобразовать ответ в модель.");
                }

                classValidator = args[3];

                AbstractCustomValidator customValidator;
                try {
                    customValidator = (AbstractCustomValidator) ((Class) classValidator).getDeclaredConstructors()[0]
                            .newInstance(concreteModel, response);
                } catch (Exception e) {
                    throw new IllegalStateException("Не удалось создать валидатор");
                }

                yield customValidator;
            }
            default -> {
                throw new IllegalStateException("Не добавлена реализация для перегрузки метода customValidation");
            }
        };
    }

    //customValidationCSVList(int expectedStatusCode, char separator, Class<Model> model, Class<Validator> customValidator)
    private Object customValidationCSVList(Object[] args) {
        Integer expectedStatusCode = (Integer) args[0];
        ValidatorFabric.beginResponseValidation("Предварительно валидировать ответ", response)
                .statusCode(expectedStatusCode)
                .validate();

        char separator = (char) args[1];
        Class<?> classModel = (Class<?>) args[2];

        Object concreteModel;
        try {
            String body = response.getBody().asString();
            concreteModel = new CsvToBeanBuilder<>(new StringReader(body))
                    .withSeparator(separator)
                    .withType(classModel)
                    .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
                    .build()
                    .parse();
        } catch (Exception e) {
            throw new IllegalStateException("Не удалось смапить ответ в формате csv в Java модель", e);
        }

        Object classValidator = args[3];
        AbstractCustomValidatorList customValidator;
        try {
            customValidator = (AbstractCustomValidatorList) ((Class) classValidator).getDeclaredConstructors()[0]
                    .newInstance(concreteModel, response);
        } catch (Exception e) {
            throw new IllegalStateException("Не удалось создать валидатор");
        }

        return customValidator;
    }

    private Object customValidationList(Object[] args) {
        Integer expectedStatusCode = (Integer) args[0];
        ValidatorFabric.beginResponseValidation("Предварительно валидировать ответ", response)
                .statusCode(expectedStatusCode)
                .validate();

        return switch (args.length) {
            //customValidationList(int expectedStatusCode, Class<Model> model)
            case 2 -> {
                Class<?> classModel = (Class<?>) args[1];

                ResponseModelDefaultCustomValidatorList fakeModel;
                try {
                    fakeModel = (ResponseModelDefaultCustomValidatorList) classModel.getDeclaredConstructor(new Class<?>[0])
                            .newInstance();
                } catch (NoSuchMethodException e) {
                    throw new IllegalStateException("У модели нет конструктора по умолчанию.");
                } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                    throw new IllegalStateException("Ошибка при создании фейк модели.");
                }

                Object concreteModel;
                try {
                    concreteModel = response.jsonPath().getList(fakeModel.jsonPathCustomValidatorList(), classModel);
                } catch (Exception e) {
                    throw new IllegalStateException("Не удалось преобразовать ответ в модель - по пути -> %s".formatted(fakeModel.jsonPathCustomValidatorList()), e);
                }

                if (concreteModel == null) {
                    throw new IllegalStateException("Не удалось преобразовать ответ в модель - по пути -> %s.".formatted(fakeModel.jsonPathCustomValidatorList()));
                }


                AbstractCustomValidatorList customValidator;
                try {
                    customValidator = (AbstractCustomValidatorList) (fakeModel.getDefaultCustomValidatorList()).getDeclaredConstructors()[0]
                            .newInstance(concreteModel, response);
                } catch (Exception e) {
                    throw new IllegalStateException("Не удалось создать валидатор");
                }

                yield customValidator;
            }
            //customValidationList(int expectedStatusCode, String jsonPath, Class<Model> model, Class<Validator> customValidator);
            case 4 -> {
                String jsonPath = (String) args[1];
                Class<?> classModel = (Class<?>) args[2];

                Object concreteModel;
                try {
                    concreteModel = response.jsonPath().getList(jsonPath, classModel);
                } catch (Exception e) {
                    throw new IllegalStateException("Не удалось преобразовать ответ в модель.", e);
                }

                Object classValidator = args[3];

                AbstractCustomValidatorList customValidator;
                try {
                    customValidator = (AbstractCustomValidatorList) ((Class) classValidator).getDeclaredConstructors()[0]
                            .newInstance(concreteModel, response);
                } catch (Exception e) {
                    throw new IllegalStateException("Не удалось создать валидатор");
                }
                yield customValidator;
            }
            default -> {
                throw new IllegalStateException("Не добавлена реализация для перегрузки метода customValidationList");
            }
        };
    }

    //customValidationString(int expectedStatusCode, Class<Validator> customValidator)
    private Object customValidationString(Object[] args) {
        Integer expectedStatusCode = (Integer) args[0];
        ValidatorFabric.beginResponseValidation("Предварительно валидировать ответ", response)
                .statusCode(expectedStatusCode)
                .validate();

        Object concreteModel;
        try {
            concreteModel = response.body().asString();
        } catch (Exception e) {
            throw new IllegalStateException("Не удалось преобразовать ответ в модель.", e);
        }

        Object classValidator = args[1];
        AbstractCustomValidator customValidator;
        try {
            customValidator = (AbstractCustomValidator) ((Class) classValidator).getDeclaredConstructors()[0]
                    .newInstance(concreteModel, response);
        } catch (Exception e) {
            throw new IllegalStateException("Не удалось создать валидатор");
        }

        return customValidator;
    }

    //customValidationWithoutModel(int expectedStatusCode, Class<Validator> customValidator)
    private Object customValidationWithoutModel(Object[] args) {
        Integer expectedStatusCode = (Integer) args[0];
        ValidatorFabric.beginResponseValidation("Предварительно валидировать ответ", response)
                .statusCode(expectedStatusCode)
                .validate();

        Object concreteModel;
        Object classValidator;

        return switch (args.length) {
            case 2 -> {
                classValidator = args[1];

                AbstractCustomValidatorWithoutModel customValidator;
                try {
                    customValidator = (AbstractCustomValidatorWithoutModel) ((Class) classValidator).getDeclaredConstructors()[0]
                            .newInstance(response);
                } catch (Exception e) {
                    throw new IllegalStateException("Не удалось создать валидатор");
                }

                yield customValidator;
            }
            default -> {
                throw new IllegalStateException("Не добавлена реализация для перегрузки метода customValidation");
            }
        };
    }

    //customValidationXLSXList(int expectedStatusCode, Class<Model> model, Class<Validator> customValidator);
    private Object customValidationXLSXList(Object[] args) {
        Integer expectedStatusCode = (Integer) args[0];
        ValidatorFabric.beginResponseValidation("Предварительно валидировать ответ", response)
                .statusCode(expectedStatusCode)
                .validate();

        Class<?> classModel = (Class<?>) args[1];

        Object concreteModel;
        ByteArrayInputStream byteResponse = new ByteArrayInputStream(response.getBody().asByteArray());
        XlsxReader reader = new XlsxReader();
        try {
            concreteModel = reader.read(classModel, byteResponse);
        } catch (SpreadsheetReadException e) {
            throw new IllegalStateException("Не удалось смапить ответ в формате xlsx в Java модель", e);
        }

        Object classValidator = args[2];
        AbstractCustomValidatorList customValidator;
        try {
            customValidator = (AbstractCustomValidatorList) ((Class) classValidator).getDeclaredConstructors()[0]
                    .newInstance(concreteModel, response);
        } catch (Exception e) {
            throw new IllegalStateException("Не удалось создать валидатор");
        }

        return customValidator;
    }

    private Object defaultMethod(Method method, Object[] args) throws Throwable {
        return method.invoke(response, args);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return switch (method.getName()) {
            case "validation" -> ValidatorFabric.beginComplexValidation("Проверить ответ", response);
            case "customValidation" -> customValidation(args);
            case "customValidationList" -> customValidationList(args);
            case "customValidationString" -> customValidationString(args);
            case "customValidationXLSXList" -> customValidationXLSXList(args);
            case "customValidationCSVList" -> customValidationCSVList(args);
            case "customValidationWithoutModel" -> customValidationWithoutModel(args);
            default -> defaultMethod(method, args);
        };
    }
}
