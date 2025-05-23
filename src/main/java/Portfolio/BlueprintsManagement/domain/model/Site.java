package Portfolio.BlueprintsManagement.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Site {

    private String id;
    private String name;
    private String address;
    private String remark;
    
}
