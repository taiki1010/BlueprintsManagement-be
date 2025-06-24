package com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint;

import com.portfolio.BlueprintsManagement.presentation.exception.validation.createdAtValidation.ValidCreatedAt;
import com.portfolio.BlueprintsManagement.presentation.exception.validation.idValidation.ValidId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "削除図面リクエスト")
@Data
@AllArgsConstructor
public class DeleteBlueprintRequest {

    @Schema(description = "図面画像ID", example = "11000000-0000-1000-8000-000000000001")
    @ValidId
    private String id;

    @Schema(description = "図面ID", example = "10000000-0000-1000-8000-000000000001")
    @ValidId
    private String blueprintId;

    @Schema(description = "作成日", example = "2025-01-01")
    @ValidCreatedAt
    private String createdAt;

    @Schema(description = "画像ファイルパス", example = "10000000-0000-1000-8000-000000000001/平面図.png")
    private String filePath;
}
