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

    @Override
    public Boolean isEnabledFilter() {
        return config.get().isEnabledFilter();
    }

    @Override
    public String includeByContainsPackage() {
        return config.get().includeByContainsPackage();
    }

    @Override
    public String[] excludeByContainsClasses() {
        return config.get().excludeByContainsClasses();
    }

    private static ValidatorFabricConfig getDefaultConfig() {
        ValidatorFabricConfig defaultWithFile = ConfigFactory.create(ValidatorFabricConfig.class);
        if (
                defaultWithFile.isEnabledFilter() == null
        ) {
            return new DefaultConfig();
        } else {
            return defaultWithFile;
        }
    }
}
