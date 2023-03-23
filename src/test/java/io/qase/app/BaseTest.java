package io.qase.app;

import org.testng.annotations.BeforeClass;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class BaseTest {

    @BeforeClass
    public void setUp() {
        getWebDriver().manage().window().maximize();
    }
}
