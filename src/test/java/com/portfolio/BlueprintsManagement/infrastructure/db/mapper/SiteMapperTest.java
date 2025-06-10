package com.portfolio.BlueprintsManagement.infrastructure.db.mapper;

import com.portfolio.BlueprintsManagement.domain.model.site.Site;
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
class SiteMapperTest {

    @Autowired
    private SiteMapper sut;

    private List<Site> siteList;

    @BeforeEach
    void before() {
        siteList = createSampleSiteList();
    }

    private List<Site> createSampleSiteList() {
        return new ArrayList<Site>(
                Arrays.asList(
                        new Site("00000000-0000-1000-8000-000000000001", "佐藤邸", "東京都表参道", ""),
                        new Site("00000000-0000-1000-8000-000000000002", "田中邸", "北海道札幌市", ""),
                        new Site("00000000-0000-1000-8000-000000000003", "青森自動車工場", "青森県青森市", "")
                ));
    }

    @Test
    void 現場の存在確認＿現場が存在する場合trueが返却されること() {
        boolean actual = sut.existSites();
        assertTrue(actual);
    }

    @Nested
    class existSiteTest {
        @Test
        void 現場の存在確認＿idに該当する現場存在する場合trueが返却されること() {
            String id = "00000000-0000-1000-8000-000000000001";
            boolean actual = sut.existSite(id);
            assertTrue(actual);
        }

        @Test
        void 現場の存在確認＿idに該当する現場存在しない場合falseが返却されること() {
            String id = "00000000-0000-1000-8000-000000000004";
            boolean actual = sut.existSite(id);
            assertFalse(actual);
        }
    }

    @Test
    void 現場の全件検索＿全件の現場が返却されること() {
        List<Site> expected = siteList;

        List<Site> actual = sut.select();
        assertEquals(3, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void 現場の一件検索＿idに該当する一件の現場が返却されること() {
        String id = "00000000-0000-1000-8000-000000000001";
        Site expected = new Site(id, "佐藤邸", "東京都表参道", "");

        Site actual = sut.selectById(id);
        assertEquals(expected, actual);
    }

    @Test
    void 現場の追加＿現場が追加されること() {
        Site newSite = new Site("00000000-0000-1000-8000-000000000004", "三菱電機工場", "静岡県掛川市", "");
        siteList.add(newSite);
        List<Site> expected = siteList;

        sut.add(newSite);
        List<Site> actual = sut.select();

        assertEquals(4, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void 現場の更新＿現場が更新されること() {
        String id = "00000000-0000-1000-8000-000000000001";
        Site site = new Site(id, "佐藤健邸", "東京都表参道１丁目", "コンビニの近く");

        sut.update(site);
        Site actual = sut.selectById(id);

        assertEquals(site, actual);
    }

    @Test
    void 現場の削除＿現場が削除されること() {
        String id = "00000000-0000-1000-8000-000000000001";
        siteList.removeIf(site -> (site.getId().equals(id)));
        List<Site> expected = siteList;

        sut.delete(id);
        List<Site> actual = sut.select();

        assertEquals(2, actual.size());
        assertEquals(expected, actual);
    }
}
