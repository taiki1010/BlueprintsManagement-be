package com.portfolio.BlueprintsManagement.presentation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.BlueprintsManagement.application.service.BlueprintService;
import com.portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import com.portfolio.BlueprintsManagement.domain.model.blueprint.Blueprint;
import com.portfolio.BlueprintsManagement.domain.model.blueprintInfo.BlueprintInfo;
import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.AddBlueprintRequest;
import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.DeleteBlueprintRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
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

    private ObjectMapper mapper;

    private String blueprintId;

    @Autowired
    public BlueprintControllerTest(ObjectMapper mapper) {
        this.mapper = new ObjectMapper();
        createInitialSampleData();
    }

    private void createInitialSampleData() {
        blueprintId = UUID.randomUUID().toString();
    }

    @Test
    void 図面情報の取得＿サービスが実行されidに該当する図面情報が返却されること() throws Exception {
        Blueprint blueprint = new Blueprint(blueprintId, UUID.randomUUID().toString(), "平面図");
        ArchitecturalDrawing architecturalDrawing = new ArchitecturalDrawing(UUID.randomUUID().toString(), blueprintId, "2025-01-01", "/static/image/hoge.png");
        BlueprintInfo blueprintInfo = new BlueprintInfo(blueprint, List.of(architecturalDrawing));
        String expectedJson = mapper.writeValueAsString(blueprintInfo);
        when(blueprintService.getBlueprintInfo(blueprintId)).thenReturn(blueprintInfo);

        mockMvc.perform(get("/blueprints/" + blueprintId))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(blueprintService, times(1)).getBlueprintInfo(blueprintId);
    }

    // FIXME: こちらを実行するとバリデーションがかかってしまいます。調べてみたのですが原因がわからず、一度ご確認よろしくお願いいたします。
    @Test
    void 図面の追加＿サービスが実行されokレスポンスと図面idが返却されること() throws Exception {
        byte[] dummyImageBytes = new byte[100];
        MockMultipartFile mockImage = new MockMultipartFile("image", "image.jpg", "image/jpeg", dummyImageBytes);
        AddBlueprintRequest request = new AddBlueprintRequest(UUID.randomUUID().toString(), "平面図", "2025-01-01", mockImage);
        Map<String, String> response = Map.of("id", blueprintId);
        String responseJson = mapper.writeValueAsString(response);
        when(blueprintService.addBlueprint(request)).thenReturn(blueprintId);

        mockMvc.perform(multipart("/blueprints")
                        .file(mockImage)
                        .param("siteId", UUID.randomUUID().toString())
                        .param("name", "平面図")
                        .param("createdAt", "2025-01-01"))
                .andExpect(status().isOk());
    }

    // TODO 上記が解決したら実装します
    @Test
    void 図面の更新() {

    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void 図面の削除＿サービスが実行されokレスポンスと論理値が返却されること(Boolean isBool) throws Exception {
        DeleteBlueprintRequest request = new DeleteBlueprintRequest(UUID.randomUUID().toString(), blueprintId, "2025-01-01", "/static/image/hoge.png");
        String requestJson = mapper.writeValueAsString(request);
        Map<String, Boolean> response = Map.of("isDeletedBlueprint", isBool);
        String responseJson = mapper.writeValueAsString(response);
        when(blueprintService.deleteBlueprint(request)).thenReturn(isBool);

        mockMvc.perform(delete("/blueprints")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }



}
