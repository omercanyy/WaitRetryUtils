package com.company.automation.waitretry;

import java.util.function.Supplier;

import static com.company.automation.waitretry.DelayUtils.limitedBackOff;

public class RetryUtils {
    private static final int DEFAULT_MAX_RETRY = 10;
    private static final long DEFAULT_DELAY_IN_MILLIS = 300;

    /**
     * Retries on a given action that returns a value until a given eligibility criteria is satisfied.
     *
     * @param extractionLogic the action that returns a result but only can be run if the eligibility criteria is
     *                        satisfied
     * @param eligibilityLogic the eligibility scenario that makes the action viable
     * @return the result of the action is returned once eligibility scenario is satisfied. If the attempts expire then
     * returns null
     * @param <T> type of the result returned from the action
     */
    public static <T> T retry(Supplier<T> extractionLogic, Supplier<Boolean> eligibilityLogic) {
        return retry(extractionLogic, eligibilityLogic, DEFAULT_MAX_RETRY, DEFAULT_DELAY_IN_MILLIS);
    }

    /**
     * Retries on a given action that returns a value until a given eligibility criteria is satisfied.
     *
     * @param extractionLogic the action that returns a result but only can be run if the eligibility criteria is
     *                        satisfied
     * @param eligibilityLogic the eligibility scenario that makes the action viable
     * @param maxRetries the maximum amount to check if the eligibility criteria is satisfied
     * @param delayInMillis the amount of wait in between retries
     * @return the result of the action is returned once eligibility scenario is satisfied. If the attempts expire then
     * returns null
     * @param <T> type of the result returned from the action
     */
    public static <T> T retry(Supplier<T> extractionLogic, Supplier<Boolean> eligibilityLogic, int maxRetries,
                              long delayInMillis) {
        int attempts = 0;
        while (attempts < maxRetries) {
            if (!eligibilityLogic.get()) {
                limitedBackOff(delayInMillis);
                attempts++;
                continue;
            }

            try {
                return extractionLogic.get();
            } catch (Exception e) {
                throw new RuntimeException("Extraction logic failed", e);
            }
        }
        return null;
    }

    /**
     * Retries on a given action that returns a value until a given eligibility criteria is satisfied.
     *
     * @param extractionLogic the action that returns a result but only can be run if the eligibility criteria is
     *                        satisfied
     * @param eligibilityLogic the eligibility scenario that makes the action viable
     * @param defaultReturn the value to return if attempts are expired and eligibility criteria is not met
     * @return the result of the action is returned once eligibility scenario is satisfied. If the attempts expire then
     * returns the default value
     * @param <T> type of the result returned from the action
     */
    public static <T> T retryOrDefault(Supplier<T> extractionLogic, Supplier<Boolean> eligibilityLogic,
                                       T defaultReturn) {
        return retryOrDefault(extractionLogic, eligibilityLogic, defaultReturn, DEFAULT_MAX_RETRY,
                DEFAULT_DELAY_IN_MILLIS);
    }

    /**
     * Retries on a given action that returns a value until a given eligibility criteria is satisfied.
     *
     * @param extractionLogic the action that returns a result but only can be run if the eligibility criteria is
     *                        satisfied
     * @param eligibilityLogic the eligibility scenario that makes the action viable
     * @param defaultReturn the value to return if attempts are expired and eligibility criteria is not met
     * @param maxRetries the maximum amount to check if the eligibility criteria is satisfied
     * @param delayInMillis the amount of wait in between retries
     * @return the result of the action is returned once eligibility scenario is satisfied. If the attempts expire then
     * returns the default value
     * @param <T> type of the result returned from the action
     */
    public static <T> T retryOrDefault(Supplier<T> extractionLogic, Supplier<Boolean> eligibilityLogic, T defaultReturn,
                                       int maxRetries, long delayInMillis) {
        int attempts = 0;
        while (attempts < maxRetries) {
            if (!eligibilityLogic.get()) {
                limitedBackOff(delayInMillis);
                attempts++;
                continue;
            }

            try {
                return extractionLogic.get();
            } catch (Exception e) {
                throw new RuntimeException("Extraction logic failed", e);
            }
        }
        return defaultReturn;
    }
}
