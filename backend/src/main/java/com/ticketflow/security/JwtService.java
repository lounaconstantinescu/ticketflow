package com.ticketflow.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {

    private final JwtProperties props;
    private final SecretKey key;

    public JwtService(JwtProperties props) {
        this.props = props;
        byte[] bytes = props.secret().getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(bytes.length >= 32 ? bytes : pad(bytes, 32));
    }

    public String issueToken(String username, String role) {
        Instant now = Instant.now();
        Instant exp = now.plus(props.ttlMinutes(), ChronoUnit.MINUTES);

        return Jwts.builder()
                .issuer(props.issuer())
                .subject(username)
                .claim("role", role)
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(key)
                .compact();
    }

    public JwtClaims parseAndValidate(String token) {
        var claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        if (!props.issuer().equals(claims.getIssuer())) {
            throw new IllegalArgumentException("Invalid issuer");
        }
        return new JwtClaims(claims.getSubject(), (String) claims.get("role"));
    }

    private static byte[] pad(byte[] in, int target) {
        byte[] out = new byte[target];
        for (int i = 0; i < out.length; i++) out[i] = in[i % in.length];
        return out;
    }

    public record JwtClaims(String username, String role) {}
}
