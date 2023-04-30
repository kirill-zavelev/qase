package io.qase.app.ui.step;

import io.qase.app.api.dto.request.Case;
import io.qase.app.api.dto.request.Project;
import io.qase.app.ui.dto.TestPlan;
import io.qase.app.ui.page.NewTestPlanPage;
import io.qase.app.ui.page.TestPlanPage;

public class TestPlanSteps {

    public TestPlanPage createTestPlanWithAllFields(Project project, String testPlanTitle, String testPlanDescription,
                                                    Case testCase) {
        new TestPlanPage()
                .open(project.getCode())
                .clickCreatePlan()
                .fillTitle(testPlanTitle)
                .fillDescription(testPlanDescription)
                .clickAddCases()
                .selectCase(testCase)
                .clickDone()
                .clickSave();
        return new TestPlanPage();
    }

    public TestPlanPage createTestPlanWithRequiredFields(Project project, String testPlanTitle, Case testCase) {
        new TestPlanPage()
                .open(project.getCode())
                .clickCreatePlan()
                .fillTitle(testPlanTitle)
                .clickAddCases()
                .selectCase(testCase)
                .clickDone()
                .clickSave();
        return new TestPlanPage();
    }

    public TestPlanPage updateTestPlan(TestPlan testPlanForUpdate, Case testCaseForUpdate) {
        return new NewTestPlanPage()
                .fillTitle(testPlanForUpdate.getTitle())
                .fillDescription(testPlanForUpdate.getDescription())
                .clickAddCases()
                .selectCase(testCaseForUpdate)
                .clickDone()
                .clickSave()
                .clickView(testPlanForUpdate.getTitle());
    }
}
