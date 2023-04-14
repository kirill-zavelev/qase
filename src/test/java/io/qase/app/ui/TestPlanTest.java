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
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestPlanTest extends BaseTest {

    private Faker faker;
    private Project expectedProject;
    private Case testCase;
    private TestPlan expectedTestPlan;
    private List<Project> projects;
    private ProjectsPage projectsPage;

    @BeforeMethod
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
        testCase = new Case(faker.company().profession());
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
                .postAddCase(testCase, expectedProject.getCode());
        assertThat(createdCase.isStatus()).as("Status should be true").isTrue();
        return testCase;
    }

    @Test
    public void createTestPlanWithReuiredFields() {
        Case expectedTestCase = createCase();

        final String testPlanCreatedMsg = "Test plan was created successfully!";
        String actualTestPlanTitle = new TestPlanPage()
                .open(expectedProject.getCode())
                .clickCreatePlan()
                .fillTitle(expectedTestPlan)
                .clickAddCases()
                .selectCase(expectedTestCase)
                .clickCreatePlan()
                .clickView(expectedTestPlan)
                .getTestPlanTitle();
        assertThat(new TestPlanPage().getAlertMessage())
                .as("Message should be " + testPlanCreatedMsg)
                .isEqualTo("Test plan was created successfully!");
        assertThat(actualTestPlanTitle)
                .as("Title should be " + expectedTestPlan.getTitle())
                .isEqualTo(expectedTestPlan.getTitle());
        String actualTestCaseTitle = new TestPlanPage().getTestCaseTitle();
        assertThat(actualTestCaseTitle)
                .as("Test case title should be " + expectedTestCase.getTitle())
                .isEqualTo(expectedTestCase.getTitle());
    }

    @Test
    public void createTestPlanWithAllFields() {
        Case expectedTestCase = createCase();

        final String testPlanCreatedMsg = "Test plan was created successfully!";
        TestPlan actualTestPlan = new TestPlanPage()
                .open(expectedProject.getCode())
                .clickCreatePlan()
                .fillTitle(expectedTestPlan)
                .fillDescription(expectedTestPlan)
                .clickAddCases()
                .selectCase(expectedTestCase)
                .clickCreatePlan()
                .clickView(expectedTestPlan)
                .getTestPlan();
        assertThat(new TestPlanPage().getAlertMessage())
                .as("Message should be " + testPlanCreatedMsg)
                .isEqualTo("Test plan was created successfully!");
        assertThat(actualTestPlan)
                .as(actualTestPlan + " should be equal to " + expectedTestPlan)
                .isEqualTo(expectedTestPlan);
        String actualTestCaseTitle = new TestPlanPage().getTestCaseTitle();
        assertThat(actualTestCaseTitle)
                .as("Test case title should be " + expectedTestCase.getTitle())
                .isEqualTo(expectedTestCase.getTitle());
    }

    @AfterMethod
    public void cleanUp() {
        new ProjectApiClient().deleteProject(expectedProject.getCode());

        GetProjectResponse actGetProjectResponse = new ProjectApiClient()
                .getProject(expectedProject.getCode(), HttpStatus.SC_NOT_FOUND);
        assertThat(actGetProjectResponse.isStatus())
                .as("Status should be false")
                .isFalse();
    }
}
