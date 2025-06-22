package com.portfolio.BlueprintsManagement.domain.model.blueprint;

import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.AddBlueprintRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Blueprint {

    private String id;
    private String siteId;
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
