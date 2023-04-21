package io.qase.app.ui.page;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProjectsPage {

    private static final String TITLE = "Projects";
    private static final By TITLE_LOCATOR = By.xpath("//h1");
    private static final By NEW_PROJECT_BTN = By.id("createButton");
    private static final String DROP_DOWN = "//a[text()='%s']//ancestor::tr//a[contains(@class, 'btn-dropdown')]";
    private static final String DELETE_ITEM = "//a[text()='%s']//ancestor::tr//button[text()='Delete']";
    private static final By DELETE_CONFIRMATION_BTN = By.xpath("//span[text()='Delete project']");

    public ProjectsPage open() {
        Selenide.open("/projects");
        return this;
    }

    public boolean isOpened() {
        return $(TITLE_LOCATOR).shouldBe(visible).getText().equals(TITLE);
    }

    public NewProjectModalPage createNewProject() {
        $(NEW_PROJECT_BTN).shouldBe(enabled).click();
        return new NewProjectModalPage();
    }

    public List<String> getAllProjectsNames() {
        return $$(By.xpath("//a[@class='defect-title']")).texts();
    }

    public ProjectsPage deleteProject(String projectName) {
        $(By.xpath(String.format(DROP_DOWN, projectName))).click();
        $(By.xpath(String.format(DELETE_ITEM, projectName))).click();
        $(DELETE_CONFIRMATION_BTN).click();
        return this;
    }
}
