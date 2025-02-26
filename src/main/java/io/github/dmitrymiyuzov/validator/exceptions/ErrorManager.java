package io.github.dmitrymiyuzov.validator.exceptions;

import io.github.dmitrymiyuzov.validator._config.impl.ValidatorFabricConfig;
import io.github.dmitrymiyuzov.validator.exceptions.base.ChainValidationError;
import io.github.dmitrymiyuzov.validator.exceptions.base.ChainValidationException;
import io.github.dmitrymiyuzov.validator.exceptions.response.body.BodyInvalidJsonError;
import io.github.dmitrymiyuzov.validator.exceptions.response.body.BodyMatcherNullError;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author "Dmitry Miyuzov"
 */
public class ErrorManager {
    private final ValidatorFabricConfig config;
    private final List<Throwable> errors;
    private int countValidation;
    private boolean isSoftAssert;

    public ErrorManager(ValidatorFabricConfig config) {
        this.config = config;
        this.isSoftAssert = false;
        this.errors = new ArrayList<>();
    }

    /**
     * Увеличивает общее количества валидаций на 1.
     */
    public void increaseCountValidation() {
        this.countValidation = countValidation + 1;
    }

    private void filterStackTrace(Throwable trace) {
        if (config.isEnabledFilter()) {
            trace.setStackTrace(Arrays.asList(trace.getStackTrace())
                    .stream()
                    .filter(lastTraceInClass())
                    .filter(predicateNeedSaveStacktraceByContainsPackage())
                    .filter(predicateNeedSaveStacktraceByContainsClasses())
                    .toArray(StackTraceElement[]::new));
        }
    }

    private Predicate<? super StackTraceElement> predicateNeedSaveStacktraceByContainsPackage() {
        String includePackage = config.includeByContainsPackage();

        if (
                includePackage == null
                ||
                includePackage.isBlank()
        ) {
            includePackage = "";
        }

        String finalIncludePackage = includePackage;
        return element -> element.getClassName().contains(finalIncludePackage);
    }

    private Predicate<? super StackTraceElement> predicateNeedSaveStacktraceByContainsClasses() {
        List<String> excludeByContainsClasses = new ArrayList<>(
                Arrays.stream(config.excludeByContainsClasses())
                        .map(String::trim)
                        .toList()
        );

        if (
                excludeByContainsClasses.size() == 1
                &&
                (
                        excludeByContainsClasses.get(0) == null
                        ||
                        excludeByContainsClasses.get(0).isBlank()
                )
        ) {
            excludeByContainsClasses.remove(0);
        }

        return element ->
                excludeByContainsClasses.stream()
                        .noneMatch(
                                filterElement -> element.getClassName().contains(filterElement)
                        );
    }

    /**
     * Предназначен для стрима. Оставляет только один последний вызов для каждого класса.
     */
    private Predicate<StackTraceElement> lastTraceInClass() {
        Set<String> seen = new HashSet<>();
        return stackTraceElement -> {
            Function<StackTraceElement, String> function = StackTraceElement::getFileName;
            return seen.add(function.apply(stackTraceElement));
        };
    }

    private boolean isHaveIllegalArgumentException() {
        for (Throwable element : errors) {
            String throwableClassName = element.getClass()
                    .getSimpleName();

            if (throwableClassName.equals(BodyInvalidJsonError.class.getSimpleName())
                    || throwableClassName.equals(BodyMatcherNullError.class.getSimpleName())) return true;
        }
        return false;
    }

    public void throwExceptionBroken() {
        throwException(getFailedTextChainBroken());
    }

    public void throwException(String message) {
        if (isHaveIllegalArgumentException()) {
            ChainValidationException chainValidationException = new ChainValidationException(message, this.countValidation, this.errors.size());
            collectErrorValidationsIN(chainValidationException);
            throw chainValidationException;
        } else {
            ChainValidationError chainValidationError = new ChainValidationError(message, this.countValidation, this.errors.size());
            collectErrorValidationsIN(chainValidationError);
            throw chainValidationError;
        }
    }

    public String getFailedTextChainBroken() {
        return "Цепочка проверок оборвалась на НЕ софт валидации. Количество не пройденных валидаций " + this.errors.size() + " из " + this.countValidation;
    }

    public String getFailedTextChainEnd() {
        return "Проверки завершились не успешно. Количество не пройденных валидаций " + errors.size() + " из " + countValidation;
    }

    private void collectErrorValidationsIN(Throwable in) {
        in.setStackTrace(new StackTraceElement[0]);
        for (Throwable element : errors) {
            filterStackTrace(element);
            in.addSuppressed(element);
        }
        filterStackTrace(in);
    }

    public List<Throwable> getErrors() {
        return errors;
    }

    public boolean isSoftAssert() {
        return isSoftAssert;
    }

    public void setSoftAssert(boolean softAssert) {
        isSoftAssert = softAssert;
    }
}
