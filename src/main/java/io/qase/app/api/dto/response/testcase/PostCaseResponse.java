package io.qase.app.api.dto.response.testcase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCaseResponse {

    private boolean status;
    private Result result;
}
