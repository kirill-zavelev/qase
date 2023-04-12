package io.qase.app.ui;

import com.github.javafaker.Faker;
import io.qase.app.api.client.CaseApiClient;
import io.qase.app.api.client.ProjectApiClient;
import io.qase.app.api.dto.request.Case;
import io.qase.app.api.dto.request.Project;
import io.qase.app.api.dto.response.singleproject.GetProjectResponse;
import io.qase.app.api.dto.response.singleproject.PostProjectResponse;
import io.qase.app.api.dto.response.testcase.PostCaseResponse;
import io.qase.app.ui.dto.TestPlan;
import io.qase.app.ui.page.*;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestPlanTest {

    private Faker faker;
    private Project expectedProject;
    private Case expectedCase;
    private TestPlan expectedTestPlan;
    private List<Project> projects;
    private ProjectsPage projectsPage;

    @BeforeClass
    public void setUp() {
        projects = new ArrayList<>();
        faker = new Faker();
        projectsPage = new LoginPage().open().loginWithValidUser();
        expectedProject = Project.builder()
                .title(faker.name().title())
                .code(faker.name().firstName().toUpperCase())
                .description(faker.animal().name())
                .access("none")
                .build();
        expectedCase = new Case(faker.company().profession());
        expectedTestPlan = new TestPlan(faker.team().name(), faker.team().state());
    }

    private void createProject() {
        PostProjectResponse createdProject = new ProjectApiClient().postAddProject(expectedProject);
        assertThat(createdProject.isStatus()).as("Status should be true").isTrue();
        assertThat(createdProject.getResult().getCode())
                .as("Status should be true")
                .isEqualTo(expectedProject.getCode());
    }

    private Case createCase() {
        createProject();

        PostCaseResponse createdCase = new CaseApiClient()
                .postAddCase(expectedCase, expectedProject.getCode());
        assertThat(createdCase.isStatus()).as("Status should be true").isTrue();
        return expectedCase;
    }

    @Test
    public void createTestPlan() {
        Case testCase = createCase();

        String actualTestPlanTitle = new TestPlanPage()
                .open(expectedProject.getCode())
                .clickCreatePlan()
                .fillTitle(expectedTestPlan)
                .clickAddCases()
                .selectCase(testCase)
                .clickCreatePlan()
                .clickView(expectedTestPlan)
                        .getTestPlanTitle();
        Assertions.assertThat(new TestPlanPage().getAlertMessage()).isEqualTo("Test plan was created successfully!");
        Assertions.assertThat(actualTestPlanTitle).isEqualTo(expectedTestPlan.getTitle());
        System.out.println();
    }

    @AfterClass
    public void cleanUp() {
        new ProjectApiClient().deleteProject(expectedProject.getCode());

        GetProjectResponse actGetProjectResponse = new ProjectApiClient()
                .getProject(expectedProject.getCode(), HttpStatus.SC_NOT_FOUND);
        assertThat(actGetProjectResponse.isStatus())
                .as("Status should be false")
                .isFalse();
    }
}
