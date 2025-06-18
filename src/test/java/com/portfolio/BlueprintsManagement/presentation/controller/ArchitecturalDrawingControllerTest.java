package com.portfolio.BlueprintsManagement.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.BlueprintsManagement.application.service.ArchitecturalDrawingService;
import com.portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import com.portfolio.BlueprintsManagement.presentation.dto.request.architecturalDrawing.ArchitecturalDrawingRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArchitecturalDrawingController.class)
class ArchitecturalDrawingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private ArchitecturalDrawingService architecturalDrawingService;

    private ObjectMapper mapper;
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private MockMultipartFile mockImage;
    private ArchitecturalDrawingRequest architecturalDrawingRequest;

    @Autowired
    public ArchitecturalDrawingControllerTest(ObjectMapper mapper) {
        this.mapper = new ObjectMapper();
        byte[] dummyImageBytes = new byte[100];
        mockImage = new MockMultipartFile("imageFile", "image.png", "image/png", dummyImageBytes);
        architecturalDrawingRequest = new ArchitecturalDrawingRequest("10000000-0000-1000-8000-000000000001", "2025-01-01", mockImage);
    }

    @Test
    void 図面画像の追加＿サービスが実行され図面画像情報が返却されること() throws Exception {
        ArchitecturalDrawing architecturalDrawing = new ArchitecturalDrawing(UUID.randomUUID().toString(), UUID.randomUUID().toString(), "2025-01-01", "/static/image/hoge.png");
        String expectedJson = mapper.writeValueAsString(architecturalDrawing);
        when(architecturalDrawingService.addArchitecturalDrawing(any(ArchitecturalDrawingRequest.class))).thenReturn(architecturalDrawing);

        mockMvc.perform(multipart("/architecturalDrawings")
                        .file(mockImage)
                        .param("siteId", UUID.randomUUID().toString())
                        .param("BlueprintId", UUID.randomUUID().toString())
                        .param("createdAt", "2025-01-01"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(architecturalDrawingService, times(1)).addArchitecturalDrawing(any(ArchitecturalDrawingRequest.class));
    }
}
