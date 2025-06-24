package com.portfolio.BlueprintsManagement.domain.model.blueprint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.AddBlueprintRequest;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

class BlueprintTest {

    @Test
    void リクエストを引数に実行すると図面が返却されること() {
        String siteId = "00000000-0000-1000-8000-000000000001";
        String name = "平面図";
        byte[] dummyImageFile = new byte[100];
        MockMultipartFile mockImage = new MockMultipartFile("imageFile", "image.png", "image/png",
                dummyImageFile);
        AddBlueprintRequest request = new AddBlueprintRequest(siteId, name, "2025-01-01",
                mockImage);

        Blueprint actual = Blueprint.formBlueprint(request);

        assertNotNull(actual.getId());
        assertEquals(siteId, actual.getSiteId());
        assertEquals(name, actual.getName());
    }

}
