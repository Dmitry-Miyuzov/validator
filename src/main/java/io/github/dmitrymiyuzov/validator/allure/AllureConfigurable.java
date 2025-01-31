package io.github.dmitrymiyuzov.validator.allure;

public interface AllureConfigurable {
    void enableValidationName();
    void enableSoftName();
    void enableMessageError();
    void disableValidationName();
    void disableSoftName();
    void disableMessageError();
}
