package io.qase.app.wrapper;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class Input {

    private String label;

    public Input(String label) {
        this.label = label;
    }

    private static final String INPUT_LOCATOR = "//input//ancestor::div//label[text()='%s']";

    public void fillIn(String text) {
        $(String.format(INPUT_LOCATOR, label)).shouldBe(visible, enabled).sendKeys(text);
    }
}
