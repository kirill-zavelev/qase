package io.qase.app.api.util;

import com.github.javafaker.Faker;
import io.qase.app.api.client.ProjectApiClient;
import io.qase.app.api.dto.request.Project;
import io.qase.app.api.dto.response.singleproject.PostProjectResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import static org.assertj.core.api.Assertions.assertThat;

@Data
public class ProjectFactory {

    private Project project;
    private Faker faker;

    public ProjectFactory() {
        this.faker = new Faker();
        project = Project.builder()
                .title(faker.name().title())
                .code(faker.name().firstName().toUpperCase())
                .description(faker.animal().name())
                .access("none")
                .build();
    }

    public Project generateProject() {
        new ProjectApiClient().postAddProject(project);
        return project;
    }
}
