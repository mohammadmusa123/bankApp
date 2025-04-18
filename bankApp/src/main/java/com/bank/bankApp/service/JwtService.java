package com.bank.bankApp.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private String key="f3ef4f303d10f3dff46420489545aad6e5c3dc2435731b92be9ca975baa6b393";
//    private String key;

    Map<String,Object> claims = new HashMap<>();

    public String generateToken(UserDetails userDetails)
    {
        return Jwts.builder().claims().add(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60*60*60*60))
                .and().signWith(getKey(), SignatureAlgorithm.HS256).compact();
    }

    private SecretKey getKey() {
        byte [] key1 = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(key1);
    }

//    public Key getKey()
//    {
//        KeyGenerator  generator;
//        try {
//            generator=KeyGenerator.getInstance("HmacSHA256");
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//        SecretKey sk =generator.generateKey();
//        key= Base64.getEncoder().encodeToString(sk.getEncoded());
//        byte[] sKey = Base64.getDecoder().decode(key);
//        return Keys.hmacShaKeyFor(sKey);
//    }

    public String extractUsername(String token) {
        Claims claims1 = getClaims(token);
        return claims1.getSubject();
    }

    private Claims getClaims(String token) {
        Claims claims1 = Jwts.parser().verifyWith(getKey())
                .build().parseSignedClaims(token).getPayload();
        return claims1;
    }

    public boolean validateToken(String token) {
        Claims claims1= getClaims(token);
        return claims1.getExpiration().after(Date.from(Instant.now()));
    }
}
