package com.portfolio.BlueprintsManagement.domain.repository;

import com.portfolio.BlueprintsManagement.domain.model.blueprint.Blueprint;
import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.UpdateBlueprintRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBlueprintRepository {

    boolean existBlueprintBySiteId(String siteId);

    boolean existBlueprint(String id);

    List<Blueprint> getBlueprintsBySiteId(String siteId);

    Blueprint getBlueprint(String id);

    void addBlueprint(Blueprint blueprint);

    void updateBlueprint(UpdateBlueprintRequest request);

    void deleteBlueprint(String id);
}
