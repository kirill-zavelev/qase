package io.qase.app.api.dto.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Project {

    private String title;
    private String code;
    private String description;
    private String access;
}
