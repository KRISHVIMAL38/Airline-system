package com.zosh.jwt;

import com.zosh.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {
    @Value("${jwt.secretkey}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    private static final Logger logger= LoggerFactory.getLogger(JwtService.class);

    public String extractEmail(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    private <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims=extractAllClaims(jwtToken);

        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();

    }
    public SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(User user) {
        logger.info("Inside generateToken 1");
        return generateToken(new HashMap<>(),user);
    }

    public String generateToken(Map<String,Object> extraClaims, User user) {
        logger.info("Inside generateToken 2");
        String authoritiesStr = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(user.getEmail())
                .claim("authorities", authoritiesStr)
                .claim("email",user.getEmail())
                .claim("userId", user.getId())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+jwtExpiration))
                .signWith(getSignInKey())
                .compact();
    }

    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        logger.info("Inside isTokenValid method");
        final String email=extractEmail(jwtToken);
        return (userDetails.getUsername().equals(email))&& !isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(String jwtToken) {
        logger.info("Inside isTokenExpired method");
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken,Claims::getExpiration);
    }


}
