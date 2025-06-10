package com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint;

import com.portfolio.BlueprintsManagement.presentation.exception.validation.blueprintNameValidation.ValidBlueprintName;
import com.portfolio.BlueprintsManagement.presentation.exception.validation.createdAtValidation.ValidCreatedAt;
import com.portfolio.BlueprintsManagement.presentation.exception.validation.fileValidation.ValidFile;
import com.portfolio.BlueprintsManagement.presentation.exception.validation.idValidation.ValidId;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class AddBlueprintRequest {

    @ValidId
    private String siteId;

    @ValidBlueprintName
    private String name;

    @ValidCreatedAt
    private String createdAt;

    @ValidFile
    private MultipartFile imageFile;

}
