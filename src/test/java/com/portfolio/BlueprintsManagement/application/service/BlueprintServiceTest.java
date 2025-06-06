package com.portfolio.BlueprintsManagement.application.service;

import com.portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import com.portfolio.BlueprintsManagement.domain.model.blueprint.Blueprint;
import com.portfolio.BlueprintsManagement.domain.model.blueprintInfo.BlueprintInfo;
import com.portfolio.BlueprintsManagement.domain.repository.IArchitecturalDrawingRepository;
import com.portfolio.BlueprintsManagement.domain.repository.IBlueprintRepository;
import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.DeleteBlueprintRequest;
import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.UpdateBlueprintRequest;
import com.portfolio.BlueprintsManagement.presentation.exception.customException.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlueprintServiceTest {

    private BlueprintService sut;

    @Mock
    private IBlueprintRepository blueprintRepository;

    @Mock
    private IArchitecturalDrawingRepository architecturalDrawingRepository;

    private String siteId;
    private String blueprintId;
    private String architecturalDrawingId;
    private Blueprint blueprint;
    private ArchitecturalDrawing architecturalDrawing;

    @BeforeEach
    public void before() {
        sut = new BlueprintService(blueprintRepository, architecturalDrawingRepository);
        createInitialSampleData();
    }

    public void createInitialSampleData() {
        blueprintId = UUID.randomUUID().toString();
        siteId = UUID.randomUUID().toString();
        blueprint = new Blueprint(blueprintId, siteId, "平面図");

        architecturalDrawingId = UUID.randomUUID().toString();
        architecturalDrawing = new ArchitecturalDrawing(architecturalDrawingId, blueprintId, "2025-01-01", "static/image/hoge.png");
    }

    @Nested
    class getBlueprintBySiteIdTest {
        @Test
        void 図面の全件取得＿リポジトリが実行され図面リストが返却されること() throws NotFoundException {
            when(blueprintRepository.existBlueprintBySiteId(siteId)).thenReturn(true);
            when(blueprintRepository.getBlueprintsBySiteId(siteId)).thenReturn(List.of(blueprint));
            List<Blueprint> expected = List.of(blueprint);

            List<Blueprint> actual = sut.getBlueprintsBySiteId(siteId);

            assertEquals(expected, actual);
        }

        @Test
        void 図面の全件取得＿現場idに該当する図面が存在しない場合例外処理が呼ばれること() {
            when(blueprintRepository.existBlueprintBySiteId(siteId)).thenReturn(false);
            String expected = "現場idに該当する図面情報が存在しません";

            NotFoundException result = assertThrows(NotFoundException.class, () -> sut.getBlueprintsBySiteId(siteId));
            String actual = result.getMessage();;

            assertEquals(expected, actual);
        }
    }

    @Nested
    class getBlueprintInfoTest {
        @Test
        void 図面情報の一件取得＿リポジトリが実行され図面情報が一件分返却されること() throws NotFoundException {
            when(blueprintRepository.existBlueprint(blueprintId)).thenReturn(true);
            when(blueprintRepository.getBlueprint(blueprintId)).thenReturn(blueprint);
            when(architecturalDrawingRepository.getArchitecturalDrawingsByBlueprintId(blueprintId)).thenReturn(List.of(architecturalDrawing));
            BlueprintInfo expected = new BlueprintInfo(blueprint, List.of(architecturalDrawing));

            BlueprintInfo actual = sut.getBlueprintInfo(blueprintId);

            assertEquals(expected, actual);
        }

        @Test
        void 図面情報の一件取得＿図面idに該当する図面が存在しない場合例外処理が呼ばれること() {
            when(blueprintRepository.existBlueprint(blueprintId)).thenReturn(false);
            String expected = "idに該当する図面情報が存在しません";

            NotFoundException result = assertThrows(NotFoundException.class, () -> sut.getBlueprintInfo(blueprintId));
            String actual = result.getMessage();

            assertEquals(expected, actual);
        }
    }

    // TODO ファイル処理のテスト実装方法を調べるため一時保留
    @Test
    void 図面の追加 () {

    }

    @Test
    void 図面の更新＿リポジトリが実行されること() {
        UpdateBlueprintRequest request = new UpdateBlueprintRequest(blueprintId, "平面図");

        sut.updateBlueprint(request);

        verify(blueprintRepository, times(1)).updateBlueprint(request);
    }

    @Nested
    class deleteBlueprintTest {
        @Test
        void 図面削除＿図面画像が削除され図面idに該当する図面画像が存在しなくなった場合falseを返却すること() {
            DeleteBlueprintRequest request = new DeleteBlueprintRequest(siteId, blueprintId, "2025-01-01", "static/image/hoge.png");
            when(architecturalDrawingRepository.existArchitecturalDrawingByBlueprintId(request.getBlueprintId())).thenReturn(true);

            assertFalse(sut.deleteBlueprint(request));
        }

        @Test
        void 図面削除＿図面画像が削除され図面idに該当する図面画像がまだ存在する場合リポジトリが実行されtrueを返却すること() {
            DeleteBlueprintRequest request = new DeleteBlueprintRequest(siteId, blueprintId, "2025-01-01", "static/image/hoge.png");
            when(architecturalDrawingRepository.existArchitecturalDrawingByBlueprintId(request.getBlueprintId())).thenReturn(false);

            assertTrue(sut.deleteBlueprint(request));
            verify(blueprintRepository, times(1)).deleteBlueprint(blueprintId);
        }
    }

}
