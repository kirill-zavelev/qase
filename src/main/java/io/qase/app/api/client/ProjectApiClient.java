package io.qase.app.api.client;

import io.qase.app.api.dto.request.Project;
import io.qase.app.api.dto.response.multipleprojects.GetProjectsResponse;
import io.qase.app.api.dto.response.singleproject.GetProjectResponse;
import io.qase.app.api.dto.response.singleproject.PostProjectResponse;
import org.apache.http.HttpStatus;

import java.util.Map;

public class ProjectApiClient extends BaseApiClient {

    private static final String PROJECT_PATH = "/v1/project";
    private static final String PROJECT_CODE_PATH = "/v1/project/{projectCode}";
    private static final String PROJECT_CODE = "projectCode";

    public PostProjectResponse postAddProject(Project body) {
        return post(PROJECT_PATH, body, PostProjectResponse.class);
    }

    public GetProjectResponse getProject(String projectCode, int statusCode) {
        return get(PROJECT_CODE_PATH, Map.of(PROJECT_CODE, projectCode))
                .then()
                .statusCode(statusCode)
                .extract()
                .body()
                .as(GetProjectResponse.class);
    }

    public GetProjectsResponse getAllProjects() {
        return get(PROJECT_PATH, 100)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(GetProjectsResponse.class);
    }

    public GetProjectResponse deleteProject(String projectCode) {
        return delete(PROJECT_CODE_PATH, Map.of(PROJECT_CODE, projectCode))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(GetProjectResponse.class);
    }
}
