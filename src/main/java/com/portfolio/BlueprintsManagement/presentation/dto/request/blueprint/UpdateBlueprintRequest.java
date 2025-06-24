package com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint;

import com.portfolio.BlueprintsManagement.presentation.exception.validation.blueprintNameValidation.ValidBlueprintName;
import com.portfolio.BlueprintsManagement.presentation.exception.validation.idValidation.ValidId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "更新図面リクエスト")
@Data
@AllArgsConstructor
public class UpdateBlueprintRequest {

    @Schema(description = "図面ID", example = "10000000-0000-1000-8000-000000000001")
    @ValidId
    private String id;

    @Schema(description = "図面名", example = "平面図")
    @ValidBlueprintName
    private String name;
}
