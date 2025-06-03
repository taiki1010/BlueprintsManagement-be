package Portfolio.BlueprintsManagement.presentation.dto.request.blueprint;

import Portfolio.BlueprintsManagement.presentation.exception.validation.blueprintNameValidation.ValidBlueprintName;
import Portfolio.BlueprintsManagement.presentation.exception.validation.createdAtValidation.ValidCreatedAt;
import Portfolio.BlueprintsManagement.presentation.exception.validation.fileValidation.ValidFile;
import Portfolio.BlueprintsManagement.presentation.exception.validation.idValidation.ValidId;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AddBlueprintRequest {

    @ValidId
    private String siteId;

    @ValidBlueprintName
    private String name;

    @ValidCreatedAt
    private String createdAt;

    @ValidFile
    private MultipartFile blueprint;

}
