package io.qase.app.ui.page;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class BasePage {

    protected void clearFieldAndFill(By locator, String text) {
        $(locator).shouldBe(visible, enabled).clear();
        $(locator).shouldBe(visible, enabled).sendKeys(text);
    }
}
