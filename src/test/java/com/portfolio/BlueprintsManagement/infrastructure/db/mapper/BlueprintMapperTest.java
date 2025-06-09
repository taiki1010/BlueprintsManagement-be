package com.portfolio.BlueprintsManagement.infrastructure.db.mapper;

import com.portfolio.BlueprintsManagement.domain.model.blueprint.Blueprint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
class BlueprintMapperTest {

    @Autowired
    BlueprintMapper sut;

    private List<Blueprint> blueprintList;

    @BeforeEach
    void before() {
        blueprintList = createSampleBlueprintList();
    }

    private List<Blueprint> createSampleBlueprintList() {
        return new ArrayList<Blueprint>(
                Arrays.asList(
                        new Blueprint("10000000-0000-1000-8000-000000000001", "00000000-0000-1000-8000-000000000001", "平面図 １階"),
                        new Blueprint("10000000-0000-1000-8000-000000000002", "00000000-0000-1000-8000-000000000002", "配線図 １階"),
                        new Blueprint("10000000-0000-1000-8000-000000000003", "00000000-0000-1000-8000-000000000003", "立面図")
                )
        );
    }

    @Nested
    class existBlueprintBySiteIdTest {
        @Test
        void 図面の存在確認＿現場idに該当する図面が存在する場合trueが返却されること() {
            String siteId = "00000000-0000-1000-8000-000000000001";
            boolean actual = sut.existBlueprintBySiteId(siteId);
            assertTrue(actual);
        }

        @Test
        void 図面の存在確認＿現場idに該当する図面が存在しない場合falseが返却されること() {
            String siteId = "00000000-0000-1000-8000-000000000004";
            boolean actual = sut.existBlueprintBySiteId(siteId);
            assertFalse(actual);
        }
    }

    @Nested
    class existBlueprintTest {
        @Test
        void 図面の存在確認＿idに該当する図面が存在する場合trueが返却されること() {
            String id = "10000000-0000-1000-8000-000000000001";
            boolean actual = sut.existBlueprint(id);
            assertTrue(actual);
        }

        @Test
        void 図面の存在確認＿idに該当する図面が存在しない場合falseが返却されること() {
            String id = "10000000-0000-1000-8000-000000000004";
            boolean actual = sut.existBlueprint(id);
            assertFalse(actual);
        }
    }

    @Test
    void 図面の全件取得＿現場idに該当する図面の全件が返却されること() {
        String siteId = "00000000-0000-1000-8000-000000000001";
        List<Blueprint> expected = List.of(new Blueprint("10000000-0000-1000-8000-000000000001", "00000000-0000-1000-8000-000000000001", "平面図 １階"));

        List<Blueprint> actual = sut.selectBySiteId(siteId);

        assertEquals(1, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void 図面の一件取得＿idに該当する図面が返却されること() {
        String id = "10000000-0000-1000-8000-000000000001";
        Blueprint expected = new Blueprint("10000000-0000-1000-8000-000000000001", "00000000-0000-1000-8000-000000000001", "平面図 １階");

        Blueprint actual = sut.select(id);

        assertEquals(expected, actual);
    }

    @Test
    void 図面の追加＿図面が追加されること() {
        String siteId = "00000000-0000-1000-8000-000000000001";
        Blueprint newBlueprint = new Blueprint("10000000-0000-1000-8000-000000000004", siteId, "平面図 2階");
        blueprintList.add(newBlueprint);
        List<Blueprint> expected = List.of(
                new Blueprint("10000000-0000-1000-8000-000000000001", "00000000-0000-1000-8000-000000000001", "平面図 １階"),
                new Blueprint("10000000-0000-1000-8000-000000000004", "00000000-0000-1000-8000-000000000001", "平面図 2階")
        );

        sut.add(newBlueprint);
        List<Blueprint> actual = sut.selectBySiteId(siteId);

        assertEquals(2, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void 図面の削除＿idに該当する図面が削除されること() {
        String id = "10000000-0000-1000-8000-000000000001";
        sut.delete(id);

        boolean actual = sut.existBlueprint(id);

        assertFalse(actual);
    }
}
