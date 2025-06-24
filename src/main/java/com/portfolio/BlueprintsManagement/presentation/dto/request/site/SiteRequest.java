package com.portfolio.BlueprintsManagement.presentation.dto.request.site;

import com.portfolio.BlueprintsManagement.presentation.exception.validation.addressValidation.ValidAddress;
import com.portfolio.BlueprintsManagement.presentation.exception.validation.remarkValidation.ValidRemark;
import com.portfolio.BlueprintsManagement.presentation.exception.validation.siteNameValidation.ValidSiteName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "現場リクエスト")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteRequest {

    @Schema(description = "現場名", example = "佐藤邸")
    @ValidSiteName
    private String name;

    @Schema(description = "住所", example = "東京都表参道")
    @ValidAddress
    private String address;

    @Schema(description = "備考", example = "コンビニの近く")
    @ValidRemark
    private String remark;
}
