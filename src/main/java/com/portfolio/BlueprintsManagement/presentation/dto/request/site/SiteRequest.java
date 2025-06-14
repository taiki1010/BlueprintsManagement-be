package com.portfolio.BlueprintsManagement.presentation.dto.request.site;

import com.portfolio.BlueprintsManagement.presentation.exception.validation.addressValidation.ValidAddress;
import com.portfolio.BlueprintsManagement.presentation.exception.validation.remarkValidation.ValidRemark;
import com.portfolio.BlueprintsManagement.presentation.exception.validation.siteNameValidation.ValidSiteName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteRequest {

    @ValidSiteName
    private String name;

    @ValidAddress
    private String address;

    @ValidRemark
    private String remark;
}
