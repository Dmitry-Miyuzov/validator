package io.github.dmitrymiyuzov.validator._config;

import io.github.dmitrymiyuzov.validator._config.defaultConfig.DefaultConfig;
import io.github.dmitrymiyuzov.validator._config.impl.ValidatorFabricConfig;
import org.aeonbits.owner.ConfigFactory;

public class ThreadLocalValidatorFabricConfig implements ValidatorFabricConfig {
    private static final ValidatorFabricConfig defaultConfig = getDefaultConfig();
    private static final ThreadLocal<ValidatorFabricConfig> config = ThreadLocal.withInitial(() -> defaultConfig);

    public ThreadLocalValidatorFabricConfig() {
    }

    public static void setConfig(ValidatorFabricConfig config) {
        ThreadLocalValidatorFabricConfig.config.set(config);
    }

    public static ValidatorFabricConfig getConfig() {
        return ThreadLocalValidatorFabricConfig.config.get();
    }

    public static void remove() {
        ThreadLocalValidatorFabricConfig.config.remove();
    }

    @Override
    public String[] stackTracesFilterByContainsClasses() {
        return config.get().stackTracesFilterByContainsClasses();
    }

    @Override
    public Boolean isNeedStackTracesFilter() {
        return config.get().isNeedStackTracesFilter();
    }

    private static ValidatorFabricConfig getDefaultConfig() {
        ValidatorFabricConfig defaultWithFile = ConfigFactory.create(ValidatorFabricConfig.class);
        if (
                defaultWithFile.stackTracesFilterByContainsClasses() == null
        ) {
            return new DefaultConfig();
        } else {
            return defaultWithFile;
        }
    }
}
