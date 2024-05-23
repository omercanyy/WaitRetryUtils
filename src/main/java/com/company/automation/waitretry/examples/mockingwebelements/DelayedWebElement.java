package com.company.automation.waitretry.examples.mockingwebelements;

import static com.company.automation.waitretry.DelayUtils.delayedAction;

/**
 * This web element mock takes some time to become visible.
 *
 * <p>Simulates a situation where a button takes some time to become visible
 *
 */
public class DelayedWebElement extends WebElement<String> {

    public DelayedWebElement() {
        isVisible = false;
        content = "This is the content from WebElement";
        delayedAction(this::setVisibility);
    }

    private synchronized void setVisibility() {
        isVisible = true;
    }
}
