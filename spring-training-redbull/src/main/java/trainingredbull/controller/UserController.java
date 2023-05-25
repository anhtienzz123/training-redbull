package trainingredbull.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import trainingredbull.dto.CreateOrderRequest;
import trainingredbull.dto.UserProfileDTO;
import trainingredbull.service.OrderService;
import trainingredbull.service.UserService;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final OrderService orderService;

    @GetMapping("/profile")
    public UserProfileDTO getUserProfile() {
        return userService.getUserProfile();
    }

    @PostMapping("/order")
    public void createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        orderService.createOrder(createOrderRequest);
    }
}
