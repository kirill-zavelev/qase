package io.qase.app.ui;

import com.codeborne.selenide.Selenide;
import org.testng.annotations.AfterMethod;

public class BaseTest {

    @AfterMethod
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}
