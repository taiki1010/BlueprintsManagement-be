package Portfolio.BlueprintsManagement.application.service;

import Portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import Portfolio.BlueprintsManagement.domain.model.blueprint.Blueprint;
import Portfolio.BlueprintsManagement.domain.model.blueprintInfo.BlueprintInfo;
import Portfolio.BlueprintsManagement.domain.repository.IArchitecturalDrawingRepository;
import Portfolio.BlueprintsManagement.domain.repository.IBlueprintRepository;
import Portfolio.BlueprintsManagement.presentation.dto.request.blueprint.BlueprintRequest;
import Portfolio.BlueprintsManagement.presentation.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlueprintService {

    private final IBlueprintRepository blueprintRepository;
    private final IArchitecturalDrawingRepository architecturalDrawingRepository;

    public List<Blueprint> getBlueprintsBySiteId(String siteId) throws NotFoundException {
        return blueprintRepository.getBlueprintsBySiteId(siteId);
    }

    public BlueprintInfo getBlueprintInfo(String id) throws NotFoundException {
        Blueprint blueprint = blueprintRepository.getBlueprint(id);
        List<ArchitecturalDrawing> architecturalDrawingList = architecturalDrawingRepository.getArchitecturalDrawingsByBlueprintId(id);
        return new BlueprintInfo(blueprint, architecturalDrawingList);
    }

    @Transactional
    public String addBlueprint(BlueprintRequest request) throws IOException {
        MultipartFile imageFile = request.getBlueprint();
        String imageFileName = imageFile.getOriginalFilename();
        Path uploadPath = Paths.get("upload/image/" + imageFileName);
        byte[] content = imageFile.getBytes();

        Blueprint blueprint = Blueprint.formBlueprint(request);
        ArchitecturalDrawing architecturalDrawing = ArchitecturalDrawing.formArchitecturalDrawing(request, blueprint.getId(), String.valueOf(uploadPath));

        blueprintRepository.addBlueprint(blueprint);
        architecturalDrawingRepository.addArchitecturalDrawing(architecturalDrawing);

        Files.write(uploadPath, content);

        return blueprint.getId();
    }
}
