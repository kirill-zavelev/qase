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
import io.qase.app.ui.step.TestPlanSteps;
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
    public void createTestPlanWithRequiredFields() {
        Case expectedTestCase = createCase();

        final String testPlanCreatedMsg = "Test plan was created successfully!";
        String actualTestPlanTitle = new TestPlanSteps()
                .createTestPlanWithRequiredFields(expectedProject, expectedTestPlan, expectedTestCase)
                .getTestPlanTitle();
        assertThat(new TestPlanPage().getAlertMessage())
                .as("Message should be " + testPlanCreatedMsg)
                .isEqualTo("Test plan was created successfully!");
        assertThat(actualTestPlanTitle)
                .as("Title should be " + expectedTestPlan.getTitle())
                .isEqualTo(expectedTestPlan.getTitle());
        assertThat(new TestPlanPage().getTestCaseTitle())
                .as("Test case title should be " + expectedTestCase.getTitle())
                .isEqualTo(expectedTestCase.getTitle());
    }

    @Test
    public void createTestPlanWithAllFields() {
        Case expectedTestCase = createCase();

        final String testPlanCreatedMsg = "Test plan was created successfully!";
        TestPlan actualTestPlan = new TestPlanSteps()
                .createTestPlanWithAllFields(expectedProject, expectedTestPlan, expectedTestCase)
                .getTestPlan();
        assertThat(new TestPlanPage().getAlertMessage())
                .as("Message should be " + testPlanCreatedMsg)
                .isEqualTo("Test plan was created successfully!");
        assertThat(actualTestPlan)
                .as(actualTestPlan + " should be equal to " + expectedTestPlan)
                .isEqualTo(expectedTestPlan);
        assertThat(new TestPlanPage().getTestCaseTitle())
                .as("Test case title should be " + expectedTestCase.getTitle())
                .isEqualTo(expectedTestCase.getTitle());
    }

    @Test
    public void checkTestPlanUpdate() {
        Case expectedTestCase = createCase();
        Case expectedTestCaseForUpdate = new Case(new Faker().team().name());
        new CaseApiClient().postAddCase(expectedTestCaseForUpdate, expectedProject.getCode());
        TestPlan expectedTestPlanForUpdate = new TestPlan(faker.team().name(), faker.team().state());

        final String testPlanEditedMsg = "Test plan was edited successfully!";
        TestPlan actualTestPlan = new TestPlanPage()
                .open(expectedProject.getCode())
                .clickCreatePlan()
                .fillTitle(expectedTestPlan)
                .fillDescription(expectedTestPlan)
                .clickAddCases()
                .selectCase(expectedTestCase)
                .clickSave()
                .clickEdit(expectedTestPlan)
                .fillTitle(expectedTestPlanForUpdate)
                .fillDescription(expectedTestPlanForUpdate)
                .clickAddCases()
                .selectCase(expectedTestCase)
                .selectCase(expectedTestCaseForUpdate)
                .clickSave()
                .getTestPlan();
        assertThat(new TestPlanPage().getAlertMessage())
                .as("Message should be " + testPlanEditedMsg)
                .isEqualTo("Test plan was edited successfully!");
        assertThat(actualTestPlan)
                .as(actualTestPlan + " should be equal to " + expectedTestPlanForUpdate)
                .isEqualTo(expectedTestPlanForUpdate);
        assertThat(new TestPlanPage().getTestCaseTitle())
                .as("Test case title should be " + expectedTestCaseForUpdate.getTitle())
                .isEqualTo(expectedTestCaseForUpdate.getTitle());

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
