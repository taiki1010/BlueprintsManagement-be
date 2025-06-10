package com.portfolio.BlueprintsManagement.infrastructure.repository;

import com.portfolio.BlueprintsManagement.domain.model.site.Site;
import com.portfolio.BlueprintsManagement.infrastructure.db.mapper.SiteMapper;
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
class SiteRepositoryTest {

    @Autowired
    private SiteRepository sut;

    @Mock
    private SiteMapper siteMapper;

    private String id;
    private Site site;

    @BeforeEach
    void before() {
        sut = new SiteRepository(siteMapper);
        createSampleData();
    }

    private void createSampleData() {
        id = "00000000-0000-1000-8000-000000000001";
        site = new Site(id, "佐藤邸", "東京都表参道", "");
    }

    @Test
    void 複数現場の存在確認＿マッパーが実行され真理値が返却されること() {
        when(siteMapper.existSites()).thenReturn(true);

        boolean actual = sut.existSites();

        verify(siteMapper, times(1)).existSites();
        assertTrue(actual);
    }

    @Test
    void 一件現場の存在確認＿マッパーが実行され真理値が返却されること() {
        when(siteMapper.existSite(id)).thenReturn(true);

        boolean actual = sut.existSite(id);

        verify(siteMapper, times(1)).existSite(id);
        assertTrue(actual);
    }

    @Test
    void 現場の全件取得＿マッパーが実行され全件の現場が返却されること() {
        List<Site> siteList = List.of(site);
        when(siteMapper.select()).thenReturn(siteList);

        List<Site> actual = sut.getSites();

        verify(siteMapper, times(1)).select();
        assertEquals(siteList, actual);
    }

    @Test
    void 現場の一件取得＿マッパーが実行され一件の現場が返却されること() {
        when(siteMapper.selectById(id)).thenReturn(site);

        Site actual = sut.getSite(id);

        verify(siteMapper, times(1)).selectById(id);
        assertEquals(site, actual);
    }

    @Test
    void 現場の追加＿マッパーが実行されること() {
        sut.addSite(site);

        verify(siteMapper, times(1)).add(site);
    }

    @Test
    void 現場の更新＿マッパーが実行されること() {
        sut.updateSite(site);

        verify(siteMapper, times(1)).update(site);
    }

    @Test
    void 現場の削除＿マッパーが実行されること() {
        sut.deleteSite(id);

        verify(siteMapper, times(1)).delete(id);
    }
}
