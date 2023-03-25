package io.qase.app;

import com.github.javafaker.Faker;
import io.qase.app.dto.Project;
import io.qase.app.page.LoginPage;
import io.qase.app.page.SettingsPage;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.List;

public class DeleteProjectTest extends BaseTest {

    @Test
    public void checkProjectDeletion() {
        Project project = Project.builder()
                .projectName(new Faker().funnyName().name())
                .projectCode(new Faker().number().digits(3))
                .description(new Faker().company().industry())
                .build();
        SettingsPage settingsPage = new LoginPage()
                .open()
                .loginWithValidUser()
                .createNewProject()
                .fillInProjectInfo(project)
                .saveProject()
                .clickSettings()
                .clickDeleteProject();
        String actualAlert = settingsPage.getDeleteAlert();
        final String expectedAlert = "Are you sure, that you want delete project \"" + project.getProjectName() + "\"";
        Assertions.assertThat(actualAlert)
                .as("The message after delete should be: " + expectedAlert)
                .isEqualTo(expectedAlert);
        List<String> actualProjectsNames = settingsPage.confirmDeletion().getAllProjectsNames();
        Assertions.assertThat(actualProjectsNames)
                .as("Project name: " + project.getProjectName() + " should not exist in the list")
                .doesNotContain(project.getProjectName());
    }
}
