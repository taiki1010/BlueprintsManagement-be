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

    public boolean existBlueprintBySiteId(String siteId) {
        return blueprintMapper.existBlueprintBySiteId(siteId);
    }

    public boolean existBlueprint(String id) {
        return blueprintMapper.existBlueprint(id);
    }

    public List<Blueprint> getBlueprintsBySiteId(String siteId) {
        return blueprintMapper.selectBySiteId(siteId);
    }

    public Blueprint getBlueprint(String id) {
        return blueprintMapper.select(id);
    }

    public void addBlueprint(Blueprint blueprint) {
        blueprintMapper.add(blueprint);
    }

    public void updateBlueprint(UpdateBlueprintRequest request) {
        blueprintMapper.update(request);
    }

    public void deleteBlueprint(String id) {
        blueprintMapper.delete(id);
    }
}
