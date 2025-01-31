package io.github.dmitrymiyuzov.validator._config.impl;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:config/validatorFabric.properties"
})
public interface ValidatorFabricConfig extends Config {

    @Config.Key("validator.isNeedStackTracesFilter")
    Boolean isNeedStackTracesFilter();

    @Config.Key("validator.stackTracesFilterByContainsClasses")
    @Separator(",")
    String[] stackTracesFilterByContainsClasses();
}
