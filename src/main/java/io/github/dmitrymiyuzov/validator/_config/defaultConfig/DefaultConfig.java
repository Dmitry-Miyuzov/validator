package io.github.dmitrymiyuzov.validator._config.defaultConfig;

import io.github.dmitrymiyuzov.validator._config.impl.ValidatorFabricConfig;

public class DefaultConfig implements ValidatorFabricConfig {
    @Override
    public String[] excludeByContainsClasses() {
        return new String[] {""};
    }

    @Override
    public String includeByContainsPackage() {
        return "";
    }

    @Override
    public Boolean isEnabledFilter() {
        return false;
    }
}
