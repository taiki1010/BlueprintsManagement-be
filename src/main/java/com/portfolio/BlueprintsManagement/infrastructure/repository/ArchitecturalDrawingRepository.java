package com.portfolio.BlueprintsManagement.infrastructure.repository;

import com.portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import com.portfolio.BlueprintsManagement.domain.repository.IArchitecturalDrawingRepository;
import com.portfolio.BlueprintsManagement.infrastructure.db.mapper.ArchitecturalDrawingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArchitecturalDrawingRepository implements IArchitecturalDrawingRepository {

    private final ArchitecturalDrawingMapper architecturalDrawingMapper;

    /**
     * 図面IDに紐づく図面画像一覧を取得します。
     *
     * @param blueprintId 図面ID
     * @return 図面IDに紐づく図面画像一覧（全件）
     */
    public List<ArchitecturalDrawing> getArchitecturalDrawingsByBlueprintId(String blueprintId) {
        return architecturalDrawingMapper.selectByBlueprintId(blueprintId);
    }

    /**
     * 新規図面画像を追加します。
     *
     * @param architecturalDrawing 図面画像
     */
    public void addArchitecturalDrawing(ArchitecturalDrawing architecturalDrawing) {
        architecturalDrawingMapper.add(architecturalDrawing);
    }

    /**
     * 図面IDに紐づく図面画像が存在するか確認します。
     *
     * @param blueprintId 図面ID
     * @return true or false
     */
    public boolean existArchitecturalDrawingByBlueprintId(String blueprintId) {
        return architecturalDrawingMapper.existArchitecturalDrawingByBlueprintId(blueprintId);
    }

    /**
     * 図面画像IDに紐づく図面画像を削除します。
     *
     * @param id 図面画像ID
     */
    public void deleteArchitecturalDrawing(String id) {
        architecturalDrawingMapper.delete(id);
    }
}
