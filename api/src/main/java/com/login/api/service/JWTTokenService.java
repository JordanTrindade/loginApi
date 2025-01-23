package com.login.api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.login.api.security.userDetails.UserDetailsImplements;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JWTTokenService {
    private final String secret = "XYZ";
    private final String issuer = "LOGIN-API";
    private final Algorithm algorithm = Algorithm.HMAC256(secret);

    public String GerarToken(UserDetailsImplements user){
        try{
            return JWT.create()
                    .withSubject(user.getUsername())
                    .withIssuer(issuer)
                    .withIssuedAt(dataDeCriacao())
                    .withExpiresAt(dataDeExpiracao())
                    .sign(algorithm);
        }catch (JWTCreationException e){
            return null;
        }
    }

    public String recuperarUsuario(String token){

        try{
            return JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException E){
            return null;
        }

    }

    public Boolean isTokenExpired(String token){
        Instant date = JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token)
                .getExpiresAtAsInstant();

        return !date.isBefore(Instant.now());
    }

    public Boolean isTokenValid(String token, UserDetailsImplements userDetailsImplements){
        String userName = recuperarUsuario(token);
        return userName.equals(userDetailsImplements.getUsername()) && !isTokenExpired(token);
    }

    public Instant dataDeCriacao(){
        return LocalDateTime.now().toInstant(ZoneOffset.UTC);
    }

    public Instant dataDeExpiracao(){
        return LocalDateTime.now().plusDays(2).toInstant(ZoneOffset.UTC);
    }
}
