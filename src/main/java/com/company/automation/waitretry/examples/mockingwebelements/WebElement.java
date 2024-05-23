package com.company.automation.waitretry.examples.mockingwebelements;

/**
 * Mocks a simple Selenium Web Element that returns its content when visible.
 *
 * <p>Accessing the content before it becomes visible throw {@link NullPointerException}
 *
 * @param <T> Type of the object returned
 */
public abstract class WebElement<T> {
    protected volatile boolean isVisible;
    protected volatile T content;

    public boolean isVisible() {
        return isVisible;
    }

    public T getContent() {
        if (!isVisible) {
            throw new NullPointerException("getContent invoked on a null object");
        }
        return content;
    }
}
