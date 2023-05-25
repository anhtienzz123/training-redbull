package trainingredbull.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import trainingredbull.dto.CreateOrderRequest;
import trainingredbull.dto.OrderResponse;
import trainingredbull.entity.Order;
import trainingredbull.entity.OrderDetail;
import trainingredbull.entity.Product;
import trainingredbull.entity.User;
import trainingredbull.exception.RedbullException;
import trainingredbull.repository.OrderRepository;
import trainingredbull.repository.ProductRepository;
import trainingredbull.service.OrderService;
import trainingredbull.util.CommonUtils;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<OrderResponse> getOrderList() {
        return orderRepository.findAll().stream().map(o -> modelMapper.map(o, OrderResponse.class))
                .toList();
    }

    @Override
    public void createOrder(CreateOrderRequest createOrderRequest) {
        Long userId = CommonUtils.getUserId();

        Order order = Order.builder().createdAt(LocalDateTime.now())
                .description(createOrderRequest.getDescription()).user(new User(userId)).build();

        // handle order detail
        List<OrderDetail> orderDetails = createOrderRequest.getOrderDetails().stream().map(od -> {

            OrderDetail result = new OrderDetail();
            result.setOrder(order);

            Product product = productRepository.findById(od.getProductId())
                    .orElseThrow(() -> new RedbullException(HttpStatus.NOT_FOUND));
            result.setProduct(product);
            result.setPrice(product.getPrice());
            result.setQuantity(od.getQuantity());
            
            return result;
        }).toList();
        
        order.setOrderDetails(orderDetails);
        
        // total price
        Long totalPrice = orderDetails.stream().mapToLong(od -> od.getPrice() * od.getQuantity()).sum();
        order.setTotalPrice(totalPrice);

        orderRepository.save(order);
    }
}
