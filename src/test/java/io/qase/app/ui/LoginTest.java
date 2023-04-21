package io.qase.app.ui;

import io.qase.app.ui.page.LoginPage;
import io.qase.app.ui.page.ProjectsPage;
import io.qase.app.util.DataProviders;
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

    @Test(dataProviderClass = DataProviders.class, dataProvider = "dataProviderForInvalidUserName")
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

    @Test(dataProviderClass = DataProviders.class, dataProvider = "dataProviderForInvalidLogin")
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
}
