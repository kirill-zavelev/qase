package io.qase.app.api.client;

import io.qase.app.api.dto.request.TestPlanApi;
import io.qase.app.api.dto.response.testcase.PostTestPlanResponse;

import java.util.Map;

public class TestPlanApiClient extends BaseApiClient {

    private static final String TEST_PLAN_PATH = "/v1/plan/{projectCode}";
    private static final String PROJECT_CODE = "projectCode";

    public PostTestPlanResponse postAddTestPlan(TestPlanApi body, String projectCode) {
        return post(TEST_PLAN_PATH, Map.of(PROJECT_CODE, projectCode), body, PostTestPlanResponse.class);
    }
}
