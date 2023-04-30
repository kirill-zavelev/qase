package io.qase.app.ui.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestPlan {

    private String title;
    private String description;
}
