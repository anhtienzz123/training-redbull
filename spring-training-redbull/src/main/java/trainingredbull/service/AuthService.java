package trainingredbull.service;

import trainingredbull.dto.LoginRequest;
import trainingredbull.dto.LoginResponse;
import trainingredbull.dto.RegisterAccountRequest;

public interface AuthService {

    LoginResponse login(LoginRequest loginRequest);

    void registerAccount(RegisterAccountRequest registerAccountRequest);
}
