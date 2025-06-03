package Portfolio.BlueprintsManagement.presentation.controller;

import Portfolio.BlueprintsManagement.application.service.ArchitecturalDrawingService;
import Portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import Portfolio.BlueprintsManagement.presentation.dto.request.architecturalDrawing.ArchitecturalDrawingRequest;
import Portfolio.BlueprintsManagement.presentation.exception.customException.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/architecturalDrawings")
public class ArchitecturalDrawingController {

    private final ArchitecturalDrawingService architecturalDrawingService;

    @GetMapping("/{blueprintId}")
    public List<ArchitecturalDrawing> searchArchitecturalDrawingsByBlueprintId(@PathVariable String blueprintId) throws NotFoundException {
        return architecturalDrawingService.getArchitecturalDrawings(blueprintId);
    }

    @PostMapping
    public ArchitecturalDrawing addArchitecturalDrawing(@ModelAttribute @Valid ArchitecturalDrawingRequest architecturalDrawingRequest) throws IOException {
        return architecturalDrawingService.addArchitecturalDrawing(architecturalDrawingRequest);
    }
}
