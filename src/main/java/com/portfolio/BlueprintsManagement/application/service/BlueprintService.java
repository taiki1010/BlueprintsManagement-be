package com.portfolio.BlueprintsManagement.application.service;

import com.portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import com.portfolio.BlueprintsManagement.domain.model.blueprint.Blueprint;
import com.portfolio.BlueprintsManagement.domain.model.blueprintInfo.BlueprintInfo;
import com.portfolio.BlueprintsManagement.domain.repository.IArchitecturalDrawingRepository;
import com.portfolio.BlueprintsManagement.domain.repository.IBlueprintRepository;
import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.AddBlueprintRequest;
import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.DeleteBlueprintRequest;
import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.UpdateBlueprintRequest;
import com.portfolio.BlueprintsManagement.presentation.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    public String addBlueprint(AddBlueprintRequest request) throws IOException {
        MultipartFile imageFile = request.getBlueprint();
        String imageFileName = imageFile.getOriginalFilename();
        Resource resource = new ClassPathResource("static/image");
        Path imageDir = resource.getFile().toPath();
        byte[] content = imageFile.getBytes();
        Path filePath = imageDir.resolve(imageFileName);

        Blueprint blueprint = Blueprint.formBlueprint(request);
        ArchitecturalDrawing architecturalDrawing = ArchitecturalDrawing.formArchitecturalDrawingFromBlueprintRequest(request, blueprint.getId(), "image/" + imageFileName);

        blueprintRepository.addBlueprint(blueprint);
        architecturalDrawingRepository.addArchitecturalDrawing(architecturalDrawing);

        Files.write(filePath, content);
        return blueprint.getId();
    }

    @Transactional
    public void updateBlueprint(UpdateBlueprintRequest request) {
        blueprintRepository.updateBlueprint(request);
    }


    @Transactional
    public boolean deleteBlueprint(DeleteBlueprintRequest request) {
        String architecturalDrawingId = request.getId();
        String blueprintId = request.getBlueprintId();
        architecturalDrawingRepository.deleteArchitecturalDrawing(architecturalDrawingId);
        if(architecturalDrawingRepository.existArchitecturalDrawingByBlueprintId(blueprintId)) {
            return false;
        } else {
            blueprintRepository.deleteBlueprint(blueprintId);
            return true;
        }


    }
}
