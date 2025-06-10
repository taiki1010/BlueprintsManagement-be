package com.portfolio.BlueprintsManagement.domain.model.site;

import com.portfolio.BlueprintsManagement.presentation.dto.request.site.SiteRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SiteTest {

    @Test
    void リクエストを引数にし実行すると現場が返却されること() {
        String name = "佐藤邸";
        String address = "東京都表参道";
        String remark = "";
        SiteRequest request = new SiteRequest(name, address, remark);

        Site actual = Site.formSite(request);

        assertNotNull(actual.getId());
        assertEquals(name, actual.getName());
        assertEquals(address, actual.getAddress());
        assertEquals(remark, actual.getRemark());
    }
}
