package io.qase.app.api.dto.response.multipleprojects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProjectsResponse {

    private boolean status;
    private Result result;
}
