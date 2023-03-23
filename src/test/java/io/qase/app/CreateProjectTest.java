package io.qase.app;

import io.qase.app.dto.Project;
import io.qase.app.page.LoginPage;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateProjectTest {

    Project project;

    @Test
    public void checkProjectCreation() {
        project = Project.builder()
                .projectName("TEST KIRILL")
                .projectCode(RandomStringUtils.random(1, false, true))
                .build();
        Project actualProject = new LoginPage()
                .open()
                .loginWithValidUser()
                .createNewProject()
                .fillInProjectInfo(project)
                .saveProject()
                .getProject();
        assertThat(actualProject)
                .as("Projects are not equal")
                .isEqualTo(project);
    }
}
