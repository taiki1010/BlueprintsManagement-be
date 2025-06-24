package com.portfolio.BlueprintsManagement.infrastructure.db.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

@MybatisTest
class ArchitecturalDrawingMapperTest {

    @Autowired
    ArchitecturalDrawingMapper sut;

    private List<ArchitecturalDrawing> architecturalDrawingList;

    @BeforeEach
    void before() {
        architecturalDrawingList = createSampleArchitecturalDrawingList();
    }

    List<ArchitecturalDrawing> createSampleArchitecturalDrawingList() {
        return new ArrayList<ArchitecturalDrawing>(
                Arrays.asList(
                        new ArchitecturalDrawing("11000000-0000-1000-8000-000000000001",
                                "10000000-0000-1000-8000-000000000001", "2025-01-01",
                                "/static/image/floor_plan1.png"),
                        new ArchitecturalDrawing("11000000-0000-1000-8000-000000000002",
                                "10000000-0000-1000-8000-000000000002", "2025-01-01",
                                "/static/image/wiring_diagram1.png"),
                        new ArchitecturalDrawing("11000000-0000-1000-8000-000000000003",
                                "10000000-0000-1000-8000-000000000003", "2025-01-01",
                                "/static/image/elevation.png")
                )
        );
    }

    @Nested
    class existArchitecturalDrawingByBlueprintIdTest {

        @Test
        void 図面画像情報の存在確認＿図面idに該当する図面画像情報が存在する場合trueが返却されること() {
            String blueprintId = "10000000-0000-1000-8000-000000000001";

            boolean actual = sut.existArchitecturalDrawingByBlueprintId(blueprintId);

            assertTrue(actual);
        }

        @Test
        void 図面画像情報の存在確認＿図面idに該当する図面画像情報が存在しない場合falseが返却されること() {
            String blueprintId = "10000000-0000-1000-8000-000000000004";

            boolean actual = sut.existArchitecturalDrawingByBlueprintId(blueprintId);

            assertFalse(actual);
        }
    }

    @Nested
    class existArchitecturalDrawing {

        @Test
        void 図面画像情報の存在確認＿idに該当する図面画像情報が存在する場合trueが返却されること() {
            String id = "11000000-0000-1000-8000-000000000001";

            boolean actual = sut.existArchitecturalDrawing(id);

            assertTrue(actual);
        }

        @Test
        void 図面画像情報の存在確認＿idに該当する図面画像情報が存在しない場合falseが返却されること() {
            String id = "11000000-0000-1000-8000-000000000004";

            boolean actual = sut.existArchitecturalDrawing(id);

            assertFalse(actual);
        }
    }

    @Test
    void 図面画像情報の検索＿図面idに該当する図面画像情報が全件返却されること() {
        String blueprintId = "10000000-0000-1000-8000-000000000001";
        List<ArchitecturalDrawing> expected = List.of(
                new ArchitecturalDrawing("11000000-0000-1000-8000-000000000001",
                        "10000000-0000-1000-8000-000000000001", "2025-01-01",
                        "/static/image/floor_plan1.png"));

        List<ArchitecturalDrawing> actual = sut.selectByBlueprintId(blueprintId);

        assertEquals(1, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void 図面画像情報の追加＿図面画像情報が追加されること() {
        String blueprintId = "10000000-0000-1000-8000-000000000001";
        ArchitecturalDrawing newArchitecturalDrawing = new ArchitecturalDrawing(
                "11000000-0000-1000-8000-000000000004", blueprintId, "2025-01-02",
                "/static/image/floor_plan2.png");
        architecturalDrawingList.add(newArchitecturalDrawing);
        List<ArchitecturalDrawing> expected = List.of(
                new ArchitecturalDrawing("11000000-0000-1000-8000-000000000001",
                        "10000000-0000-1000-8000-000000000001", "2025-01-01",
                        "/static/image/floor_plan1.png"),
                new ArchitecturalDrawing("11000000-0000-1000-8000-000000000004",
                        "10000000-0000-1000-8000-000000000001", "2025-01-02",
                        "/static/image/floor_plan2.png")
        );

        sut.add(newArchitecturalDrawing);
        List<ArchitecturalDrawing> actual = sut.selectByBlueprintId(blueprintId);

        assertEquals(2, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void 図面画像情報の削除＿idに該当する図面画像情報が削除されること() {
        String id = "11000000-0000-1000-8000-000000000001";

        sut.delete(id);
        boolean actual = sut.existArchitecturalDrawing(id);

        assertFalse(actual);
    }

}
