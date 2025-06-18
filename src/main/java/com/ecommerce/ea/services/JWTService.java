package com.ecommerce.ea.services;

import com.ecommerce.ea.entities.UserAcc;
import com.ecommerce.ea.interfaces.IUserAcc;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    @Autowired
    private IUserAcc _userAcc;

    private String secretKey = "";

    public JWTService(){
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        }
        catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>(); //Data Structure where the data is stored
        UserAcc user = GetUserData(userName); // Invokes the method to retrieve the user info
        claims.put("name",user.getName());
        claims.put("firstName",user.getUserLastName1());
        claims.put("secondName",user.getUserLastName2());
        claims.put("role",user.getRole());
        claims.put("email",user.getEmail());
        claims.put("Id",user.getUserId());

        //Configuring the start and expiration date from the token
        long expirationTime = 1000L * 60 * 60 * 24 * 10; // 10 días
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + expirationTime);


        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userName)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .and()
                .signWith(getKey())
                .compact();
    }

    //Gets the User info to use it as a claim
    private UserAcc GetUserData(String userName){
        UserAcc user = _userAcc.findByUserName(userName);
        System.out.println(user);
        return user;
    }
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
