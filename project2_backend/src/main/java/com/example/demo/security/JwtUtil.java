package main.java.com.example.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    // Secret key for signing tokens (use env variable in production)
    private static final String SECRET = "REPLACE_WITH_SECURE_ENV_SECRET";

    // Token validity: 1 day
    private static final long EXPIRATION_MS = 24 * 60 * 60 * 1000;

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }
}
