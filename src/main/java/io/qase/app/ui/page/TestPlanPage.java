package io.qase.app.ui.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.qase.app.ui.dto.TestPlan;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TestPlanPage {

    private static final By TITLE = By.xpath("//h1");
    private static final By DESCRIPTION = By.xpath("//div[@class='lAiioy']//p");
    private static final By VIEW_BTN = By.xpath("//a[text()='View']");
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
        $(By.xpath(String.format(PLAN_CONTEXT_MENU, testPlan.getTitle()))).shouldBe(visible, enabled).click();
        $(VIEW_BTN).shouldBe(visible, enabled).click();
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
        //Test plan was created successfully!
    }
}
