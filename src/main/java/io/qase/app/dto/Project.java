package io.qase.app.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Project {

    private String projectName;
    private String projectCode;
//    private String description;
//    private String projectAccessType;
//    private String memberAccess;
}
