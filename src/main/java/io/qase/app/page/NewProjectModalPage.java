package io.qase.app.page;

import io.qase.app.dto.Project;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class NewProjectModalPage {

    private static final By CREATE_PROJECT_BTN = By.xpath("//span[text()='Create project']");
    private static final By PROJECT_NAME_INPUT = By.id("project-name");
    private static final By PROJECT_CODE_INPUT = By.id("project-code");
    private static final By DESCRIPTION_INPUT = By.id("description-area");

    public NewProjectModalPage fillInProjectInfo(Project project) {
        $(PROJECT_NAME_INPUT).shouldBe(visible, enabled).sendKeys(project.getProjectName());
        $(DESCRIPTION_INPUT).sendKeys(project.getDescription());
        $(PROJECT_CODE_INPUT).clear();
        $(PROJECT_CODE_INPUT).sendKeys(project.getProjectCode());
        return this;
    }

    public RepositoryPage saveProject() {
        $(CREATE_PROJECT_BTN).click();
        return new RepositoryPage();
    }
}
