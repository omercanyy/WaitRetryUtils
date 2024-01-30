package com.company.automation.waitretry;

import java.util.Random;

public class DelayUtils {
    private static final Random RANDOM = new Random();
    private static final int MAX_DELAY_IN_MILLIS = 1000;
    private static final long MAX_BACKOFF_IN_MILLIS = 100;

    public static void limitedBackOff(long backOffMillis) {
        try {
            Thread.sleep(Math.min(backOffMillis, MAX_BACKOFF_IN_MILLIS));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Retry interrupted", e);
        }
    }

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

    private static String extractClassNameFromRunnable(Runnable runnable) {
        String fullClassName = runnable.getClass().getName();
        String[] parts = fullClassName.split("\\.|\\$\\$");
        return (parts.length > 2) ? parts[parts.length - 2] : fullClassName;
    }
}
