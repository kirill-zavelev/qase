package io.qase.app.ui;

import io.netty.util.internal.StringUtil;
import io.qase.app.ui.page.LoginPage;
import io.qase.app.ui.page.ProjectsPage;
import io.qase.app.ui.util.PropertiesLoader;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest extends BaseTest {

    @Test
    public void checkSuccessfulLogin() {
        boolean isUserLoggedIn = new LoginPage()
                .open()
                .loginWithValidUser()
                .isOpened();
        assertThat(isUserLoggedIn)
                .as("User was not logged in")
                .isTrue();
    }

    @Test(dataProvider = "dataProviderForInvalidUserName")
    public void checkUnsuccessfulLoginInlineMessages(String login, String pass, String expectedMsg) {
        String actMessage = new LoginPage()
                .open()
                .login(login, pass)
                .getInlineAlertForUserName();
        assertThat(actMessage)
                .as("Message should be " + expectedMsg)
                .isEqualTo(expectedMsg);
        assertThat(new ProjectsPage().isOpened())
                .as("Projects page should not be opened")
                .isFalse();
    }

    @Test(dataProvider = "dataProviderForInvalidLogin")
    public void checkUnsuccessfulLogin(String login, String pass, String expectedMsg) {
        String actMessage = new LoginPage()
                .open()
                .login(login, pass)
                .getInvalidCredentialsMessage();
        assertThat(actMessage)
                .as("Message should be " + expectedMsg)
                .isEqualTo(expectedMsg);
        assertThat(new ProjectsPage().isOpened())
                .as("Projects page should not be opened")
                .isFalse();
    }

    @DataProvider(name = "dataProviderForInvalidUserName")
    public Object[][] dataProviderForInvalidUserName() {
        return new Object[][]{
                {"abc", PropertiesLoader.loadProperties().getProperty("standard.password"),
                        "Please include an '@' in the email address. 'abc' is missing an '@'."},
                {"abc@.", PropertiesLoader.loadProperties().getProperty("standard.password"),
                        "'.' is used at a wrong position in '.'."},
                {"abc@", PropertiesLoader.loadProperties().getProperty("standard.password"),
                        "Please enter a part following '@'. 'abc@' is incomplete."},
                {StringUtil.EMPTY_STRING, PropertiesLoader.loadProperties().getProperty("standard.password"),
                        "Please fill in this field."},
                {"abc@@", PropertiesLoader.loadProperties().getProperty("standard.password"),
                        "A part following '@' should not contain the symbol '@'."}
        };
    }

    @DataProvider(name = "dataProviderForInvalidLogin")
    public Object[][] dataProviderForInvalidLogin() {
        return new Object[][]{
                {"abc@abc.com", PropertiesLoader.loadProperties().getProperty("standard.password"),
                        "These credentials do not match our records."},
                {PropertiesLoader.loadProperties().getProperty("standard.username"), "12345",
                        "These credentials do not match our records."}
        };
    }
}
