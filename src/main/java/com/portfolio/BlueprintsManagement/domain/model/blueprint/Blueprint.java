package com.portfolio.BlueprintsManagement.domain.model.blueprint;

import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.AddBlueprintRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Schema(description = "図面")
@Data
@AllArgsConstructor
public class Blueprint {

    @Schema(description = "図面ID", example = "10000000-0000-1000-8000-000000000001")
    private String id;

    @Schema(description = "現場ID", example = "00000000-0000-1000-8000-000000000001")
    private String siteId;

    @Schema(description = "図面名", example = "平面図")
    private String name;

    /**
     * リクエスト情報から図面を作成します。
     *
     * @param request 追加図面リクエスト（現場ID, 図面名, 作成日, 図面画像データ）
     * @return 新規UUIDが付与された図面
     */
    public static Blueprint formBlueprint(AddBlueprintRequest request) {
        return new Blueprint(
                UUID.randomUUID().toString(),
                request.getSiteId(),
                request.getName()
        );
    }
}
