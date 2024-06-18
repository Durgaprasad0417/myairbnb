package com.airbnb.service;

import com.airbnb.entity.PropertyUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    private final static  String USER_NAME="username";

    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiry.duration}")
    private int expiryTime;

    private Algorithm algorithm;

    @PostConstruct
    public void postConstructor(){
         algorithm = algorithm.HMAC256(algorithmKey);
    }
    public String generateToken(PropertyUser propertyUser){
       return JWT.create().
                withClaim(USER_NAME,propertyUser.getUsername()).
                withExpiresAt(new Date(System.currentTimeMillis()+expiryTime)).
                withIssuer(issuer).
                 sign(algorithm);

    }
    //verify the token return username if valid
    public String getUsername(String token){
        //roj With Beatiful Vara(ShortCut)
        DecodedJWT decodeJWT = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return  decodeJWT.getClaim(USER_NAME).asString();
    }

}
