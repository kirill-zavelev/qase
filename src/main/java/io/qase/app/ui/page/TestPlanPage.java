package io.qase.app.ui.page;

import com.codeborne.selenide.Selenide;
import io.qase.app.ui.dto.TestPlan;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$x;

@Log4j2
public class TestPlanPage {

    private static final By TITLE = By.xpath("//h1");
    private static final By DESCRIPTION = By.xpath("//div[@class='lAiioy']//p");
    private static final By VIEW_BTN = By.xpath("//a[text()='View']");
    private static final By EDIT_BTN = By.xpath("//a[text()='Edit']");
    private static final By CREATE_BTN = By.id("createButton");
    private static final By DELETE_BTN = By.xpath("//li[text()='Delete']");
    private static final By DELETE_CONFIRMATION_BTN = By.xpath("//span[text()='Delete plan']");
    private static final By ALERT_LOCATOR = By.xpath("//div[@role='alert']//span//span");
    private static final By EMPTY_PLANS_LOCATOR = By.xpath("//div[@class='npCN4H']");
    private static final String PLAN_CONTEXT_MENU = "//a[text()='%s']//ancestor::tbody//button";

    public TestPlanPage open(String projectCode) {
        Selenide.open("/plan/" + projectCode);
        return this;
    }

    public NewTestPlanPage clickCreatePlan() {
        $(CREATE_BTN).shouldBe(visible, enabled).click();
        return new NewTestPlanPage();
    }

    public TestPlanPage clickView(String testPlanTitle) {
        openTestPlanContextMenu(testPlanTitle);
        $(VIEW_BTN).shouldBe(visible, enabled).click();
        log.info("Details of {} are displayed", testPlanTitle);
        return this;
    }

    public NewTestPlanPage clickEdit(String testPlanTitle) {
        openTestPlanContextMenu(testPlanTitle);
        $(EDIT_BTN).shouldBe(visible, enabled).click();
        log.info("Edit page of {} is displayed", testPlanTitle);
        return new NewTestPlanPage();
    }

    public TestPlanPage clickDelete(String testPlanTitle) {
        openTestPlanContextMenu(testPlanTitle);
        $(DELETE_BTN).shouldBe(visible, enabled).click();
        $(DELETE_CONFIRMATION_BTN).shouldBe(visible, enabled).click();
        log.info("Test plan {} was deleted", testPlanTitle);
        return this;
    }

    public TestPlanPage openTestPlanContextMenu(String testPlanTitle) {
        $(By.xpath(String.format(PLAN_CONTEXT_MENU, testPlanTitle))).shouldBe(visible, enabled).click();
        log.info("Context menu of {} is displayed", testPlanTitle);
        return this;
    }

    public String getTestPlanTitle() {
        log.info("Title of Test Plan is {}", $(TITLE).getText());
        return $(TITLE).getText();
    }

    public TestPlan getTestPlan() {
        log.info("Test Plan is: {}, {}", $(TITLE).getText(), $(DESCRIPTION).getText());
        return new TestPlan($(TITLE).getText(), $(DESCRIPTION).getText());
    }

    public String getAlertMessage() {
        log.info("Alert message is {}", $(ALERT_LOCATOR).getText());
        return $(ALERT_LOCATOR).shouldBe(visible).getText();
    }

    public String getEmptyTestPlansMessage() {
        log.info("Empty Test Plans message is {}", $(EMPTY_PLANS_LOCATOR).getText());
        return $(EMPTY_PLANS_LOCATOR).shouldBe(visible).getText();
    }

    public List<String> getTestCasesTitles() {
        $(By.id("tab-test-cases")).shouldBe(visible, enabled).click();
        List<String> testCasesTitles = $$x("//div[@class='testcase-title']//p")
                .shouldHave(sizeGreaterThan(0))
                .texts();
        log.info("Test cases titles are: {}", testCasesTitles);
        return testCasesTitles;
    }
}
