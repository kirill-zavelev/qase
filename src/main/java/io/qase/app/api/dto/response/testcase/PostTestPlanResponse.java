package io.qase.app.api.dto.response.testcase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostTestPlanResponse {

    private boolean status;
}
