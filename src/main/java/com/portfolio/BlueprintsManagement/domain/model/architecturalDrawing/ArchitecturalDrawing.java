package com.portfolio.BlueprintsManagement.domain.model.architecturalDrawing;

import com.portfolio.BlueprintsManagement.presentation.dto.request.architecturalDrawing.ArchitecturalDrawingRequest;
import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.AddBlueprintRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ArchitecturalDrawing {

    private String id;
    private String BlueprintId;
    private String createdAt;
    private String filePath;

    /**
     * リクエストとパラメータ情報から図面画像を作成します。
     *
     * @param request  追加図面リクエスト（現場ID, 図面名, 作成日, 図面画像データ）
     * @param id       図面ID
     * @param filePath 画像ファイルパス
     * @return 新規UUIDが付与された図面画像
     */
    public static ArchitecturalDrawing formArchitecturalDrawingFromBlueprintRequest(AddBlueprintRequest request, String id, String filePath) {
        return new ArchitecturalDrawing(
                UUID.randomUUID().toString(),
                id,
                request.getCreatedAt(),
                filePath
        );
    }

    /**
     * リクエストとパラメータ情報から図面画像を作成します。
     *
     * @param request  図面画像リクエスト（図面ID, 作成日, 図面画像データ）
     * @param filePath 画像ファイルパス
     * @return 新規UUIDが付与された図面画像
     */
    public static ArchitecturalDrawing formArchitecturalDrawing(ArchitecturalDrawingRequest request, String filePath) {
        return new ArchitecturalDrawing(
                UUID.randomUUID().toString(),
                request.getBlueprintId(),
                request.getCreatedAt(),
                filePath
        );
    }
}
