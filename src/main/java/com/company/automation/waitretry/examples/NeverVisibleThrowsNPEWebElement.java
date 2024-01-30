package com.company.automation.waitretry.examples;

/**
 * This web element mock never becomes visible therefore calling {@link NeverVisibleThrowsNPEWebElement#getContent()}
 * throws {@link NullPointerException}
 */
public class NeverVisibleThrowsNPEWebElement extends WebElement<Object> {

    public NeverVisibleThrowsNPEWebElement() {
        isVisible = false;
    }
}
