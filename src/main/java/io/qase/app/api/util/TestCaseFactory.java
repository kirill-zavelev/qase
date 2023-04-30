package io.qase.app.api.util;

import com.github.javafaker.Faker;
import io.qase.app.api.client.CaseApiClient;
import io.qase.app.api.dto.request.Case;
import io.qase.app.api.dto.request.Project;
import lombok.Data;

@Data
public class TestCaseFactory {

    private Case testCase;
    private Faker faker;

    public TestCaseFactory() {
        this.faker = new Faker();
        testCase = new Case(faker.company().profession());
    }

    public Case generateTestCase(Project project) {
        new CaseApiClient().postAddCase(testCase, project.getCode());
        return testCase;
    }

    public Long getTestCaseId(Project project) {
        return new CaseApiClient().postAddCase(testCase, project.getCode()).getResult().getId();
    }
}
