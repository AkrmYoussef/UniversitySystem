package com.example.demo.service;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

@Component
public class JwtService {
  static final long EXPIRATIONTIME = 3600000; // 1 hour
  static final String PREFIX = "Bearer ";
  static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  // Generate signed JWT token
  public String getToken(String username, List<String> roles) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("roles", roles);

    String token = Jwts.builder()
        .setClaims(claims)
        .setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
        .signWith(key)
        .compact();
    System.out.println(token);
    return token;
  }

  // Get a token from request Authorization header, verify the token, and get username
  public String getAuthUser(HttpServletRequest request) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (token != null) {
      String user = Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(token.replace(PREFIX, ""))
          .getBody()
          .getSubject();
      if (user != null) return user;
    }
    return null;
  }

  // Extract roles from the token
  public List<String> getRolesFromToken(String token) {
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token.replace(PREFIX, ""))
        .getBody();
    return claims.get("roles", List.class);
  }
}
