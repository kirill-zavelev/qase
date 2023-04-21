package io.qase.app.page;

import io.qase.app.dto.Project;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class SettingsPage extends BasePage {

    private static final By PROJECT_NAME_INPUT = By.id("project-name");
    private static final By PROJECT_CODE_INPUT = By.id("project-code");
    private static final By DESCRIPTION_INPUT = By.id("description-area");
    private static final By UPDATE_SETTINGS_BTN = By.xpath("//span[text()=' Update settings']");
    private static final By DELETE_PROJECT_BTN = By.xpath("//span[text()=' Delete project']");
    private static final By CONFIRM_DELETE_PROJECT_BTN = By.xpath("//div[@id='modals']//span[text()='Delete project']");
    private static final By DELETE_ALERT = By.xpath("//div[@role='dialog']//small");
    private static final By UPDATE_ALERT = By.xpath("//div[@role='alert']//span//span");
    private static final By REPOSITORY_BTN = By.xpath("//span[text()='Repository']");

    public Project getProject() {
        return Project.builder()
                .projectName($(PROJECT_NAME_INPUT).shouldBe(visible, enabled).getAttribute("value"))
                .projectCode($(PROJECT_CODE_INPUT).shouldBe(visible, enabled).getAttribute("value"))
                .description($(DESCRIPTION_INPUT).shouldBe(visible, enabled).getText())
                .build();
    }

    public SettingsPage updateProjectSettings(Project project) {
        clearFieldAndFill(PROJECT_NAME_INPUT, project.getProjectName());
        clearFieldAndFill(PROJECT_CODE_INPUT, project.getProjectCode());
        clearFieldAndFill(DESCRIPTION_INPUT, project.getDescription());
        $(UPDATE_SETTINGS_BTN).shouldBe(visible, enabled).click();
        return this;
    }

    public SettingsPage clickDeleteProject() {
        $(DELETE_PROJECT_BTN).shouldBe(visible, enabled).click();
        return this;
    }

    public ProjectsPage confirmDeletion() {
        $(CONFIRM_DELETE_PROJECT_BTN).click();
        return new ProjectsPage();
    }

    public String getDeleteAlert() {
        return $(DELETE_ALERT).shouldBe(visible).getText();
    }

    public String getUpdateAlertMessage() {
        return $(UPDATE_ALERT).shouldBe(visible).getText();
    }

    public ProjectPage openProjectPage() {
        $(REPOSITORY_BTN).shouldBe(visible, enabled).click();
        return new ProjectPage();
    }
}
