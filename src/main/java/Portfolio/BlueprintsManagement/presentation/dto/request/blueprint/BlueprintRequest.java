package Portfolio.BlueprintsManagement.presentation.dto.request.blueprint;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class BlueprintRequest {

    private String siteId;
    private String name;
    private String createdAt;
    private MultipartFile blueprint;

}
