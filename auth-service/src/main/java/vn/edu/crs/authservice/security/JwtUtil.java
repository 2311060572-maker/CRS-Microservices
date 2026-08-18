package vn.edu.crs.authservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String username, String role) {
        // Tự động bỏ chữ ROLE_ nếu có
        String cleanRole = role != null && role.startsWith("ROLE_") ? role.substring(5) : role;

        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 86400000); // 1 ngày

        return Jwts.builder()
                .subject(username)
                .claim("role", cleanRole)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key)
                .compact();
    }
}