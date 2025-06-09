package com.portfolio.BlueprintsManagement.presentation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.BlueprintsManagement.application.service.BlueprintService;
import com.portfolio.BlueprintsManagement.application.service.SiteService;
import com.portfolio.BlueprintsManagement.domain.model.blueprint.Blueprint;
import com.portfolio.BlueprintsManagement.domain.model.site.Site;
import com.portfolio.BlueprintsManagement.presentation.dto.request.site.SiteRequest;
import com.portfolio.BlueprintsManagement.presentation.exception.customException.NotFoundException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SiteController.class)
class SiteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private SiteService siteService;

    @MockitoBean
    private BlueprintService blueprintService;

    private ObjectMapper mapper;
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private String id;
    private Site site;
    private SiteRequest siteRequest;
    private Blueprint blueprint;

    @Autowired
    public SiteControllerTest(ObjectMapper mapper) {
        this.mapper = new ObjectMapper();
        createInitialSampleData();
    }

    private void createInitialSampleData() {
        id = UUID.randomUUID().toString();
        site = new Site(id, "佐藤邸", "東京都表参道", "");
        siteRequest = new SiteRequest("佐藤邸", "東京都表参道", "");
        blueprint = new Blueprint(UUID.randomUUID().toString(),id,"平面図");
    }

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
    void 現場の一件検索＿サービスが実行され現場情報一件が返却されること() throws Exception {
        when(siteService.getSite(id)).thenReturn(site);
        String expectedJson = mapper.writeValueAsString(site);

        mockMvc.perform(get("/sites/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(siteService, times(1)).getSite(id);
    }

    @Test
    void 図面一覧の検索＿サービスが実行され現場idに該当する図面一覧が返却されること() throws Exception {
        List<Blueprint> blueprintList = List.of(blueprint);
        when(blueprintService.getBlueprintsBySiteId(id)).thenReturn(blueprintList);
        String expectedJson = mapper.writeValueAsString(blueprintList);

        mockMvc.perform(get("/sites/" + id + "/blueprints"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(blueprintService, times(1)).getBlueprintsBySiteId(id);
    }

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
    void 現場の更新＿サービスが実行されokレスポンスとメッセージが返却されること() throws Exception {
        String requestJson = mapper.writeValueAsString(siteRequest);
        Map<String, String> message = Map.of("message", "現場情報が更新されました");
        String expectedJson = mapper.writeValueAsString(message);

        mockMvc.perform(put("/sites/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(siteService, times(1)).updateSite(siteRequest, id);
    }

    @Test
    void 現場の削除＿サービスが実行されokレスポンスとメッセージが返却されること() throws Exception {
        Map<String, String> message = Map.of("message", "現場が削除されました");
        String expectedJson = mapper.writeValueAsString(message);

        mockMvc.perform(delete("/sites/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(siteService, times(1)).deleteSite(id);
    }

}
