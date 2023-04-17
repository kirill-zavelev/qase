package io.qase.app.ui.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.qase.app.ui.dto.TestPlan;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class TestPlanPage {

    private static final By TITLE = By.xpath("//h1");
    private static final By DESCRIPTION = By.xpath("//div[@class='lAiioy']//p");
    private static final By VIEW_BTN = By.xpath("//a[text()='View']");
    private static final By EDIT_BTN = By.xpath("//a[text()='Edit']");
    private static final String PLAN_CONTEXT_MENU = "//a[text()='%s']//ancestor::tbody//button";

    public TestPlanPage open(String projectCode) {
        Selenide.open("/plan/" + projectCode);
        return this;
    }

    public NewTestPlanPage clickCreatePlan() {
        $(By.id("createButton")).shouldBe(visible, enabled).click();
        return new NewTestPlanPage();
    }

    public TestPlanPage clickView(TestPlan testPlan) {
        openTestPlanContextMenu(testPlan);
        $(VIEW_BTN).shouldBe(visible, enabled).click();
        return this;
    }

    public NewTestPlanPage clickEdit(TestPlan testPlan) {
        openTestPlanContextMenu(testPlan);
        $(EDIT_BTN).shouldBe(visible, enabled).click();
        return new NewTestPlanPage();
    }

    public TestPlanPage openTestPlanContextMenu(TestPlan testPlan) {
        $(By.xpath(String.format(PLAN_CONTEXT_MENU, testPlan.getTitle()))).shouldBe(visible, enabled).click();
        return this;
    }

    public String getTestPlanTitle() {
        return $(TITLE).getText();
    }

    public TestPlan getTestPlan() {
        return new TestPlan($(TITLE).getText(), $(DESCRIPTION).getText());
    }

    public String getAlertMessage() {
        return $(By.xpath("//div[@role='alert']//span//span")).shouldBe(visible).getText();
    }

    public String getTestCaseTitle() {
        $(By.id("tab-test-cases")).shouldBe(visible, enabled).click();
        return $x("//div[@class='testcase-title']//p").shouldBe(visible).getText();
    }
}
