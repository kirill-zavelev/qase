package io.qase.app.ui.page;

import com.codeborne.selenide.Selenide;
import io.qase.app.ui.util.PropertiesLoader;
import org.openqa.selenium.By;

import java.util.Properties;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class BasePage {

    protected Properties properties;

    public BasePage() {
        this.properties = PropertiesLoader.loadProperties();
    }

    protected void clearFieldAndFill(By locator, String text) {
        $(locator).shouldBe(visible, enabled).clear();
        $(locator).shouldBe(visible, enabled).sendKeys(text);
    }

    protected String getInlineAlertMessage(By locator) {
        return Selenide.executeJavaScript("return arguments[0].validationMessage;", $(locator));
    }

    protected void open(String url) {
        Selenide.open(url);
    }
}
