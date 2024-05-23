package com.company.automation.waitretry;

import java.util.Random;

/**
 * Utility class providing methods to introduce delays and back-offs in application execution,
 * typically used in retry mechanisms.
 */
public class DelayUtils {
    private static final Random RANDOM = new Random();
    private static final int MAX_DELAY_IN_MILLIS = 1000;
    private static final long MAX_BACKOFF_IN_MILLIS = 100;

    /**
     * Introduces a limited back-off delay up to a maximum specified amount.
     *
     * @param backOffMillis The desired delay in milliseconds. If this exceeds the maximum allowed back-off,
     *                      the delay will be limited to the maximum.
     */
    public static void limitedBackOff(long backOffMillis) {
        try {
            Thread.sleep(Math.min(backOffMillis, MAX_BACKOFF_IN_MILLIS));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Retry interrupted", e);
        }
    }

    /**
     * Executes a provided Runnable action after a random delay.
     *
     * @param action The Runnable to be executed. The action is executed in a new thread.
     */
    public static void delayedAction(Runnable action) {
        new Thread(
                () -> {
                    try {
                        Thread.sleep(RANDOM.nextInt(MAX_DELAY_IN_MILLIS));
                        action.run();
                    } catch (InterruptedException e) {
                        System.out.printf("Failed to run action: %s%n", extractClassNameFromRunnable(action));
                    }
                }
        ).start();
    }

    /**
     * Extracts a class name from a Runnable instance, handling nested or anonymous classes.
     *
     * @param runnable The Runnable from which the class name is extracted.
     * @return A string representing the class name. If the class is nested or anonymous,
     *         returns the parent class name.
     */
    private static String extractClassNameFromRunnable(Runnable runnable) {
        String fullClassName = runnable.getClass().getName();
        String[] parts = fullClassName.split("\\.|\\$\\$");
        return (parts.length > 2) ? parts[parts.length - 2] : fullClassName;
    }
}
