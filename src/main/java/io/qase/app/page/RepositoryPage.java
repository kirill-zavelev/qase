package io.qase.app.page;

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
                .projectName(getProjectInfo("Project name"))
                .projectCode(getProjectInfo("Project code"))
                .build();
    }

    private void clickSettings() {
        $(SETTINGS_LOCATOR).shouldBe(visible, enabled).click();
    }


}
