package io.qase.app.api.dto.response.multipleprojects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private List<io.qase.app.api.dto.response.singleproject.Result> entities;
}
