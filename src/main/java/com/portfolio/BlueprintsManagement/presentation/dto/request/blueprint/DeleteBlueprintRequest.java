package com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint;

import com.portfolio.BlueprintsManagement.presentation.exception.validation.createdAtValidation.ValidCreatedAt;
import com.portfolio.BlueprintsManagement.presentation.exception.validation.idValidation.ValidId;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteBlueprintRequest {

    @ValidId
    private String siteId;

    @ValidId
    private String blueprintId;

    @ValidCreatedAt
    private String createdAt;

    private String filePath;
}
