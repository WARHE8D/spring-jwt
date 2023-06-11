package com.warhead.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SIGNING_KEY = "5558fbbba28bc468507aa45cbf2d74d286cf043ff4ccee9f531b5d8bc5586052";
    public String getUsername(String jwt) {
    return extractClaim(jwt,Claims::getSubject);//subject will be the username
    }
    public Claims extractAllClaims(String jwt){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwt)//this is not parseClaimsJwt but parseClaimsJws!!!
                .getBody();
    }

    private Key getSigningKey() {
    byte[] keyByte = Decoders.BASE64.decode(SIGNING_KEY);
    return Keys.hmacShaKeyFor(keyByte);
    }

    //note this method is for extracting a single claim like userName
    public <T> T extractClaim(String jwt, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    //note: here the extractClaims is a map that stores multiple claims
    public String generateToken(Map<String,Object> extractClaims, UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))//24 hours + 1000 ms
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //to generate token without using claim hashmap
    public String generateToken(UserDetails ud){
        return generateToken(new HashMap<>(),ud);
    }

    public boolean isTokenValid(String jwt,UserDetails ud){
        final String userName = getUsername(jwt);
        return (userName.equals(ud.getUsername())) && !isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String jwt) {
        return getTokenExpiry(jwt).before(new Date());
    }

    private Date getTokenExpiry(String jwt) {
        return extractClaim(jwt,Claims::getExpiration);
    }
}
