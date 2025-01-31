package io.github.dmitrymiyuzov.validator.allure;

public class AllureConfig implements AllureConfigurable {
    private boolean disableValidationNameAllure;
    private boolean disableSoftNameAllure;
    private boolean disableMessageErrorAllure;

    public boolean isDisableValidationNameAllure() {
        return disableValidationNameAllure;
    }
    public boolean isDisableSoftNameAllure() {
        return disableSoftNameAllure;
    }
    public boolean isDisableMessageErrorAllure() {
        return disableMessageErrorAllure;
    }

    @Override
    public void enableValidationName() {
        this.disableValidationNameAllure = false;
    }

    @Override
    public void enableSoftName() {
        this.disableSoftNameAllure = false;
    }

    @Override
    public void enableMessageError() {
        this.disableMessageErrorAllure = false;
    }

    @Override
    public void disableValidationName() {
        this.disableValidationNameAllure = true;
    }

    @Override
    public void disableSoftName() {
        this.disableSoftNameAllure = true;
    }

    @Override
    public void disableMessageError() {
        this.disableMessageErrorAllure = true;
    }
}
