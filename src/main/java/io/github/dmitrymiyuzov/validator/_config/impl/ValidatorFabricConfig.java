package io.github.dmitrymiyuzov.validator._config.impl;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:config/validatorFabric.properties"
})
public interface ValidatorFabricConfig extends Config {
    @Config.Key("validator.stackTracesFilter")
    String stackTracesFilter();
}
