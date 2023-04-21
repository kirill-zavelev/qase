package io.qase.app.api.dto.response.multipleprojects;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Result {

    private List<io.qase.app.api.dto.response.singleproject.Result> entities;
}
