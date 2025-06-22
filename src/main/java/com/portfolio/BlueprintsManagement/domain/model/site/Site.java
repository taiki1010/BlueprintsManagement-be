package com.portfolio.BlueprintsManagement.domain.model.site;

import com.portfolio.BlueprintsManagement.presentation.dto.request.site.SiteRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Site {

    private String id;
    private String name;
    private String address;
    private String remark;

    /**
     * リクエスト情報から現場を作成します。
     *
     * @param request 現場リクエスト（現場名, 住所, 備考）
     * @return 新規UUIDが付与された現場
     */
    public static Site formSite(SiteRequest request) {
        return new Site(
                UUID.randomUUID().toString(),
                request.getName(),
                request.getAddress(),
                request.getRemark()
        );
    }
}
