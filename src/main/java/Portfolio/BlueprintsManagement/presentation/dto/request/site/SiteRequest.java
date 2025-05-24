package Portfolio.BlueprintsManagement.presentation.dto.request.site;

import Portfolio.BlueprintsManagement.presentation.exception.validation.addressValidation.ValidAddress;
import Portfolio.BlueprintsManagement.presentation.exception.validation.remarkValidation.ValidRemark;
import Portfolio.BlueprintsManagement.presentation.exception.validation.siteNameValidation.ValidSiteName;
import lombok.Data;

@Data
public class SiteRequest {

    @ValidSiteName
    private String name;

    @ValidAddress
    private String address;

    @ValidRemark
    private String remark;
}
