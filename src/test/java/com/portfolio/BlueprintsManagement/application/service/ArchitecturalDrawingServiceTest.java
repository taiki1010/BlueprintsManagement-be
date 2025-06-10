package com.portfolio.BlueprintsManagement.application.service;

import com.portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import com.portfolio.BlueprintsManagement.domain.repository.IArchitecturalDrawingRepository;
import com.portfolio.BlueprintsManagement.presentation.dto.request.architecturalDrawing.ArchitecturalDrawingRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArchitecturalDrawingServiceTest {

    private ArchitecturalDrawingService sut;

    @Mock
    private IArchitecturalDrawingRepository architecturalDrawingRepository;

    @BeforeEach
    public void before() {
        sut = new ArchitecturalDrawingService(architecturalDrawingRepository);
    }

    @Test
    void 図面画像情報の追加＿リポジトリが実行されること() throws IOException {
        byte[] dummyImageFile = new byte[100];
        String fileName = "image.png";
        Resource resource = new ClassPathResource("static/image");
        Path imageDir = resource.getFile().toPath();
        Path filePath = imageDir.resolve(fileName);

        MockMultipartFile mockImage = new MockMultipartFile("imageFile", fileName, "image/png", dummyImageFile);
        ArchitecturalDrawingRequest request = new ArchitecturalDrawingRequest("10000000-0000-1000-8000-000000000001", "2025-01-01", mockImage);
        ArchitecturalDrawing architecturalDrawing = new ArchitecturalDrawing("11000000-0000-1000-8000-000000000001", "10000000-0000-1000-8000-000000000001", "2025-01-01", filePath.toString());

        try (MockedStatic<ArchitecturalDrawing> mockedStatic = mockStatic(ArchitecturalDrawing.class)) {
            mockedStatic.when(() -> ArchitecturalDrawing.formArchitecturalDrawing(request, "image/" + fileName)).thenReturn(architecturalDrawing);

            sut.addArchitecturalDrawing(request);

            verify(architecturalDrawingRepository, times(1)).addArchitecturalDrawing(architecturalDrawing);

            Files.delete(filePath);
        }
    }
}
