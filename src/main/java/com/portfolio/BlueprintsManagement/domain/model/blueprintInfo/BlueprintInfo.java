package com.portfolio.BlueprintsManagement.domain.model.blueprintInfo;

import com.portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import com.portfolio.BlueprintsManagement.domain.model.blueprint.Blueprint;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BlueprintInfo {

    private Blueprint blueprint;
    private List<ArchitecturalDrawing> architecturalDrawingList;

}
