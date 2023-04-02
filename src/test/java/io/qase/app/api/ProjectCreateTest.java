package io.qase.app.api;

import com.github.javafaker.Faker;
import io.qase.app.api.client.ProjectApiClient;
import io.qase.app.api.dto.request.Project;
import io.qase.app.api.dto.response.multipleprojects.GetProjectsResponse;
import io.qase.app.api.dto.response.singleproject.GetProjectResponse;
import io.qase.app.api.dto.response.singleproject.PostProjectResponse;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectCreateTest {

    private Faker faker;
    private Project expectedProject;
    private List<Project> projects;

    @BeforeClass
    public void setUp() {
        projects = new ArrayList<>();
        faker = new Faker();
        expectedProject = Project.builder()
                .title(faker.name().title())
                .code(faker.animal().name().toUpperCase())
                .description(faker.animal().name())
                .access("none")
                .build();
    }

    @Test
    public void checkProjectCreation() {
        createProject();

        GetProjectResponse actGetProjectResponse = new ProjectApiClient().getProject(expectedProject.getCode(), HttpStatus.SC_OK);
        assertThat(actGetProjectResponse.getResult().getTitle())
                .as("Title should be " + expectedProject.getTitle())
                .isEqualTo(expectedProject.getTitle());
        assertThat(actGetProjectResponse.getResult().getCode())
                .as("Code should be " + expectedProject.getCode())
                .isEqualTo(expectedProject.getCode());
        assertThat(actGetProjectResponse.isStatus())
                .as("Status should be true")
                .isTrue();

        GetProjectsResponse actGetProjectsResponse = new ProjectApiClient().getAllProjects();
        assertThat(actGetProjectsResponse.isStatus())
                .as("Status should be true")
                .isTrue();
        assertThat(actGetProjectsResponse.getResult().getEntities())
                .as("List should contain " + actGetProjectResponse.getResult())
                .contains(actGetProjectResponse.getResult());
        projects.add(expectedProject);
    }

    @Test
    public void checkDeleteProject() {
        createProject();

        GetProjectResponse actGetProjectResponse = new ProjectApiClient().deleteProject(expectedProject.getCode());
        assertThat(actGetProjectResponse.isStatus())
                .as("Status should be true")
                .isTrue();

        actGetProjectResponse = new ProjectApiClient().getProject(expectedProject.getCode(), HttpStatus.SC_NOT_FOUND);
        assertThat(actGetProjectResponse.isStatus())
                .as("Status should be false")
                .isFalse();
        GetProjectsResponse actGetProjectsResponse = new ProjectApiClient().getAllProjects();
        assertThat(actGetProjectsResponse.getResult().getEntities())
                .as("List should contain " + actGetProjectResponse.getResult())
                .doesNotContain(actGetProjectResponse.getResult());
    }

    private void createProject() {
        PostProjectResponse createdProject = new ProjectApiClient().postAddProject(expectedProject);
        assertThat(createdProject.isStatus()).as("Status should be true").isTrue();
        assertThat(createdProject.getResult().getCode())
                .as("Status should be true")
                .isEqualTo(expectedProject.getCode());
    }

    @AfterClass
    public void cleanUp() {
        for (Project project:projects) {
            new ProjectApiClient().deleteProject(project.getCode());
        }
    }
}
