package io.qase.app.api.util;

import com.github.javafaker.Faker;
import io.qase.app.api.client.CaseApiClient;
import io.qase.app.api.dto.request.Case;
import io.qase.app.api.dto.request.Project;
import lombok.Data;

import static org.assertj.core.api.Assertions.assertThat;

@Data
public class TestCaseFactory {

    private static Case testCase;
    private Faker faker;

    public TestCaseFactory() {
        this.faker = new Faker();
        testCase = new Case(faker.company().profession());
    }

    public Case generateTestCase(Project project) {
        new CaseApiClient().postAddCase(testCase, project.getCode());
        return testCase;
    }
}
