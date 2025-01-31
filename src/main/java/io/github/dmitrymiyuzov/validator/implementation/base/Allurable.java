package io.github.dmitrymiyuzov.validator.implementation.base;

import io.github.dmitrymiyuzov.validator.allure.AllureConfigurable;

import java.util.function.Consumer;

public interface Allurable<Validator> {
    Validator allureConfigure(Consumer<AllureConfigurable> config);
}
