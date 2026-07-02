package com.orderlist.api.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.orderlist.api.model.entities.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class TokenService {

    @Value("${jwt.public.key}")
    private RSAPublicKey publicKey;

    @Value("${jwt.private.key}")
    private RSAPrivateKey privateKey;

    @Value("${jwt.issuer:order-list}")
    private String issuer;

    private Algorithm algorithm;

    @PostConstruct
    private void init(){
        this.algorithm = Algorithm.RSA256(publicKey, privateKey);
    }

    public String generateToken(User user){
        try{
            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(user.getId().toString())
                    .withJWTId(UUID.randomUUID().toString())
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);

        } catch(JWTCreationException e){
            throw new RuntimeException("Error creating JWT", e);
        }
    }

    public String validateToken(String token){
        DecodedJWT decoded = JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token);
        return decoded.getSubject();
    }

    private Instant genExpirationDate(){
        return Instant.now().plus(2, ChronoUnit.HOURS);
    }

    public long getExpiresInSeconds(){
        return 7200;
    }
}
