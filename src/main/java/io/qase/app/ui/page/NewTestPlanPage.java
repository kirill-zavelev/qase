package io.qase.app.ui.page;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class NewTestPlanPage {

    private static final String CHECKBOX = "//p[text()='%s']//ancestor::div[@class='suitecase']//div[@class='checkbox']";

    public NewTestPlanPage fillTitle(String title) {
        $(By.id("title")).shouldBe(visible, enabled).sendKeys(title);
        $(By.xpath("//p[@class='gYZSEd']")).shouldBe(visible, enabled).sendKeys(title);
        return this;
    }

    public NewTestPlanPage clickAddCases() {
        $(By.id("edit-plan-add-cases-button")).shouldBe(enabled).click();
        return this;
    }

    public NewTestPlanPage selectCase(String caseTitle) {
        $(By.id("suite-0")).shouldBe(visible, enabled).click();
        $(By.xpath(String.format(CHECKBOX, caseTitle))).shouldBe(enabled).click();
        $(By.xpath("//span[text()='Done']")).shouldBe(visible, enabled).click();
        return this;
    }

    public TestPlanPage clickCreatePlan() {
        $(By.id("save-plan")).shouldBe(visible, enabled).click();
        return new TestPlanPage();
    }
}
