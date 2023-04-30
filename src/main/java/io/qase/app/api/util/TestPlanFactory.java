package io.qase.app.api.util;

import com.github.javafaker.Faker;
import io.qase.app.api.client.TestPlanApiClient;
import io.qase.app.api.dto.request.Case;
import io.qase.app.api.dto.request.Project;
import io.qase.app.api.dto.request.TestPlanApi;

import java.util.List;

public class TestPlanFactory {

    private static TestPlanApi testPlanApi;
    private Faker faker;
    private Long testCaseId;
    private TestCaseFactory testCaseFactory;
    private Project project;

    public TestPlanFactory(Project project) {
        testCaseFactory = new TestCaseFactory();
        this.project = project;
        this.faker = new Faker();
        testCaseId = testCaseFactory.getTestCaseId(project);
        testPlanApi = new TestPlanApi(faker.team().sport(), faker.animal().name(), List.of(testCaseId));
    }

    public TestPlanApi generateTestPlan() {
        new TestPlanApiClient().postAddTestPlan(testPlanApi, project.getCode());
        return testPlanApi;
    }

    public Case getTestCase() {
        return testCaseFactory.getTestCase();
    }
}
