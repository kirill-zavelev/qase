package io.qase.app.api.dto.response.singleproject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Result {

    private String code;
    private String title;
}
