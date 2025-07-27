package com.springbootcode.store.services;

import com.springbootcode.store.config.JwtConfig;
import com.springbootcode.store.entities.Role;
import com.springbootcode.store.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
@Getter
@Setter
public class JwtService {
    private JwtConfig jwtConfig;
    public Jwt generateAccessToken(User user) {
        return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }

    public Jwt generateRefreshToken(User user) {
        return generateToken(user, jwtConfig.getRefreshTokenExpiration());
    }

    private Jwt generateToken(User user, long tokenExpiration) {
        var claims = Jwts.claims()
                .subject(user.getId().toString())
                .add("email", user.getEmail())
                .add("name", user.getName())
                .add("role", user.getRole())
                .setIssuedAt(new Date())
                 .setExpiration(new Date(System.currentTimeMillis() + 1000*tokenExpiration))
                .build();

        return new Jwt(claims,jwtConfig.getSecretKey());

    }

    public Jwt parseToken(String token) {
        try{
            var claims = getClaims(token);
            return new Jwt(claims,jwtConfig.getSecretKey());
        }catch (JwtException e){
            return null;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                 .parseClaimsJws(token)
                .getPayload();
    }



}
