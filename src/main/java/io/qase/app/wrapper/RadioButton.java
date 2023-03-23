package io.qase.app.wrapper;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class RadioButton {

    private static final String RADIO_BTN_LOCATOR = "//span[text()='%s']//preceding-sibling::span//input";

    private String label;

    public RadioButton(String label) {
        this.label = label;
    }

    public void select() {
        $(String.format(RADIO_BTN_LOCATOR, label)).shouldBe(visible, enabled).click();
    }
}
