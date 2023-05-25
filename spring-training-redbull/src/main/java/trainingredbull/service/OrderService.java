package trainingredbull.service;

import java.util.List;
import trainingredbull.dto.CreateOrderRequest;
import trainingredbull.dto.OrderResponse;

public interface OrderService {

    List<OrderResponse> getOrderList();
    
    void createOrder(CreateOrderRequest createOrderRequest);
}
