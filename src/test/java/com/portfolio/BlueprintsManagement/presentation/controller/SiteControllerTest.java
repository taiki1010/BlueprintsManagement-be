package com.portfolio.BlueprintsManagement.presentation.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.BlueprintsManagement.application.service.BlueprintService;
import com.portfolio.BlueprintsManagement.application.service.SiteService;
import com.portfolio.BlueprintsManagement.domain.model.blueprint.Blueprint;
import com.portfolio.BlueprintsManagement.domain.model.site.Site;
import com.portfolio.BlueprintsManagement.presentation.dto.message.ErrorMessage;
import com.portfolio.BlueprintsManagement.presentation.dto.message.SuccessMessage;
import com.portfolio.BlueprintsManagement.presentation.dto.request.site.SiteRequest;
import com.portfolio.BlueprintsManagement.presentation.exception.customException.NotFoundException;
import com.portfolio.BlueprintsManagement.presentation.exception.validation.idValidation.ValidId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SiteController.class)
class SiteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private SiteService siteService;

    @MockitoBean
    private BlueprintService blueprintService;

    @Autowired
    ObjectMapper mapper;

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private String id;
    private Site site;
    private SiteRequest siteRequest;
    private Blueprint blueprint;

    @BeforeEach
    void before() {
        createInitialSampleData();
    }

    private void createInitialSampleData() {
        id = "00000000-0000-1000-8000-000000000001";
        site = new Site(id, "佐藤邸", "東京都表参道", "");
        siteRequest = new SiteRequest("佐藤邸", "東京都表参道", "");
        blueprint = new Blueprint("10000000-0000-1000-8000-000000000001", id, "平面図");
    }

    @Nested
    class checkExistSitesTest {

        @Test
        void 現場の存在確認＿現場が存在する場合okレスポンスが返却されること() throws Exception {
            when(siteService.checkExistSites()).thenReturn(true);

            mockMvc.perform(head("/sites"))
                    .andExpect(status().isOk());
        }

        @Test
        void 現場の存在確認＿現場が存在しない場合NotFoundレスポンスが返却されること()
                throws Exception {
            when(siteService.checkExistSites()).thenReturn(false);

            mockMvc.perform(head("/sites"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class searchSitesTest {

        @Test
        void 現場の一覧検索＿サービスが実行され現場情報の一覧が返却されること() throws Exception {
            List<Site> siteList = List.of(site);

            when(siteService.getSites()).thenReturn(siteList);
            String expectedJson = mapper.writeValueAsString(siteList);

            mockMvc.perform(get("/sites"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedJson));

            verify(siteService, times(1)).getSites();
        }

        @Test
        void 現場の一覧検索＿例外処理が発生した場合エラーメッセージが返却されること()
                throws Exception {
            when(siteService.getSites()).thenThrow(
                    new NotFoundException(ErrorMessage.NOT_FOUND_SITES.getMessage()));
            Map<String, String> response = Map.of("message",
                    ErrorMessage.NOT_FOUND_SITES.getMessage());
            String expectedJson = mapper.writeValueAsString(response);

            mockMvc.perform(get("/sites"))
                    .andExpect(status().isNotFound())
                    .andExpect(content().json(expectedJson));
        }
    }

    @Nested
    class searchSiteTest {

        @Test
        void 現場の一件検索＿サービスが実行され現場情報が一件返却されること() throws Exception {
            when(siteService.getSite(id)).thenReturn(site);
            String expectedJson = mapper.writeValueAsString(site);

            mockMvc.perform(get("/sites/" + id))
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedJson));

            verify(siteService, times(1)).getSite(id);
        }

        @Test
        void 現場の一件検索＿例外処理が発生した場合エラーメッセージが返却されること()
                throws Exception {
            when(siteService.getSite(id)).thenThrow(
                    new NotFoundException(ErrorMessage.NOT_FOUND_SITE_BY_ID.getMessage()));
            Map<String, String> response = Map.of("message",
                    ErrorMessage.NOT_FOUND_SITE_BY_ID.getMessage());
            String expectedJson = mapper.writeValueAsString(response);

            mockMvc.perform(get("/sites/" + id))
                    .andExpect(status().isNotFound())
                    .andExpect(content().json(expectedJson));
        }

        @Test
        void 現場の一件検索＿idがUUID形式に不適切な場合エラーメッセージが返却されること()
                throws Exception {
            id = "1";
            Map<String, String> response = Map.of("message",
                    ErrorMessage.ID_MUST_BE_UUID.getMessage());
            String expectedJson = mapper.writeValueAsString(response);

            mockMvc.perform(get("/sites/" + id))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().json(expectedJson));
        }
    }

    @Nested
    class searchBlueprintsBySiteId {

        @Test
        void 図面一覧の検索＿サービスが実行され現場idに該当する図面一覧が返却されること()
                throws Exception {
            List<Blueprint> blueprintList = List.of(blueprint);
            when(blueprintService.getBlueprintsBySiteId(id)).thenReturn(blueprintList);
            String expectedJson = mapper.writeValueAsString(blueprintList);

            mockMvc.perform(get("/sites/" + id + "/blueprints"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedJson));

            verify(blueprintService, times(1)).getBlueprintsBySiteId(id);
        }

        @Test
        void 図面一覧の検索＿例外処理が発生した場合エラーメッセージが返却されること()
                throws Exception {
            when(blueprintService.getBlueprintsBySiteId(id)).thenThrow(
                    new NotFoundException(ErrorMessage.NOT_FOUND_BLUEPRINT_BY_ID.getMessage()));
            Map<String, String> response = Map.of("message",
                    ErrorMessage.NOT_FOUND_BLUEPRINT_BY_ID.getMessage());
            String expectedJson = mapper.writeValueAsString(response);

            mockMvc.perform(get("/sites/" + id + "/blueprints"))
                    .andExpect(status().isNotFound())
                    .andExpect(content().json(expectedJson));
        }
    }

    @Nested
    class registerSiteTest {

        @Test
        void 現場の登録＿サービスが実行されokレスポンスと現場idが返却されること() throws Exception {
            when(siteService.addSite(siteRequest)).thenReturn(id);
            String requestJson = mapper.writeValueAsString(siteRequest);
            Map<String, String> response = Map.of("id", id);
            String expectedJson = mapper.writeValueAsString(response);

            mockMvc.perform(post("/sites")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedJson));

            verify(siteService, times(1)).addSite(siteRequest);
        }

        @Test
        void 現場の登録＿リクエストに不適切な情報が存在した場合エラーメッセージが返却されること()
                throws Exception {
            String name = "あああああああああああああああああああああああああああああああああああああああああああああああああああ";
            siteRequest.setName(name);
            String requestJson = mapper.writeValueAsString(siteRequest);

            String message = ErrorMessage.CHAR_COUNT_SITE_NAME_TOO_LONG.getMessage();
            Map<String, String> response = Map.of("message", message);
            String expectedJson = mapper.writeValueAsString(response);

            mockMvc.perform(post("/sites")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().json(expectedJson));
        }
    }

    @Nested
    class updateSiteTest {

        @Test
        void 現場の更新＿サービスが実行されokレスポンスとメッセージが返却されること()
                throws Exception {
            String requestJson = mapper.writeValueAsString(siteRequest);
            Map<String, String> response = Map.of("message",
                    SuccessMessage.COMPLETE_UPDATE_SITE.getMessage());
            String expectedJson = mapper.writeValueAsString(response);

            mockMvc.perform(put("/sites/" + id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedJson));

            verify(siteService, times(1)).updateSite(siteRequest, id);
        }

        @Test
        void 現場の更新＿リクエストに不適切な情報が存在した場合エラーメッセージが返却されること()
                throws Exception {
            String name = "あああああああああああああああああああああああああああああああああああああああああああああああああああ";
            siteRequest.setName(name);
            String requestJson = mapper.writeValueAsString(siteRequest);

            String errorMessage = ErrorMessage.CHAR_COUNT_SITE_NAME_TOO_LONG.getMessage();
            Map<String, String> response = Map.of("message", errorMessage);
            String expectedJson = mapper.writeValueAsString(response);

            mockMvc.perform(put("/sites/" + id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().json(expectedJson));
        }

        @Test
        void 現場の更新＿idがUUID形式に不適切な場合エラーメッセージが返却されること()
                throws Exception {
            String requestJson = mapper.writeValueAsString(siteRequest);
            id = "1";
            Map<String, String> response = Map.of("message",
                    ErrorMessage.ID_MUST_BE_UUID.getMessage());
            String expectedJson = mapper.writeValueAsString(response);

            mockMvc.perform(put("/sites/" + id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().json(expectedJson));
        }

    }

    @Nested
    class deleteSiteTest {

        @Test
        void 現場の削除＿サービスが実行されokレスポンスとメッセージが返却されること()
                throws Exception {
            Map<String, String> response = Map.of("message",
                    SuccessMessage.COMPLETE_DELETE_SITE.getMessage());
            String expectedJson = mapper.writeValueAsString(response);

            mockMvc.perform(delete("/sites/" + id))
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedJson));

            verify(siteService, times(1)).deleteSite(id);
        }

        @Test
        void 現場の削除＿idがUUID形式に不適切な場合エラーメッセージが返却されること()
                throws Exception {
            id = "1";
            Map<String, String> response = Map.of("message",
                    ErrorMessage.ID_MUST_BE_UUID.getMessage());
            String expectedJson = mapper.writeValueAsString(response);

            mockMvc.perform(delete("/sites/" + id))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().json(expectedJson));
        }
    }


    @Nested
    class ValidationTest {

        @Nested
        class idValidationTest {

            @ParameterizedTest
            @ValueSource(strings = {"00000000-0000-1000-8000-000000000000",
                    "ffffffff-ffff-5fff-bfff-ffffffffffff"})
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
            void idがUUID形式に適していない場合バリデーションチェックがかかりエラーメッセージが返却されること(
                    String id) {
                class PathIdWrapper {

                    @ValidId
                    private String id;

                    public PathIdWrapper(String id) {
                        this.id = id;
                    }
                }
                PathIdWrapper wrapper = new PathIdWrapper(id);

                Set<ConstraintViolation<PathIdWrapper>> violations = validator.validate(wrapper);
                String expected = ErrorMessage.ID_MUST_BE_UUID.getMessage();
                String actual = violations.iterator().next().getMessage();

                assertEquals(1, violations.size());
                assertEquals(expected, actual);
            }
        }

    }
}
