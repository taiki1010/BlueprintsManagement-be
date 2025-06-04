package Portfolio.BlueprintsManagement.domain.repository;

import Portfolio.BlueprintsManagement.domain.model.blueprint.Blueprint;
import Portfolio.BlueprintsManagement.presentation.dto.request.blueprint.UpdateBlueprintRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBlueprintRepository {

    List<Blueprint> getBlueprintsBySiteId(String siteId);

    Blueprint getBlueprint(String id);

    void addBlueprint(Blueprint blueprint);

    void updateBlueprint(UpdateBlueprintRequest request);

    void deleteBlueprint(String id);
}
