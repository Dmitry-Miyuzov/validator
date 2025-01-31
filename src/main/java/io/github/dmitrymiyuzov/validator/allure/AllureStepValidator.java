package io.github.dmitrymiyuzov.validator.allure;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * @author "Dmitry Miyuzov"
 */
public class AllureStepValidator {
    protected final UUID stepUUID;
    protected final StepResult stepResult;
    private final boolean disableAllure;

    protected AllureStepValidator(UUID stepUUID, StepResult stepResult, boolean disableAllure) {
        this.stepUUID = stepUUID;
        this.stepResult = stepResult;
        this.disableAllure = disableAllure;
    }

    public static void doStep(String allureStepName, Status status, boolean disableAllure) {
        AllureStepValidator allureStepValidator = beginStep(allureStepName, disableAllure);
        allureStepValidator.exitStepWithStopTime(status);
    }

    public static AllureStepValidator beginStep(String allureStepName, Status status, boolean disableAllure) {
        AllureStepValidator allureStepValidator;

        UUID stepUUID = UUID.randomUUID();
        StepResult stepResult = new StepResult();

        allureStepValidator = new AllureStepValidator(stepUUID, stepResult, disableAllure)
                .setName(allureStepName)
                .setStart()
                .setStatus(status)
                .setStopTime();

        if (!disableAllure) {
            Allure.getLifecycle()
                    .startStep(stepUUID.toString(), stepResult);
        }

        return allureStepValidator;
    }

    public static AllureStepValidator beginStep(String allureStepName, boolean disableAllure) {
        return beginStep(allureStepName, Status.FAILED, disableAllure);
    }

    private AllureStepValidator setName(String allureStepName) {
        updateStep(step -> step.setName(allureStepName));
        return this;
    }

    private AllureStepValidator setStart() {
        updateStep(step -> step.setStart(System.currentTimeMillis()));
        return this;
    }

    public AllureStepValidator setStatus(Status status) {
        updateStep(step -> step.setStatus(status));
        return this;
    }

    private AllureStepValidator setStopTime() {
        updateStep(step -> step.setStop(System.currentTimeMillis()));
        return this;
    }

    public void exitStepWithStopTime(Status status) {
        setStatus(status);
        setStopTime();
        stopStep();
    }

    public void stopStep() {
        if (!disableAllure) {
            Allure.getLifecycle()
                    .stopStep(stepUUID.toString());
        }
    }

    private void updateStep(Consumer<StepResult> update) {
        update.accept(stepResult);
    }
}
