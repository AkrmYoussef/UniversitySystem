package com.example.demo.service;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import org.springframework.http.HttpHeaders;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtService {
  static final long EXPIRATIONTIME = 3600000;   
  static final String PREFIX = "Bearer";
  static final Key key = Keys.secretKeyFor (SignatureAlgorithm.    HS256);
  // Generate signed JWT token
  public String getToken(String username) {
    String token = Jwts.builder()
    .setSubject(username)
    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
    .signWith(key)
    .compact();  
    System.out.println(token);
    return token;
  }
  // Get a token from request Authorization header,
  // verify the token, and get username
  public String getAuthUser(HttpServletRequest request) {
    String token = request.getHeader
        (HttpHeaders.AUTHORIZATION);
    if (token != null) {
   
      String user = Jwts.parserBuilder()
      .setSigningKey(key)
      .build()
      .parseClaimsJws(token.replace(PREFIX, ""))
      .getBody()
      .getSubject();
      if (user != null)
        return user;
    }
    return null;
  }
}
