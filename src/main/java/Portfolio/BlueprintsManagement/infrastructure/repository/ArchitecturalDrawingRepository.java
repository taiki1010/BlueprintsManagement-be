package Portfolio.BlueprintsManagement.infrastructure.repository;

import Portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import Portfolio.BlueprintsManagement.domain.repository.IArchitecturalDrawingRepository;
import Portfolio.BlueprintsManagement.infrastructure.db.mapper.ArchitecturalDrawingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArchitecturalDrawingRepository implements IArchitecturalDrawingRepository {

    private final ArchitecturalDrawingMapper architecturalDrawingMapper;

    public List<ArchitecturalDrawing> getArchitecturalDrawingsByBlueprintId(String blueprintId) {
        return architecturalDrawingMapper.selectByBlueprintId(blueprintId);
    }

    public void addArchitecturalDrawing(ArchitecturalDrawing architecturalDrawing) {
        architecturalDrawingMapper.add(architecturalDrawing);
    }

    public boolean existArchitecturalDrawingByBlueprintId(String blueprintId) {
        return architecturalDrawingMapper.existArchitecturalDrawingByBlueprintId(blueprintId);
    }

    public void deleteArchitecturalDrawing(String id) {
        architecturalDrawingMapper.delete(id);
    }
}
