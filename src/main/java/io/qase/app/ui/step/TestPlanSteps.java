package io.qase.app.ui.step;

import io.qase.app.api.dto.request.Case;
import io.qase.app.api.dto.request.Project;
import io.qase.app.ui.dto.TestPlan;
import io.qase.app.ui.page.TestPlanPage;

public class TestPlanSteps {

    public TestPlanPage createTestPlanWithAllFields(Project project, TestPlan testPlan, Case testCase) {
        new TestPlanPage()
                .open(project.getCode())
                .clickCreatePlan()
                .fillTitle(testPlan)
                .fillDescription(testPlan)
                .clickAddCases()
                .selectCase(testCase)
                .clickDone()
                .clickSave();
        return new TestPlanPage();
    }

    public TestPlanPage createTestPlanWithRequiredFields(Project project, TestPlan testPlan, Case testCase) {
        new TestPlanPage()
                .open(project.getCode())
                .clickCreatePlan()
                .fillTitle(testPlan)
                .clickAddCases()
                .selectCase(testCase)
                .clickDone()
                .clickSave();
        return new TestPlanPage();
    }

    public TestPlanPage deleteTestPlan(Project project, TestPlan testPlan, Case testCase) {
        return new TestPlanPage()
                .open(project.getCode())
                .clickCreatePlan()
                .fillTitle(testPlan)
                .clickAddCases()
                .selectCase(testCase)
                .clickDone()
                .clickSave()
                .clickDelete(testPlan);
    }

    public TestPlanPage updateTestPlan(TestPlan testPlan, TestPlan updatedTestPlan, Case updatedTestCase) {
        new TestPlanPage()
                .clickEdit(testPlan)
                .fillTitle(updatedTestPlan)
                .fillDescription(updatedTestPlan)
                .clickAddCases()
                .selectCase(updatedTestCase)
                .clickDone()
                .clickSave();
        return new TestPlanPage();
    }
}
