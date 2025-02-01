package io.github.dmitrymiyuzov.validator._config.impl;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:config/validatorFabric.properties"
})
public interface ValidatorFabricConfig extends Config {

    @Key("validator.stacktrace.isEnableFilter")
    Boolean isEnabledFilter();

    @Key("validator.stacktrace.includeByContainsPackage")
    String  includeByContainsPackage();

    @Key("validator.stacktrace.excludeByContainsClasses")
    @Separator(",")
    String[] excludeByContainsClasses();
}
