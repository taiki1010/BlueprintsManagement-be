package Portfolio.BlueprintsManagement.domain.model.blueprint;

import Portfolio.BlueprintsManagement.presentation.dto.request.blueprint.AddBlueprintRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Blueprint {

    private String id;
    private String siteId;
    private String name;

    public static Blueprint formBlueprint(AddBlueprintRequest request) {
        return new Blueprint(
                UUID.randomUUID().toString(),
                request.getSiteId(),
                request.getName()
        );
    }
}
