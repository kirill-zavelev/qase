package io.qase.app.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestPlanApi {

    private String title;
    private String description;
    private List<Long> cases;
}
