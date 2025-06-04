package Portfolio.BlueprintsManagement.presentation.dto.request.architecturalDrawing;

import Portfolio.BlueprintsManagement.presentation.exception.validation.createdAtValidation.ValidCreatedAt;
import Portfolio.BlueprintsManagement.presentation.exception.validation.fileValidation.ValidFile;
import Portfolio.BlueprintsManagement.presentation.exception.validation.idValidation.ValidId;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ArchitecturalDrawingRequest {

    @ValidId
    private String BlueprintId;

    @ValidCreatedAt
    private String createdAt;

    @ValidFile
    private MultipartFile architecturalDrawing;
}
