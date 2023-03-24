package io.qase.app.page;

import com.codeborne.selenide.Command;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.commands.PressEnter;
import io.qase.app.dto.Project;
import io.qase.app.wrapper.InputText;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class RepositoryPage {

    public static final By TITLE = By.xpath("//h1");
    public static final By SETTINGS_LOCATOR = By.xpath("//a[@title='Settings']");

    public String getRepositoryName() {
        return $(TITLE).shouldBe(visible).getText();
    }

    public String getProjectInfo(String label) {
        return new InputText(label).getText();
    }

    public Project getProject() {
        clickSettings();
        return Project.builder()
                .projectName($(By.id("project-name")).shouldBe(visible, enabled).getAttribute("value"))
                .projectCode($(By.id("project-code")).shouldBe(visible, enabled).getAttribute("value"))
                .description($(By.id("description-area")).shouldBe(visible, enabled).getText())
                .build();
    }

    public RepositoryPage updateProjectSettings(Project project) {
        $(By.id("project-name")).shouldBe(visible, enabled).clear();
        $(By.id("project-name")).shouldBe(visible, enabled).sendKeys(project.getProjectName());
        $(By.id("project-code")).shouldBe(visible, enabled).clear();
        $(By.id("project-code")).shouldBe(visible, enabled).sendKeys(project.getProjectCode());
        $(By.id("description-area")).shouldBe(visible, enabled).clear();
        $(By.id("description-area")).shouldBe(visible, enabled).sendKeys(project.getDescription());
        $(By.xpath("//span[text()=' Update settings']")).shouldBe(visible, enabled).click();
        return this;
    }

    public RepositoryPage clickDeleteProject() {
        $(By.xpath("//span[text()=' Delete project']")).shouldBe(visible, enabled).click();
        return this;
    }

    public ProjectsPage confirmDeletion() {
        $(By.xpath("//div[@id='modals']//span[text()='Delete project']")).click();
        return new ProjectsPage();
    }

    public String getDeleteAlert() {
        return $(By.xpath("//div[@role='dialog']//small")).shouldBe(visible).getText();
    }

    public String getAlertMessage() {
        return $(By.xpath("//div[@role='alert']//span//span")).shouldBe(visible).getText();
    }

    public RepositoryPage goToRepository() {
        $(By.xpath("//span[text()='Repository']")).shouldBe(visible, enabled).click();
        return this;
    }

    private void clickSettings() {
        $(SETTINGS_LOCATOR).shouldBe(visible, enabled).click();
    }
}
