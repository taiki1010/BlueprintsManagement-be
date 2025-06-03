package Portfolio.BlueprintsManagement.application.service;

import Portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import Portfolio.BlueprintsManagement.domain.repository.IArchitecturalDrawingRepository;
import Portfolio.BlueprintsManagement.presentation.dto.request.architecturalDrawing.ArchitecturalDrawingRequest;
import Portfolio.BlueprintsManagement.presentation.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
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

    public ArchitecturalDrawing addArchitecturalDrawing(ArchitecturalDrawingRequest request) throws IOException {

        MultipartFile imageFile = request.getArchitecturalDrawing();
        String fileType = imageFile.getContentType();
        String imageFileName = imageFile.getOriginalFilename();
        Resource resource = new ClassPathResource("static/image");
        Path imageDir = resource.getFile().toPath();
        byte[] content = imageFile.getBytes();
        Path filePath = imageDir.resolve(imageFileName);

        ArchitecturalDrawing architecturalDrawing = ArchitecturalDrawing.formArchitecturalDrawing(request,"image/" + imageFileName);

        architecturalDrawingRepository.addArchitecturalDrawing(architecturalDrawing);

        Files.write(filePath, content);

        return architecturalDrawing;

    }
}
