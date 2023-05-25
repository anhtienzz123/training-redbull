package trainingredbull.dto;

import lombok.Data;
import trainingredbull.entity.RoleType;

@Data
public class UserDTO {

    private Long id;

    private String name;
    
    private String email;
    
    private RoleType roleType;
}
