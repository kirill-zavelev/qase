package io.qase.app.api.client;

import io.qase.app.api.dto.request.Case;
import io.qase.app.api.dto.request.Project;
import io.qase.app.api.dto.response.testcase.PostCaseResponse;

import java.util.Map;

public class CaseApiClient extends BaseApiClient {

    private static final String CASE_PATH = "/v1/case/{projectCode}";
    private static final String PROJECT_CODE = "projectCode";

    public PostCaseResponse postAddCase(Case body, String projectCode) {
        return post(CASE_PATH, Map.of(PROJECT_CODE, projectCode), body, PostCaseResponse.class);
    }
}
