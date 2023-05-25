package trainingredbull.service;

public interface JWTService {

    Long getUserIdFromToken(String token);

    boolean validateToken(String token);

    String generateToken(Long userId);
}
