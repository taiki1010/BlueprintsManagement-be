package Portfolio.BlueprintsManagement.application.service;

import Portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import Portfolio.BlueprintsManagement.domain.repository.IArchitecturalDrawingRepository;
import Portfolio.BlueprintsManagement.presentation.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArchitecturalDrawingService {

    private final IArchitecturalDrawingRepository architecturalDrawingRepository;

    public List<ArchitecturalDrawing> getArchitecturalDrawings(String blueprintId) throws NotFoundException {
        return architecturalDrawingRepository.getArchitecturalDrawingsByBlueprintId(blueprintId);
    }
}
