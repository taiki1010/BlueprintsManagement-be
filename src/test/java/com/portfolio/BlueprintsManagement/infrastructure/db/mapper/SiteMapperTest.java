package com.portfolio.BlueprintsManagement.infrastructure.db.mapper;

import com.portfolio.BlueprintsManagement.domain.model.site.Site;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
class SiteMapperTest {

    @Autowired
    private SiteMapper sut;

    // FIXME テーブルが存在しないというエラーが発生してします
    @Test
    void 現場の全件検索＿全件の現場が返却されること() {
        List<Site> actual = sut.select();
        assertEquals(3, actual.size());

    }

}
