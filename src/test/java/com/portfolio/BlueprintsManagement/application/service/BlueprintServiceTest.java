package com.portfolio.BlueprintsManagement.application.service;

import com.portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import com.portfolio.BlueprintsManagement.domain.model.blueprint.Blueprint;
import com.portfolio.BlueprintsManagement.domain.model.blueprintInfo.BlueprintInfo;
import com.portfolio.BlueprintsManagement.domain.repository.IArchitecturalDrawingRepository;
import com.portfolio.BlueprintsManagement.domain.repository.IBlueprintRepository;
import com.portfolio.BlueprintsManagement.presentation.dto.message.ErrorMessage;
import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.AddBlueprintRequest;
import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.DeleteBlueprintRequest;
import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.UpdateBlueprintRequest;
import com.portfolio.BlueprintsManagement.presentation.exception.customException.FailedToPutObjectException;
import com.portfolio.BlueprintsManagement.presentation.exception.customException.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlueprintServiceTest {

    private BlueprintService sut;

    @Mock
    private IBlueprintRepository blueprintRepository;

    @Mock
    private IArchitecturalDrawingRepository architecturalDrawingRepository;

    @Mock
    private S3Client s3;

    private String siteId;
    private String blueprintId;
    private String architecturalDrawingId;
    private Blueprint blueprint;
    private ArchitecturalDrawing architecturalDrawing;
    private byte[] dummyImageFile;

    private AddBlueprintRequest request;
    private MockedStatic<Blueprint> blueprintMockedStatic;
    private MockedStatic<ArchitecturalDrawing> architecturalDrawingMockedStatic;

    @BeforeEach
    public void before() {
        sut = new BlueprintService(blueprintRepository, architecturalDrawingRepository, s3, "test-bucket");
        createInitialSampleData();
        dummyImageFile = new byte[100];
        MockMultipartFile mockImage = new MockMultipartFile("imageFile", "dummy.png", "image/png", dummyImageFile);
        request = new AddBlueprintRequest(siteId, "平面図", "2025-01-01", mockImage);

        blueprintMockedStatic = mockStatic(Blueprint.class);
        blueprintMockedStatic.when(() -> Blueprint.formBlueprint(any())).thenReturn(blueprint);
        architecturalDrawingMockedStatic = mockStatic(ArchitecturalDrawing.class);
        architecturalDrawingMockedStatic.when(() -> ArchitecturalDrawing.formArchitecturalDrawingFromBlueprintRequest(any(), any(), any())).thenReturn(architecturalDrawing);
    }

    @AfterEach
    public void after() {
        blueprintMockedStatic.close();
        architecturalDrawingMockedStatic.close();
    }

    public void createInitialSampleData() {
        blueprintId = "10000000-0000-1000-8000-000000000001";
        siteId = "00000000-0000-1000-8000-000000000001";
        blueprint = new Blueprint(blueprintId, siteId, "平面図");

        architecturalDrawingId = "11000000-0000-1000-8000-000000000001";
        architecturalDrawing = new ArchitecturalDrawing(architecturalDrawingId, blueprintId, "2025-01-01", "image/hoge.png");
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
            String actual = result.getMessage();

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

    @Nested
    class addBlueprintTest {

        @Test
        void 図面の追加＿リポジトリとputObjectメソッドが実行されること() throws IOException {
            String actual = sut.addBlueprint(request);

            verify(blueprintRepository, times(1)).addBlueprint(blueprint);
            verify(architecturalDrawingRepository, times(1)).addArchitecturalDrawing(architecturalDrawing);
            verify(s3).putObject(any(PutObjectRequest.class), any(RequestBody.class));
            assertEquals(blueprintId, actual);
        }

        @Test
        void 図面の追加＿putObjectメソッドが失敗したとき例外処理が発生すること() {
            when(s3.putObject(any(PutObjectRequest.class), any(RequestBody.class))).thenThrow(new FailedToPutObjectException(ErrorMessage.FAILED_TO_PUT_OBJECT.getMessage()));

            FailedToPutObjectException result = assertThrows(FailedToPutObjectException.class, () -> sut.addBlueprint(request));

            assertEquals("ファイルのアップロードに失敗しました", result.getMessage());
        }
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
