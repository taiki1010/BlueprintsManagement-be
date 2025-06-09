package com.portfolio.BlueprintsManagement.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.BlueprintsManagement.application.service.ArchitecturalDrawingService;
import com.portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import com.portfolio.BlueprintsManagement.presentation.dto.request.architecturalDrawing.ArchitecturalDrawingRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        architecturalDrawingRequest = new ArchitecturalDrawingRequest(UUID.randomUUID().toString(), "2025-01-01", mockImage);
    }

    @Test
    void 図面画像の追加＿サービスが実行され図面画像情報が返却されること() throws Exception {
        ArchitecturalDrawing architecturalDrawing = new ArchitecturalDrawing(UUID.randomUUID().toString(), UUID.randomUUID().toString(), "2025-01-01", "/static/image/hoge.png");
        String expectedJson = mapper.writeValueAsString(architecturalDrawing);
        when(architecturalDrawingService.addArchitecturalDrawing(any(ArchitecturalDrawingRequest.class))).thenReturn(architecturalDrawing);

        mockMvc.perform(multipart("/architecturalDrawings")
                        .file(mockImage)
                        .param("BlueprintId", UUID.randomUUID().toString())
                        .param("createdAt", "2025-01-01"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(architecturalDrawingService, times(1)).addArchitecturalDrawing(any(ArchitecturalDrawingRequest.class));
    }

    @Nested
    class ValidationTest {

        @Nested
        class architecturalDrawingRequestTest {

            @Nested
            class blueprintIdValidationTest {

                @ParameterizedTest
                @ValueSource(strings = {"00000000-0000-1000-8000-000000000000", "ffffffff-ffff-5fff-bfff-ffffffffffff"})
                void 図面idがUUID形式に適している場合正常に処理が実行されること(String blueprintId) {
                    architecturalDrawingRequest.setBlueprintId(blueprintId);
                    Set<ConstraintViolation<ArchitecturalDrawingRequest>> violations = validator.validate(architecturalDrawingRequest);

                    assertEquals(0, violations.size());
                }

                @ParameterizedTest
                @ValueSource(strings = {"-1", "1", "abc", ""})
                void 図面idがUUID形式に適していない場合バリデーションチェックされること(String blueprintId) {
                    architecturalDrawingRequest.setBlueprintId(blueprintId);
                    Set<ConstraintViolation<ArchitecturalDrawingRequest>> violations = validator.validate(architecturalDrawingRequest);
                    String actual = violations.iterator().next().getMessage();

                    assertEquals(1, violations.size());
                    assertEquals("idの形式が正しくありません", actual);
                }
            }

            @Nested
            class createdAtValidationTest {

                @ParameterizedTest
                @ValueSource(strings = {"0000-01-01", "9999-12-31"})
                void 作成日が適切な文字列の場合にオブジェクトが作成されること(String createdAt) {
                    architecturalDrawingRequest.setCreatedAt(createdAt);
                    Set<ConstraintViolation<ArchitecturalDrawingRequest>> violations = validator.validate(architecturalDrawingRequest);

                    assertEquals(0, violations.size());
                }

                @ParameterizedTest
                @ValueSource(strings = {"000-01-01", "9999-12-311"})
                void 作成日が適切ではない文字列の場合にバリデーションチェックがかかること(String createdAt) {
                    architecturalDrawingRequest.setCreatedAt(createdAt);
                    Set<ConstraintViolation<ArchitecturalDrawingRequest>> violations = validator.validate(architecturalDrawingRequest);
                    String actual = violations.iterator().next().getMessage();

                    assertEquals(1, violations.size());
                    assertEquals("入力された日付の形式が正しくありません", actual);
                }

                @Test
                void 作成日が空文字の場合にバリデーションチェックがかかること() {
                    String createdAt = "";
                    architecturalDrawingRequest.setCreatedAt(createdAt);
                    Set<ConstraintViolation<ArchitecturalDrawingRequest>> violations = validator.validate(architecturalDrawingRequest);
                    String actual = violations.iterator().next().getMessage();

                    assertEquals(1, violations.size());
                    assertEquals("入力欄が空です", actual);
                }
            }

            @Nested
            class imageFileValidationTest {

                @Test
                void ファイルサイズが5MBを超える場合バリデーションチェックがかかること() {
                    byte[] dummyImageBytes = new byte[6 * 1024 * 1024];
                    MockMultipartFile mockImage = new MockMultipartFile("imageFile", "image.png", "image/png", dummyImageBytes);
                    architecturalDrawingRequest.setImageFile(mockImage);
                    Set<ConstraintViolation<ArchitecturalDrawingRequest>> violations = validator.validate(architecturalDrawingRequest);
                    String actual = violations.iterator().next().getMessage();

                    assertEquals(1, violations.size());
                    assertEquals("ファイルサイズは5MBまでにしてください", actual);
                }

                @Test
                void ファイル形式が異なる場合バリデーションチェックがかかること() {
                    byte[] dummyFileBytes = new byte[100];
                    MockMultipartFile mockFile = new MockMultipartFile("imageFile", "text.txt", "text/plain", dummyFileBytes);
                    architecturalDrawingRequest.setImageFile(mockFile);
                    Set<ConstraintViolation<ArchitecturalDrawingRequest>> violations = validator.validate(architecturalDrawingRequest);
                    String actual = violations.iterator().next().getMessage();

                    assertEquals(1, violations.size());
                    assertEquals("画像ファイルを選択してください", actual);
                }

            }

        }
    }

}
