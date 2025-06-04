package Portfolio.BlueprintsManagement.domain.repository;

import Portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IArchitecturalDrawingRepository {

    List<ArchitecturalDrawing> getArchitecturalDrawingsByBlueprintId(String blueprintId);

    void addArchitecturalDrawing(ArchitecturalDrawing architecturalDrawing);

    boolean existArchitecturalDrawingByBlueprintId(String blueprintId);

    void deleteArchitecturalDrawing(String id);
}
