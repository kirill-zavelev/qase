package io.qase.app.page;

import com.codeborne.selenide.Selenide;
import io.qase.app.util.PropertiesLoader;
import org.openqa.selenium.By;

import java.util.Properties;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class LoginPage {

    private static final By EMAIL_INPUT = By.id("inputEmail");
    private static final By PASSWORD_INPUT = By.id("inputPassword");
    private static final By LOGIN_BTN = By.id("btnLogin");

    private final Properties properties;

    public LoginPage() {
        this.properties = PropertiesLoader.loadProperties();
    }

    public LoginPage open() {
        Selenide.open("/login");
        getWebDriver().manage().window().maximize();
        return this;
    }

    public ProjectsPage loginWithValidUser() {
        login(properties.getProperty("standard.username"), properties.getProperty("standard.password"));
        return new ProjectsPage();
    }

    public ProjectsPage login(String email, String password) {
        $(EMAIL_INPUT).shouldBe(visible).sendKeys(email);
        $(PASSWORD_INPUT).shouldBe(visible).sendKeys(password);
        $(LOGIN_BTN).shouldBe(enabled).click();
        return new ProjectsPage();
    }
}
