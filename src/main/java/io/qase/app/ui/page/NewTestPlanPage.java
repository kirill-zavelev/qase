package io.qase.app.ui.page;

import io.qase.app.api.dto.request.Case;
import io.qase.app.ui.dto.TestPlan;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class NewTestPlanPage {

    private static final String CASE_CHECKBOX = "//p[text()='%s']//ancestor::div[@class='suitecase']" +
            "//div[@class='checkbox']";
    private static final By TITLE_INPUT = By.id("title");
    private static final By DESCRIPTION_INPUT = By.xpath("//p[@class='gYZSEd']");
    private static final By ADD_CASES_BTN = By.id("edit-plan-add-cases-button");
    private static final By DONE_BTN = By.xpath("//span[text()='Done']");
    private static final By SUITE_BTN = By.id("suite-0");
    private static final By SAVE_PLAN_BTN = By.id("save-plan");

    public NewTestPlanPage fillTitle(TestPlan testPlan) {
        $(TITLE_INPUT).shouldBe(visible, enabled).sendKeys(testPlan.getTitle());
        return this;
    }

    public NewTestPlanPage fillDescription(TestPlan testPlan) {
        $(DESCRIPTION_INPUT).shouldBe(visible, enabled).sendKeys(testPlan.getDescription());
        return this;
    }

    public NewTestPlanPage clickAddCases() {
        $(ADD_CASES_BTN).shouldBe(enabled).click();
        return this;
    }

    public NewTestPlanPage selectCase(Case testCase) {
        $(SUITE_BTN).shouldBe(visible, enabled).click();
        $(By.xpath(String.format(CASE_CHECKBOX, testCase.getTitle()))).shouldBe(enabled).click();
        $(DONE_BTN).shouldBe(visible, enabled).click();
        return this;
    }

    public TestPlanPage clickCreatePlan() {
        $(SAVE_PLAN_BTN).shouldBe(visible, enabled).click();
        return new TestPlanPage();
    }
}
