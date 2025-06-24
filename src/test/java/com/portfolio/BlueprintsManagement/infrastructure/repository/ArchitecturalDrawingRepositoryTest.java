package com.portfolio.BlueprintsManagement.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import com.portfolio.BlueprintsManagement.infrastructure.db.mapper.ArchitecturalDrawingMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
class ArchitecturalDrawingRepositoryTest {

    @Autowired
    private ArchitecturalDrawingRepository sut;

    @Mock
    private ArchitecturalDrawingMapper architecturalDrawingMapper;

    private String id;
    private String blueprintId;
    private ArchitecturalDrawing architecturalDrawing;

    @BeforeEach
    void before() {
        sut = new ArchitecturalDrawingRepository(architecturalDrawingMapper);
        createSampleData();
    }

    private void createSampleData() {
        id = "11000000-0000-1000-8000-000000000001";
        blueprintId = "10000000-0000-1000-8000-000000000001";
        architecturalDrawing = new ArchitecturalDrawing(id, blueprintId, "2025-01-01",
                "/static/image/hoge.png");
    }

    @Test
    void 図面画像情報の全件取得＿マッパーが実行され全件の図面画像情報が返却されること() {
        List<ArchitecturalDrawing> architecturalDrawingList = List.of(architecturalDrawing);
        when(architecturalDrawingMapper.selectByBlueprintId(blueprintId)).thenReturn(
                architecturalDrawingList);

        List<ArchitecturalDrawing> actual = sut.getArchitecturalDrawingsByBlueprintId(blueprintId);

        verify(architecturalDrawingMapper, times(1)).selectByBlueprintId(blueprintId);
        assertEquals(architecturalDrawingList, actual);
    }

    @Test
    void 図面画像情報の追加＿マッパーが実行されること() {
        sut.addArchitecturalDrawing(architecturalDrawing);

        verify(architecturalDrawingMapper, times(1)).add(architecturalDrawing);
    }

    @Test
    void 図面画像情報の存在確認＿マッパーが実行され真理値が返却されること() {
        when(architecturalDrawingMapper.existArchitecturalDrawingByBlueprintId(
                blueprintId)).thenReturn(true);
        boolean actual = sut.existArchitecturalDrawingByBlueprintId(blueprintId);

        verify(architecturalDrawingMapper, times(1)).existArchitecturalDrawingByBlueprintId(
                blueprintId);
        assertTrue(actual);
    }

    @Test
    void 図面画像情報の削除＿マッパーが実行されること() {
        sut.deleteArchitecturalDrawing(id);

        verify(architecturalDrawingMapper, times(1)).delete(id);
    }

}
