package io.qase.app.page;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.$;

public class ProjectsPage {

    public static final String TITLE = "Projects";
    private static final By TITLE_LOCATOR = By.xpath("//h1");
    private static final By NEW_PROJECT_BTN = By.id("createButton");

    public boolean isOpened() {
        return $(TITLE_LOCATOR).getText().equals(TITLE);
    }

    public NewProjectModalPage createNewProject() {
        $(NEW_PROJECT_BTN).shouldBe(enabled).click();
        return new NewProjectModalPage();
    }
}
