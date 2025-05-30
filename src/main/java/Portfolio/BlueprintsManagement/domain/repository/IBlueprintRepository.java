package Portfolio.BlueprintsManagement.domain.repository;

import Portfolio.BlueprintsManagement.domain.model.blueprint.Blueprint;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBlueprintRepository {

    List<Blueprint> getBlueprintsBySiteId(String siteId);

    Blueprint getBlueprint(String id);

    void addBlueprint(Blueprint blueprint);
}
