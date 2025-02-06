package io.github.dmitrymiyuzov;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import io.github.dmitrymiyuzov.helper.AllureListener;
import io.github.dmitrymiyuzov.validator._config.ThreadLocalValidatorFabricConfig;
import io.github.dmitrymiyuzov.validator._config.impl.ValidatorFabricConfig;
import io.github.dmitrymiyuzov.validator.base.Validator;
import io.github.dmitrymiyuzov.validator.base.ValidatorFabric;
import io.github.dmitrymiyuzov.validator.exceptions.ErrorManager;
import io.github.dmitrymiyuzov.validator.exceptions.base.ChainValidationError;
import io.github.dmitrymiyuzov.validator.exceptions.base.ChainValidationException;
import io.github.dmitrymiyuzov.validator.exceptions.response.body.bodyFile.ExpectedFileNotFoundError;
import io.github.dmitrymiyuzov.validator.exceptions.response.body.bodyFile.ReadFileError;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class ResponseValidatorTest {
    private static final Integer port = 8080;
    private static WireMockServer wireMockServer;

    @BeforeAll
    public static void beforeAll() {
        wireMockServer = new WireMockServer(
                WireMockConfiguration.wireMockConfig()
                        .port(port)
        );
        wireMockServer.start();

        ThreadLocalValidatorFabricConfig.setConfig(
                new ValidatorFabricConfig() {
                    @Override
                    public Boolean isEnabledFilter() {
                        return true;
                    }

                    @Override
                    public String includeByContainsPackage() {
                        return "io.github.dmitrymiyuzov";
                    }

                    @Override
                    public String[] excludeByContainsClasses() {
                        return new String[] {"io.github.dmitrymiyuzov.validator"};
                    }
                }
        );
    }

    @AfterAll
    public static void teardown() {
        wireMockServer.stop();
    }

    public static RequestSpecification getBaseReqSpec() {
        return RestAssured.given()
                .baseUri("http://localhost:%s".formatted(port))
                .filters(AllureListener.getHttpListener());
    }

    @Nested
    class Soft {
        @Nested
        @Epic("Response")
        @Feature("Soft")
        @Story("Body Equals Soft")
        class BodyEqualsSoft {
            @Test
            @DisplayName("Успешный")
            public void assertSoft1() {
                String url = "/api/bodyEqualsSoft1";

                WireMock.stubFor(
                        get(
                                urlEqualTo(url)
                        )
                                .willReturn(
                                        aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-type", "application/json")
                                                .withBody(readFileAsString("stubs/bodyEquals/stub1.json"))
                                )
                );

                Response response = getBaseReqSpec().get(url);

                ValidatorFabric.beginResponseValidation(response)
                        .bodyEqualsSoft("message", "hello")
                        .validate();
            }

            @Test
            @DisplayName("Проваленный. Атрибут присутствует, но не равен ожидаемому результату.")
            public void assertSoft2() {
                String url = "/api/bodyEqualsSoft2";

                WireMock.stubFor(
                        get(
                                urlEqualTo(url)
                        )
                                .willReturn(
                                        aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-type", "application/json")
                                                .withBody(readFileAsString("stubs/bodyEquals/stub1.json"))
                                )
                );

                Response response = getBaseReqSpec().get(url);


                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyEqualsSoft("message", "helloo")
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();

                    Integer expectedCountValidation = 1;
                    Integer expectedCountValidationError = 1;
                    Assertions.assertEquals(expectedCountValidation, e.getCountValidation(), "Ожидаемое количество проверок - %d".formatted(expectedCountValidation));
                    Assertions.assertEquals(expectedCountValidationError, e.getCountErrorValidation(), "Ожидаемое количество ошибок - %d".formatted(expectedCountValidationError));
                }
            }

            @Test
            @DisplayName("Проваленный. Атрибут отсутствует.")
            public void assertSoft3() {
                String url = "/api/bodyEqualsSoft3";

                WireMock.stubFor(
                        get(
                                urlEqualTo(url)
                        )
                                .willReturn(
                                        aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-type", "application/json")
                                                .withBody(readFileAsString("stubs/bodyEquals/stub1.json"))
                                )
                );

                Response response = getBaseReqSpec().get(url);


                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyEqualsSoft("message2", "hello")
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();

                    Integer expectedCountValidation = 1;
                    Integer expectedCountValidationError = 1;
                    Assertions.assertEquals(expectedCountValidation, e.getCountValidation(), "Ожидаемое количество проверок - %d".formatted(expectedCountValidation));
                    Assertions.assertEquals(expectedCountValidationError, e.getCountErrorValidation(), "Ожидаемое количество ошибок - %d".formatted(expectedCountValidationError));
                }
            }

            @Test
            @DisplayName("Проваленный. В ответе не указан Content-type -> application/json.")
            public void assertSoft4() {
                String url = "/api/bodyEqualsSoft4";

                WireMock.stubFor(
                        get(
                                urlEqualTo(url)
                        )
                                .willReturn(
                                        aResponse()
                                                .withStatus(200)
                                                .withBody(readFileAsString("stubs/bodyEquals/stub1.json"))
                                )
                );

                Response response = getBaseReqSpec().get(url);


                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyEqualsSoft("message2", "hello")
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();

                    Integer expectedCountValidation = 1;
                    Integer expectedCountValidationError = 1;
                    Assertions.assertEquals(expectedCountValidation, e.getCountValidation(), "Ожидаемое количество проверок - %d".formatted(expectedCountValidation));
                    Assertions.assertEquals(expectedCountValidationError, e.getCountErrorValidation(), "Ожидаемое количество ошибок - %d".formatted(expectedCountValidationError));
                }
            }

            @Test
            @DisplayName("Проваленный. Попытка не валидного jsonPath.")
            public void assertSoft5() {
                String url = "/api/bodyEqualsSoft5";

                WireMock.stubFor(
                        get(
                                urlEqualTo(url)
                        )
                                .willReturn(
                                        aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-type", "application/json")
                                                .withBody(readFileAsString("stubs/bodyEquals/stub1.json"))
                                )
                );

                Response response = getBaseReqSpec().get(url);


                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyEqualsSoft("#", "hello")
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationException e) {
                    e.printStackTrace();

                    Integer expectedCountValidation = 1;
                    Integer expectedCountValidationError = 1;
                    Assertions.assertEquals(expectedCountValidation, e.getCountValidation(), "Ожидаемое количество проверок - %d".formatted(expectedCountValidation));
                    Assertions.assertEquals(expectedCountValidationError, e.getCountErrorValidation(), "Ожидаемое количество ошибок - %d".formatted(expectedCountValidationError));
                }
            }

            @Test
            @DisplayName("Проваленный. Две проваленные, одна успешная.")
            public void assertSoft6() {
                String url = "/api/bodyEqualsSoft6";

                WireMock.stubFor(
                        get(
                                urlEqualTo(url)
                        )
                                .willReturn(
                                        aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-type", "application/json")
                                                .withBody(readFileAsString("stubs/bodyEquals/stub1.json"))
                                )
                );

                Response response = getBaseReqSpec().get(url);


                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyEqualsSoft("message", "hello2")
                            .bodyEqualsSoft("message", "hello")
                            .bodyEqualsSoft("message1", "hello2")
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();

                    Integer expectedCountValidation = 3;
                    Integer expectedCountValidationError = 2;
                    Assertions.assertEquals(expectedCountValidation, e.getCountValidation(), "Ожидаемое количество проверок - %d".formatted(expectedCountValidation));
                    Assertions.assertEquals(expectedCountValidationError, e.getCountErrorValidation(), "Ожидаемое количество ошибок - %d".formatted(expectedCountValidationError));
                }
            }
        }

        @Nested
        @Epic("Response")
        @Feature("Soft")
        @Story("Body Contains Soft")
        class BodyContainsSoft {
            @Test
            @DisplayName("Успешный. Полное совпадение")
            public void assertSoft1() {
                String url = "/api/bodyContainsSoft1";

                WireMock.stubFor(
                        get(
                                urlEqualTo(url)
                        )
                                .willReturn(
                                        aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-type", "application/json")
                                                .withBody(readFileAsString("stubs/bodyContains/stub1.json"))
                                )
                );

                Response response = getBaseReqSpec().get(url);

                ValidatorFabric.beginResponseValidation(response)
                        .bodyContainsSoft("message", "hello")
                        .validate();
            }

            @Test
            @DisplayName("Успешный. Частичное совпадени.")
            public void assertSoft2() {
                String url = "/api/bodyContainsSoft2";

                WireMock.stubFor(
                        get(
                                urlEqualTo(url)
                        )
                                .willReturn(
                                        aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-type", "application/json")
                                                .withBody(readFileAsString("stubs/bodyContains/stub1.json"))
                                )
                );

                Response response = getBaseReqSpec().get(url);

                ValidatorFabric.beginResponseValidation(response)
                        .bodyContainsSoft("message", "hel")
                        .validate();
            }

            @Test
            @DisplayName("Проваленный. Атрибут присутствует, но нет совпадения.")
            public void assertSoft3() {
                String url = "/api/bodyContainsSoft3";

                WireMock.stubFor(
                        get(
                                urlEqualTo(url)
                        )
                                .willReturn(
                                        aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-type", "application/json")
                                                .withBody(readFileAsString("stubs/bodyContains/stub1.json"))
                                )
                );

                Response response = getBaseReqSpec().get(url);


                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyContainsSoft("message", "hahaha")
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();

                    Integer expectedCountValidation = 1;
                    Integer expectedCountValidationError = 1;
                    Assertions.assertEquals(expectedCountValidation, e.getCountValidation(), "Ожидаемое количество проверок - %d".formatted(expectedCountValidation));
                    Assertions.assertEquals(expectedCountValidationError, e.getCountErrorValidation(), "Ожидаемое количество ошибок - %d".formatted(expectedCountValidationError));
                }
            }

            @Test
            @DisplayName("Проваленный. Атрибут отсутствует.")
            public void assertSoft4() {
                String url = "/api/bodyContainsSoft4";

                WireMock.stubFor(
                        get(
                                urlEqualTo(url)
                        )
                                .willReturn(
                                        aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-type", "application/json")
                                                .withBody(readFileAsString("stubs/bodyContains/stub1.json"))
                                )
                );

                Response response = getBaseReqSpec().get(url);


                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyContainsSoft("message2", "hello")
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();

                    Integer expectedCountValidation = 1;
                    Integer expectedCountValidationError = 1;
                    Assertions.assertEquals(expectedCountValidation, e.getCountValidation(), "Ожидаемое количество проверок - %d".formatted(expectedCountValidation));
                    Assertions.assertEquals(expectedCountValidationError, e.getCountErrorValidation(), "Ожидаемое количество ошибок - %d".formatted(expectedCountValidationError));
                }
            }

            @Test
            @DisplayName("Проваленный. В ответе не указан Content-type -> application/json.")
            public void assertSoft5() {
                String url = "/api/bodyContainsSoft5";

                WireMock.stubFor(
                        get(
                                urlEqualTo(url)
                        )
                                .willReturn(
                                        aResponse()
                                                .withStatus(200)
                                                .withBody(readFileAsString("stubs/bodyContains/stub1.json"))
                                )
                );

                Response response = getBaseReqSpec().get(url);


                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyContainsSoft("message2", "hello")
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();

                    Integer expectedCountValidation = 1;
                    Integer expectedCountValidationError = 1;
                    Assertions.assertEquals(expectedCountValidation, e.getCountValidation(), "Ожидаемое количество проверок - %d".formatted(expectedCountValidation));
                    Assertions.assertEquals(expectedCountValidationError, e.getCountErrorValidation(), "Ожидаемое количество ошибок - %d".formatted(expectedCountValidationError));
                }
            }

            @Test
            @DisplayName("Проваленный. Попытка не валидного jsonPath.")
            public void assertSoft6() {
                String url = "/api/bodyContainsSoft6";

                WireMock.stubFor(
                        get(
                                urlEqualTo(url)
                        )
                                .willReturn(
                                        aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-type", "application/json")
                                                .withBody(readFileAsString("stubs/bodyContains/stub1.json"))
                                )
                );

                Response response = getBaseReqSpec().get(url);


                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyContainsSoft("#", "hello")
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationException e) {
                    e.printStackTrace();

                    Integer expectedCountValidation = 1;
                    Integer expectedCountValidationError = 1;
                    Assertions.assertEquals(expectedCountValidation, e.getCountValidation(), "Ожидаемое количество проверок - %d".formatted(expectedCountValidation));
                    Assertions.assertEquals(expectedCountValidationError, e.getCountErrorValidation(), "Ожидаемое количество ошибок - %d".formatted(expectedCountValidationError));
                }
            }

            @Test
            @DisplayName("Проваленный. Две проваленные, одна успешная.")
            public void assertSoft7() {
                String url = "/api/bodyContainsSoft7";

                WireMock.stubFor(
                        get(
                                urlEqualTo(url)
                        )
                                .willReturn(
                                        aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-type", "application/json")
                                                .withBody(readFileAsString("stubs/bodyContains/stub1.json"))
                                )
                );

                Response response = getBaseReqSpec().get(url);


                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyContainsSoft("message", "hello2")
                            .bodyContainsSoft("message", "he")
                            .bodyContainsSoft("message1", "hello2")
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();

                    Integer expectedCountValidation = 3;
                    Integer expectedCountValidationError = 2;
                    Assertions.assertEquals(expectedCountValidation, e.getCountValidation(), "Ожидаемое количество проверок - %d".formatted(expectedCountValidation));
                    Assertions.assertEquals(expectedCountValidationError, e.getCountErrorValidation(), "Ожидаемое количество ошибок - %d".formatted(expectedCountValidationError));
                }
            }
        }

        @Nested
        @Epic("Response")
        @Feature("Soft")
        @Story("Status Code Soft")
        class StatusCodeSoft {
            private static final String url = "/api/statusCodeSoft";

            @BeforeAll
            public static void createStub() {
                WireMock.stubFor(
                        get(
                                urlEqualTo(url)
                        )
                                .willReturn(
                                        aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-type", "application/json")
                                )
                );
            }
            @Test
            @DisplayName("Успешный")
            public void assertSoft1() {
                Response response = getBaseReqSpec().get(url);

                ValidatorFabric.beginResponseValidation(response)
                        .statusCodeSoft(200)
                        .validate();
            }

            @Test
            @DisplayName("Проваленный.")
            public void assertSoft2() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .statusCodeSoft(201)
                            .validate();
                } catch (ChainValidationError e) {
                    e.printStackTrace();

                    Integer expectedCountValidation = 1;
                    Integer expectedCountValidationError = 1;
                    Assertions.assertEquals(expectedCountValidation, e.getCountValidation(), "Ожидаемое количество проверок - %d".formatted(expectedCountValidation));
                    Assertions.assertEquals(expectedCountValidationError, e.getCountErrorValidation(), "Ожидаемое количество ошибок - %d".formatted(expectedCountValidationError));
                }
            }

            @Test
            @DisplayName("Проваленный. Два проваленных, один успешный.")
            public void assertSoft3() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .statusCodeSoft(201)
                            .statusCodeSoft(200)
                            .statusCodeSoft(203)
                            .validate();
                } catch (ChainValidationError e) {
                    e.printStackTrace();

                    Integer expectedCountValidation = 3;
                    Integer expectedCountValidationError = 2;
                    Assertions.assertEquals(expectedCountValidation, e.getCountValidation(), "Ожидаемое количество проверок - %d".formatted(expectedCountValidation));
                    Assertions.assertEquals(expectedCountValidationError, e.getCountErrorValidation(), "Ожидаемое количество ошибок - %d".formatted(expectedCountValidationError));
                }
            }
        }

        @Nested
        @Epic("Response")
        @Feature("Soft")
        @Story("Status Code Soft Or Else")
        class StatusCodeOrElseSoft {
            private static final String url = "/api/statusCodeOrElseSoft";

            @BeforeAll
            public static void createStub() {
                WireMock.stubFor(
                        get(
                                urlEqualTo(url)
                        )
                                .willReturn(
                                        aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-type", "application/json")
                                )
                );
            }

            @Test
            @DisplayName("Успешный")
            public void assertSoft1() {
                Response response = getBaseReqSpec().get(url);

                List<String> list = new ArrayList<>();

                ValidatorFabric.beginResponseValidation(response)
                        .statusCodeOrElseSoft(
                                200,
                                nestedResponse -> list.add("hello")
                        )
                        .validate();

                if (!list.isEmpty()) {
                    Assertions.fail("Действие не должно было выполниться, но выполнилось.");
                }
            }

            @Test
            @DisplayName("Проваленный. Действие должно выполниться.")
            public void assertSoft2() {
                Response response = getBaseReqSpec().get(url);

                List<String> list = new ArrayList<>();
                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .statusCodeOrElseSoft(
                                    201,
                                    nestedResponse -> list.add("hello")
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();

                    Integer expectedCountValidation = 1;
                    Integer expectedCountValidationError = 1;
                    Assertions.assertEquals(expectedCountValidation, e.getCountValidation(), "Ожидаемое количество проверок - %d".formatted(expectedCountValidation));
                    Assertions.assertEquals(expectedCountValidationError, e.getCountErrorValidation(), "Ожидаемое количество ошибок - %d".formatted(expectedCountValidationError));
                }

                if (list.size() != 1) {
                    Assertions.fail("Действие должно было выполниться, но не выполнилось");
                }
            }

            @Test
            @DisplayName("Проваленный. Два проваленных, один успешный. Два действия должны выполниться.")
            public void assertSoft3() {
                Response response = getBaseReqSpec().get(url);

                List<String> list = new ArrayList<>();
                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .statusCodeOrElseSoft(
                                    201,
                                    nestedResponse -> list.add("hello")
                            )
                            .statusCodeOrElseSoft(
                                    200,
                                    nestedResponse -> list.add("hello")
                            )
                            .statusCodeOrElseSoft(
                                    201,
                                    nestedResponse -> list.add("hello")
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();

                    Integer expectedCountValidation = 3;
                    Integer expectedCountValidationError = 2;
                    Assertions.assertEquals(expectedCountValidation, e.getCountValidation(), "Ожидаемое количество проверок - %d".formatted(expectedCountValidation));
                    Assertions.assertEquals(expectedCountValidationError, e.getCountErrorValidation(), "Ожидаемое количество ошибок - %d".formatted(expectedCountValidationError));
                }

                if (list.size() != 2) {
                    Assertions.fail("Действия должны было выполниться, но не выполнились.");
                }
            }
        }

        @Nested
        @Epic("Response")
        @Feature("Soft")
        @Story("Status Code Soft And Else")
        class StatusCodeAndElseSoft {
            private static final String url = "/api/statusCodeAndElseSoft";

            @BeforeAll
            public static void createStub() {
                WireMock.stubFor(
                        get(
                                urlEqualTo(url)
                        )
                                .willReturn(
                                        aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-type", "application/json")
                                )
                );
            }

            @Test
            @DisplayName("Успешный. Действие должно выполниться.")
            public void assertSoft1() {
                Response response = getBaseReqSpec().get(url);

                List<String> list = new ArrayList<>();

                ValidatorFabric.beginResponseValidation(response)
                        .statusCodeAndElseSoft(
                                200,
                                nestedResponse -> list.add("hello")
                        )
                        .validate();

                if (list.size() != 1) {
                    Assertions.fail("Действие должно было выполниться, но не выполнилось.");
                }
            }

            @Test
            @DisplayName("Проваленный. Действие должно выполниться.")
            public void assertSoft2() {
                Response response = getBaseReqSpec().get(url);

                List<String> list = new ArrayList<>();
                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .statusCodeAndElseSoft(
                                    201,
                                    nestedResponse -> list.add("hello")
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();

                    Integer expectedCountValidation = 1;
                    Integer expectedCountValidationError = 1;
                    Assertions.assertEquals(expectedCountValidation, e.getCountValidation(), "Ожидаемое количество проверок - %d".formatted(expectedCountValidation));
                    Assertions.assertEquals(expectedCountValidationError, e.getCountErrorValidation(), "Ожидаемое количество ошибок - %d".formatted(expectedCountValidationError));
                }

                if (list.size() != 1) {
                    Assertions.fail("Действие должно было выполниться, но не выполнилось");
                }
            }

            @Test
            @DisplayName("Проваленный. Два проваленных, один успешный. Все действия должны выполниться.")
            public void assertSoft3() {
                Response response = getBaseReqSpec().get(url);

                List<String> list = new ArrayList<>();
                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .statusCodeAndElseSoft(
                                    201,
                                    nestedResponse -> list.add("hello")
                            )
                            .statusCodeAndElseSoft(
                                    200,
                                    nestedResponse -> list.add("hello")
                            )
                            .statusCodeAndElseSoft(
                                    201,
                                    nestedResponse -> list.add("hello")
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();

                    Integer expectedCountValidation = 3;
                    Integer expectedCountValidationError = 2;
                    Assertions.assertEquals(expectedCountValidation, e.getCountValidation(), "Ожидаемое количество проверок - %d".formatted(expectedCountValidation));
                    Assertions.assertEquals(expectedCountValidationError, e.getCountErrorValidation(), "Ожидаемое количество ошибок - %d".formatted(expectedCountValidationError));
                }

                if (list.size() != 3) {
                    Assertions.fail("Действия должны было выполниться, но не выполнились.");
                }
            }
        }
    }

    private static String readFileAsString(String pathFile) {
        var inputString = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathFile);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputString))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
