package io.qase.app.ui.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TestPlanPage {

    public TestPlanPage open(String projectCode) {
        Selenide.open("/plan/" + projectCode);
        return this;
    }

    public NewTestPlanPage clickCreatePlan() {
        $(By.id("createButton")).shouldBe(visible, enabled).click();
        return new NewTestPlanPage();
    }
}
