package com.longld.feedback_system.Util;

import com.longld.feedback_system.Entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    private String secret= "LydFNDsA9oZzjwgu0E+8okuKC1xvKynBHjVCMQaueObW2K8EeNQpHSdHiEuZWNyt";

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 1000*60*60*10)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }
    private Key getPrivateKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
