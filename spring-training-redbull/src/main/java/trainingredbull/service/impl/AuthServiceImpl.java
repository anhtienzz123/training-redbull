package trainingredbull.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import trainingredbull.dto.LoginRequest;
import trainingredbull.dto.LoginResponse;
import trainingredbull.dto.RegisterAccountRequest;
import trainingredbull.entity.RoleType;
import trainingredbull.entity.User;
import trainingredbull.exception.RedbullException;
import trainingredbull.repository.UserRepository;
import trainingredbull.service.AuthService;
import trainingredbull.service.JWTService;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final JWTService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> {
            throw new RedbullException(HttpStatus.NOT_FOUND);
        });

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RedbullException(HttpStatus.BAD_REQUEST);
        }

        String accessToken = jwtService.generateToken(user.getId());

        return new LoginResponse(accessToken);
    }

    @Override
    public void registerAccount(RegisterAccountRequest registerAccountRequest) {

        boolean existsEmail = userRepository.existsByEmail(registerAccountRequest.getEmail());
        if (existsEmail) {
            throw new RedbullException(HttpStatus.NOT_FOUND);
        }

        String hashPassword = passwordEncoder.encode(registerAccountRequest.getPassword());
        
        User user = modelMapper.map(registerAccountRequest, User.class);
        user.setPassword(hashPassword);
        user.setRoleType(RoleType.ROLE_USER);
        
        userRepository.save(user);
    }

}
