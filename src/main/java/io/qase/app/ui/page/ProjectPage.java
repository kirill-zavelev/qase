package io.qase.app.ui.page;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ProjectPage {

    public static final By TITLE = By.xpath("//h1");
    public static final By SETTINGS_LOCATOR = By.xpath("//a[@title='Settings']");
    public static final By PROJECT_NAME_LOCATOR = By.xpath("//div[@class='LALnEw']");

    public boolean isOpened() {
        return $(SETTINGS_LOCATOR).shouldBe(visible, enabled).isDisplayed();
    }

    public String getRepositoryName() {
        return $(TITLE).shouldBe(visible).getText();
    }

    public String getProjectName() {
        return $(PROJECT_NAME_LOCATOR).getText();
    }

    public SettingsPage clickSettings() {
        $(SETTINGS_LOCATOR).shouldBe(visible, enabled).click();
        return new SettingsPage();
    }
}
