package com.login.api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.login.api.security.userDetails.UsarioDetailsImplements;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JWTTokenService {
    private final String secret = "XYZ";
    private final String issuer = "LOGIN-API";
    private final Algorithm algorithm = Algorithm.HMAC256(secret);

    public String GerarToken(UsarioDetailsImplements user){
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

    public String recuperarToken(String token){

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

    public Instant dataDeCriacao(){
        return LocalDateTime.now().toInstant(ZoneOffset.UTC);
    }

    public Instant dataDeExpiracao(){
        return LocalDateTime.now().plusDays(2).toInstant(ZoneOffset.UTC);
    }
}
