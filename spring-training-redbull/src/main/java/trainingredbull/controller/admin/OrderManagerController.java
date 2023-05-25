package trainingredbull.controller.admin;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import trainingredbull.dto.OrderResponse;
import trainingredbull.service.OrderService;

@RestController
@RequestMapping("/admin/orders")
@AllArgsConstructor
public class OrderManagerController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderResponse> getOrderList() {
        return orderService.getOrderList();
    }
}
