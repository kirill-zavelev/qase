package io.qase.app.ui;

import com.github.javafaker.Faker;
import io.qase.app.api.client.CaseApiClient;
import io.qase.app.api.client.ProjectApiClient;
import io.qase.app.api.dto.request.Case;
import io.qase.app.api.dto.request.Project;
import io.qase.app.api.dto.response.singleproject.PostProjectResponse;
import io.qase.app.api.dto.response.testcase.PostCaseResponse;
import io.qase.app.ui.page.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestPlanTest {

    private Faker faker;
    private Project expectedProject;
    private List<Project> projects;
    private ProjectsPage projectsPage;

    @BeforeClass
    public void setUp() {
        projects = new ArrayList<>();
        faker = new Faker();
        projectsPage = new LoginPage().open().loginWithValidUser();
        expectedProject = Project.builder()
                .title(faker.name().title())
                .code(faker.name().firstName().toUpperCase())
                .description(faker.animal().name())
                .access("none")
                .build();
    }

    private void createProject() {
        PostProjectResponse createdProject = new ProjectApiClient().postAddProject(expectedProject);
        assertThat(createdProject.isStatus()).as("Status should be true").isTrue();
        assertThat(createdProject.getResult().getCode())
                .as("Status should be true")
                .isEqualTo(expectedProject.getCode());
    }

    private long createCaseAndGetId() {
        createProject();

        PostCaseResponse createdCase = new CaseApiClient()
                .postAddCase(new Case(faker.team().name()), expectedProject.getCode());
        assertThat(createdCase.isStatus()).as("Status should be true").isTrue();
        return createdCase.getResult().getId();
    }

    @Test
    public void createTestPlan() {
        createProject();

        Case case1 = new Case("UI testCase");

        PostCaseResponse createdCase = new CaseApiClient()
                .postAddCase(case1, expectedProject.getCode());
        assertThat(createdCase.isStatus()).as("Status should be true").isTrue();

        new TestPlanPage()
                .open(expectedProject.getCode())
                .clickCreatePlan()
                .fillTitle("UI test")
                .clickAddCases()
                .selectCase(case1.getTitle())
                .clickCreatePlan();
        System.out.println();
    }
}
