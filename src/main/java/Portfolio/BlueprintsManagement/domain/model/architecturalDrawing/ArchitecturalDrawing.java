package Portfolio.BlueprintsManagement.domain.model.architecturalDrawing;

import Portfolio.BlueprintsManagement.presentation.dto.request.blueprint.BlueprintRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ArchitecturalDrawing {

    private String id;
    private String BlueprintId;
    private String createdAt;
    private String filePath;

    public static ArchitecturalDrawing formArchitecturalDrawing(BlueprintRequest request, String id, String filePath) {
        return new ArchitecturalDrawing(
                UUID.randomUUID().toString(),
                id,
                request.getCreatedAt(),
                filePath
        );
    }
}
