package Portfolio.BlueprintsManagement.domain.repository;

import Portfolio.BlueprintsManagement.domain.model.site.SelectSiteReturnVal;
import Portfolio.BlueprintsManagement.domain.model.site.Site;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISiteRepository {

    boolean existSites();

    boolean existSite(String id);

    List<SelectSiteReturnVal> getSites();

    Site getSite(String id);

    void addSite(Site site);

    void updateSite(Site site);

    void deleteSite(String id);
}
