package io.qase.app.ui.page;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class LoginPage extends BasePage {

    private static final By EMAIL_INPUT = By.id("inputEmail");
    private static final By PASSWORD_INPUT = By.id("inputPassword");
    private static final By LOGIN_BTN = By.id("btnLogin");
    private static final By LOGIN_ERROR = By.xpath("//div[@data-qase-test='login-error']");

    public LoginPage open() {
        open(properties.getProperty("login.url"));
        getWebDriver().manage().window().maximize();
        return this;
    }

    public ProjectsPage loginWithValidUser() {
        login(properties.getProperty("standard.username"), properties.getProperty("standard.password"));
        return new ProjectsPage();
    }

    public LoginPage login(String email, String password) {
        $(EMAIL_INPUT).shouldBe(visible).sendKeys(email);
        $(PASSWORD_INPUT).shouldBe(visible).sendKeys(password);
        $(LOGIN_BTN).shouldBe(enabled).click();
        return this;
    }

    public String getInvalidCredentialsMessage() {
        return $(LOGIN_ERROR).shouldBe(visible).getText();
    }

    public String getInlineAlertForUserName() {
        return getInlineAlertMessage(EMAIL_INPUT);
    }
}
