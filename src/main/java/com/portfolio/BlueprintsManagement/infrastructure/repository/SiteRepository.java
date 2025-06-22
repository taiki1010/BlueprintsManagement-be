package com.portfolio.BlueprintsManagement.infrastructure.repository;

import com.portfolio.BlueprintsManagement.domain.model.site.Site;
import com.portfolio.BlueprintsManagement.domain.repository.ISiteRepository;
import com.portfolio.BlueprintsManagement.infrastructure.db.mapper.SiteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SiteRepository implements ISiteRepository {

    private final SiteMapper siteMapper;

    /**
     * 現場が存在するか確認します。
     *
     * @return true or false
     */
    public boolean existSites() {
        return siteMapper.existSites();
    }

    /**
     * 現場IDに紐づく現場が存在するか確認します。
     *
     * @param id 現場ID
     * @return true or false
     */
    public boolean existSite(String id) {
        return siteMapper.existSite(id);
    }

    /**
     * 現場一覧を取得します。
     *
     * @return 現場一覧（全件）
     */
    public List<Site> getSites() {
        return siteMapper.select();
    }

    /**
     * 現場IDに紐づく現場を取得します。
     *
     * @param id 現場ID
     * @return 現場IDに紐づく現場一件
     */
    public Site getSite(String id) {
        return siteMapper.selectById(id);
    }

    /**
     * 新規現場を追加します。
     *
     * @param site 現場
     */
    public void addSite(Site site) {
        siteMapper.add(site);
    }

    /**
     * 現場を更新します。
     *
     * @param site 現場
     */
    public void updateSite(Site site) {
        siteMapper.update(site);
    }

    /**
     * 現場IDに紐づく現場を削除します。
     *
     * @param id 現場ID
     */
    public void deleteSite(String id) {
        siteMapper.delete(id);
    }
}
