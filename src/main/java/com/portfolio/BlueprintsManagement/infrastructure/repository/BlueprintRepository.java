package com.portfolio.BlueprintsManagement.infrastructure.repository;

import com.portfolio.BlueprintsManagement.domain.model.blueprint.Blueprint;
import com.portfolio.BlueprintsManagement.domain.repository.IBlueprintRepository;
import com.portfolio.BlueprintsManagement.infrastructure.db.mapper.BlueprintMapper;
import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.UpdateBlueprintRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BlueprintRepository implements IBlueprintRepository {

    private final BlueprintMapper blueprintMapper;

    /**
     * 現場IDに紐づく図面が存在するか確認します。
     *
     * @param siteId 現場ID
     * @return true or false
     */
    public boolean existBlueprintBySiteId(String siteId) {
        return blueprintMapper.existBlueprintBySiteId(siteId);
    }

    /**
     * 図面IDに紐づく図面が存在するか確認します。
     *
     * @param id 図面ID
     * @return true or false
     */
    public boolean existBlueprint(String id) {
        return blueprintMapper.existBlueprint(id);
    }

    /**
     * 現場IDに紐づく図面一覧を取得します。
     *
     * @param siteId 現場ID
     * @return 現場IDに紐づく図面一覧（全件）
     */
    public List<Blueprint> getBlueprintsBySiteId(String siteId) {
        return blueprintMapper.selectBySiteId(siteId);
    }

    /**
     * 図面IDに紐づく図面を取得します。
     *
     * @param id 図面ID
     * @return 図面IDに紐づく図面一件
     */
    public Blueprint getBlueprint(String id) {
        return blueprintMapper.select(id);
    }

    /**
     * 新規図面を追加します。
     *
     * @param blueprint 図面
     */
    public void addBlueprint(Blueprint blueprint) {
        blueprintMapper.add(blueprint);
    }

    /**
     * 図面を更新します。
     *
     * @param request 図面
     */
    public void updateBlueprint(UpdateBlueprintRequest request) {
        blueprintMapper.update(request);
    }

    /**
     * 図面IDに紐づく図面を削除します。
     *
     * @param id 図面ID
     */
    public void deleteBlueprint(String id) {
        blueprintMapper.delete(id);
    }
}
