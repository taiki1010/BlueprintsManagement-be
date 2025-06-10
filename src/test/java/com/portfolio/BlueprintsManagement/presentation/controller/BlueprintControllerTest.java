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
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    private AddBlueprintRequest addBlueprintRequest;
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
        addBlueprintRequest = new AddBlueprintRequest(siteId, "平面図", "2025-01-01", mockImage);
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

        @Nested
        class addBlueprintRequestTest {

            @Nested
            class siteIdValidationTest {

                @ParameterizedTest
                @ValueSource(strings = {"00000000-0000-1000-8000-000000000000", "ffffffff-ffff-5fff-bfff-ffffffffffff"})
                void 図面idがUUID形式に適している場合正常に処理が実行されること(String siteId) {
                    addBlueprintRequest.setSiteId(siteId);
                    Set<ConstraintViolation<AddBlueprintRequest>> violations = validator.validate(addBlueprintRequest);

                    assertEquals(0, violations.size());
                }

                @ParameterizedTest
                @ValueSource(strings = {"-1", "1", "abc", ""})
                void 図面idがUUID形式に適していない場合バリデーションチェックされること(String siteId) {
                    addBlueprintRequest.setSiteId(siteId);
                    Set<ConstraintViolation<AddBlueprintRequest>> violations = validator.validate(addBlueprintRequest);
                    String actual = violations.iterator().next().getMessage();

                    assertEquals(1, violations.size());
                    assertEquals("idの形式が正しくありません", actual);
                }
            }

            @Nested
            class nameValidationTest {

                @ParameterizedTest
                @ValueSource(strings = {"あ", "ああああああああああああああああああああ"})
                void 図面名が1文字以上20文字以内の場合にオブジェクトが作成されること(String name) {
                    addBlueprintRequest.setName(name);
                    Set<ConstraintViolation<AddBlueprintRequest>> violations = validator.validate(addBlueprintRequest);
                    int length = addBlueprintRequest.getName().length();
                    boolean isBool = 1 <= length & length <= 20;

                    assertTrue(isBool);
                    assertEquals(0, violations.size());
                }

                @Test
                void 図面名が20文字を超えた場合にバリデーションエラーになること() {
                    String name = "あああああああああああああああああああああ";
                    addBlueprintRequest.setName(name);
                    Set<ConstraintViolation<AddBlueprintRequest>> violations = validator.validate(addBlueprintRequest);
                    int length = addBlueprintRequest.getName().length();
                    boolean isBool = 20 < length;
                    String actual = violations.iterator().next().getMessage();

                    assertTrue(isBool);
                    assertEquals(1, violations.size());
                    assertEquals("図面名は20文字以内で入力してください", actual);
                }

                @Test
                void 図面名が空文字だった場合にバリデーションエラーになること() {
                    String name = "";
                    addBlueprintRequest.setName(name);
                    Set<ConstraintViolation<AddBlueprintRequest>> violations = validator.validate(addBlueprintRequest);
                    String actual = violations.iterator().next().getMessage();

                    assertEquals(1, violations.size());
                    assertEquals("入力欄が空です", actual);
                }
            }

            @Nested
            class createdAtValidationTest {

                @ParameterizedTest
                @ValueSource(strings = {"0000-01-01", "9999-12-31"})
                void 作成日が適切な文字列の場合にオブジェクトが作成されること(String createdAt) {
                    addBlueprintRequest.setCreatedAt(createdAt);
                    Set<ConstraintViolation<AddBlueprintRequest>> violations = validator.validate(addBlueprintRequest);

                    assertEquals(0, violations.size());
                }

                @ParameterizedTest
                @ValueSource(strings = {"000-01-01", "9999-12-311"})
                void 作成日が適切ではない文字列の場合にバリデーションチェックがかかること(String createdAt) {
                    addBlueprintRequest.setCreatedAt(createdAt);
                    Set<ConstraintViolation<AddBlueprintRequest>> violations = validator.validate(addBlueprintRequest);
                    String actual = violations.iterator().next().getMessage();

                    assertEquals(1, violations.size());
                    assertEquals("入力された日付の形式が正しくありません", actual);
                }

                @Test
                void 作成日が空文字の場合にバリデーションチェックがかかること() {
                    String createdAt = "";
                    addBlueprintRequest.setCreatedAt(createdAt);
                    Set<ConstraintViolation<AddBlueprintRequest>> violations = validator.validate(addBlueprintRequest);
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
                    addBlueprintRequest.setImageFile(mockImage);
                    Set<ConstraintViolation<AddBlueprintRequest>> violations = validator.validate(addBlueprintRequest);
                    String actual = violations.iterator().next().getMessage();

                    assertEquals(1, violations.size());
                    assertEquals("ファイルサイズは5MBまでにしてください", actual);
                }

                @Test
                void ファイル形式が異なる場合バリデーションチェックがかかること() {
                    byte[] dummyFileBytes = new byte[100];
                    MockMultipartFile mockFile = new MockMultipartFile("imageFile", "text.txt", "text/plain", dummyFileBytes);
                    addBlueprintRequest.setImageFile(mockFile);
                    Set<ConstraintViolation<AddBlueprintRequest>> violations = validator.validate(addBlueprintRequest);
                    String actual = violations.iterator().next().getMessage();

                    assertEquals(1, violations.size());
                    assertEquals("画像ファイルを選択してください", actual);
                }
            }
        }

        @Nested
        class updateBlueprintRequestTest {

            @Nested
            class idValidationTest {

                @ParameterizedTest
                @ValueSource(strings = {"00000000-0000-1000-8000-000000000000", "ffffffff-ffff-5fff-bfff-ffffffffffff"})
                void idがUUID形式に適している場合正常に処理が実行されること(String id) {
                    updateBlueprintRequest.setId(id);
                    Set<ConstraintViolation<UpdateBlueprintRequest>> violations = validator.validate(updateBlueprintRequest);

                    assertEquals(0, violations.size());
                }

                @ParameterizedTest
                @ValueSource(strings = {"-1", "1", "abc", ""})
                void idがUUID形式に適していない場合バリデーションチェックされること(String id) {
                    updateBlueprintRequest.setId(id);
                    Set<ConstraintViolation<UpdateBlueprintRequest>> violations = validator.validate(updateBlueprintRequest);
                    String actual = violations.iterator().next().getMessage();

                    assertEquals(1, violations.size());
                    assertEquals("idの形式が正しくありません", actual);
                }
            }

            @Nested
            class nameValidationTest {

                @ParameterizedTest
                @ValueSource(strings = {"あ", "ああああああああああああああああああああ"})
                void 図面名が1文字以上20文字以内の場合にオブジェクトが作成されること(String name) {
                    updateBlueprintRequest.setName(name);
                    Set<ConstraintViolation<UpdateBlueprintRequest>> violations = validator.validate(updateBlueprintRequest);
                    int length = updateBlueprintRequest.getName().length();
                    boolean isBool = 1 <= length & length <= 20;

                    assertTrue(isBool);
                    assertEquals(0, violations.size());
                }

                @Test
                void 図面名が20文字を超えた場合にバリデーションエラーになること() {
                    String name = "あああああああああああああああああああああ";
                    updateBlueprintRequest.setName(name);
                    Set<ConstraintViolation<UpdateBlueprintRequest>> violations = validator.validate(updateBlueprintRequest);
                    int length = updateBlueprintRequest.getName().length();
                    boolean isBool = 20 < length;
                    String actual = violations.iterator().next().getMessage();

                    assertTrue(isBool);
                    assertEquals(1, violations.size());
                    assertEquals("図面名は20文字以内で入力してください", actual);
                }

                @Test
                void 図面名が空文字だった場合にバリデーションエラーになること() {
                    String name = "";
                    updateBlueprintRequest.setName(name);
                    Set<ConstraintViolation<UpdateBlueprintRequest>> violations = validator.validate(updateBlueprintRequest);
                    String actual = violations.iterator().next().getMessage();

                    assertEquals(1, violations.size());
                    assertEquals("入力欄が空です", actual);
                }
            }
        }

        @Nested
        class deleteBlueprintRequestTest {

            @Nested
            class siteIdValidationTest {

                @ParameterizedTest
                @ValueSource(strings = {"00000000-0000-1000-8000-000000000000", "ffffffff-ffff-5fff-bfff-ffffffffffff"})
                void idがUUID形式に適している場合正常に処理が実行されること(String id) {
                    deleteBlueprintRequest.setId(id);
                    Set<ConstraintViolation<DeleteBlueprintRequest>> violations = validator.validate(deleteBlueprintRequest);

                    assertEquals(0, violations.size());
                }

                @ParameterizedTest
                @ValueSource(strings = {"-1", "1", "abc", ""})
                void idがUUID形式に適していない場合バリデーションチェックされること(String id) {
                    deleteBlueprintRequest.setId(id);
                    Set<ConstraintViolation<DeleteBlueprintRequest>> violations = validator.validate(deleteBlueprintRequest);
                    String actual = violations.iterator().next().getMessage();

                    assertEquals(1, violations.size());
                    assertEquals("idの形式が正しくありません", actual);
                }
            }

            @Nested
            class blueprintIdValidationTest {

                @ParameterizedTest
                @ValueSource(strings = {"00000000-0000-1000-8000-000000000000", "ffffffff-ffff-5fff-bfff-ffffffffffff"})
                void idがUUID形式に適している場合正常に処理が実行されること(String id) {
                    deleteBlueprintRequest.setBlueprintId(id);
                    Set<ConstraintViolation<DeleteBlueprintRequest>> violations = validator.validate(deleteBlueprintRequest);

                    assertEquals(0, violations.size());
                }

                @ParameterizedTest
                @ValueSource(strings = {"-1", "1", "abc", ""})
                void idがUUID形式に適していない場合バリデーションチェックされること(String id) {
                    deleteBlueprintRequest.setBlueprintId(id);
                    Set<ConstraintViolation<DeleteBlueprintRequest>> violations = validator.validate(deleteBlueprintRequest);
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
                    deleteBlueprintRequest.setCreatedAt(createdAt);
                    Set<ConstraintViolation<DeleteBlueprintRequest>> violations = validator.validate(deleteBlueprintRequest);

                    assertEquals(0, violations.size());
                }

                @ParameterizedTest
                @ValueSource(strings = {"000-01-01", "9999-12-311"})
                void 作成日が適切ではない文字列の場合にバリデーションチェックがかかること(String createdAt) {
                    deleteBlueprintRequest.setCreatedAt(createdAt);
                    Set<ConstraintViolation<DeleteBlueprintRequest>> violations = validator.validate(deleteBlueprintRequest);
                    String actual = violations.iterator().next().getMessage();

                    assertEquals(1, violations.size());
                    assertEquals("入力された日付の形式が正しくありません", actual);
                }

                @Test
                void 作成日が空文字の場合にバリデーションチェックがかかること() {
                    String createdAt = "";
                    deleteBlueprintRequest.setCreatedAt(createdAt);
                    Set<ConstraintViolation<DeleteBlueprintRequest>> violations = validator.validate(deleteBlueprintRequest);
                    String actual = violations.iterator().next().getMessage();

                    assertEquals(1, violations.size());
                    assertEquals("入力欄が空です", actual);
                }
            }

        }
    }
}
