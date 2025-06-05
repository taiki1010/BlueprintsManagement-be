package com.portfolio.BlueprintsManagement.infrastructure.db.mapper;

import com.portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArchitecturalDrawingMapper {

    List<ArchitecturalDrawing> selectByBlueprintId(String blueprintId);

    void add(ArchitecturalDrawing architecturalDrawing);

    boolean existArchitecturalDrawingByBlueprintId(String blueprintId);

    void delete(String id);
}
