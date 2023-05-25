package trainingredbull.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;

    private Long totalPrice;

    private String description;

    private LocalDateTime createdAt;

    private List<OrderDetailResponse> orderDetails;

    private OrderUserResponse user;
}
