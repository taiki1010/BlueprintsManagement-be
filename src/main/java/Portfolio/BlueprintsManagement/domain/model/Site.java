package Portfolio.BlueprintsManagement.domain.model;

import Portfolio.BlueprintsManagement.presentation.dto.request.site.SiteRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Site {

    private String id;
    private String name;
    private String address;
    private String remark;

    public static Site formSite(SiteRequest request) {
        return new Site(
                UUID.randomUUID().toString(),
                request.getName(),
                request.getAddress(),
                request.getRemark()
        );
    }
}
