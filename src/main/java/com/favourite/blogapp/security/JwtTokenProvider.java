package com.favourite.blogapp.security;

import com.favourite.blogapp.exception.ApiException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${api.jwt-secret}")
    private String jwtSecret;
    @Value("${api-jwt-expiration-milleseconds}")
    private long expirationDate;

    // method to generate token
    public String generateToken(Authentication authentication){
        String userName =  authentication.getName();

        Date newDate = new Date();

        Date expirationTime  = new Date(newDate.getTime() + expirationDate);

        String token = Jwts.builder()
                .subject(userName)
                .issuedAt(new Date())
                .expiration(expirationTime)
                .signWith(key())
                .compact();
        return token;
    }

    // get userName form the token
    public String getUserName(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    // Validate Jwt token

    public boolean validateToken (String token){
        try{
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parse(token);
            return true;
        }catch (MalformedJwtException e){
            throw new ApiException(HttpStatus.BAD_REQUEST,"invalid Jwt");
        }catch(ExpiredJwtException e){
            throw new ApiException(HttpStatus.BAD_REQUEST, "token has expired");
        }catch(UnsupportedJwtException e){
            throw new ApiException(HttpStatus.BAD_REQUEST, "token not supported");
        }catch (IllegalArgumentException e){
            throw new ApiException(HttpStatus.BAD_REQUEST, "jwt string is null or empty");
        }
    }
    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}
