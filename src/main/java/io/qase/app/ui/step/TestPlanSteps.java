package io.qase.app.ui.step;

import io.qase.app.api.dto.request.Case;
import io.qase.app.api.dto.request.Project;
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
}
