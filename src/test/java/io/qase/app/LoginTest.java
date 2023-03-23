package io.qase.app;

import io.qase.app.page.LoginPage;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest {

    @Test
    public void checkSuccessfulLogin() {
        final String email = "kiri4by@gmail.com";
        final String password = "31199620Kirill";
        boolean isUserLoggedIn = new LoginPage().open().login(email, password).isOpened();
        assertThat(isUserLoggedIn)
                .as("User was not logged in")
                .isTrue();
    }
}
