package com.portfolio.BlueprintsManagement.domain.repository;

import com.portfolio.BlueprintsManagement.domain.model.site.Site;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISiteRepository {

    boolean existSites();

    boolean existSite(String id);

    List<Site> getSites();

    Site getSite(String id);

    void addSite(Site site);

    void updateSite(Site site);

    void deleteSite(String id);
}
