package Portfolio.BlueprintsManagement.infrastructure.db.mapper;

import Portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArchitecturalDrawingMapper {

    List<ArchitecturalDrawing> selectByBlueprintId(String blueprintId);

    void add(ArchitecturalDrawing architecturalDrawing);
}
