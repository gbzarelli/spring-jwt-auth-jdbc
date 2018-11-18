package br.com.helpdev.springjwtauthjdbc.security.jwt;

import io.jsonwebtoken.Claims;
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
import java.util.stream.Collectors;

/**
 * Classe responsável em gerar e extrair do Servlet o token
 * <p>
 * Class responsible for generating and extracting from the Servlet the token
 */
class JWTAuth {
    // EXPIRATION_TIME = 1 dias
    private static final long EXPIRATION_TIME = TimeUnit.DAYS.toMillis(1);
    private static final String SECRET = "MySeCrEt";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";
    private static final String CLAIMS_PRIVILEGES = "privileges";

    /**
     * Método que adiciona uma nova autenticação, gerando o token e adicionando no header do HttpServletResponse
     *
     * @param response HttpServletResponse
     * @param auth     Authentication
     */
    static void addAuthentication(HttpServletResponse response, Authentication auth) {
        Claims claims = Jwts.claims().setSubject(auth.getName());
        claims.put(CLAIMS_PRIVILEGES, auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        String JWT = Jwts.builder()
                .setSubject(auth.getName())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setClaims(claims)
                .compact();

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    /**
     * Método que extrai o token do Header e converte para o objeto Authentication;
     *
     * @param request HttpServletRequest
     * @return Authentication
     */
    static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            Claims body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            String user = body.getSubject();

            List<GrantedAuthority> objects = new ArrayList<>();
            if (body.containsKey(CLAIMS_PRIVILEGES)) {
                List<String> privileges = (List<String>) body.get(CLAIMS_PRIVILEGES);
                for (String privilege : privileges) {
                    objects.add(() -> privilege);
                }
            }
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, objects);
            }
        }
        return null;
    }
}
