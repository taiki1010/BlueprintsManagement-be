package Portfolio.BlueprintsManagement.application.service;

import Portfolio.BlueprintsManagement.domain.model.Site;
import Portfolio.BlueprintsManagement.domain.repository.ISiteRepository;
import Portfolio.BlueprintsManagement.presentation.dto.message.ErrorMessage;
import Portfolio.BlueprintsManagement.presentation.dto.request.site.SiteRequest;
import Portfolio.BlueprintsManagement.presentation.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SiteService {

    private final ISiteRepository siteRepository;

    public List<Site> getSites() throws NotFoundException {
        if (!siteRepository.existSites()) throw new NotFoundException(ErrorMessage.NOT_FOUND_SITES.getMessage());
        return siteRepository.getSites();
    }

    @Transactional
    public void addSite(SiteRequest request) {
        Site site = Site.formSite(request);
        siteRepository.addSite(site);
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
