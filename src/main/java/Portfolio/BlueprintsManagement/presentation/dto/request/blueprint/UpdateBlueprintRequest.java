package Portfolio.BlueprintsManagement.presentation.dto.request.blueprint;

import Portfolio.BlueprintsManagement.presentation.exception.validation.blueprintNameValidation.ValidBlueprintName;
import Portfolio.BlueprintsManagement.presentation.exception.validation.idValidation.ValidId;
import lombok.Data;

@Data
public class UpdateBlueprintRequest {

    @ValidId
    private String id;

    @ValidBlueprintName
    private String name;
}
