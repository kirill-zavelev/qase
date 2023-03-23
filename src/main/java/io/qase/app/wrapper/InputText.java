package io.qase.app.wrapper;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class InputText {

    private static final String INPUT_LOCATOR = "//div//label[text()='%s']//ancestor::div[@class='prXRTX']//input";

    private String label;

    public InputText(String label) {
        this.label = label;
    }

    public String getText() {
        return $(String.format(INPUT_LOCATOR, label)).shouldBe(visible, enabled).getText();
    }
}
