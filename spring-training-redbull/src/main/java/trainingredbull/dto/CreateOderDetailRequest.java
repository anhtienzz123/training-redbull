package trainingredbull.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOderDetailRequest {

    private Long productId;
    
    private Integer quantity;
}
