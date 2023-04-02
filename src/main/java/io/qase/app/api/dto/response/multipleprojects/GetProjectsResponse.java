package io.qase.app.api.dto.response.multipleprojects;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetProjectsResponse {

    private boolean status;
    private Result result;
}
