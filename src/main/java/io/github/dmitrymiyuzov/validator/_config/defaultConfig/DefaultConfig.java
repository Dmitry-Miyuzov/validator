package io.github.dmitrymiyuzov.validator._config.defaultConfig;

import io.github.dmitrymiyuzov.validator._config.impl.ValidatorFabricConfig;

public class DefaultConfig implements ValidatorFabricConfig {
    @Override
    public String[] stackTracesFilterByContainsClasses() {
        return new String[] {""};
    }

    @Override
    public Boolean isNeedStackTracesFilter() {
        return false;
    }
}
