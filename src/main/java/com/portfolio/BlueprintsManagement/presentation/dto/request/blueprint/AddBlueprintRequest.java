package com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint;

import com.portfolio.BlueprintsManagement.presentation.exception.validation.blueprintNameValidation.ValidBlueprintName;
import com.portfolio.BlueprintsManagement.presentation.exception.validation.createdAtValidation.ValidCreatedAt;
import com.portfolio.BlueprintsManagement.presentation.exception.validation.fileValidation.ValidFile;
import com.portfolio.BlueprintsManagement.presentation.exception.validation.idValidation.ValidId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "追加図面リクエスト")
@Data
@AllArgsConstructor
public class AddBlueprintRequest {

    @Schema(description = "現場ID", example = "00000000-0000-1000-8000-000000000001")
    @ValidId
    private String siteId;

    @Schema(description = "図面名", example = "平面図")
    @ValidBlueprintName
    private String name;

    @Schema(description = "作成日", example = "2025-01-01")
    @ValidCreatedAt
    private String createdAt;

    @Schema(description = "図面画像", example = "平面図.png")
    @ValidFile
    private MultipartFile imageFile;

}
