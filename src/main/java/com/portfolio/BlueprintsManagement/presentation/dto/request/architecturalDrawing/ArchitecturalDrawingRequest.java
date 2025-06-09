package com.portfolio.BlueprintsManagement.presentation.dto.request.architecturalDrawing;

import com.portfolio.BlueprintsManagement.presentation.exception.validation.createdAtValidation.ValidCreatedAt;
import com.portfolio.BlueprintsManagement.presentation.exception.validation.fileValidation.ValidFile;
import com.portfolio.BlueprintsManagement.presentation.exception.validation.idValidation.ValidId;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class ArchitecturalDrawingRequest {

    @ValidId
    private String BlueprintId;

    @ValidCreatedAt
    private String createdAt;

    @ValidFile
    private MultipartFile imageFile;
}
