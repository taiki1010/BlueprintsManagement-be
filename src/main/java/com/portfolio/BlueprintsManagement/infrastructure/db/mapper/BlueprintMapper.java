package com.portfolio.BlueprintsManagement.infrastructure.db.mapper;

import com.portfolio.BlueprintsManagement.domain.model.blueprint.Blueprint;
import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.UpdateBlueprintRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlueprintMapper {

    List<Blueprint> selectBySiteId(String siteId);

    Blueprint select(String id);

    void add(Blueprint blueprint);

    void update(UpdateBlueprintRequest request);

    void delete(String id);
}
