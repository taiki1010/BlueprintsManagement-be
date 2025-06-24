package com.portfolio.BlueprintsManagement.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.BlueprintsManagement.application.service.ArchitecturalDrawingService;
import com.portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import com.portfolio.BlueprintsManagement.presentation.dto.message.ErrorMessage;
import com.portfolio.BlueprintsManagement.presentation.dto.request.architecturalDrawing.ArchitecturalDrawingRequest;
import com.portfolio.BlueprintsManagement.presentation.exception.customException.FailedToPutObjectException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
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

    @Autowired
    public ArchitecturalDrawingControllerTest(ObjectMapper mapper) {
        this.mapper = new ObjectMapper();
        byte[] dummyImageBytes = new byte[100];
        mockImage = new MockMultipartFile("imageFile", "image.png", "image/png", dummyImageBytes);
    }

    @Nested
    class addArchitecturalDrawingTest {

        @Test
        void 図面画像の追加＿サービスが実行され図面画像情報が返却されること() throws Exception {
            ArchitecturalDrawing architecturalDrawing = new ArchitecturalDrawing(UUID.randomUUID().toString(), UUID.randomUUID().toString(), "2025-01-01", "/static/image/hoge.png");
            String expectedJson = mapper.writeValueAsString(architecturalDrawing);
            when(architecturalDrawingService.addArchitecturalDrawing(any(ArchitecturalDrawingRequest.class))).thenReturn(architecturalDrawing);

            mockMvc.perform(multipart("/architecturalDrawings")
                            .file(mockImage)
                            .param("blueprintId", "10000000-0000-1000-8000-000000000001")
                            .param("createdAt", "2025-01-01"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedJson));

            verify(architecturalDrawingService, times(1)).addArchitecturalDrawing(any(ArchitecturalDrawingRequest.class));
        }

        @Test
        void 図面画像の追加＿リクエストに不適切な情報が存在した場合エラーメッセージが返却されること() throws Exception {
            Map<String, String> response = Map.of("message", ErrorMessage.ID_MUST_BE_UUID.getMessage());
            String expectedJson = mapper.writeValueAsString(response);

            mockMvc.perform(multipart("/architecturalDrawings")
                            .file(mockImage)
                            .param("blueprintId", "1")
                            .param("createdAt", "2025-01-01"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().json(expectedJson));
        }

        @Test
        void 図面画像の追加＿例外処理が発生した場合エラーメッセージが返却されること() throws Exception {
            when(architecturalDrawingService.addArchitecturalDrawing(any(ArchitecturalDrawingRequest.class))).thenThrow(new FailedToPutObjectException(ErrorMessage.FAILED_TO_PUT_OBJECT.getMessage()));
            Map<String, String> response = Map.of("message", ErrorMessage.FAILED_TO_PUT_OBJECT.getMessage());
            String expectedJson = mapper.writeValueAsString(response);

            mockMvc.perform(multipart("/architecturalDrawings")
                            .file(mockImage)
                            .param("blueprintId", "10000000-0000-1000-8000-000000000001")
                            .param("createdAt", "2025-01-01"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().json(expectedJson));
        }

    }
}
