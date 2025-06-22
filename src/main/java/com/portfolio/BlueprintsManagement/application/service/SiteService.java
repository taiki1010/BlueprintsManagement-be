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

    /**
     * 現場が存在するか確認します。
     *
     * @return true or false
     */
    public boolean checkExistSites() {
        return siteRepository.existSites();
    }

    /**
     * 現場の一覧を取得します。
     *
     * @return 現場情報一覧（全件）
     * @throws NotFoundException 404エラーメッセージ
     */
    public List<Site> getSites() throws NotFoundException {
        if (!siteRepository.existSites()) throw new NotFoundException(ErrorMessage.NOT_FOUND_SITES.getMessage());
        return siteRepository.getSites();
    }

    /**
     * IDに紐づく現場を取得します。
     *
     * @param id 現場ID
     * @return IDに紐づく現場
     * @throws NotFoundException 404エラーメッセージ
     */
    public Site getSite(String id) throws NotFoundException {
        if (!siteRepository.existSite(id)) throw new NotFoundException(ErrorMessage.NOT_FOUND_SITE_BY_ID.getMessage());
        return siteRepository.getSite(id);
    }

    /**
     * 新規現場を追加します。
     *
     * @param request 現場リクエスト（現場名, 住所, 備考）
     * @return 新規現場情報のID
     */
    @Transactional
    public String addSite(SiteRequest request) {
        Site site = Site.formSite(request);
        siteRepository.addSite(site);
        return site.getId();
    }

    /**
     * 現場IDに紐づく現場を更新します。
     *
     * @param request 現場リクエスト（現場名, 住所, 備考）
     * @param id      現場ID
     * @throws NotFoundException 404エラーメッセージ
     */
    @Transactional
    public void updateSite(SiteRequest request, String id) throws NotFoundException {
        if (!siteRepository.existSite(id)) throw new NotFoundException(ErrorMessage.NOT_FOUND_SITE_BY_ID.getMessage());
        Site site = new Site(id, request.getName(), request.getAddress(), request.getRemark());
        siteRepository.updateSite(site);
    }

    /**
     * 現場IDに紐づく現場を削除します。
     *
     * @param id 現場ID
     * @throws NotFoundException 404エラーメッセージ
     */
    @Transactional
    public void deleteSite(String id) throws NotFoundException {
        if (!siteRepository.existSite(id)) throw new NotFoundException(ErrorMessage.NOT_FOUND_SITE_BY_ID.getMessage());
        siteRepository.deleteSite(id);
    }

}
