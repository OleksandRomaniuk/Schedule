package com.schedule.proj.security.jwt;


import com.schedule.proj.exсeption.InvalidTokenRequestException;
import com.schedule.proj.model.User;
import com.schedule.proj.repository.UserRepository;
import com.schedule.proj.security.jwt.cache.TokenCache;
import com.schedule.proj.security.jwt.cache.event.OnUserLogoutSuccessEvent;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;


@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Autowired
    private TokenCache tokenCache;

    @Autowired
    UserRepository userRepository;

    public JwtProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    static Key jwtSecret = MacProvider.generateKey();
    private String header="Authorization";

    public String generateAuthToken(Authentication authentication) {

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expireDate = new Date(now.getTime()+604800000);

        User user = userRepository.findUserByEmail(customUserDetails.getUsername());

        String id = Integer.toString(Math.toIntExact(user.getId()));
        Claims claims = Jwts.claims().setSubject(customUserDetails.getUsername());
        claims.put("role",customUserDetails.getAuthorities());
        claims.put("id",id);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }


    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            validateTokenIsNotForALoggedOutDevice(authToken);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {

            return false;
        }

    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "Authorization");

        return cookie == null ? request.getHeader(header) : cookie.getValue();
    }

    public Date getExpireDateFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration();
    }

    private void validateTokenIsNotForALoggedOutDevice(String authToken) {
        OnUserLogoutSuccessEvent previouslyLoggedOutEvent = tokenCache.getLogoutEventForToken(authToken);
        if (previouslyLoggedOutEvent != null) {
                String userEmail = previouslyLoggedOutEvent.getEmail();
                Date logoutEventDate = previouslyLoggedOutEvent.getTime();
                String errorMessage = String.format("Token corresponds to an already logged out user [%s] at [%s]. Please login again", userEmail, logoutEventDate);
                throw new InvalidTokenRequestException("JWT", authToken, errorMessage);
        }
    }

}