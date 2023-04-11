package io.qase.app.api;

import io.qase.app.api.client.CaseApiClient;
import io.qase.app.api.client.ProjectApiClient;
import io.qase.app.api.dto.request.Case;
import io.qase.app.api.dto.response.singleproject.PostProjectResponse;
import io.qase.app.api.dto.response.testcase.PostCaseResponse;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CaseCreateTest {

//    @Test
//    public void createProject() {
//        PostProjectResponse createdProject = new ProjectApiClient().postAddProject(expectedProject);
//
//
//        PostCaseResponse createdCase = new CaseApiClient().postAddCase(new Case("API1"), );
//        assertThat(createdCase.isStatus()).as("Status should be true").isTrue();
//        assertThat(createdCase.getResult().getCode())
//                .as("Status should be true")
//                .isEqualTo(expectedProject.getCode());
//    }
}
