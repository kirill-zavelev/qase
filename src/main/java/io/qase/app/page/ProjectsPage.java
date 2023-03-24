package io.qase.app.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

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

    public List<String> getAllProjectsNames() {
//        return $$(By.xpath("//a[@class='defect-title']")).stream()
//                .map(SelenideElement::getText)
//                .collect(Collectors.toList());
        return $$(By.xpath("//a[@class='defect-title']")).texts();
    }
}
