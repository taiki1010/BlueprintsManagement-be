package Portfolio.BlueprintsManagement.infrastructure.db.mapper;

import Portfolio.BlueprintsManagement.domain.model.Site;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SiteMapper {

    boolean existSites();

    boolean existSite(String id);

    List<Site> select();

    void add(Site site);

    void update(Site site);

    void delete(String id);
}
