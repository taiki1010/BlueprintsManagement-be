package Portfolio.BlueprintsManagement.application.service;

import Portfolio.BlueprintsManagement.domain.repository.ISiteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SiteServiceTest {

    private SiteService sut;

    @Mock
    private ISiteRepository siteRepository;

    @BeforeEach
    public void before() {
        sut = new SiteService(siteRepository);
    }

    @Test
    void test() {

    }

}
