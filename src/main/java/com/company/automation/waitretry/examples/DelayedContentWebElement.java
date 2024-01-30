package com.company.automation.waitretry.examples;

import static com.company.automation.waitretry.DelayUtils.delayedAction;


/**
 * This web element mock is visible from the start, but it takes some time for the content to be set.
 *
 * <p>Simulates situations like an auto-complete dropdown taking some time to populate even though element is visible.
 *
 * <p>One might need to write complicated logic to check if the element is ready to read its contents. In this case, a
 * simple null check. In real world this could be more complicated.
 *
 */
public class DelayedContentWebElement extends WebElement<Integer> {

    public DelayedContentWebElement() {
        isVisible = true;
        delayedAction(this::setContent);
    }

    private synchronized void setContent() {
        content = 12345;
    }
}
