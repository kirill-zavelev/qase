package io.qase.app.page;

import io.qase.app.dto.Project;
import io.qase.app.wrapper.Input;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class NewProjectModalPage {

    public static final By CREATE_PROJECT_BTN = By.xpath("//span[text()='Create project']");

    public NewProjectModalPage fillInProjectInfo(Project project) {
        $(By.id("project-name")).shouldBe(visible, enabled).sendKeys(project.getProjectName());
        $(By.id("project-code")).shouldBe(visible, enabled).sendKeys(project.getProjectCode());
//        new Input("Project name").fillIn(project.getProjectName());
//        new Input("Project code").fillIn(project.getProjectCode());
        return this;
    }

    public RepositoryPage saveProject() {
        $(CREATE_PROJECT_BTN).click();
        return new RepositoryPage();
    }
}
