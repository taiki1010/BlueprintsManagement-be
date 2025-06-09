package com.portfolio.BlueprintsManagement.application.service;

import com.portfolio.BlueprintsManagement.domain.model.site.Site;
import com.portfolio.BlueprintsManagement.domain.repository.ISiteRepository;
import com.portfolio.BlueprintsManagement.presentation.dto.message.ErrorMessage;
import com.portfolio.BlueprintsManagement.presentation.dto.request.site.SiteRequest;
import com.portfolio.BlueprintsManagement.presentation.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SiteService {

    private final ISiteRepository siteRepository;

    public boolean checkExistSites() {
        return siteRepository.existSites();
    }

    public List<Site> getSites() throws NotFoundException {
        if (!siteRepository.existSites()) throw new NotFoundException(ErrorMessage.NOT_FOUND_SITES.getMessage());
        return siteRepository.getSites();
    }

    public Site getSite(String id) throws NotFoundException {
        if (!siteRepository.existSite(id)) throw new NotFoundException(ErrorMessage.NOT_FOUND_SITE_BY_ID.getMessage());
        return siteRepository.getSite(id);
    }

    @Transactional
    public String addSite(SiteRequest request) {
        Site site = Site.formSite(request);
        siteRepository.addSite(site);
        return site.getId();
    }

    @Transactional
    public void updateSite(SiteRequest request, String id) throws NotFoundException {
        if (!siteRepository.existSite(id)) throw new NotFoundException(ErrorMessage.NOT_FOUND_SITE_BY_ID.getMessage());
        Site site = new Site(id, request.getName(), request.getAddress(), request.getRemark());
        siteRepository.updateSite(site);
    }

    @Transactional
    public void deleteSite(String id) throws NotFoundException {
        if (!siteRepository.existSite(id)) throw new NotFoundException(ErrorMessage.NOT_FOUND_SITE_BY_ID.getMessage());
        siteRepository.deleteSite(id);
    }

}
