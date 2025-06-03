package Portfolio.BlueprintsManagement.presentation.dto.request.blueprint;

import Portfolio.BlueprintsManagement.presentation.exception.validation.createdAtValidation.ValidCreatedAt;
import Portfolio.BlueprintsManagement.presentation.exception.validation.idValidation.ValidId;
import lombok.Data;

@Data
public class DeleteBlueprintRequest {

    @ValidId
    private String id;

    @ValidId
    private String blueprintId;

    @ValidCreatedAt
    private String createdAt;

    private String filePath;
}
