package io.qase.app.step;

import io.qase.app.dto.Project;
import io.qase.app.page.ProjectPage;
import io.qase.app.page.ProjectsPage;

public class ProjectSteps {

    public ProjectPage createNewProject(Project project) {
        new ProjectsPage()
                .createNewProject()
                .fillInProjectInfo(project)
                .saveProject();
        return new ProjectPage();
    }
}
