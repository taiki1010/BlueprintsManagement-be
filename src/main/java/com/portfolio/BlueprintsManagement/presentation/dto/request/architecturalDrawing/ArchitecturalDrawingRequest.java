package com.portfolio.BlueprintsManagement.presentation.dto.request.architecturalDrawing;

import com.portfolio.BlueprintsManagement.presentation.exception.validation.createdAtValidation.ValidCreatedAt;
import com.portfolio.BlueprintsManagement.presentation.exception.validation.fileValidation.ValidFile;
import com.portfolio.BlueprintsManagement.presentation.exception.validation.idValidation.ValidId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "図面画像リクエスト")
@Data
@AllArgsConstructor
public class ArchitecturalDrawingRequest {

    @Schema(description = "図面ID", example = "10000000-0000-1000-8000-000000000001")
    @ValidId
    private String blueprintId;

    @Schema(description = "作成日", example = "2025-01-01")
    @ValidCreatedAt
    private String createdAt;

    @Schema(description = "図面画像", example = "平面図.png")
    @ValidFile
    private MultipartFile imageFile;
}
