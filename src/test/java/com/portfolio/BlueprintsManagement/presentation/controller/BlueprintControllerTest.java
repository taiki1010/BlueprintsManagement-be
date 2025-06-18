package com.portfolio.BlueprintsManagement.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.BlueprintsManagement.application.service.BlueprintService;
import com.portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import com.portfolio.BlueprintsManagement.domain.model.blueprint.Blueprint;
import com.portfolio.BlueprintsManagement.domain.model.blueprintInfo.BlueprintInfo;
import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.AddBlueprintRequest;
import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.DeleteBlueprintRequest;
import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.UpdateBlueprintRequest;
import com.portfolio.BlueprintsManagement.presentation.exception.validation.idValidation.ValidId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BlueprintController.class)
class BlueprintControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private BlueprintService blueprintService;

    @Autowired
    ObjectMapper mapper;

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private String siteId;
    private String blueprintId;
    private MockMultipartFile mockImage;
    private UpdateBlueprintRequest updateBlueprintRequest;
    private DeleteBlueprintRequest deleteBlueprintRequest;

    @BeforeEach
    void before() {
        createInitialSampleData();
    }

    private void createInitialSampleData() {
        siteId = "00000000-0000-1000-8000-000000000001";
        blueprintId = "10000000-0000-1000-8000-000000000001";
        byte[] dummyImageBytes = new byte[100];
        mockImage = new MockMultipartFile("imageFile", "image.png", "image/png", dummyImageBytes);
        updateBlueprintRequest = new UpdateBlueprintRequest(blueprintId, "平面図");
        deleteBlueprintRequest = new DeleteBlueprintRequest(siteId, blueprintId, "2025-01-01", "/static/image/hoge.png");
    }

    @Test
    void 図面情報の取得＿サービスが実行されidに該当する図面情報が返却されること() throws Exception {
        Blueprint blueprint = new Blueprint(blueprintId, "00000000-0000-1000-8000-000000000001", "平面図");
        ArchitecturalDrawing architecturalDrawing = new ArchitecturalDrawing("11000000-0000-1000-8000-000000000001", blueprintId, "2025-01-01", "/static/image/hoge.png");
        BlueprintInfo blueprintInfo = new BlueprintInfo(blueprint, List.of(architecturalDrawing));
        String expectedJson = mapper.writeValueAsString(blueprintInfo);
        when(blueprintService.getBlueprintInfo(blueprintId)).thenReturn(blueprintInfo);

        mockMvc.perform(get("/blueprints/" + blueprintId))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(blueprintService, times(1)).getBlueprintInfo(blueprintId);
    }

    @Test
    void 図面の追加＿サービスが実行されokレスポンスとidを含めたメッセージが返却されること() throws Exception {
        Map<String, String> response = Map.of("id", blueprintId);
        String expectedJson = mapper.writeValueAsString(response);
        when(blueprintService.addBlueprint(any(AddBlueprintRequest.class))).thenReturn(blueprintId);

        mockMvc.perform(multipart("/blueprints")
                        .file(mockImage)
                        .param("siteId", "00000000-0000-1000-8000-000000000004")
                        .param("name", "平面図")
                        .param("createdAt", "2025-01-01"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(blueprintService, times(1)).addBlueprint(any(AddBlueprintRequest.class));
    }

    @Test
    void 図面の更新＿サービスが実行されokレスポンスとメッセージが返却されること() throws Exception {
        String requestJson = mapper.writeValueAsString(updateBlueprintRequest);
        Map<String, String> response = Map.of("message", "図面名の更新が完了しました");
        String expectedJson = mapper.writeValueAsString(response);

        mockMvc.perform(put("/blueprints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(blueprintService, times(1)).updateBlueprint(updateBlueprintRequest);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void 図面の削除＿サービスが実行されokレスポンスと論理値が返却されること(Boolean isBool) throws Exception {
        String requestJson = mapper.writeValueAsString(deleteBlueprintRequest);
        Map<String, Boolean> response = Map.of("isDeletedBlueprint", isBool);
        String responseJson = mapper.writeValueAsString(response);
        when(blueprintService.deleteBlueprint(deleteBlueprintRequest)).thenReturn(isBool);

        mockMvc.perform(delete("/blueprints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Nested
    class ValidationTest {

        @Nested
        class idValidationTest {

            @ParameterizedTest
            @ValueSource(strings = {"00000000-0000-1000-8000-000000000000", "ffffffff-ffff-5fff-bfff-ffffffffffff"})
            void idがUUID形式に適している場合正常に処理が実行されること(String id) {
                class PathIdWrapper {
                    @ValidId
                    private String id;

                    public PathIdWrapper(String id) {
                        this.id = id;
                    }
                }
                PathIdWrapper wrapper = new PathIdWrapper(id);

                Set<ConstraintViolation<PathIdWrapper>> violations = validator.validate(wrapper);

                assertEquals(0, violations.size());
            }

            @ParameterizedTest
            @ValueSource(strings = {"-1", "1", "abc", ""})
            void idがUUID形式に適していない場合バリデーションチェックがかかりエラーメッセージが返却されること(String id) {
                class PathIdWrapper {
                    @ValidId
                    private String id;

                    public PathIdWrapper(String id) {
                        this.id = id;
                    }
                }
                PathIdWrapper wrapper = new PathIdWrapper(id);

                Set<ConstraintViolation<PathIdWrapper>> violations = validator.validate(wrapper);
                String expected = "idの形式が正しくありません";
                String actual = violations.iterator().next().getMessage();

                assertEquals(1, violations.size());
                assertEquals(expected, actual);
            }
        }
    }
}
