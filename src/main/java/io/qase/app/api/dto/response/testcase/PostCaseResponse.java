package io.qase.app.api.dto.response.testcase;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostCaseResponse {

    private boolean status;
    private Result result;
}
