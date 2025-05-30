package Portfolio.BlueprintsManagement.infrastructure.db.mapper;

import Portfolio.BlueprintsManagement.domain.model.blueprint.Blueprint;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlueprintMapper {

    List<Blueprint> selectBySiteId(String siteId);

    Blueprint select(String id);

    void add(Blueprint blueprint);
}
