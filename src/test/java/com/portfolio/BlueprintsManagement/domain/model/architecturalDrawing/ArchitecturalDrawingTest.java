package com.portfolio.BlueprintsManagement.domain.model.architecturalDrawing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.portfolio.BlueprintsManagement.presentation.dto.request.architecturalDrawing.ArchitecturalDrawingRequest;
import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.AddBlueprintRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

class ArchitecturalDrawingTest {

    private String siteId;
    private String name;
    private String createdAt;
    private String blueprintId;
    private String filePath;
    private MockMultipartFile mockImage;

    @BeforeEach
    void before() {
        createSampleData();
    }

    public void createSampleData() {
        siteId = "00000000-0000-1000-8000-000000000001";
        name = "平面図";
        createdAt = "2025-01-01";
        blueprintId = "10000000-0000-1000-8000-000000000001";
        filePath = "/static/image/hoge.png";
        byte[] dummyImageFile = new byte[100];
        mockImage = new MockMultipartFile("imageFile", "image.png", "image/png", dummyImageFile);
    }

    @Test
    void 図面追加リクエストを引数に実行すると図面画像情報が返却されること() {
        AddBlueprintRequest request = new AddBlueprintRequest(siteId, name, createdAt, mockImage);

        ArchitecturalDrawing actual = ArchitecturalDrawing.formArchitecturalDrawingFromBlueprintRequest(
                request, blueprintId, filePath);

        assertNotNull(actual.getId());
        assertEquals(blueprintId, actual.getBlueprintId());
        assertEquals(createdAt, actual.getCreatedAt());
        assertEquals(filePath, actual.getFilePath());
    }

    @Test
    void リクエストを引数に実行すると図面画像情報が返却されること() {
        ArchitecturalDrawingRequest request = new ArchitecturalDrawingRequest(blueprintId,
                createdAt, mockImage);

        ArchitecturalDrawing actual = ArchitecturalDrawing.formArchitecturalDrawing(request,
                filePath);

        assertNotNull(actual.getId());
        assertEquals(blueprintId, actual.getBlueprintId());
        assertEquals(createdAt, actual.getCreatedAt());
        assertEquals(filePath, actual.getFilePath());
    }

}
