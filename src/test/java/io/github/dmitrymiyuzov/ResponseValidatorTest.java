package io.github.dmitrymiyuzov;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.github.dmitrymiyuzov.helper.AllureListener;
import io.github.dmitrymiyuzov.validator._config.ThreadLocalValidatorFabricConfig;
import io.github.dmitrymiyuzov.validator._config.impl.ValidatorFabricConfig;
import io.github.dmitrymiyuzov.validator.base.ValidatorFabric;
import io.github.dmitrymiyuzov.validator.exceptions.base.ChainValidationError;
import io.github.dmitrymiyuzov.validator.exceptions.base.ChainValidationException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
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

        @Nested
        @Epic("Response")
        @Feature("Soft")
        @Story("Body Array Empty Soft")
        class ArrayEmptySoft {
            private static final String url = "/api/bodyArrayEmpty";

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
                                                .withBody(readFileAsString("stubs/bodyArrayEmpty/stub1.json"))
                                )
                );
            }

            @Test
            @DisplayName("Успешный.")
            public void assertSoft1() {
                Response response = getBaseReqSpec().get(url);

                ValidatorFabric.beginResponseValidation(response)
                        .bodyArrayEmptySoft("arrayEmpty")
                        .validate();
            }

            @Test
            @DisplayName("Проваленный. Массив не пустой.")
            public void assertSoft2() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyArrayEmptySoft("arraySizeThree")
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
            @DisplayName("Проваленный. Массива не существует.")
            public void assertSoft3() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyArrayEmptySoft("hello")
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
            @DisplayName("Проваленный. Некорректный jsonPath.")
            public void assertSoft4() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyArrayEmptySoft("##")
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
            @DisplayName("Проваленный. Это не массив - значение заполнено.")
            public void assertSoft5() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyArrayEmptySoft("attributeWithValue")
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
            @DisplayName("Проваленный. Это не массив - значение пустое.")
            public void assertSoft6() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyArrayEmptySoft("attributeEmpty")
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
            @DisplayName("Проваленный. Два не прошло, один прошел.")
            public void assertSoft7() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyArrayEmptySoft("arraySizeThree")
                            .bodyArrayEmptySoft("arrayEmpty")
                            .bodyArrayEmptySoft("hello")
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
        @Story("Body Array Size Soft")
        class ArraySizeSoft {
            private static final String url = "/api/bodyArraySize";

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
                                                .withBody(readFileAsString("stubs/bodyArraySize/stub1.json"))
                                )
                );
            }

            @Test
            @DisplayName("Успешный. Массив не пустой.")
            public void assertSoft1() {
                Response response = getBaseReqSpec().get(url);

                ValidatorFabric.beginResponseValidation(response)
                        .bodyArraySizeEqualsSoft("arraySizeThree", 3)
                        .validate();
            }

            @Test
            @DisplayName("Успешный. Массив пустой.")
            public void assertSoft2() {
                Response response = getBaseReqSpec().get(url);

                ValidatorFabric.beginResponseValidation(response)
                        .bodyArraySizeEqualsSoft("arrayEmpty", 0)
                        .validate();
            }

            @Test
            @DisplayName("Проваленный. Массив не пустой.")
            public void assertSoft3() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyArraySizeEqualsSoft("arraySizeThree", 2)
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
            @DisplayName("Проваленный. Массив пустой.")
            public void assertSoft4() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyArraySizeEqualsSoft("arrayEmpty", 2)
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
            @DisplayName("Проваленный. Массива не существует.")
            public void assertSoft5() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyArraySizeEqualsSoft("hello", 2)
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
            @DisplayName("Проваленный. Некорректный jsonPath.")
            public void assertSoft6() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyArraySizeEqualsSoft("##", 2)
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
            @DisplayName("Проваленный. Это не массив - с значением.")
            public void assertSoft7() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyArraySizeEqualsSoft("attributeWithValue", 1)
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
            @DisplayName("Проваленный. Это не массив - значение пустое.")
            public void assertSoft8() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyArraySizeEqualsSoft("attributeEmpty", 0)
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
            @DisplayName("Проваленный. Два не прошло, один прошел.")
            public void assertSoft9() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyArraySizeEqualsSoft("arraySizeThree", 2)
                            .bodyArraySizeEqualsSoft("arraySizeThree", 3)
                            .bodyArraySizeEqualsSoft("attributeEmpty", 0)
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
        @Story("Body Has Attribute Soft")
        class BodyHasAttributeSoft {
            private static final String url = "/api/bodyHasAttribute";

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
                                                .withBody(readFileAsString("stubs/bodyHasAttribute/stub1.json"))
                                )
                );
            }

            @Test
            @DisplayName("Успешный. Простой атрибут.")
            public void assertSoft1() {
                Response response = getBaseReqSpec().get(url);

                ValidatorFabric.beginResponseValidation(response)
                        .bodyHasAttributeSoft("$", "attrSimple")
                        .validate();
            }

            @Test
            @DisplayName("Успешный. Массив.")
            public void assertSoft2() {
                Response response = getBaseReqSpec().get(url);

                ValidatorFabric.beginResponseValidation(response)
                        .bodyHasAttributeSoft("$", "attrArray")
                        .validate();
            }

            @Test
            @DisplayName("Проваленный. Атрибут отсутствует.")
            public void assertSoft3() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyHasAttributeSoft("$", "hello")
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
            @DisplayName("Проваленный. Два провалено, один успешный.")
            public void assertSoft4() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyHasAttributeSoft("$", "hello")
                            .bodyHasAttributeSoft("$", "attrArray")
                            .bodyHasAttributeSoft("$", "hello2")
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

            @Test
            @DisplayName("Проваленный. Parent Json Path - невалидный.")
            public void assertSoft5() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyHasAttributeSoft("#", "hello")
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
            @DisplayName("Проваленный. Attribute - невалидный.")
            public void assertSoft6() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyHasAttributeSoft("$", "#")
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
        }

        @Nested
        @Epic("Response")
        @Feature("Soft")
        @Story("Body Not Has Attribute Soft")
        class BodyNotHasAttributeSoft {
            private static final String url = "/api/bodyNotHasAttribute";

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
                                                .withBody(readFileAsString("stubs/bodyNotHasAttribute/stub1.json"))
                                )
                );
            }

            @Test
            @DisplayName("Успешный. Атрибут отсутствует.")
            public void assertSoft1() {
                Response response = getBaseReqSpec().get(url);

                ValidatorFabric.beginResponseValidation(response)
                        .bodyNotHasAttributeSoft("$", "hello")
                        .validate();
            }

            @Test
            @DisplayName("Проваленный. Атрибут присутствует. Простой атрибут.")
            public void assertSoft3() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyNotHasAttributeSoft("$", "attrSimple")
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
            @DisplayName("Проваленный. Атрибут присутствует. Массив.")
            public void assertSoft4() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyNotHasAttributeSoft("$", "attrArray")
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
            @DisplayName("Проваленный. Два провалено, один успешный.")
            public void assertSoft5() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyNotHasAttributeSoft("$", "attrSimple")
                            .bodyNotHasAttributeSoft("$", "hello")
                            .bodyNotHasAttributeSoft("$", "attrArray")
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

            @Test
            @DisplayName("Проваленный. Parent Json Path - невалидный.")
            public void assertSoft6() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyNotHasAttributeSoft("#", "hello")
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
        }

        @Nested
        @Epic("Response")
        @Feature("Soft")
        @Story("Header Contains Soft")
        class HeaderContainsSoft {
            private static final String url = "/api/headerContains";

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
            @DisplayName("Успешный.")
            public void assertSoft1() {
                Response response = getBaseReqSpec().get(url);

                ValidatorFabric.beginResponseValidation(response)
                        .headerContainsSoft("Content-type", "application")
                        .validate();
            }

            @Test
            @DisplayName("Проваленный. Заголовок присутствует.")
            public void assertSoft2() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .headerContainsSoft("Content-type", "Application")
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
            @DisplayName("Проваленный. Заголовок отсутствует.")
            public void assertSoft3() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .headerContainsSoft("Conten", "application")
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
            @DisplayName("Проваленный. Два проваленных, один успешный.")
            public void assertSoft4() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .headerContainsSoft("Conten", "application")
                            .headerContainsSoft("Content-type", "application")
                            .headerContainsSoft("Content-type", "Application")
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
        @Story("Header Equals Soft")
        class HeaderEqualsSoft {
            private static final String url = "/api/headerEquals";

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
            @DisplayName("Успешный.")
            public void assertSoft1() {
                Response response = getBaseReqSpec().get(url);

                ValidatorFabric.beginResponseValidation(response)
                        .headerEqualsSoft("Content-type", "application/json")
                        .validate();
            }

            @Test
            @DisplayName("Проваленный. Заголовок присутствует.")
            public void assertSoft2() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .headerEqualsSoft("Content-type", "Application/json")
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
            @DisplayName("Проваленный. Заголовок отсутствует.")
            public void assertSoft3() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .headerEqualsSoft("Conten", "application/json")
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
            @DisplayName("Проваленный. Два проваленных, один успешный.")
            public void assertSoft4() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .headerEqualsSoft("Conten", "application")
                            .headerEqualsSoft("Content-type", "application/json")
                            .headerEqualsSoft("Content-type", "Application/json")
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
        @Story("Matches Json Schema Soft")
        class MatchesJsonSchemaSoft {
            private static final String urlSuccess = "/api/matchesJsonSchemaSuccess";
            private static final String urlFailed = "/api/matchesJsonSchemaFailed";

            @BeforeAll
            public static void createStub() {
                WireMock.stubFor(
                        get(
                                urlEqualTo(urlSuccess)
                        )
                                .willReturn(
                                        aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-type", "application/json")
                                                .withBody(readFileAsString("stubs/matchesJsonSchema/stub1.json"))
                                )
                );
                WireMock.stubFor(
                        get(
                                urlEqualTo(urlFailed)
                        )
                                .willReturn(
                                        aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-type", "application/json")
                                                .withBody(readFileAsString("stubs/matchesJsonSchema/stub2.json"))
                                )
                );
            }

            @Test
            @DisplayName("Успешный.")
            public void assertSoft1() {
                Response response = getBaseReqSpec().get(urlSuccess);

                ValidatorFabric.beginResponseValidation(response)
                        .matchesJsonSchemaSoft(readFileAsString("stubs/matchesJsonSchema/schema/schema.json"))
                        .validate();
            }

            @Test
            @DisplayName("Проваленный.")
            public void assertSoft2() {
                Response response = getBaseReqSpec().get(urlFailed);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .matchesJsonSchemaSoft(readFileAsString("stubs/matchesJsonSchema/schema/schema.json"))
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
        }

        @Nested
        @Epic("Response")
        @Feature("Soft")
        @Story("Body Equals File Soft")
        class BodyEqualsFile {
            private static final String url = "/api/bodyEqualsFile";

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
                                                .withBody(readFileAsString("stubs/bodyEqualsFile/stub1.json"))
                                )
                );
            }

            @Test
            @DisplayName("Успешный. Файл полностью соответствует ответу.")
            public void assertSoft1() {
                Response response = getBaseReqSpec().get(url);

                ValidatorFabric.beginResponseValidation(response)
                        .bodyEqualsFile("stubs/bodyEqualsFile/files/file1.json")
                        .validate();
            }

            @Test
            @DisplayName("Успешный. В файле другой порядок атрибутов простых атрибутов (но уровни не нарушены).")
            public void assertSoft2() {
                Response response = getBaseReqSpec().get(url);

                ValidatorFabric.beginResponseValidation(response)
                        .bodyEqualsFile("stubs/bodyEqualsFile/files/file2.json")
                        .validate();
            }

            @Test
            @DisplayName("Успешный. В файле другой порядок простых атрибутов, другой порядок в объектах, но массив в том же порядке.")
            public void assertSoft3() {
                Response response = getBaseReqSpec().get(url);

                ValidatorFabric.beginResponseValidation(response)
                        .bodyEqualsFileSoft("stubs/bodyEqualsFile/files/file3.json")
                        .validate();
            }

            @Test
            @DisplayName("Проваленный, в файле другой порядок объектов массивов.")
            public void assertSoft4() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyEqualsFileSoft("stubs/bodyEqualsFile/files/file4.json")
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
            @DisplayName("Проваленный, файл не найден.")
            public void assertSoft5() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyEqualsFileSoft("stubs/bodyEqualsFile/files/ahaha.json")
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
            @DisplayName("Проваленный, файл не может спарсится.")
            public void assertSoft6() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyEqualsFileSoft("stubs/bodyEqualsFile/files/file5.json")
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
            @DisplayName("Проваленный, два проваленных, один успешный.")
            public void assertSoft7() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodyEqualsFileSoft("stubs/bodyEqualsFile/files/file5.json")
                            .bodyEqualsFileSoft("stubs/bodyEqualsFile/files/file1.json")
                            .bodyEqualsFileSoft("stubs/bodyEqualsFile/files/file5.json")
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
        @Story("Body Soft")
        class BodySoft {
            private static final String url = "/api/body";

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
                                                .withBody(readFileAsString("stubs/body/stub1.json"))
                                )
                );
            }

            @Test
            @DisplayName("Успешный. С дефолтным аллюром.")
            public void assertSoft1() {
                Response response = getBaseReqSpec().get(url);

                ValidatorFabric.beginResponseValidation(response)
                        .bodySoft( "name", Matchers.equalTo("hello"))
                        .validate();
            }

            @Test
            @DisplayName("Успешный. С своим аллюром.")
            public void assertSoft2() {
                Response response = getBaseReqSpec().get(url);

                ValidatorFabric.beginResponseValidation(response)
                        .bodySoft( "Название шага", "name", Matchers.equalTo("hello"))
                        .validate();
            }

            @Test
            @DisplayName("Проваленный. Дефолтное описание ошибки.")
            public void assertSoft3() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodySoft(  "name", Matchers.equalTo("hell"))
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
            @DisplayName("Проваленный. Свое описание ошибки.")
            public void assertSoft4() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodySoft(  "name", Matchers.equalTo("hell"), "Свое описание")
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
            @DisplayName("Проваленный. Пустой матчер.")
            public void assertSoft5() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodySoft(  "name", null)
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
            @DisplayName("Проваленный. Два упало, один прошел.")
            public void assertSoft6() {
                Response response = getBaseReqSpec().get(url);

                try {
                    ValidatorFabric.beginResponseValidation(response)
                            .bodySoft(  "name", Matchers.equalTo("hell"), "Свое описание")
                            .bodySoft(  "name", Matchers.equalTo("hello"), "Свое описание")
                            .bodySoft(  "name", Matchers.equalTo("hell"), "Свое описание")
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
        @Story("Group Soft")
        class GroupSoft {
            private static final String url = "/api/soft";

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
                                                .withBody(readFileAsString("stubs/soft/stub1.json"))
                                )
                );
            }

            @Test
            @DisplayName("Успешный. Один уровень вложенности.")
            public void assertSoft1() {
                Response response = getBaseReqSpec().get(url);

                ValidatorFabric.beginResponseValidation(response)
                        .groupSoft(
                                "Первая группа. Первый уровень",
                                nestedValidator -> nestedValidator
                                        .bodyEqualsSoft("name", "hello")
                                        .bodyEqualsSoft("name", "hello")
                                        .bodyEqualsSoft("name", "hello")
                        )
                        .groupSoft(
                                "Вторая группа. Первый уровень",
                                nestedValidator -> nestedValidator
                                        .bodyEqualsSoft("name", "hello")
                                        .bodyEqualsSoft("name", "hello")
                                        .bodyEqualsSoft("name", "hello")
                        )
                        .validate();
            }

            @Test
            @DisplayName("Успешный. Два уровня вложенности.")
            public void assertSoft2() {
                Response response = getBaseReqSpec().get(url);

                ValidatorFabric.beginResponseValidation(response)
                        .groupSoft(
                                "Первая группа. Первый уровень",
                                nestedValidator -> nestedValidator
                                        .bodyEqualsSoft("name", "hello")
                                        .bodyEqualsSoft("name", "hello")
                                        .bodyEqualsSoft("name", "hello")
                                        .groupSoft(
                                                "Первая группа. Второй уровень",
                                                nestedValidator2 -> nestedValidator2
                                                        .bodyEqualsSoft("name", "hello")
                                                        .bodyEqualsSoft("name", "hello")
                                                        .bodyEqualsSoft("name", "hello")
                                        )
                        )
                        .groupSoft(
                                "Вторая группа. Первый уровень",
                                nestedValidator -> nestedValidator
                                        .bodyEqualsSoft("name", "hello")
                                        .bodyEqualsSoft("name", "hello")
                                        .bodyEqualsSoft("name", "hello")
                                        .groupSoft(
                                                "Вторая группа. Второй уровень",
                                                nestedValidator2 -> nestedValidator2
                                                        .bodyEqualsSoft("name", "hello")
                                                        .bodyEqualsSoft("name", "hello")
                                                        .bodyEqualsSoft("name", "hello")
                                        )
                        )
                        .validate();
            }

            @Test
            @DisplayName("Проваленный. Один уровень вложенности. Одна из групп провалена.")
            public void assertSoft3() {
                try {
                    Response response = getBaseReqSpec().get(url);
                    ValidatorFabric.beginResponseValidation(response)
                            .groupSoft(
                                    "Первая группа. Первый уровень",
                                    nestedValidator -> nestedValidator
                                            .bodyEqualsSoft("name", "hello")
                                            .bodyEqualsSoft("name", "hello")
                                            .bodyEqualsSoft("name", "hell")
                            )
                            .groupSoft(
                                    "Вторая группа. Первый уровень",
                                    nestedValidator -> nestedValidator
                                            .bodyEqualsSoft("name", "hello")
                                            .bodyEqualsSoft("name", "hello")
                                            .bodyEqualsSoft("name", "hello")
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();

                    Integer expectedCountValidation = 6;
                    Integer expectedCountValidationError = 1;
                    Assertions.assertEquals(expectedCountValidation, e.getCountValidation(), "Ожидаемое количество проверок - %d".formatted(expectedCountValidation));
                    Assertions.assertEquals(expectedCountValidationError, e.getCountErrorValidation(), "Ожидаемое количество ошибок - %d".formatted(expectedCountValidationError));
                }
            }

            @Test
            @DisplayName("Проваленный. Один уровень вложенности. Две группы провалены.")
            public void assertSoft4() {
                try {
                    Response response = getBaseReqSpec().get(url);
                    ValidatorFabric.beginResponseValidation(response)
                            .groupSoft(
                                    "Первая группа. Первый уровень",
                                    nestedValidator -> nestedValidator
                                            .bodyEqualsSoft("name", "hello")
                                            .bodyEqualsSoft("name", "hello")
                                            .bodyEqualsSoft("name", "hell")
                            )
                            .groupSoft(
                                    "Вторая группа. Первый уровень",
                                    nestedValidator -> nestedValidator
                                            .bodyEqualsSoft("name", "hello")
                                            .bodyEqualsSoft("name", "hell")
                                            .bodyEqualsSoft("name", "hello")
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();

                    Integer expectedCountValidation = 6;
                    Integer expectedCountValidationError = 2;
                    Assertions.assertEquals(expectedCountValidation, e.getCountValidation(), "Ожидаемое количество проверок - %d".formatted(expectedCountValidation));
                    Assertions.assertEquals(expectedCountValidationError, e.getCountErrorValidation(), "Ожидаемое количество ошибок - %d".formatted(expectedCountValidationError));
                }
            }

            @Test
            @DisplayName("Проваленный. Два уровня вложенности. Одна из групп провалена.")
            public void assertSoft5() {
                try {
                    Response response = getBaseReqSpec().get(url);
                    ValidatorFabric.beginResponseValidation(response)
                            .groupSoft(
                                    "Первая группа. Первый уровень",
                                    nestedValidator -> nestedValidator
                                            .bodyEqualsSoft("name", "hello")
                                            .bodyEqualsSoft("name", "hello")
                                            .bodyEqualsSoft("name", "hello")
                                            .groupSoft(
                                                    "Первая группа. Второй уровень",
                                                    nestedValidator2 -> nestedValidator2
                                                            .bodyEqualsSoft("name", "hell")
                                                            .bodyEqualsSoft("name", "hello")
                                                            .bodyEqualsSoft("name", "hello")
                                            )
                            )
                            .groupSoft(
                                    "Вторая группа. Первый уровень",
                                    nestedValidator -> nestedValidator
                                            .bodyEqualsSoft("name", "hello")
                                            .bodyEqualsSoft("name", "hello")
                                            .bodyEqualsSoft("name", "hello")
                                            .groupSoft(
                                                    "Вторая группа. Второй уровень",
                                                    nestedValidator2 -> nestedValidator2
                                                            .bodyEqualsSoft("name", "hello")
                                                            .bodyEqualsSoft("name", "hello")
                                                            .bodyEqualsSoft("name", "hello")
                                            )
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();

                    Integer expectedCountValidation = 12;
                    Integer expectedCountValidationError = 1;
                    Assertions.assertEquals(expectedCountValidation, e.getCountValidation(), "Ожидаемое количество проверок - %d".formatted(expectedCountValidation));
                    Assertions.assertEquals(expectedCountValidationError, e.getCountErrorValidation(), "Ожидаемое количество ошибок - %d".formatted(expectedCountValidationError));
                }
            }

            @Test
            @DisplayName("Проваленный. Два уровня вложенности. Две группы провалены.")
            public void assertSoft6() {
                try {
                    Response response = getBaseReqSpec().get(url);
                    ValidatorFabric.beginResponseValidation(response)
                            .groupSoft(
                                    "Первая группа. Первый уровень",
                                    nestedValidator -> nestedValidator
                                            .bodyEqualsSoft("name", "hello")
                                            .bodyEqualsSoft("name", "hello")
                                            .bodyEqualsSoft("name", "hello")
                                            .groupSoft(
                                                    "Первая группа. Второй уровень",
                                                    nestedValidator2 -> nestedValidator2
                                                            .bodyEqualsSoft("name", "hell")
                                                            .bodyEqualsSoft("name", "hello")
                                                            .bodyEqualsSoft("name", "hello")
                                            )
                            )
                            .groupSoft(
                                    "Вторая группа. Первый уровень",
                                    nestedValidator -> nestedValidator
                                            .bodyEqualsSoft("name", "hello")
                                            .bodyEqualsSoft("name", "hello")
                                            .bodyEqualsSoft("name", "hello")
                                            .groupSoft(
                                                    "Вторая группа. Второй уровень",
                                                    nestedValidator2 -> nestedValidator2
                                                            .bodyEqualsSoft("name", "hello")
                                                            .bodyEqualsSoft("name", "hello")
                                                            .bodyEqualsSoft("name", "hell")
                                            )
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();

                    Integer expectedCountValidation = 12;
                    Integer expectedCountValidationError = 2;
                    Assertions.assertEquals(expectedCountValidation, e.getCountValidation(), "Ожидаемое количество проверок - %d".formatted(expectedCountValidation));
                    Assertions.assertEquals(expectedCountValidationError, e.getCountErrorValidation(), "Ожидаемое количество ошибок - %d".formatted(expectedCountValidationError));
                }
            }

            @Test
            @DisplayName("Проваленный. Два уровня вложенности. Две группы провалены. На каждом уровне ошибки.")
            public void assertSoft7() {
                try {
                    Response response = getBaseReqSpec().get(url);
                    ValidatorFabric.beginResponseValidation(response)
                            .groupSoft(
                                    "Первая группа. Первый уровень",
                                    nestedValidator -> nestedValidator
                                            .bodyEqualsSoft("name", "hello")
                                            .bodyEqualsSoft("name", "hell2o")
                                            .bodyEqualsSoft("name", "hello")
                                            .groupSoft(
                                                    "Первая группа. Второй уровень",
                                                    nestedValidator2 -> nestedValidator2
                                                            .bodyEqualsSoft("name", "h2ello")
                                                            .bodyEqualsSoft("name", "hello")
                                                            .bodyEqualsSoft("name", "hello")
                                            )
                            )
                            .groupSoft(
                                    "Вторая группа. Первый уровень",
                                    nestedValidator -> nestedValidator
                                            .bodyEqualsSoft("name", "hel2lo")
                                            .bodyEqualsSoft("name", "hello")
                                            .bodyEqualsSoft("name", "hello")
                                            .groupSoft(
                                                    "Вторая группа. Второй уровень",
                                                    nestedValidator2 -> nestedValidator2
                                                            .bodyEqualsSoft("name", "hello")
                                                            .bodyEqualsSoft("name", "hello")
                                                            .bodyEqualsSoft("name", "hello2")
                                            )
                            )
                            .validate();
                    Assertions.fail();
                } catch (ChainValidationError e) {
                    e.printStackTrace();

                    Integer expectedCountValidation = 12;
                    Integer expectedCountValidationError = 4;
                    Assertions.assertEquals(expectedCountValidation, e.getCountValidation(), "Ожидаемое количество проверок - %d".formatted(expectedCountValidation));
                    Assertions.assertEquals(expectedCountValidationError, e.getCountErrorValidation(), "Ожидаемое количество ошибок - %d".formatted(expectedCountValidationError));
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
