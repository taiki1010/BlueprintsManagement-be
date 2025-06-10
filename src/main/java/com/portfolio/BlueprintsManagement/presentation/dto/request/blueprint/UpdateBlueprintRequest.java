package com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint;

import com.portfolio.BlueprintsManagement.presentation.exception.validation.blueprintNameValidation.ValidBlueprintName;
import com.portfolio.BlueprintsManagement.presentation.exception.validation.idValidation.ValidId;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateBlueprintRequest {

    @ValidId
    private String id;

    @ValidBlueprintName
    private String name;
}
