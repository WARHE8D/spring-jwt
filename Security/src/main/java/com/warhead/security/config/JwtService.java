package com.warhead.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JwtService {
    private static final String SIGNING_KEY = "5558fbbba28bc468507aa45cbf2d74d286cf043ff4ccee9f531b5d8bc5586052";
    public String getUserName(String jwt) {
    return null;
    }
    public Claims extractAllClaims(String jwt){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJwt(jwt)
                .getBody();
    }

    private Key getSigningKey() {
    byte[] keyByte = Decoders.BASE64.decode(SIGNING_KEY);
    return Keys.hmacShaKeyFor(keyByte);
    }

}
