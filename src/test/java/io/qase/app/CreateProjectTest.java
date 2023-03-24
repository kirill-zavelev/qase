package io.qase.app;

import com.github.javafaker.Faker;
import io.qase.app.dto.Project;
import io.qase.app.page.LoginPage;
import io.qase.app.page.RepositoryPage;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateProjectTest {

    Project project;
    Faker faker;

    @Test
    public void projectCRUD() {
        faker = new Faker();
        project = Project.builder()
                .projectName(faker.funnyName().name())
                .projectCode(faker.number().digits(3))
                .description(faker.company().industry())
                .build();
        Project actualProject = new LoginPage()
                .open()
                .loginWithValidUser()
                .createNewProject()
                .fillInProjectInfo(project)
                .saveProject()
                .getProject();
        assertThat(actualProject)
                .as("Projects are not equal")
                .isEqualTo(project);
        project = Project.builder()
                .projectName(faker.funnyName().name())
                .projectCode(faker.number().digits(3))
                .description(faker.company().industry())
                .build();
        String actualAlert = new RepositoryPage()
                .updateProjectSettings(project)
                .getAlertMessage();
        String expectedAlert = "Project settings were successfully updated!";
        assertThat(actualAlert)
                .as("The message after update should be: " + expectedAlert)
                .isEqualTo(expectedAlert);
        RepositoryPage repositoryPage = new RepositoryPage();
        Project updatedProject = repositoryPage.getProject();
        assertThat(updatedProject)
                .as("Projects are not equal")
                .isEqualTo(project);
        actualAlert = repositoryPage.clickDeleteProject().getDeleteAlert();
        expectedAlert = "Are you sure, that you want delete project \"" + project.getProjectName() + "\"";
        assertThat(actualAlert)
                .as("The message after delete should be: " + expectedAlert)
                .isEqualTo(expectedAlert);
        List<String> actualProjectsNames = repositoryPage.confirmDeletion().getAllProjectsNames();
        assertThat(actualProjectsNames)
                .as("Project name: " + project.getProjectName() + " should not exist in the list")
                .doesNotContain(project.getProjectName());
    }
}
