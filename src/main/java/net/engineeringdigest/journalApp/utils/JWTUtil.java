package net.engineeringdigest.journalApp.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {  // frequently used methods

    private String SECRET_KEY = "2xlz3trVQExANSJDCN412CSDxk8Bpq7Z";

    private SecretKey getSignInKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }


    public String generateToken(String username){
        Map<String,Object> claims = new HashMap<>(); // pass the content of the body in payload
        return createToken(claims,username);
    }

    public String createToken(Map<String,Object> claims, String subject){
        return Jwts.builder()
                .header().empty().add("typ","JWT") // header of token with type JWT
                .and()
                .claims() // data to be sent
                .subject(subject) // username
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000*60*60)) // 60 minutes expiration
                .signWith(getSignInKey())
                .compact();
    }

    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();  // subject is username so we extract the subject of toekn
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Date extractExpiration(String token){  // get expiration of all token
        return extractAllClaims(token).getExpiration();
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token ){
        return !isTokenExpired(token);  // if username thats provided matches the extracted username from JWT token and is not expired
    }




}
