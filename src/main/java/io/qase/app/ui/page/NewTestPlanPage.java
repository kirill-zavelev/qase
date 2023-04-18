package io.qase.app.ui.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qase.app.api.dto.request.Case;
import io.qase.app.ui.dto.TestPlan;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class NewTestPlanPage extends BasePage {

    private static final String CASE_CHECKBOX = "//p[text()='%s']//ancestor::div[@class='suitecase']" +
            "//div[@class='checkbox']";
    private static final By TITLE_INPUT = By.id("title");
    private static final By DESCRIPTION_INPUT = By.xpath("//label[text()='Description']" +
            "//ancestor::div[@class='form-group qaOPP6']//div[@class='toastui-editor-ww-container']//p");
    private static final By ADD_CASES_BTN = By.id("edit-plan-add-cases-button");
    private static final By DONE_BTN = By.xpath("//span[text()='Done']");
    private static final By SUITE_BTN = By.id("suite-0");
    private static final By SAVE_PLAN_BTN = By.id("save-plan");

    public NewTestPlanPage fillTitle(TestPlan testPlan) {
        clearFieldAndFill(TITLE_INPUT, testPlan.getTitle());
        return this;
    }

    public NewTestPlanPage fillDescription(TestPlan testPlan) {
        clearFieldAndFill(DESCRIPTION_INPUT, testPlan.getDescription());
        return this;
    }

    public NewTestPlanPage clickAddCases() {
        $(ADD_CASES_BTN).shouldBe(enabled).click();
        return this;
    }

    public NewTestPlanPage selectCase(Case testCase) {
        $(SUITE_BTN).shouldBe(visible, enabled).click();
        $(By.xpath(String.format(CASE_CHECKBOX, testCase.getTitle()))).shouldBe(enabled).click();
        return this;
    }

    public NewTestPlanPage clickDone() {
        $(DONE_BTN).shouldBe(visible, enabled).click();
        return this;
    }

    public TestPlanPage clickSave() {
        $(SAVE_PLAN_BTN).shouldBe(visible, enabled).click();
        return new TestPlanPage();
    }

    public String getAlertMessage() {
        return Selenide.executeJavaScript("return arguments[0].validationMessage;", $(TITLE_INPUT));
    }
}
