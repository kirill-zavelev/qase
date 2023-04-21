package io.qase.app.ui.step;

import io.qase.app.ui.dto.Project;
import io.qase.app.ui.page.ProjectPage;
import io.qase.app.ui.page.ProjectsPage;

public class ProjectSteps {

    public ProjectPage createNewProject(Project project) {
        new ProjectsPage()
                .createNewProject()
                .fillInProjectInfo(project)
                .saveProject();
        return new ProjectPage();
    }
}
