package Portfolio.BlueprintsManagement.infrastructure.repository;

import Portfolio.BlueprintsManagement.domain.model.blueprint.Blueprint;
import Portfolio.BlueprintsManagement.domain.repository.IBlueprintRepository;
import Portfolio.BlueprintsManagement.infrastructure.db.mapper.BlueprintMapper;
import Portfolio.BlueprintsManagement.presentation.dto.request.blueprint.UpdateBlueprintRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BlueprintRepository implements IBlueprintRepository {

    private final BlueprintMapper blueprintMapper;

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
