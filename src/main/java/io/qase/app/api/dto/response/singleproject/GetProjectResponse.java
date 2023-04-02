package io.qase.app.api.dto.response.singleproject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetProjectResponse {

    private boolean status;
    private Result result;
}
