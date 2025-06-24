package com.portfolio.BlueprintsManagement.domain.model.site;

import com.portfolio.BlueprintsManagement.presentation.dto.request.site.SiteRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Schema(description = "現場")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Site {

    @Schema(description = "現場ID", example = "00000000-0000-1000-8000-000000000001")
    private String id;

    @Schema(description = "現場名", example = "佐藤邸")
    private String name;

    @Schema(description = "住所", example = "東京都表参道")
    private String address;

    @Schema(description = "備考", example = "コンビニの近く")
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
