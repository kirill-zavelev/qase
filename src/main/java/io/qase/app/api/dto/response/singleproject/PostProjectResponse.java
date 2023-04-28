package io.qase.app.api.dto.response.singleproject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostProjectResponse {

    private boolean status;
    private Result result;
}