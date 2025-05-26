package Portfolio.BlueprintsManagement.infrastructure.repository;

import Portfolio.BlueprintsManagement.domain.model.site.SelectSiteReturnVal;
import Portfolio.BlueprintsManagement.domain.model.site.Site;
import Portfolio.BlueprintsManagement.domain.repository.ISiteRepository;
import Portfolio.BlueprintsManagement.infrastructure.db.mapper.SiteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SiteRepository implements ISiteRepository {

    private final SiteMapper siteMapper;

    public boolean existSites() {
        return siteMapper.existSites();
    }

    public boolean existSite(String id) {
        return siteMapper.existSite(id);
    }

    public List<SelectSiteReturnVal> getSites() {
        return siteMapper.select();
    }

    public Site getSite(String id) {
        return siteMapper.selectById(id);
    }

    public void addSite(Site site) {
        siteMapper.add(site);
    }

    public void updateSite(Site site) {
        siteMapper.update(site);
    }

    public void deleteSite(String id) {
        siteMapper.delete(id);
    }
}
