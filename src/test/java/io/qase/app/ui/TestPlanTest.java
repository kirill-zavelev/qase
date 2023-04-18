package io.qase.app.ui;

import com.github.javafaker.Faker;
import io.qase.app.api.client.ProjectApiClient;
import io.qase.app.api.dto.request.Case;
import io.qase.app.api.dto.request.Project;
import io.qase.app.api.util.ProjectFactory;
import io.qase.app.api.util.TestCaseFactory;
import io.qase.app.ui.dto.TestPlan;
import io.qase.app.ui.page.LoginPage;
import io.qase.app.ui.page.NewTestPlanPage;
import io.qase.app.ui.page.TestPlanPage;
import io.qase.app.ui.step.TestPlanSteps;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestPlanTest extends BaseTest {

    private Faker faker;
    private Project expectedProject;
    private TestPlan expectedTestPlan;

    @BeforeMethod
    public void setUp() {
        faker = new Faker();
        new LoginPage().open().loginWithValidUser();
        expectedProject = new ProjectFactory().generateProject();
        expectedTestPlan = new TestPlan(faker.team().name(), faker.team().state());
    }

    @Test
    public void checkTestPlanCreationWithRequiredFields() {
        Case expectedTestCase = new TestCaseFactory().generateTestCase(expectedProject);

        final String testPlanCreatedMsg = "Test plan was created successfully!";
        String actualTestPlanTitle = new TestPlanSteps()
                .createTestPlanWithRequiredFields(expectedProject, expectedTestPlan, expectedTestCase)
                .clickView(expectedTestPlan)
                .getTestPlanTitle();
        assertThat(new TestPlanPage().getAlertMessage())
                .as("Message should be " + testPlanCreatedMsg)
                .isEqualTo("Test plan was created successfully!");
        assertThat(actualTestPlanTitle)
                .as("Title should be " + expectedTestPlan.getTitle())
                .isEqualTo(expectedTestPlan.getTitle());
//        assertThat(new TestPlanPage().getTestCasesTitles())
//                .as("Test case title should be " + expectedTestCase.getTitle())
//                .isEqualTo(expectedTestCase.getTitle());
    }

    @Test
    public void checkTestPlanCreationWithoutTitle() {
        Case expectedTestCase = new TestCaseFactory().generateTestCase(expectedProject);
        expectedTestPlan.setTitle("");

        final String expectedInlineAlertMsg = "Please fill in this field.";
        final String expectedAlertMsg = "The cases field is required.";
        new TestPlanSteps()
                .createTestPlanWithRequiredFields(expectedProject, expectedTestPlan, expectedTestCase);
        String actualInlineAlertMsg = new NewTestPlanPage().getAlertMessage();
        assertThat(actualInlineAlertMsg)
                .as("Alert should be " + expectedInlineAlertMsg)
                .isEqualTo(expectedInlineAlertMsg);
        expectedTestPlan.setTitle(faker.name().firstName());
        String actualAlertMsg = new NewTestPlanPage()
                .fillTitle(expectedTestPlan)
                .clickAddCases()
                .selectCase(expectedTestCase)
                .clickDone()
                .clickSave()
                .getAlertMessage();
        assertThat(actualAlertMsg)
                .as("Alert should be " + expectedAlertMsg)
                .isEqualTo(expectedAlertMsg);
    }

    @Test
    public void checkTestPlanDelete() {
        Case expectedTestCase = new TestCaseFactory().generateTestCase(expectedProject);

        final String expectedAlertMsg = "Looks like you don’t have any fields yet.";
        String actualAlertMsg = new TestPlanSteps().deleteTestPlan(expectedProject, expectedTestPlan, expectedTestCase).getEmptyTestPlansMessage();
        assertThat(actualAlertMsg)
                .as("Alert should be " + expectedAlertMsg)
                .isEqualTo(expectedAlertMsg);
    }

    @Test
    public void checkTestPlanCreationWithAllFields() {
        Case expectedTestCase = new TestCaseFactory().generateTestCase(expectedProject);

        final String testPlanCreatedMsg = "Test plan was created successfully!";
        TestPlan actualTestPlan = new TestPlanSteps()
                .createTestPlanWithAllFields(expectedProject, expectedTestPlan, expectedTestCase)
                .clickView(expectedTestPlan)
                .getTestPlan();
        assertThat(new TestPlanPage().getAlertMessage())
                .as("Message should be " + testPlanCreatedMsg)
                .isEqualTo("Test plan was created successfully!");
        assertThat(actualTestPlan)
                .as(actualTestPlan + " should be equal to " + expectedTestPlan)
                .isEqualTo(expectedTestPlan);
        assertThat(new TestPlanPage().getTestCasesTitles())
                .as("Test case title should be " + expectedTestCase.getTitle())
                .isEqualTo(expectedTestCase.getTitle());
    }

    @Test
    public void checkTestPlanUpdate() {
        Case expectedTestCase = new TestCaseFactory().generateTestCase(expectedProject);
        Case expectedTestCaseForUpdate = new TestCaseFactory().generateTestCase(expectedProject);
        TestPlan expectedTestPlanForUpdate = new TestPlan(faker.team().name(), faker.team().state());

        new TestPlanSteps()
                .createTestPlanWithAllFields(expectedProject, expectedTestPlan, expectedTestCase);
        TestPlan actualTestPlan = new TestPlanSteps()
                .updateTestPlan(expectedTestPlan, expectedTestPlanForUpdate, expectedTestCaseForUpdate)
                .clickView(expectedTestPlanForUpdate)
                .getTestPlan();
        assertThat(actualTestPlan)
                .as(actualTestPlan + " should be equal to " + expectedTestPlanForUpdate)
                .isEqualTo(expectedTestPlanForUpdate);
        assertThat(new TestPlanPage().getTestCasesTitles())
                .as("Test case title should be present in the list: " + expectedTestCaseForUpdate.getTitle())
                .contains(expectedTestCaseForUpdate.getTitle());
    }

    @AfterMethod
    public void cleanUp() {
        new ProjectApiClient().deleteProject(expectedProject.getCode());
    }
}
