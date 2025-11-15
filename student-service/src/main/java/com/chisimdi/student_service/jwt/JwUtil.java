package com.chisimdi.student_service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
@Component
public class JwUtil {
    private static final Logger log = LoggerFactory.getLogger(JwUtil.class);
    private int expirationTime=1000*60*60*24;
    private final static String secretKey="mySecretKeyThatIsAtLeast25CharactersLongForTheAlgorithm";

    public SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
    public String generateToken(String userName, int userId, String roles){
        log.info("Extracting token for user with userName {}", userName);
        HashMap<String,Object> claims=new HashMap<>();
        claims.put("roles",roles);
        claims.put("userId",userId);
        return Jwts.builder().setSubject(userName).addClaims(claims).
                signWith(getSecretKey()).setIssuedAt(new Date()).
                setExpiration(new Date(System.currentTimeMillis()+expirationTime))
                .signWith(getSecretKey(),io.jsonwebtoken.SignatureAlgorithm.HS384).compact();
    }
    public Claims extractAllClaims(String token){
        log.info("Extracting all tokens");
        return Jwts.parserBuilder().
                setSigningKey(getSecretKey()).
                build().parseClaimsJws(token).getBody();
    }
    public Boolean isTokenValid(String token){
        try {
            extractAllClaims(token);
            log.info("token valid");
            return true;
        }
        catch (Exception e) {
            log.info("token Invalid");
            return false;
        }
    }
    public String extractUsername(String token) {
        log.info("extracting username");
        return extractAllClaims(token).getSubject();
    }
    public int extractUserId(String token){
        log.info("extracting userId");
        return extractAllClaims(token).get("userId",Integer.class);
    }
    public String extractRoles(String token){
        log.info("extracting Role");
        return extractAllClaims(token).get("roles",String.class);
    }

}
