package com.portfolio.BlueprintsManagement.application.service;

import com.portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import com.portfolio.BlueprintsManagement.domain.repository.IArchitecturalDrawingRepository;
import com.portfolio.BlueprintsManagement.presentation.dto.request.architecturalDrawing.ArchitecturalDrawingRequest;
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
public class ArchitecturalDrawingService {

    private final IArchitecturalDrawingRepository architecturalDrawingRepository;

    public List<ArchitecturalDrawing> getArchitecturalDrawings(String blueprintId) throws NotFoundException {
        return architecturalDrawingRepository.getArchitecturalDrawingsByBlueprintId(blueprintId);
    }

    @Transactional
    public ArchitecturalDrawing addArchitecturalDrawing(ArchitecturalDrawingRequest request) throws IOException {

        MultipartFile imageFile = request.getArchitecturalDrawing();
        String fileType = imageFile.getContentType();
        String imageFileName = imageFile.getOriginalFilename();
        Resource resource = new ClassPathResource("static/image");
        Path imageDir = resource.getFile().toPath();
        byte[] content = imageFile.getBytes();
        Path filePath = imageDir.resolve(imageFileName);

        ArchitecturalDrawing architecturalDrawing = ArchitecturalDrawing.formArchitecturalDrawing(request, "image/" + imageFileName);

        architecturalDrawingRepository.addArchitecturalDrawing(architecturalDrawing);

        Files.write(filePath, content);

        return architecturalDrawing;

    }
}
