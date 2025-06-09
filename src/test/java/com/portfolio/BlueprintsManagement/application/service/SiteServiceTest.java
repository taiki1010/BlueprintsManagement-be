package com.portfolio.BlueprintsManagement.application.service;

import com.portfolio.BlueprintsManagement.domain.model.site.Site;
import com.portfolio.BlueprintsManagement.domain.repository.ISiteRepository;
import com.portfolio.BlueprintsManagement.presentation.dto.request.site.SiteRequest;
import com.portfolio.BlueprintsManagement.presentation.exception.customException.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SiteServiceTest {

    private SiteService sut;

    @Mock
    private ISiteRepository siteRepository;

    private String id;
    private Site site;
    private SiteRequest siteRequest;

    @BeforeEach
    public void before() {
        sut = new SiteService(siteRepository);
        createInitialSampleData();
    }

    public void createInitialSampleData() {
        id = "00000000-0000-1000-8000-000000000001";
        site = new Site(id, "佐藤邸", "東京都表参道", "");
        siteRequest = new SiteRequest("佐藤邸", "東京都表参道", "");
    }

    @Nested
    class getSitesTest {
        @Test
        void 現場情報の全件取得＿リポジトリが実行されリスト返却されること() throws NotFoundException {
            when(siteRepository.existSites()).thenReturn(true);
            when(sut.getSites()).thenReturn(List.of(site));
            List<Site> expected = List.of(site);

            List<Site> actual = sut.getSites();

            verify(siteRepository, times(1)).getSites();
            assertEquals(expected, actual);
        }

        @Test
        void 現場情報の全件取得＿現場が存在しない場合例外処理が呼ばれること() throws NotFoundException {
            when(siteRepository.existSites()).thenReturn(false);
            String expected = "現場が追加されていません";

            NotFoundException result = assertThrows(NotFoundException.class, () -> sut.getSites());
            String actual = result.getMessage();

            assertEquals(expected, actual);
        }
    }

    @Nested
    class getSiteTest {
        @Test
        void 現場情報の一件検索＿リポジトリが実行され現場一件分のデータが返却されること() throws NotFoundException {
            when(siteRepository.existSite(id)).thenReturn(true);
            when(siteRepository.getSite(id)).thenReturn(site);
            Site expected = site;

            Site actual = sut.getSite(id);

            verify(siteRepository, times(1)).getSite(id);
            assertEquals(expected, actual);
        }

        @Test
        void 現場情報の一件検索＿idに該当する現場が存在しない場合例外処理が呼ばれること() throws NotFoundException {
            when(siteRepository.existSite(id)).thenReturn(false);
            String expected = "idに該当する現場が存在しません";

            NotFoundException result = assertThrows(NotFoundException.class, () -> sut.getSite(id));
            String actual = result.getMessage();

            assertEquals(expected, actual);
        }
    }

    @Test
    void 現場情報の登録＿リポジトリが実行されuuidが返却されること() {
        try (MockedStatic<Site> mock = Mockito.mockStatic(Site.class)) {
            String expected = id;
            mock.when(() -> Site.formSite(siteRequest)).thenReturn(site);

            String actual = sut.addSite(siteRequest);

            verify(siteRepository, times(1)).addSite(site);
            assertEquals(expected, actual);
        }
    }

    @Nested
    class updateSiteTest {
        @Test
        void 現場情報の更新＿リポジトリが実行されること() throws NotFoundException {
            when(siteRepository.existSite(id)).thenReturn(true);

            sut.updateSite(siteRequest, id);

            verify(siteRepository, times(1)).updateSite(site);
        }

        @Test
        void 現場情報の更新＿idに該当する現場が存在しない場合例外が返却処理が呼ばれること() throws NotFoundException {
            when(siteRepository.existSite(id)).thenReturn(false);
            String expected = "idに該当する現場が存在しません";

            NotFoundException result = assertThrows(NotFoundException.class, () -> sut.updateSite(siteRequest, id));
            String actual = result.getMessage();

            assertEquals(expected, actual);
        }
    }

    @Nested
    class deleteSite {
        @Test
        void 現場情報の削除＿リポジトリが実行されること() throws NotFoundException {
            when(siteRepository.existSite(id)).thenReturn(true);

            sut.deleteSite(id);

            verify(siteRepository, times(1)).deleteSite(id);
        }

        @Test
        void 現場情報の削除＿idに該当する現場が存在しない場合例外が返却処理が呼ばれること() {
            when(siteRepository.existSite(id)).thenReturn(false);
            String expected = "idに該当する現場が存在しません";

            NotFoundException result = assertThrows(NotFoundException.class, () -> sut.deleteSite(id));
            String actual = result.getMessage();

            assertEquals(expected, actual);
        }
    }
}
