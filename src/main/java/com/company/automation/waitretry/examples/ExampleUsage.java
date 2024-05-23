package com.company.automation.waitretry.examples;

import com.company.automation.waitretry.examples.mockingwebelements.DelayedContentWebElement;
import com.company.automation.waitretry.examples.mockingwebelements.DelayedWebElement;
import com.company.automation.waitretry.examples.mockingwebelements.NeverVisibleThrowsNPEWebElement;

import java.util.function.Supplier;

import static com.company.automation.waitretry.RetryUtils.retry;
import static com.company.automation.waitretry.RetryUtils.retryOrDefault;

public class ExampleUsage {
    public static void main(String[] args) throws InterruptedException {
        delayedVisibility();
        immediateVisibilityButDelayedContent();
        neverVisibleReturnsDefault();
    }

    public static void delayedVisibility() throws InterruptedException {
        // Without this repo
        DelayedWebElement webElement = new DelayedWebElement();
        int attempt = 0;
        int limit = 5000;
        String val = null;
        while(attempt < limit) {
            if (webElement.isVisible()) {
                val = webElement.getContent();
                break;
            }
            Thread.sleep(100);
            attempt++;
        }
        System.out.printf("Old-school: %s%n", val);

        // With this repo
        DelayedWebElement webElement2 = new DelayedWebElement();
        Supplier<String> extractionLogic = webElement2::getContent;
        Supplier<Boolean> retryStopLogic = webElement2::isVisible;
        val = retry(extractionLogic, retryStopLogic);
        System.out.printf("Modern: %s%n", val);
    }

    public static void immediateVisibilityButDelayedContent() throws InterruptedException {
        // Without this repo
        DelayedContentWebElement webElement = new DelayedContentWebElement();
        int attempt = 0;
        int limit = 500000;
        Integer val = null;
        while(attempt < limit) {
            if (webElement.isVisible() && webElement.getContent() != null) {
                val = webElement.getContent();
                break;
            }
            Thread.sleep(100);
            attempt++;
        }
        System.out.printf("Old-school: %s%n", val);

        // With this repo
        DelayedContentWebElement webElement2 = new DelayedContentWebElement();
        Supplier<Integer> extractionLogic = webElement2::getContent;
        Supplier<Boolean> retryStopLogic = () ->  webElement2.isVisible() && webElement2.getContent() != null;
        val = retry(extractionLogic, retryStopLogic);
        System.out.printf("Modern: %s%n", val);
    }

    public static void neverVisibleReturnsDefault() throws InterruptedException {
        // Without this repo
        NeverVisibleThrowsNPEWebElement webElement = new NeverVisibleThrowsNPEWebElement();
        int attempt = 0;
        int limit = 5000;
        Object val = null;
        while(attempt < limit) {
            if (webElement.isVisible() && webElement.getContent() != null) {
                // TODO: Causes NPE down the line if invoked, needs fixing, pain in the butt
                val = webElement.getContent();
                break;
            }
            Thread.sleep(1); // Just for show
            attempt++;
        }
        System.out.printf("Old-school: %s%n", val);

        // With this repo
        NeverVisibleThrowsNPEWebElement webElement2 = new NeverVisibleThrowsNPEWebElement();
        Supplier<Object> extractionLogic = webElement2::getContent;
        Supplier<Boolean> retryStopLogic = webElement2::isVisible;
        String defaultVal = "Default value to prevent NPE down the line";
        val = retryOrDefault(extractionLogic, retryStopLogic, defaultVal);
        System.out.printf("Modern: %s%n", val);
    }
}