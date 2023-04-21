package io.qase.app.ui;

import com.github.javafaker.Faker;
import io.qase.app.ui.dto.Project;
import io.qase.app.ui.page.LoginPage;
import io.qase.app.ui.page.ProjectPage;
import io.qase.app.ui.page.ProjectsPage;
import io.qase.app.ui.page.SettingsPage;
import io.qase.app.ui.step.ProjectSteps;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateUpdateProjectTest extends BaseTest {

    Project project;
    List<Project> projects;
    Faker faker;
    ProjectsPage projectsPage;

    @BeforeMethod
    public void setUp() {
        faker = new Faker();
        projects = new ArrayList<>();
        projectsPage = new LoginPage().open().loginWithValidUser();
        project = Project.builder()
                .projectName(faker.funnyName().name())
                .projectCode(faker.number().digits(3))
                .description(faker.company().industry())
                .build();
        projects.add(project);
    }

    @Test
    public void checkUpdateProject() {
        SettingsPage settingsPage = new ProjectSteps()
                .createNewProject(project)
                .clickSettings();
        project = Project.builder()
                .projectName(faker.funnyName().name())
                .projectCode(faker.number().digits(3))
                .description(faker.company().industry())
                .build();
        String actualAlert = settingsPage
                .updateProjectSettings(project)
                .getUpdateAlertMessage();
        String expectedAlert = "Project settings were successfully updated!";
        assertThat(actualAlert)
                .as("The message after update should be: " + expectedAlert)
                .isEqualTo(expectedAlert);
        Project actualProject = settingsPage.getProject();
        assertThat(actualProject)
                .as("Projects are not equal")
                .isEqualTo(project);
        ProjectPage projectPage = settingsPage.openProjectPage();
        assertThat(projectPage.isOpened()).as("Project page should be opened").isTrue();
        final String expectedRepositoryName = project.getProjectCode() + " repository";
        final String expectedProjectName = project.getProjectName();
        assertThat(projectPage.getRepositoryName())
                .as("Repository name should be " + expectedRepositoryName)
                .contains(expectedRepositoryName);
        assertThat(projectPage.getProjectName())
                .as("Project name should be " + expectedProjectName)
                .isEqualTo(expectedProjectName);
        projects.add(project);
    }

    @Test
    public void checkCreateProject() {
        final String expectedRepositoryName = project.getProjectCode() + " repository";
        final String expectedProjectName = project.getProjectName();
        ProjectPage projectPage = new ProjectSteps().createNewProject(project);
        assertThat(projectPage.isOpened()).as("Project page should be opened").isTrue();
        assertThat(projectPage.getRepositoryName())
                .as("Repository name should be " + expectedRepositoryName)
                .contains(expectedRepositoryName);
        assertThat(projectPage.getProjectName())
                .as("Project name should be " + expectedProjectName)
                .isEqualTo(expectedProjectName);
        assertThat(projectPage.clickSettings().getProject())
                .as("Projects are not equal")
                .isEqualTo(project);
        projects.add(project);
    }

    @AfterMethod
    public void cleanUp() {
        projectsPage.open().isOpened();
        List<String> projectsNames = projects.stream()
                .map(Project::getProjectName)
                .collect(Collectors.toList());
        for (String projectName : projectsNames) {
            projectsPage.deleteProject(projectName);
        }
        projects = null;
    }
}
