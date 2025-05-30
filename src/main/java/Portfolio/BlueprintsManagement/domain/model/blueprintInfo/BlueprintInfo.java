package Portfolio.BlueprintsManagement.domain.model.blueprintInfo;

import Portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import Portfolio.BlueprintsManagement.domain.model.blueprint.Blueprint;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BlueprintInfo {

    private Blueprint blueprint;
    private List<ArchitecturalDrawing> architecturalDrawingList;

}
