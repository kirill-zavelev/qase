package io.qase.app.ui;

import com.github.javafaker.Faker;
import io.qase.app.api.client.ProjectApiClient;
import io.qase.app.api.dto.request.Case;
import io.qase.app.api.dto.request.Project;
import io.qase.app.api.dto.request.TestPlanApi;
import io.qase.app.api.util.ProjectFactory;
import io.qase.app.api.util.TestCaseFactory;
import io.qase.app.api.util.TestPlanFactory;
import io.qase.app.ui.dto.TestPlan;
import io.qase.app.ui.page.LoginPage;
import io.qase.app.ui.page.NewTestPlanPage;
import io.qase.app.ui.page.TestPlanPage;
import io.qase.app.ui.step.TestPlanSteps;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
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
                .createTestPlanWithRequiredFields(expectedProject, expectedTestPlan.getTitle(), expectedTestCase)
                .clickView(expectedTestPlan.getTitle())
                .getTestPlanTitle();
        assertThat(new TestPlanPage().getAlertMessage())
                .as("Message should be " + testPlanCreatedMsg)
                .isEqualTo("Test plan was created successfully!");
        assertThat(actualTestPlanTitle)
                .as("Title should be " + expectedTestPlan.getTitle())
                .isEqualTo(expectedTestPlan.getTitle());
        assertThat(new TestPlanPage().getTestCasesTitles())
                .as("Test case title should be " + expectedTestCase.getTitle())
                .contains(expectedTestCase.getTitle());
    }

    @Test
    public void checkTestPlanCreationWithoutRequiredFields() {
        Case expectedTestCase = new TestCaseFactory().generateTestCase(expectedProject);
        expectedTestPlan.setTitle("");

        final String expectedInlineAlertMsg = "Please fill in this field.";
        final String expectedAlertMsg = "The cases field is required.";
        new TestPlanSteps()
                .createTestPlanWithRequiredFields(expectedProject, expectedTestPlan.getTitle(), expectedTestCase);
        String actualInlineAlertMsg = new NewTestPlanPage().getInlineAlertMessage();
        assertThat(actualInlineAlertMsg)
                .as("Alert should be " + expectedInlineAlertMsg)
                .isEqualTo(expectedInlineAlertMsg);
        expectedTestPlan.setTitle(faker.name().firstName());
        String actualAlertMsg = new NewTestPlanPage()
                .fillTitle(expectedTestPlan.getTitle())
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
        TestPlanApi testPlan = new TestPlanFactory(expectedProject).generateTestPlan();

        String actualAlertMsg = new TestPlanPage()
                .open(expectedProject.getCode())
                .clickDelete(testPlan.getTitle())
                .getEmptyTestPlansMessage();
        final String expectedAlertMsg = "Looks like you don’t have any fields yet.";
        assertThat(actualAlertMsg)
                .as("Alert should be " + expectedAlertMsg)
                .isEqualTo(expectedAlertMsg);
    }

    @Test
    public void checkTestPlanCreationWithAllFields() {
        Case expectedTestCase = new TestCaseFactory().generateTestCase(expectedProject);

        final String testPlanCreatedMsg = "Test plan was created successfully!";
        TestPlan actualTestPlan = new TestPlanSteps()
                .createTestPlanWithAllFields(expectedProject, expectedTestPlan.getTitle(),
                        expectedTestPlan.getDescription(), expectedTestCase)
                .clickView(expectedTestPlan.getTitle())
                .getTestPlan();
        assertThat(new TestPlanPage().getAlertMessage())
                .as("Message should be " + testPlanCreatedMsg)
                .isEqualTo("Test plan was created successfully!");
        assertThat(actualTestPlan)
                .as(actualTestPlan + " should be equal to " + expectedTestPlan)
                .isEqualTo(expectedTestPlan);
        assertThat(new TestPlanPage().getTestCasesTitles())
                .as("Test case title should be " + expectedTestCase.getTitle())
                .contains(expectedTestCase.getTitle());
    }

    @Test
    public void checkTestPlanUpdate() {
        TestPlanFactory testPlanFactory = new TestPlanFactory(expectedProject);
        TestPlanApi testPlan = testPlanFactory.generateTestPlan();
        Case testCaseForUpdate = new TestCaseFactory().generateTestCase(expectedProject);
        TestPlan testPlanForUpdate = new TestPlan(faker.team().name(), faker.team().state());
        TestPlanPage testPlanPage = new TestPlanPage();
        testPlanPage.open(expectedProject.getCode()).clickEdit(testPlan.getTitle());
        TestPlan actualTestPlan = new NewTestPlanPage().fillTitle(testPlanForUpdate.getTitle())
                .fillDescription(testPlanForUpdate.getDescription())
                .clickAddCases()
                .selectCase(testCaseForUpdate)
                .clickDone()
                .clickSave()
                .clickView(testPlanForUpdate.getTitle())
                .getTestPlan();
        assertThat(actualTestPlan)
                .as(actualTestPlan + " should be equal to " + testPlanForUpdate)
                .isEqualTo(testPlanForUpdate);
        assertThat(new TestPlanPage().getTestCasesTitles())
                .as("Test case title should be present in the list: " + testCaseForUpdate.getTitle())
                .contains(testCaseForUpdate.getTitle());
    }

    @Test
    public void checkTestPlanUpdateWithoutRequiredFields() {
        final String expectedInlineAlertMsg = "Please fill in this field.";
        final String expectedAlertMsg = "The cases field is required.";
        TestPlanFactory testPlanFactory = new TestPlanFactory(expectedProject);
        TestPlanApi testPlan = testPlanFactory.generateTestPlan();

        TestPlanPage testPlanPage = new TestPlanPage();
        testPlanPage.open(expectedProject.getCode()).clickEdit(testPlan.getTitle());
        testPlan.setTitle("");
        NewTestPlanPage newTestPlanPage = new NewTestPlanPage();
        newTestPlanPage.fillTitle(testPlan.getTitle()).clickSave();
        assertThat(newTestPlanPage.getInlineAlertMessage())
                .as("Message should be " + expectedInlineAlertMsg)
                .isEqualTo(expectedInlineAlertMsg);
        testPlan.setTitle(faker.name().title());
        String actAlertMessage = newTestPlanPage.clickAddCases().selectCase(testPlanFactory.getTestCase())
                .clickDone()
                .clickSave()
                .getAlertMessage();
        assertThat(actAlertMessage)
                .as("Message should be " + expectedAlertMsg)
                .isEqualTo(expectedAlertMsg);
    }

    @AfterMethod
    public void cleanUp() {
        new ProjectApiClient().deleteProject(expectedProject.getCode());
        log.info("Project {} was deleted", expectedProject);
    }
}
