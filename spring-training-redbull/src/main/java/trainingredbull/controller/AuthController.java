package trainingredbull.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import trainingredbull.dto.LoginRequest;
import trainingredbull.dto.LoginResponse;
import trainingredbull.dto.RegisterAccountRequest;
import trainingredbull.service.AuthService;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterAccountRequest registerAccountRequest) {
        authService.registerAccount(registerAccountRequest);
    }
}
