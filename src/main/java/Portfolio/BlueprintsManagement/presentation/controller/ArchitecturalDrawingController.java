package Portfolio.BlueprintsManagement.presentation.controller;

import Portfolio.BlueprintsManagement.application.service.ArchitecturalDrawingService;
import Portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import Portfolio.BlueprintsManagement.presentation.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/architecturalDrawings")
public class ArchitecturalDrawingController {

    private final ArchitecturalDrawingService architecturalDrawingService;

    @GetMapping("/{blueprintId}")
    public List<ArchitecturalDrawing> searchArchitecturalDrawingsByBlueprintId(@PathVariable String blueprintId) throws NotFoundException {
        return architecturalDrawingService.getArchitecturalDrawings(blueprintId);
    }
}
