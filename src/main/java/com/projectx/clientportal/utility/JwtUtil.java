package com.projectx.clientportal.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("jwtUtil")
public class JwtUtil {
    public static final String SECRET = "Project-X";
    public static final Algorithm algorithm = Algorithm.HMAC256(SECRET);
    public static final JWTVerifier jwtVerifier = JWT.require(algorithm).build();
    // 1000 milliseconds (1 second), 60 seconds (1 minute), 60 minutes (1 hour)
    public static final Integer time = 1000 * 60 * 60;

    public String generateToken(Integer userId) {
        try {
            return JWT.create().withClaim("userId", userId)
                    .withExpiresAt(new Date(System.currentTimeMillis() + time))
                    .sign(algorithm);
        } catch(JWTCreationException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public DecodedJWT verify(String token) {
        if(token == null) {
            return null;
        } try {
            return jwtVerifier.verify(token);
        } catch(JWTVerificationException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
