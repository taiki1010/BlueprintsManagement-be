package com.portfolio.BlueprintsManagement.infrastructure.repository;

import com.portfolio.BlueprintsManagement.domain.model.blueprint.Blueprint;
import com.portfolio.BlueprintsManagement.infrastructure.db.mapper.BlueprintMapper;
import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.UpdateBlueprintRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlueprintRepositoryTest {

    @Autowired
    private BlueprintRepository sut;

    @Mock
    private BlueprintMapper blueprintMapper;

    private String siteId;
    private String id;
    private Blueprint blueprint;

    @BeforeEach
    void before() {
        sut = new BlueprintRepository(blueprintMapper);
        createSampleData();
    }

    private void createSampleData() {
        siteId = "00000000-0000-1000-8000-000000000001";
        id = "10000000-0000-1000-8000-000000000001";
        blueprint = new Blueprint(id, siteId, "");
    }

    @Test
    void 現場idに該当する図面の存在確認＿マッパーが実行され真理値が返却されること() {
        when(blueprintMapper.existBlueprintBySiteId(siteId)).thenReturn(true);

        boolean actual = sut.existBlueprintBySiteId(siteId);

        verify(blueprintMapper, times(1)).existBlueprintBySiteId(siteId);
        assertTrue(actual);
    }

    @Test
    void 図面の存在確認＿マッパーが実行され真理値が返却されること() {
        when(blueprintMapper.existBlueprint(id)).thenReturn(true);

        boolean actual = sut.existBlueprint(id);

        verify(blueprintMapper, times(1)).existBlueprint(id);
        assertTrue(actual);
    }

    @Test
    void 図面の全件取得＿マッパーが実行され全件の図面が返却されること() {
        List<Blueprint> blueprintList = List.of(blueprint);
        when(blueprintMapper.selectBySiteId(siteId)).thenReturn(blueprintList);

        List<Blueprint> actual = sut.getBlueprintsBySiteId(siteId);

        verify(blueprintMapper, times(1)).selectBySiteId(siteId);
        assertEquals(blueprintList, actual);
    }

    @Test
    void 図面の一件取得＿マッパーが実行され一件の図面が返却されること() {
        when(blueprintMapper.select(id)).thenReturn(blueprint);

        Blueprint actual = sut.getBlueprint(id);

        verify(blueprintMapper, times(1)).select(id);
        assertEquals(blueprint, actual);
    }

    @Test
    void 図面の追加＿マッパーが実行されること() {
        sut.addBlueprint(blueprint);

        verify(blueprintMapper, times(1)).add(blueprint);
    }

    @Test
    void 図面の更新＿マッパーが実行されること() {
        UpdateBlueprintRequest request = new UpdateBlueprintRequest(id, "平面図");
        sut.updateBlueprint(request);

        verify(blueprintMapper, times(1)).update(request);
    }

    @Test
    void 図面の削除＿マッパーが実行されること() {
        sut.deleteBlueprint(id);

        verify(blueprintMapper, times(1)).delete(id);
    }
}
