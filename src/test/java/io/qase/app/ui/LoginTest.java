package io.qase.app.ui;

import io.qase.app.ui.page.LoginPage;
import io.qase.app.ui.util.PropertiesLoader;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest extends BaseTest {

    @Test
    public void checkSuccessfulLogin() {
        final String email = PropertiesLoader.loadProperties().getProperty("standard.username");
        final String password = PropertiesLoader.loadProperties().getProperty("standard.password");
        boolean isUserLoggedIn = new LoginPage()
                .open()
                .login(email, password)
                .isOpened();
        assertThat(isUserLoggedIn)
                .as("User was not logged in")
                .isTrue();
    }
}
