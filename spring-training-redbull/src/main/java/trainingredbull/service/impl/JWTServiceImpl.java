package trainingredbull.service.impl;

import java.util.Date;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import trainingredbull.service.JWTService;

@Service
public class JWTServiceImpl implements JWTService {

    private final Algorithm algorithm = Algorithm.HMAC256("th");

    @Override
    public Long getUserIdFromToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        String subject = verifier.verify(token).getSubject();
        return Long.valueOf(subject);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);

            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public String generateToken(Long userId) {
        return JWT.create().withSubject(String.valueOf(userId))
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .sign(algorithm);
    }
}
