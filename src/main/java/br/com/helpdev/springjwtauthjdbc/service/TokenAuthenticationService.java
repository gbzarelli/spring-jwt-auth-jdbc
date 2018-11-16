package br.com.helpdev.springjwtauthjdbc.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TokenAuthenticationService {
    // EXPIRATION_TIME = 1 dias
    private static final long EXPIRATION_TIME = TimeUnit.DAYS.toMillis(1);
    private static final String SECRET = "MySecret";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    public static void addAuthentication(HttpServletResponse response, Authentication auth) {
        String JWT = Jwts.builder()
                .setSubject(auth.getName())

                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            // faz parse do token
            String user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            if (user != null) {
                List<GrantedAuthority> objects = new ArrayList<>();
                objects.add(() -> "ROLE_USER");
                return new UsernamePasswordAuthenticationToken(user, null, objects);
            }
        }
        return null;
    }
}
