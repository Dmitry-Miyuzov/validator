package io.github.dmitrymiyuzov.validator._config;

import io.github.dmitrymiyuzov.validator._config.defaultConfig.DefaultConfig;
import io.github.dmitrymiyuzov.validator._config.impl.ValidatorFabricConfig;
import org.aeonbits.owner.ConfigFactory;

public class ThreadLocalValidatorFabricConfig implements ValidatorFabricConfig {
    private static final ValidatorFabricConfig defaultConfig = getDefaultConfig();
    private static final ThreadLocal<ValidatorFabricConfig> config = ThreadLocal.withInitial(() -> defaultConfig);

    public ThreadLocalValidatorFabricConfig() {
        System.out.println();
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
    public String stackTracesFilter() {
        return config.get().stackTracesFilter();
    }

    private static ValidatorFabricConfig getDefaultConfig() {
        ValidatorFabricConfig defaultWithFile = ConfigFactory.create(ValidatorFabricConfig.class);
        if (defaultWithFile.stackTracesFilter() == null) {
            return new DefaultConfig();
        } else {
            return defaultWithFile;
        }
    }
}
